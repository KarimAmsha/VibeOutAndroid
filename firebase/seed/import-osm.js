/**
 * Imports REAL places into Cloud Firestore from OpenStreetMap via the
 * Overpass API (free, no API key, data © OpenStreetMap contributors, ODbL).
 *
 * For every city in cities.json it fetches real cafes, restaurants, dessert
 * spots, libraries, parks and viewpoints around the city centre, maps them to
 * the app's place model (type + mood tags + price level) and upserts them.
 *
 * Usage:
 *   1) Place serviceAccount.json next to this file (same key used by seed.js).
 *   2) npm install
 *   3) npm run import            # all cities
 *      npm run import istanbul baghdad      # only specific city ids
 *
 * Requires Node 18+ (uses the built-in fetch). Re-running is safe: each place
 * has a deterministic id (osm-<type>-<id>) and is upserted, not duplicated.
 *
 * Tunables via env:
 *   RADIUS_M   search radius in metres (default 6000)
 *   PER_CITY   max places kept per city (default 40)
 */
const admin = require("firebase-admin");
const fs = require("fs");
const path = require("path");

const serviceAccountPath = path.join(__dirname, "serviceAccount.json");
if (!fs.existsSync(serviceAccountPath)) {
  console.error(
    "Missing serviceAccount.json. Download it from Firebase Console > " +
      "Project settings > Service accounts, and place it next to this file."
  );
  process.exit(1);
}
admin.initializeApp({ credential: admin.credential.cert(require(serviceAccountPath)) });
const db = admin.firestore();

// Several public Overpass mirrors; tried in order until one answers. The
// main instance rejects requests without a proper User-Agent (HTTP 406),
// so a descriptive UA is mandatory.
const ENDPOINTS = [
  "https://overpass-api.de/api/interpreter",
  "https://overpass.kumi.systems/api/interpreter",
  "https://maps.mail.ru/osm/tools/overpass/api/interpreter",
];
const REQUEST_HEADERS = {
  "User-Agent": "VibeOutTalaa/1.0 (Firestore place importer; contact karim.amsha@gmail.com)",
  Accept: "application/json",
  "Content-Type": "application/x-www-form-urlencoded",
};
const RADIUS_M = Number(process.env.RADIUS_M || 3000);
const PER_CITY = Number(process.env.PER_CITY || 40);
const round = (n) => Math.round(n * 1e6) / 1e6;
const sleep = (ms) => new Promise((r) => setTimeout(r, ms));

// OSM tag -> app place model.
function classify(tags) {
  if (tags.amenity === "cafe") {
    return { type: "CAFE", priceLevel: "MEDIUM", moodTags: ["QUIET", "CAFE", "CALM", "STUDY"] };
  }
  if (tags.amenity === "restaurant") {
    return { type: "RESTAURANT", priceLevel: "MEDIUM", moodTags: ["SOCIAL", "FOOD"] };
  }
  if (tags.amenity === "fast_food") {
    return { type: "RESTAURANT", priceLevel: "LOW", moodTags: ["FOOD", "BUDGET", "SOCIAL"] };
  }
  if (tags.amenity === "ice_cream" || tags.shop === "pastry" || tags.shop === "confectionery") {
    return { type: "DESSERT", priceLevel: "LOW", moodTags: ["FOOD", "SOCIAL"] };
  }
  if (tags.amenity === "library") {
    return { type: "STUDY_PLACE", priceLevel: "LOW", moodTags: ["STUDY", "QUIET"] };
  }
  if (tags.leisure === "park" || tags.leisure === "garden") {
    return { type: "PARK", priceLevel: "LOW", moodTags: ["WALK", "PHOTOGRAPHY", "BUDGET", "NEW_EXPERIENCE"] };
  }
  if (tags.tourism === "viewpoint") {
    return { type: "PHOTOGRAPHY_SPOT", priceLevel: "LOW", moodTags: ["PHOTOGRAPHY", "NEW_EXPERIENCE"] };
  }
  return null;
}

const PHOTO_BY_TYPE = {
  CAFE: "https://images.unsplash.com/photo-1495474472287-4d71bcdd2085",
  RESTAURANT: "https://images.unsplash.com/photo-1517248135467-4c7edcad34c4",
  DESSERT: "https://images.unsplash.com/photo-1488477181946-6428a0291777",
  STUDY_PLACE: "https://images.unsplash.com/photo-1521587760476-6c12a4b040da",
  PARK: "https://images.unsplash.com/photo-1441974231531-c6227db76b6e",
  PHOTOGRAPHY_SPOT: "https://images.unsplash.com/photo-1502602898657-3e91760cbb34",
};

