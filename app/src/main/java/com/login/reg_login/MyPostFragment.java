package com.login.reg_login;


import android.app.Service;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyPostFragment extends Fragment {
    ArrayList<Person> arrayList1;
    CustomAdapterMyPost adapter;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    ListView lvitem;

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
        newtwork();
        return v;
        }
    private void newtwork() {
        ConnectivityManager cm= (ConnectivityManager) getContext().getSystemService(Service.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo Ninfo = cm.getActiveNetworkInfo();
            if (Ninfo != null) {
                if (Ninfo.getState() == NetworkInfo.State.CONNECTED) {
                    firebaseAuth = FirebaseAuth.getInstance();
                    FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                    String uid = currentUser.getUid();
                    databaseReference = FirebaseDatabase.getInstance().getReference();
                    Query query = databaseReference.child("Add_Information").orderByChild("uid").equalTo(uid);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                            for (DataSnapshot child : children) {
                                Person person = child.getValue(Person.class);
                                arrayList1.add(person);
                                adapter = new CustomAdapterMyPost(getContext(), R.layout.custommypost, arrayList1);
                                lvitem.setAdapter(adapter);
                            }
                        }

                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }else {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
                builder.setMessage("Internet Not Connected");
                android.app.AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        }
        else {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
            builder.setMessage("Interner Not Connected");
            android.app.AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

}
