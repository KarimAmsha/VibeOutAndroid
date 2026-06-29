/**
 * Seeds the VibeOut / طلعة catalog (cities + places) into Cloud Firestore.
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

async function seedCollection(name, items) {
  const batch = db.batch();
  for (const item of items) {
    const { id, ...data } = item;
    batch.set(db.collection(name).doc(id), data, { merge: true });
  }
  await batch.commit();
  console.log(`Seeded ${items.length} document(s) into "${name}".`);
}

async function main() {
  await seedCollection("cities", load("cities.json"));
  await seedCollection("places", load("places.json"));
  console.log("Done.");
  process.exit(0);
}

main().catch((err) => {
  console.error(err);
  process.exit(1);
});
