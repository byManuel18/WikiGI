package com.manueh.wikigi.views;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.manueh.wikigi.R;

public class HelpActivity extends AppCompatActivity {
    private Window window;
    private Context myContext;
    private String TAG="HELP";
    private String type;
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.window=getWindow();
        this.window.setNavigationBarColor(getResources().getColor(R.color.black));
        this.myContext=this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_help));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"Al clikar vuelve hacia detrás");
                onBackPressed();
            }
        });
        webView=findViewById(R.id.web);
        type=getIntent().getStringExtra("HelpType");
        webView.getSettings().setJavaScriptEnabled(true);
        final Activity activity = this;

        webView.setWebViewClient(new WebViewClient() {
            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();
            }
            @TargetApi(android.os.Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                // Redirect to deprecated method, so you can use it in all SDK versions
                onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
            }
        });

        if(type.equals("form")) {
            webView.loadUrl("https://bymanuel18.github.io/WikiGI/form.html?");
        }else if(type.equals("list")){
            webView.loadUrl("https://bymanuel18.github.io/WikiGI/lista.html?");
        }else if(type.equals("search")){
            webView.loadUrl("https://bymanuel18.github.io/WikiGI/buscar.html?");
        }

    }
}