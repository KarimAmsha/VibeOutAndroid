/**
 * Seeds the VibeOut / طلعة catalog (cities + places) into Cloud Firestore.
 *
 * - Cities come from cities.json.
 * - Places come from places.json (curated). For any city that has no curated
 *   place, three clearly-labelled SAMPLE places are generated so the city is
 *   not empty in the app. Replace samples with real venues over time.
 *
 * Usage:
 *   1) Firebase Console > Project settings > Service accounts > Generate new
 *      private key. Save the JSON as serviceAccount.json in this folder
 *      (it is git-ignored).
 *   2) npm install
 *   3) npm run seed
 *
 * Re-running is safe: documents are written with a fixed id (merge), so the
 * catalog is upserted rather than duplicated.
 */
const admin = require("firebase-admin");
const fs = require("fs");
const path = require("path");

const serviceAccountPath = path.join(__dirname, "serviceAccount.json");
if (!fs.existsSync(serviceAccountPath)) {
  console.error(
    "Missing serviceAccount.json. Download it from Firebase Console > " +
      "Project settings > Service accounts, and place it next to seed.js."
  );
  process.exit(1);
}

admin.initializeApp({
  credential: admin.credential.cert(require(serviceAccountPath)),
});

const db = admin.firestore();

function load(file) {
  return JSON.parse(fs.readFileSync(path.join(__dirname, file), "utf8"));
}

const round = (n) => Math.round(n * 1e4) / 1e4;

// Templates used to generate sample places for cities lacking curated data.
const SAMPLE_TEMPLATES = [
  {
    suffix: "coffee",
    label: "Quiet Coffee",
    type: "CAFE",
    priceLevel: "MEDIUM",
    moodTags: ["QUIET", "CAFE", "CALM", "STUDY"],
    dLat: 0.004,
    dLng: 0.004,
    rating: 4.4,
    photo: "https://images.unsplash.com/photo-1495474472287-4d71bcdd2085",
  },
  {
    suffix: "walk",
    label: "City Walk",
    type: "WALKING_AREA",
    priceLevel: "LOW",
    moodTags: ["WALK", "PHOTOGRAPHY", "BUDGET", "NEW_EXPERIENCE"],
    dLat: -0.006,
    dLng: 0.005,
    rating: 4.6,
    photo: "https://images.unsplash.com/photo-1502602898657-3e91760cbb34",
  },
  {
    suffix: "social",
    label: "Social Bistro",
    type: "RESTAURANT",
    priceLevel: "MEDIUM",
    moodTags: ["SOCIAL", "FOOD", "NEW_EXPERIENCE"],
    dLat: 0.005,
    dLng: -0.006,
    rating: 4.3,
    photo: "https://images.unsplash.com/photo-1517248135467-4c7edcad34c4",
  },
];

function samplePlacesFor(city) {
  return SAMPLE_TEMPLATES.map((t) => ({
    id: `${city.id}-${t.suffix}`,
    name: `${t.label} — ${city.nameEn}`,
    description:
      `Sample ${t.type.toLowerCase().replace(/_/g, " ")} in ${city.nameEn}. ` +
      "Placeholder data — replace with a real venue.",
    cityId: city.id,
    area: city.nameEn,
    address: city.nameEn,
    latitude: round(city.latitude + t.dLat),
    longitude: round(city.longitude + t.dLng),
    type: t.type,
    priceLevel: t.priceLevel,
    moodTags: t.moodTags,
    amenities: { wifi: true },
    openingHours: { everyday: "09:00-23:00" },
    ratingInternal: t.rating,
    isPartner: false,
    photos: [{ url: t.photo, sortOrder: 0 }],
  }));
}

async function seedCollection(name, items) {
  // Firestore batches are limited to 500 writes; chunk to stay safe.
  for (let i = 0; i < items.length; i += 400) {
    const slice = items.slice(i, i + 400);
    const batch = db.batch();
    for (const item of slice) {
      const { id, ...data } = item;
      batch.set(db.collection(name).doc(id), data, { merge: true });
    }
    await batch.commit();
  }
  console.log(`Seeded ${items.length} document(s) into "${name}".`);
}

async function main() {
  const cities = load("cities.json");
  const curatedPlaces = load("places.json");

  await seedCollection("cities", cities);

  const citiesWithCurated = new Set(curatedPlaces.map((p) => p.cityId));
  const generated = cities
    .filter((c) => !citiesWithCurated.has(c.id))
    .flatMap(samplePlacesFor);

  await seedCollection("places", [...curatedPlaces, ...generated]);
  console.log(
    `(${curatedPlaces.length} curated + ${generated.length} generated sample places)`
  );
  console.log("Done.");
  process.exit(0);
}

main().catch((err) => {
  console.error(err);
  process.exit(1);
});
