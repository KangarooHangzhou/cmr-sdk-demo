package com.daishupei.cmrdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.webkit.WebView
import android.widget.EditText
import com.tongji.cmr.DrawPartListener
import com.tongji.cmr.DrawPartWebView
import com.tongji.cmr.jointURL

/**
 * 直接使用 DrawPartWebView 需要在 build.gradle 中导入 implementation 'com.github.lzyzsd:jsbridge:1.0.4' 库
 */

class CarModelCircleByWebviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_model_circle_by_webview)

        val webView = findViewById<DrawPartWebView>(R.id.myWebView).apply {
            drawPartListener = object : DrawPartListener {
                override fun onDrawParted(data: String?) {
                    data?.let {
                        startActivity(
                            Intent(
                                this@CarModelCircleByWebviewActivity,
                                ShowDrawPartResultActivity::class.java
                            ).apply {
                                putExtra("json", data)
                            })
                    }
                }
            }
        }

        findViewById<View>(R.id.btn_submit).setOnClickListener { _ ->
            findViewById<EditText>(R.id.etUrl).let {
                it.text.toString().apply {
                    if (TextUtils.isEmpty(this)) {
                        it.error = "网址不能为空"
                    } else {
                        /**
                         * 1. 方法一：通过 [DrawPartWebView.loadUrl] 直接传递参数加载网址。
                         * 2. 方法二：通过 [jointURL] 传递参数获取到拼接好的网址，然后再调用 [WebView.loadUrl]
                         */
                        (webView as WebView).loadUrl(this)
                    }
                }
            }
        }
    }
}