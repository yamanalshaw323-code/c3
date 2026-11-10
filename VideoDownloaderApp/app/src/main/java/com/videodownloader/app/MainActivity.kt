package com.videodownloader.app

import android.app.DownloadManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView

    /*
     * ═══════════════════════════════════════════════════
     *  للنسخة الحقيقية: ضع رابط السيرفر المنشور هنا
     *  مثال: private val SERVER_URL = "https://myserver.onrender.com"
     *  للنسخة التجريبية (تعمل بدون إنترنت): اتركه فارغاً ""
     * ═══════════════════════════════════════════════════
     */
    private val SERVER_URL = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        webView = WebView(this)
        setContentView(webView)

        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            allowFileAccess = true
            allowContentAccess = true
            mediaPlaybackRequiresUserGesture = false
            loadWithOverviewMode = true
            useWideViewPort = true
        }

        webView.webViewClient = WebViewClient()
        webView.webChromeClient = WebChromeClient()

        // التقاط أحداث التنزيل من صفحة الويب
        webView.setDownloadListener { url, _, _, _, _ ->
            handleDownload(url)
        }

        if (SERVER_URL.isNotEmpty()) {
            webView.loadUrl(SERVER_URL)
        } else {
            webView.loadUrl("file:///android_asset/demo.html")
        }
    }

    private fun handleDownload(url: String) {
        when {
            // ملفات مدمجة داخل التطبيق (النسخة التجريبية)
            url.startsWith("file:///android_asset/") -> {
                val fileName = url.substringAfterLast("/")
                saveAssetToDownloads(fileName)
            }
            // روابط خارجية (النسخة الحقيقية) عبر مدير تنزيل أندرويد
            else -> {
                try {
                    val request = DownloadManager.Request(Uri.parse(url)).apply {
                        setTitle("تنزيل الفيديو")
                        setDescription("جاري التنزيل...")
                        setNotificationVisibility(
                            DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED
                        )
                        setDestinationInExternalPublicDir(
                            Environment.DIRECTORY_DOWNLOADS,
                            "video_${System.currentTimeMillis()}.mp4"
                        )
                    }
                    val dm = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
                    dm.enqueue(request)
                    Toast.makeText(this, "بدأ التنزيل...", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Toast.makeText(this, "خطأ: ${'$'}{e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun saveAssetToDownloads(fileName: String) {
        try {
            val outDir = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)!!
            val outFile = File(outDir, fileName)
            assets.open(fileName).use { input ->
                outFile.outputStream().use { output -> input.copyTo(output) }
            }
            Toast.makeText(this, "تم الحفظ في: ${'$'}{outFile.name}", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Toast.makeText(this, "خطأ في الحفظ: ${'$'}{e.message}", Toast.LENGTH_LONG).show()
        }
    }

    // زر الرجوع داخل صفحات الويب أولاً
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (::webView.isInitialized && webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}
