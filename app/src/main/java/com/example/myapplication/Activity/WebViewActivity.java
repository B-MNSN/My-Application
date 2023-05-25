package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.myapplication.databinding.ActivityMainMenuBinding;
import com.example.myapplication.databinding.ActivityWebviewBinding;

public class WebViewActivity extends AppCompatActivity {

    ActivityWebviewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_webView);

        binding = ActivityWebviewBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        init();
    }

    public void init() {
        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.setWebViewClient(new myWebViewClient());
        binding.webView.loadUrl("https://www.youtube.com/");
    }

    private class myWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            binding.progressbar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            binding.progressbar.setVisibility(View.GONE);
        }
    }
}