package com.login.reg_login;


import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Connection;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class PostAndAddFragment extends Fragment implements View.OnClickListener {


    public PostAndAddFragment() {
        // Required empty public constructor
    }

    //DatabaseReference databaseReference;
    EditText etname,etphone,etaddress;
    Button btnsubmit,btncancel;
    TextView tvdatainfirebase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         View v=inflater.inflate(R.layout.fragment_post_and_add, container, false);
        etname=v.findViewById(R.id.etName);
        etaddress=v.findViewById(R.id.etAddress);
        etphone=v.findViewById(R.id.etPhone);
        btnsubmit=v.findViewById(R.id.btnSubmit);
        btncancel=v.findViewById(R.id.btnCancel);
        btnsubmit.setOnClickListener(this);
        btncancel.setOnClickListener(this);
        tvdatainfirebase=v.findViewById(R.id.tvdatainfirebase);
        return v;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSubmit:

                String address=etaddress.getText().toString();
                String name=etname.getText().toString();
                String phone=etphone.getText().toString();
                if (name.isEmpty()){
                    etname.setError("must be fill up");
                }else {
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Add_Information");
                    String KEY = databaseReference.push().getKey();
                    Person person = new Person(name, address, phone, KEY);
                    databaseReference.push().setValue(person);
                    String data="Data Inserted";
                    tvdatainfirebase.setText(data);
                }
                break;
            case R.id.btnCancel:
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment fragment=new HomeFragment();
                fragmentTransaction.replace(R.id.loadfagment,fragment);
                fragmentTransaction.commit();
                break;
        }

    }
}