package com.login.reg_login;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    public HomeFragment() {
    }
    ListView lvdata;
    ArrayList<Person> arrayList;
    MyCustomAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_home, container, false);
          lvdata=v.findViewById(R.id.lvdata);
          arrayList=new ArrayList<>();

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Add_Information");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot child:children){
                    Person person=child.getValue(Person.class);
                    arrayList.add(person);
                    adapter=new MyCustomAdapter(getContext(),R.layout.custom,arrayList);
                    lvdata.setAdapter(adapter);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return v;
    }
}