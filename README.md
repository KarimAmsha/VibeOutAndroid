# VibeOut / طلعة — Android Native MVP

مشروع Android Studio أصلي مبني بـ Kotlin وJetpack Compose ومربوط بعقد VibeOut Backend.

## التشغيل السريع

1. افتح المشروع من Android Studio عبر **Open**.
2. انتظر اكتمال Gradle Sync.
3. انسخ `local.properties.example` إلى `local.properties` إن لم ينشئه Android Studio.
4. على Android Emulator استخدم:

```properties
VIBEOUT_API_BASE_URL=http://10.0.2.2:3000/api/v1/
GOOGLE_MAPS_API_KEY=
```

5. شغّل الباك إند على المنفذ `3000` ثم اضغط Run.

## الهاتف الحقيقي

استبدل `10.0.2.2` بعنوان IP الخاص بالـMac على نفس الشبكة، مثل:

```properties
VIBEOUT_API_BASE_URL=http://192.168.1.20:3000/api/v1/
```

## ما تم ربطه من السورس المرفوع

- `GET /cities`
- `GET /cities/:id`
- `POST /auth/register`
- `POST /auth/login`
- `GET /auth/me`
- `GET /users/me`
- `PUT /users/me`
- `GET /users/:id/public`

بقية ميزات الـMVP موجودة في تطبيق Android ومجهزة بعقودها. يلزم دمج مجلد **VibeOutBackendCompletedSrc** المرفق لتفعيل: Preferences، Places، AI Plans، Vibes، Chat، Reports، Blocks، Notifications، Refresh وLogout.

## بناء APK

من Terminal داخل المشروع:

```bash
./gradlew clean testDebugUnitTest lintDebug assembleDebug
```

المسار المتوقع:

```text
app/build/outputs/apk/debug/app-debug.apk
```
