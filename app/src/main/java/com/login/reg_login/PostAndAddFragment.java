package com.login.reg_login;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static android.app.Activity.RESULT_OK;


public class PostAndAddFragment extends Fragment implements View.OnClickListener {


    private ContentResolver contentResolver;

    public PostAndAddFragment() {

    }
    ImageView ivimage;
    String uri;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private Uri imageuri;
    public static final String FB_STORAGE_PATH="image/";
    public static final String FB_DATABASE_PATH="image";
    public static final int REQUEST_CODE=1234;
    EditText etname,etBookName;
    Button btnsubmit,btncancel,btnChoose;
    TextView tvdatainfirebase;
    FirebaseAuth firebaseAuth;
    FirebaseUser currentUser;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         View v=inflater.inflate(R.layout.fragment_post_and_add, container, false);
        storageReference= FirebaseStorage.getInstance().getReference();
        etname=v.findViewById(R.id.etname);
        etBookName=v.findViewById(R.id.etBookName);
        btnsubmit=v.findViewById(R.id.btnSubmit);
        btncancel=v.findViewById(R.id.btnCancel);
        btnsubmit.setOnClickListener(this);
        btncancel.setOnClickListener(this);
        btnChoose=v.findViewById(R.id.btnChoose);
        btnChoose.setOnClickListener(this);
        return v;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnChoose:
                Intent intent = new Intent();
                intent.setType("application/pdf");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Pdf"), REQUEST_CODE);

                break;
            case R.id.btnSubmit:
                newtwork();
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_CODE && resultCode==RESULT_OK && data!=null && data.getData()!= null){
            imageuri=data.getData();
            btnChoose.setText("pdf Choosed");
            Toast.makeText(this.getContext(), "Choosed", Toast.LENGTH_SHORT).show();
        }
    }
    public String getImageExt(Uri uri){
        ContentResolver resolver = getActivity().getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(resolver.getType(uri));
    }
    private void newtwork() {
        ConnectivityManager cm= (ConnectivityManager) getContext().getSystemService(Service.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo Ninfo = cm.getActiveNetworkInfo();
            if (Ninfo != null) {
                if (Ninfo.getState() == NetworkInfo.State.CONNECTED) {
                    final String bookName = etBookName.getText().toString();
                    final String name=etname.getText().toString();
                    if (name.isEmpty() || bookName.isEmpty() || imageuri==null){
                        etname.setError("must be fill up");
                        etBookName.setError("must be fill up");
                        Toast.makeText(getContext(), "Select pdf", Toast.LENGTH_SHORT).show();
                    }else {
                        final ProgressDialog dialog=new ProgressDialog(getContext());
                        dialog.setTitle("Uploading Pdf");
                        dialog.show();
                        StorageReference reference=storageReference.child(FB_STORAGE_PATH + System.currentTimeMillis()+"."+getImageExt(imageuri));
                        reference.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                dialog.dismiss();
                                uri=taskSnapshot.getDownloadUrl().toString();
                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Add_Information");
                                firebaseAuth = FirebaseAuth.getInstance();
                                currentUser = firebaseAuth.getCurrentUser();
                                String uid = currentUser.getUid();
                                Person person = new Person(name, bookName,uri,uid);
                                databaseReference.push().setValue(person);
                                Toast.makeText(getContext(), "upload completed", Toast.LENGTH_SHORT).show();
                                etBookName.getText().clear();
                                etname.getText().clear();
                                imageuri=null;
                                btnChoose.setText("Choose");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                dialog.dismiss();
                                Toast.makeText(getContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {

                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                double progress=(100* taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                                dialog.setMessage("Please Wait..."+(int)progress+"%");
                            }
                        });
                    }
                }
            }else {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
                builder.setMessage("Internet Not Connect");
                android.app.AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        }
        else {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
            builder.setMessage("Interner Not Connect");
            android.app.AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }
}