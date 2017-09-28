package com.login.reg_login;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class Search extends AppCompatActivity {
    String[] item;
    ListView listView;
    ArrayList<Person> arrayLists;
   // ArrayList<Person> arrayLists=new ArrayList<>();
    ArrayAdapter<Person> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
         arrayLists=new ArrayList<>();

        listView= (ListView) findViewById(R.id.listviewCountry);


        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Add_Information");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Person person=snapshot.getValue(Person.class);
                    String name = person.getName();
                    String address = person.getAddress();
                    String phone = person.getPhone();
                    String key = person.getKey();
                    arrayLists.add(new Person(name,address,phone,key));
                    //arrayLists=new ArrayList<>(Arrays.asList(arrayLists));
                    adapter= new MyCustomAdapter(Search.this,R.layout.custom,arrayLists);
                    listView.setAdapter(adapter);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_search,menu);
        MenuItem item=menu.findItem(R.id.menuSearch);
        SearchView searchView=(SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


}
