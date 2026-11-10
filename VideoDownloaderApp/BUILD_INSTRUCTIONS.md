# تعليمات بناء التطبيق (APK)

## الطريقة 1: GitHub (أسهل طريقة — بدون تثبيت أي برامج) ⭐

1. أنشئ حساباً مجانياً على github.com
2. أنشئ مستودعاً جديداً (New Repository) باسم `VideoDownloaderApp`
3. ارفع جميع ملفات هذا المجلد إلى المستودع
4. اذهب إلى تبويب **Actions** في المستودع
5. اضغط **Build APK** ثم **Run workflow**
6. بعد 5-10 دقائق، اضغط على آخر تشغيل وحمّل ملف
   `video-downloader-apk` — هذا هو ملف الـ APK!
7. انقل الملف لهاتفك وثبّته (فعّل "التثبيت من مصادر غير معروفة")

## الطريقة 2: Android Studio (لتخصيص أكثر)

1. ثبّت Android Studio من developer.android.com
2. افتح هذا المجلد كمشروع (Open)
3. انتظر اكتمال المزامنة (Sync)
4. Build > Build Bundle(s) / APK(s) > Build APK(s)
5. ستجد الملف في: app/build/outputs/apk/debug/

## النسخة الحقيقية (تنزيل فعلي من المنصات)

التطبيق الحالي يعمل بالنسخة التجريبية بدون إنترنت.
للتنزيل الفعلي:
1. انشر السيرفر app.py على استضافة مجانية (Render / Railway)
2. افتح MainActivity.kt وضع رابط السيرفر في SERVER_URL
3. أعد بناء الـ APK
