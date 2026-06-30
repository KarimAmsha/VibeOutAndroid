/**
 * Removes the placeholder SAMPLE places created by seed.js, leaving real
 * (OpenStreetMap-imported) and curated places untouched.
 *
 * It deletes precisely:
 *   - any place document whose `source` field equals "sample", and
 *   - the exact deterministic sample ids (`<cityId>-coffee|walk|social`) for
 *     cities that have no curated places — this also covers samples that were
 *     seeded before the `source` tag existed.
 *
 * Usage:
 *   cd firebase/seed
 *   npm run clean-samples
 *
 * Safe to re-run (deletes are idempotent).
 */
const admin = require("firebase-admin");
const fs = require("fs");
const path = require("path");

const serviceAccountPath = path.join(__dirname, "serviceAccount.json");
if (!fs.existsSync(serviceAccountPath)) {
  console.error("Missing serviceAccount.json (place it next to this file).");
  process.exit(1);
}
admin.initializeApp({ credential: admin.credential.cert(require(serviceAccountPath)) });
const db = admin.firestore();

function load(file) {
  return JSON.parse(fs.readFileSync(path.join(__dirname, file), "utf8"));
}

function deterministicSampleIds() {
  const cities = load("cities.json");
  const curated = load("places.json");
  const hasCurated = new Set(curated.map((p) => p.cityId));
  const suffixes = ["coffee", "walk", "social"];
  const ids = [];
  for (const city of cities) {
    if (!hasCurated.has(city.id)) {
      for (const s of suffixes) ids.push(`${city.id}-${s}`);
    }
  }
  return ids;
}

async function deleteRefs(refs) {
  let count = 0;
  for (let i = 0; i < refs.length; i += 400) {
    const batch = db.batch();
    for (const ref of refs.slice(i, i + 400)) batch.delete(ref);
    await batch.commit();
    count += refs.slice(i, i + 400).length;
  }
  return count;
}

async function main() {
  // 1) Anything explicitly tagged as a sample.
  const tagged = await db.collection("places").where("source", "==", "sample").get();
  const taggedRefs = tagged.docs.map((d) => d.ref);

  // 2) Deterministic ids (covers pre-tag samples). Only delete those that exist.
  const ids = deterministicSampleIds();
  const existing = [];
  for (let i = 0; i < ids.length; i += 100) {
    const snaps = await db.getAll(...ids.slice(i, i + 100).map((id) => db.collection("places").doc(id)));
    for (const s of snaps) if (s.exists) existing.push(s.ref);
  }

  // Deduplicate refs by path.
  const byPath = new Map();
  for (const ref of [...taggedRefs, ...existing]) byPath.set(ref.path, ref);

  const removed = await deleteRefs([...byPath.values()]);
  console.log(`Removed ${removed} sample places.`);
  process.exit(0);
}

main().catch((e) => {
  console.error(e);
  process.exit(1);
});
