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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public EditText etEamil,etPassword;
    TextView tvToCreateNewAcc,tvError;
    Button btnCreateAccount;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ini();
        firebaseAuth= FirebaseAuth.getInstance();
    }

    private void ini() {
        tvToCreateNewAcc= (TextView) findViewById(R.id.tvToCreateNewAcc);
        tvError= (TextView) findViewById(R.id.tvError);
        etEamil= (EditText) findViewById(R.id.etEmail);
        etPassword= (EditText) findViewById(R.id.etPassword);
        btnCreateAccount= (Button) findViewById(R.id.btnCreateAccount);
        btnCreateAccount.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                break;
            case R.id.btnCreateAccount:
                startRegistration();
                break;

        }

    }

    private void startRegistration() {
            String email = etEamil.getText().toString();
            String pass = etPassword.getText().toString();
            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)) {
                etEamil.setError("Must be fill up");
                etPassword.setError("Must be fill up");
            }else {
                firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Successfully registered", Toast.LENGTH_SHORT).show();
                        } else {
                            String bad = task.getException().getMessage();
                            tvError.setText(bad);
                        }
                    }
                });
            }
        }
    }