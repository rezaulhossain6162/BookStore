package com.login.reg_login;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class PostAndAddFragment extends Fragment implements View.OnClickListener {


    public PostAndAddFragment() {

    }

    EditText etname,etBookName;
    Button btnsubmit,btncancel;
    TextView tvdatainfirebase;
    FirebaseAuth firebaseAuth;
    FirebaseUser currentUser;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         View v=inflater.inflate(R.layout.fragment_post_and_add, container, false);
        etname=v.findViewById(R.id.etName);
        etBookName=v.findViewById(R.id.etBookName);
        btnsubmit=v.findViewById(R.id.btnSubmit);
        btncancel=v.findViewById(R.id.btnCancel);
        btnsubmit.setOnClickListener(this);
        btncancel.setOnClickListener(this);

        return v;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSubmit:

                String bookName = etBookName.getText().toString();
                String name=etname.getText().toString();
                if (name.isEmpty()&& bookName.isEmpty()){
                    etname.setError("must be fill up");
                    etBookName.setError("must be fill up");
                }else {
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Add_Information");
                    String KEY = databaseReference.push().getKey();
                    firebaseAuth = FirebaseAuth.getInstance();
                    currentUser = firebaseAuth.getCurrentUser();
                    String uid = currentUser.getUid();
                    Person person = new Person(name, bookName,KEY,uid);
                    databaseReference.push().setValue(person);
                    String data="Data Inserted";

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