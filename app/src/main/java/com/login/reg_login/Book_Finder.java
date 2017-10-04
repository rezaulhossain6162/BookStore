package com.login.reg_login;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Book_Finder extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
private FrameLayout frameLayout;
    FirebaseAuth firebaseAuth;
    ListView lvdatafindbook;
    ArrayList<Person> arrayList;
    MyCustomAdapter adapter;
    Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book__finder);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firebaseAuth=FirebaseAuth.getInstance();
        ini();
        arrayList=new ArrayList<>();
        Fragment fragment=new HomeFragment();
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.replace(R.id.loadfagment,fragment);
        ft.commit();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }



    private void ini() {
        frameLayout= (FrameLayout) findViewById(R.id.loadfagment);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.book__finder, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
       switch (item.getItemId()){
           case R.id.action_settings:
               Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/rezaulkarim21?ref=bookmarks"));
               startActivity(intent);
               break;
           case R.id.rate:
               Intent intent_rate=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/naim.reza.566?lst=100002640358241%3A100008095686186%3A1505986822"));
               startActivity(intent_rate);
               break;
           case R.id.share:
               Intent myintent=new Intent(Intent.ACTION_SEND);
               myintent.setType("text/plain");
               String sharebody="https://www.facebook.com/rezaulkarim21";
               String sharetsub="your sub here";
               myintent.putExtra(Intent.EXTRA_SUBJECT,sharetsub);
               myintent.putExtra(Intent.EXTRA_TEXT,sharebody);
               startActivity(Intent.createChooser(myintent,"share using"));
               break;
           case R.id.refresh:
               Fragment fragment=new HomeFragment();
               FragmentManager fm=getSupportFragmentManager();
               FragmentTransaction ft=fm.beginTransaction();
               ft.replace(R.id.loadfagment,fragment);
               setTitle("Home");
               ft.commit();
               break;
       }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Fragment fragment=null;
        int id = item.getItemId();

        if (id == R.id.home) {
            fragment=new HomeFragment();
            FragmentManager fm=getSupportFragmentManager();
            FragmentTransaction ft=fm.beginTransaction();
            ft.replace(R.id.loadfagment,fragment);
            setTitle("Home");
            ft.commit();

        } else if (id == R.id.search) {
            Intent intent=new Intent(Book_Finder.this,Search.class);
            startActivity(intent);

        } else if (id == R.id.postandadd) {
            fragment=new PostAndAddFragment();
            FragmentManager fm=getSupportFragmentManager();
            FragmentTransaction ft=fm.beginTransaction();
            ft.replace(R.id.loadfagment,fragment);
            setTitle("Post and add");
            ft.commit();


        } else if (id == R.id.mypost) {
            fragment=new MyPostFragment();
            FragmentManager fm=getSupportFragmentManager();
            FragmentTransaction ft=fm.beginTransaction();
            ft.replace(R.id.loadfagment,fragment);
            setTitle("My post");
            ft.commit();

        } else if (id == R.id.favorites) {
            fragment=new MyFavouritesFragment();
            FragmentManager fm=getSupportFragmentManager();
            FragmentTransaction ft=fm.beginTransaction();
            ft.replace(R.id.loadfagment,fragment);
            setTitle("My Favorites");
            ft.commit();

        } else if (id == R.id.aboutapplication) {
            Intent intent=new Intent(Book_Finder.this,About.class);
            startActivity(intent);
        }
        else if(id==R.id.logout){
            firebaseAuth.signOut();
            finish();
            Intent intent=new Intent(Book_Finder.this,Login.class);
            startActivity(intent);

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}