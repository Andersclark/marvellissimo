package com.andersclark.marvellissimo

import android.app.Activity
import android.os.Bundle
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_character_details.*
import kotlinx.android.synthetic.main.webview.*
import kotlinx.android.synthetic.main.webview.webView


class WebViewActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.webview)

        val url = intent.extras.getString("url")
        webView.settings.javaScriptEnabled = true
        webView.setWebViewClient(WebViewClient())
        webView.loadUrl(url)
    }
}