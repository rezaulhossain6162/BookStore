package com.login.reg_login;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;


public class PdfView extends AppCompatActivity {
    WebView mWebview;
    final Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view);

        mWebview = (WebView) findViewById(R.id.webview);

        Intent intent = getIntent();
        String pdf = intent.getStringExtra("pdflink");
        if(!pdf.equals("")){
            loadWebUrl(pdf);
           // openWebPage(pdf);
            Toast.makeText(activity, "link :"+pdf, Toast.LENGTH_SHORT).show();

        }
        //loadWebUrl("https://firebasestorage.googleapis.com/v0/b/bookfinder-da2b7.appspot.com/o/image%2F1519550907327.pdf?alt=media&token=35345a3a-8239-4096-a8a8-98ae9ad1a03d");
    }
    public void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
    private void loadWebUrl(String url) {
    mWebview.setBackgroundColor(Color.TRANSPARENT);
    mWebview.getSettings().setJavaScriptEnabled(true);
    mWebview.getSettings().setPluginState(WebSettings.PluginState.ON);
//    mWebview.getSettings().setLoadsImagesAutomatically(true);
//    mWebview.getSettings().setAllowFileAccessFromFileURLs(true);
//    mWebview.getSettings().setAllowUniversalAccessFromFileURLs(true);
    mWebview.getSettings().setBuiltInZoomControls(true);
        if (url!=null&&!url.isEmpty()) {

            //mWebview.loadUrl("https://docs.google.com/viewer?url=http://www.anweitong.com/upload/document/standard/national_standards/138793918364316200.pdf");
            mWebview.loadUrl("http://docs.google.com/gview?embedded=true&url="+url);
            setContentView(mWebview);
        }else {
        }
}
}