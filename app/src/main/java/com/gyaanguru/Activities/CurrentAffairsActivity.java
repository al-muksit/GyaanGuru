package com.gyaanguru.Activities;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.gyaanguru.R;

public class CurrentAffairsActivity extends AppCompatActivity {

    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_current_affairs);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        webView = findViewById(R.id.web);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true); // Enable DOM storage for better compatibility
        webView.getSettings().setSupportMultipleWindows(true);
        webView.setWebViewClient(new WebViewClient());
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
/*        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);    // Enable caching
        // Enable hardware acceleration
        if (Build.VERSION.SDK_INT >= 19) {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);   // chromium, enable hardware acceleration
        } else {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);   // older android version, disable hardware acceleration
        }
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);   // Set higher render priority
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);  // Disable cache*/
        webView.loadUrl(getIntent().getStringExtra("CurrentAffairsURL"));

    }
    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {webView.goBack();
        } else {finish();}
    }
}