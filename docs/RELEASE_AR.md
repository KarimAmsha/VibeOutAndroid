# تجهيز نسخة Release للنشر على Google Play

التطبيق جاهز للبناء بنسخة موقّعة (AAB). اتبع الخطوات بالترتيب.

## ١) أنشئ مفتاح التوقيع (keystore) — مرة واحدة فقط
⚠️ **احتفظ بهذا الملف وكلمات المرور بأمان وللأبد** — بدونه ما تقدر تحدّث تطبيقك على المتجر لاحقاً.

```bash
keytool -genkeypair -v \
  -keystore ~/keys/vibeout-release.jks \
  -alias vibeout \
  -keyalg RSA -keysize 2048 -validity 10000
```
رح يسألك عن كلمة مرور واسمك/مؤسستك. تذكّر:
- مسار الملف: `~/keys/vibeout-release.jks`
- كلمة مرور المخزن (storePassword)
- اسم المفتاح (alias): `vibeout`
- كلمة مرور المفتاح (keyPassword)

## ٢) اربط المفتاح بالمشروع
انسخ القالب وعبّئه بقيمك:
```bash
cp keystore.properties.example keystore.properties
```
ثم افتح `keystore.properties` وحطّ القيم (الملف متجاهَل في git فآمن):
```properties
storeFile=/Users/karimothman/keys/vibeout-release.jks
storePassword=كلمة_مرور_المخزن
keyAlias=vibeout
keyPassword=كلمة_مرور_المفتاح
```

## ٣) ابنِ حزمة الإصدار (AAB)
```bash
./gradlew bundleRelease
```
الناتج:
```
app/build/outputs/bundle/release/app-release.aab
```
> لو بدك APK موقّع للتجربة على جهاز: `./gradlew assembleRelease`
> الناتج: `app/build/outputs/apk/release/app-release.apk`

## ٤) جرّب نسخة الـ Release قبل الرفع (مهم)
نسخة الـ release تستخدم تصغير الكود (R8). جرّبها على جهاز حقيقي وتأكد إن:
- التسجيل/الدخول يشتغل،
- الأماكن والخطط تظهر،
- الدردشة والإشعارات تشتغل.
> قواعد الحفاظ على نماذج Firestore مضافة في `app/proguard-rules.pro`.

## ٥) ارفع على Google Play
1. أنشئ حساب **Google Play Console** (٢٥$ مرة واحدة).
2. أنشئ تطبيق جديد، واسم الحزمة `com.vibeout.talaa`.
3. ارفع ملف `app-release.aab` في مسار **Internal testing** أول للتجربة.
4. عبّئ المطلوب: **سياسة الخصوصية**، **Data safety**, تقييم المحتوى، صور الشاشات، الأيقونة، الوصف.
5. بعد التجربة، رقّيه إلى **Production**.

## نقاط مهمة قبل النشر
- **سياسة خصوصية إجبارية** (لأنك تجمع إيميل + بيانات Firebase).
- **نموذج Data safety**: صرّح بجمع البريد والاسم والموقع التقريبي.
- **قيّد مفاتيح الـ API** (Firebase + Google Maps) من Google Cloud Console.
- زوّد **مفتاح Google Maps** في `local.properties` لتعمل الخريطة:
  `GOOGLE_MAPS_API_KEY=مفتاحك`
- بعد كل تحديث، زِد `versionCode` و `versionName` في `app/build.gradle.kts`.
