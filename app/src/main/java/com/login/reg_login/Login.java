package com.login.reg_login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Login extends AppCompatActivity implements View.OnClickListener {

    public EditText etemailforlogin1,etpasswordforlogin1;
    public Button btnforlogin1,btnsignup;
    FirebaseAuth firebaseAuth;
    TextView incorrect;
    FirebaseAuth.AuthStateListener authStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth=FirebaseAuth.getInstance();
        ini();
        authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
                if (firebaseUser != null){
                    Intent intent=new Intent(Login.this,Book_Finder.class);
                    startActivity(intent);
                }
            }
        };

    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        if (authStateListener!=null){
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
        super.onStop();
    }

    private void ini() {
        incorrect= (TextView) findViewById(R.id.incorrect);
        etemailforlogin1= (EditText) findViewById(R.id.etemailforlogin);
        etpasswordforlogin1= (EditText) findViewById(R.id.etpasswordforlogin);
        btnforlogin1= (Button) findViewById(R.id.btnforlogin);
        btnsignup= (Button) findViewById(R.id.btnsignup);
        btnforlogin1.setOnClickListener(this);
        btnsignup.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnforlogin:
                String email=etemailforlogin1.getText().toString().trim();
                String password=etpasswordforlogin1.getText().toString().trim();

                if (email.isEmpty()) {
                    etemailforlogin1.setError("This field can not be blank");
                    if (password.isEmpty()) {
                        etpasswordforlogin1.setError("This field can not be blank");
                    }
                }else {
                    firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(Login.this, Book_Finder.class);
                                startActivity(intent);
                            } else {
                                String mes = task.getException().getMessage();
                                incorrect.setText(mes);
                            }
                        }
                    });
                }
                break;
            case R.id.btnsignup:
                Intent intent=new Intent(Login.this,MainActivity.class);
                startActivity(intent);
                break;
        }

    }

}