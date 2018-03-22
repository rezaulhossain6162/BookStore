package com.login.reg_login;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.Toast;

/**
 * Created by jack on 2/27/2018.
 */

public class Download extends AppCompatActivity{
    WebView mWebview;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Activity activity = this;
        mWebview= (WebView) findViewById(R.id.webview2);
        Intent intent = getIntent();
        String pdf = intent.getStringExtra("pdflink");
        if(!pdf.equals("")){
            openWebPage(pdf);
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
    }}