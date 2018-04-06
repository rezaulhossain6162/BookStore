package com.login.reg_login;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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
    View v;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        v= inflater.inflate(R.layout.fragment_home, container, false);
        lvdata=v.findViewById(R.id.lvdata);
        arrayList=new ArrayList<>();
        newtwork();
        return v;
    }

    private void newtwork() {
        ConnectivityManager cm= (ConnectivityManager) getContext().getSystemService(Service.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo Ninfo = cm.getActiveNetworkInfo();
            if (Ninfo != null) {
                if (Ninfo.getState() == NetworkInfo.State.CONNECTED) {
                    progressDialog = new ProgressDialog(getContext());
                    progressDialog.setTitle("Loading...");
                    progressDialog.show();
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
                                lvdata.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        // the position of the item clicked will come in as the 3rd parameter of the onItemClick callback
                                        // which is 'position'. You can use the value to do whatever you want
                                        Toast.makeText(getActivity(), "positon: "+position, Toast.LENGTH_SHORT).show();
                                    }
                                });
                                progressDialog.dismiss();
                            }
                        }
                        @Override
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
            builder.setMessage("Internet Not Connected");
            android.app.AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }
}