package com.login.reg_login;

import android.icu.util.Calendar;
import android.os.Build;
import android.sax.Element;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import mehdi.sakout.aboutpage.AboutPage;

public class About extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mehdi.sakout.aboutpage.Element element=new mehdi.sakout.aboutpage.Element();
        element.setTitle("About page");
        View aboutpage=new AboutPage(this)
                .isRTL(false)
                .setImage(R.drawable.book)
                .setDescription("This is Demo Version")
                .addItem(new mehdi.sakout.aboutpage.Element().setTitle("Version 1.1.1"))
                .addEmail("rezaulkarim94@gmail.com")
                .addWebsite("https://developer.android.com/studio/index.html")
                .addFacebook("https://www.facebook.com/rezaulkarim21")
                .addYoutube("https://www.youtube.com/")
                .addTwitter("https://twitter.com/rezaulk51075888")
                .addItem(createCopyright())
                .create();
        setContentView(aboutpage);


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private mehdi.sakout.aboutpage.Element createCopyright() {
        mehdi.sakout.aboutpage.Element element=new mehdi.sakout.aboutpage.Element();
        final String copystring=String.format("copyright by @ Rezaul Hossain/karim", Calendar.getInstance().get(Calendar.YEAR));
        element.setTitle(copystring);
        element.setGravity(Gravity.CENTER);
        element.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(About.this, copystring, Toast.LENGTH_SHORT).show();
            }
        });
        return element;
    }
}
