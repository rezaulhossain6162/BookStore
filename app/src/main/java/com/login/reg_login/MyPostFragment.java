package com.login.reg_login;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyPostFragment extends Fragment {
    ArrayList<Person> arrayList1;
    MyCustomAdapter adapter;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ListView lvitem;
    String key;

    public MyPostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_my_post, container, false);
         lvitem=v.findViewById(R.id.lvitem);
         arrayList1=new ArrayList<>();
         firebaseAuth = FirebaseAuth.getInstance();
         FirebaseUser currentUser = firebaseAuth.getCurrentUser();
         String uid = currentUser.getUid();
         firebaseDatabase.getInstance();

         databaseReference = FirebaseDatabase.getInstance().getReference().child("Add_Information");
         key = databaseReference.getKey();
         databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Person person=snapshot.getValue(Person.class);
                    String name = person.getName();
                    String address = person.getAddress();
                    String phone = person.getPhone();
                    String key = person.getKey();
                    String uid = person.getUid();
                    arrayList1.add(new Person(name,address,phone,key,uid));
                    adapter=new MyCustomAdapter(getContext(),R.layout.custom,arrayList1);
                    lvitem.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "unsuccess", Toast.LENGTH_SHORT).show();

            }
        });

        return v;
        }

}
