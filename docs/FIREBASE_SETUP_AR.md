# ربط التطبيق بـ Firebase (دليل خطوة بخطوة)

التطبيق صار يعتمد بالكامل على **Firebase** (Firestore + Authentication + Storage)
بدون أي سيرفر تدفع عليه. كل الخطوات التالية مجانية على خطة Firebase المجانية (Spark).

## ١) أنشئ مشروع Firebase
1. ادخل على https://console.firebase.google.com وأنشئ مشروع جديد (مثلاً `vibeout-talaa`).
2. تقدر تتجاهل Google Analytics إذا ما بدك ياه.

## ٢) أضف تطبيق Android
1. من إعدادات المشروع → **Add app** → اختر Android.
2. ضع اسم الحزمة بالضبط: `com.vibeout.talaa`
3. نزّل ملف **google-services.json**.
4. حطّه داخل مجلد `app/` في المشروع (نفس مكان `app/build.gradle.kts`).
   - الملف متجاهَل في git (لأنه خاص بك). يوجد ملف مثال: `app/google-services.json.example`.

> ملاحظة: نسخة الـ debug تستخدم اسم الحزمة `com.vibeout.talaa.debug`. لتشغيلها
> أضف تطبيق Android ثاني في Firebase بهذا الاسم (اختياري — أو شغّل نسخة release).

## ٣) فعّل تسجيل الدخول
Authentication → Sign-in method → فعّل **Email/Password**.

## ٤) أنشئ قاعدة بيانات Firestore
Firestore Database → Create database → ابدأ بوضع **Production**.

## ٥) فعّل Storage (اختياري للصور)
Storage → Get started.

## ٦) ارفع قواعد الأمان (Rules)
ثبّت Firebase CLI مرة وحدة:
```bash
npm install -g firebase-tools
firebase login
firebase use --add        # اختر مشروعك
firebase deploy --only firestore:rules,storage
```
ملفات القواعد جاهزة في جذر المشروع: `firestore.rules` و `storage.rules`.

## ٧) عبّئ بيانات المدن والأماكن (Seed)
1. Project settings → Service accounts → **Generate new private key**.
2. احفظ الملف باسم `serviceAccount.json` داخل `firebase/seed/` (متجاهَل في git).
3. شغّل:
```bash
cd firebase/seed
npm install
npm run seed
```
هذا بيضيف كل **المدن** (٦٥ مدينة) + أماكن تجريبية لأي مدينة بدون أماكن.

### استيراد أماكن حقيقية (موصى به)
لجلب أماكن **حقيقية** (كافيهات/مطاعم/حدائق/مكتبات) من OpenStreetMap مجاناً وبدون مفتاح:
```bash
cd firebase/seed
npm run import                 # كل المدن
npm run import istanbul baghdad   # مدن محددة فقط
```
- البيانات © OpenStreetMap (رخصة ODbL — يُفضّل ذكر المصدر في التطبيق لاحقاً).
- المتغيرات الاختيارية: `PER_CITY` (افتراضي ٤٠ مكان للمدينة)، `RADIUS_M` (افتراضي ٦٠٠٠ متر).
- التشغيل قابل للتكرار بأمان (يحدّث بدون تكرار).
- ملاحظة: تغطية الأماكن تختلف حسب المدينة (ممتازة بتركيا/أوروبا، جيدة بالمدن الكبرى).

## ٨) ابنِ التطبيق
```bash
./gradlew clean testDebugUnitTest lintDebug assembleDebug
```
> إضافة Google Services plugin تشتغل تلقائياً فقط لما يكون ملف
> `app/google-services.json` موجود، عشان لا ينكسر البناء على CI أو عند من
> لم يضف الملف بعد.

## ملاحظات
- خطط الذكاء الاصطناعي حالياً تُحسب **داخل التطبيق** من أماكن Firestore الحقيقية
  (بدون تكلفة خارجية). يمكن لاحقاً ترقيتها إلى OpenAI عبر Cloud Functions.
- استعادة كلمة المرور تتم عبر **رابط يُرسل بالبريد** من Firebase.
- الخريطة تحتاج مفتاح Google Maps في `local.properties` (`GOOGLE_MAPS_API_KEY`).