// One selector per request. A union of many heavy selectors over a large
// radius times out in dense cities, so we fetch categories individually.
const CATEGORY_SELECTORS = [
  '["amenity"="cafe"]',
  '["amenity"="restaurant"]',
  '["amenity"="ice_cream"]',
  '["amenity"="library"]',
  '["leisure"="park"]',
  '["tourism"="viewpoint"]',
];

function categoryQuery(city, selector) {
  const a = `(around:${RADIUS_M},${city.latitude},${city.longitude})`;
  return `[out:json][timeout:25];(node${selector}["name"]${a};);out body 30;`;
}

function toPlace(el, city) {
  const tags = el.tags || {};
  const name = tags["name:en"] || tags.name;
  if (!name || el.lat == null || el.lon == null) return null;
  const klass = classify(tags);
  if (!klass) return null;
  const area = tags["addr:suburb"] || tags["addr:district"] || tags["addr:city"] || city.nameEn;
  const street = [tags["addr:street"], tags["addr:housenumber"]].filter(Boolean).join(" ");
  return {
    id: `osm-${el.type}-${el.id}`,
    name,
    description: "",
    cityId: city.id,
    area,
    address: [street, city.nameEn].filter(Boolean).join(", "),
    latitude: round(el.lat),
    longitude: round(el.lon),
    type: klass.type,
    priceLevel: klass.priceLevel,
    moodTags: klass.moodTags,
    amenities: {
      wifi: tags.internet_access === "wlan" || tags.internet_access === "yes",
      outdoorSeating: tags.outdoor_seating === "yes",
    },
    openingHours: tags.opening_hours ? { raw: tags.opening_hours } : {},
    ratingInternal: 4.0,
    safetyNote: null,
    isPartner: false,
    photos: [{ url: PHOTO_BY_TYPE[klass.type], sortOrder: 0 }],
    source: "osm",
  };
}

async function overpass(query) {
  const body = "data=" + encodeURIComponent(query);
  let lastErr;
  for (const url of ENDPOINTS) {
    const host = new URL(url).host;
    try {
      const res = await fetch(url, { method: "POST", headers: REQUEST_HEADERS, body });
      if (!res.ok) {
        lastErr = new Error(`HTTP ${res.status} @ ${host}`);
        if (res.status === 429 || res.status >= 500) await sleep(2000);
        continue;
      }
      const json = await res.json();
      const count = (json.elements || []).length;
      // A busy instance answers fast with empty elements + a remark
      // (timeout / out of memory). Treat that as a miss and try the next mirror.
      if (count === 0 && json.remark) {
        lastErr = new Error(`${json.remark.trim()} @ ${host}`);
        continue;
      }
      return { json, host };
    } catch (e) {
      lastErr = e;
    }
  }
  throw lastErr || new Error("All Overpass endpoints failed");
}

async function fetchCity(city) {
  const seen = new Set();
  const places = [];
  let rawCount = 0;
  let host = "—";
  for (const selector of CATEGORY_SELECTORS) {
    if (places.length >= PER_CITY) break;
    let json;
    try {
      const r = await overpass(categoryQuery(city, selector));
      json = r.json;
      host = r.host;
    } catch (e) {
      await sleep(800); // category failed (timeout/load) — try the next one
      continue;
    }
    const els = json.elements || [];
    rawCount += els.length;
    for (const el of els) {
      const p = toPlace(el, city);
      if (p && !seen.has(p.id)) {
        seen.add(p.id);
        places.push(p);
      }
      if (places.length >= PER_CITY) break;
    }
    await sleep(800); // be gentle between category requests
  }
  return { places, rawCount, host };
}

async function upsert(places) {
  for (let i = 0; i < places.length; i += 400) {
    const batch = db.batch();
    for (const { id, ...data } of places.slice(i, i + 400)) {
      batch.set(db.collection("places").doc(id), data, { merge: true });
    }
    await batch.commit();
  }
}

async function main() {
  const all = JSON.parse(fs.readFileSync(path.join(__dirname, "cities.json"), "utf8"));
  const only = process.argv.slice(2);
  const cities = only.length ? all.filter((c) => only.includes(c.id)) : all;
  let total = 0;
  for (const city of cities) {
    try {
      const { places, rawCount, host } = await fetchCity(city);
      await upsert(places);
      total += places.length;
      console.log(`${city.nameEn}: ${places.length} kept / ${rawCount} found (via ${host}).`);
    } catch (e) {
      console.warn(`${city.nameEn}: skipped (${e.message}).`);
    }
    await sleep(2000); // be gentle with the public Overpass endpoint
  }
  console.log(`Done. Imported ${total} real places across ${cities.length} cities.`);
  process.exit(0);
}

main().catch((e) => {
  console.error(e);
  process.exit(1);
});
