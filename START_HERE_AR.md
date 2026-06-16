# ابدأ من هنا — بدون تعقيد

## أولًا: افتح تطبيق Android

1. فك ضغط `VibeOutAndroid.zip`.
2. افتح Android Studio.
3. اختر **Open**.
4. اختر مجلد `VibeOutAndroid` نفسه، الذي يحتوي `settings.gradle.kts`.
5. انتظر Gradle Sync.

## ثانيًا: اضبط رابط الباك إند

داخل ملف `local.properties` أضف:

```properties
VIBEOUT_API_BASE_URL=http://10.0.2.2:3000/api/v1/
GOOGLE_MAPS_API_KEY=
```

`10.0.2.2` يعني جهاز الـMac من داخل Android Emulator.

## ثالثًا: أكمل الباك إند

المجلد المرفق `VibeOutBackendCompletedSrc` هو نسخة `src` مكتملة. خذ نسخة احتياطية من مجلد `src` الحالي في الباك إند، ثم استبدله بالمجلد الجديد.

بعدها شغل الباك إند كالمعتاد:

```bash
npm run start:dev
```

ثم شغل تطبيق Android بزر Run.
