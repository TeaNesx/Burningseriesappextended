package com.example.burningseriesapp_extended;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class WebPreviewActivity extends AppCompatActivity {
    public static String EXTRA_LINK = "com.example.burningseriesapp_extended.EXTRA_LINK";
    WebView webView;
    String extra_url;

    @SuppressLint({"JavascriptInterface", "SetJavaScriptEnabled"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_preview);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            extra_url = bundle.getString(EXTRA_LINK);
        }
        webView = findViewById(R.id.web_view);
        webView.loadUrl("https://bs.to/" + extra_url);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }
}