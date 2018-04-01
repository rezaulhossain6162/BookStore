package com.login.reg_login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Login extends AppCompatActivity implements View.OnClickListener {

    public EditText etEmail,etpassword;
    public Button btnLogin,resetPassword;
    FirebaseAuth firebaseAuth;
    TextView tvForSign,tvForSignup,tvError;
    FirebaseAuth.AuthStateListener authStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth=FirebaseAuth.getInstance();
        ini();
        userNowLoginCheck();

    }

    private void userNowLoginCheck() {
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
        resetPassword= (Button) findViewById(R.id.resetPassword);
        tvForSign= (TextView) findViewById(R.id.tvForSign);
        etEmail= (EditText) findViewById(R.id.etEmail);
        etpassword= (EditText) findViewById(R.id.etPassword);
        btnLogin= (Button) findViewById(R.id.btnLogin);
        tvForSignup= (TextView) findViewById(R.id.tvForSignup);
        tvError= (TextView) findViewById(R.id.tvError);
        tvForSignup.setOnClickListener(this);
        resetPassword.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.resetPassword:
                Intent intent1=new Intent(this,ResetPassword.class);
                startActivity(intent1);
                break;
            case R.id.btnLogin:
             startLogin();
                break;
            case R.id.tvForSignup:
                Intent intent=new Intent(Login.this,MainActivity.class);
                startActivity(intent);
                break;
        }

    }

    private void startLogin() {
        String email=etEmail.getText().toString().trim();
        String password=etpassword.getText().toString().trim();
        if (TextUtils.isEmpty(email)|| TextUtils.isEmpty(password)) {
            etEmail.setError("Must be fill up");
            etpassword.setError("Must be fill up");
        }else {
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Intent intent = new Intent(Login.this, Book_Finder.class);
                        startActivity(intent);
                    } else {
                        String mes = task.getException().getMessage();
                        tvError.setText(mes);
                    }
                }
            });
        }
    }

}