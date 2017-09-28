package com.login.reg_login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public EditText etemail,etpassword;
    TextView tvmessage;
    Button btnreg,btnlongin;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ini();
        firebaseAuth= FirebaseAuth.getInstance();
    }

    private void ini() {
        tvmessage= (TextView) findViewById(R.id.tvmessage);
        etpassword= (EditText) findViewById(R.id.etpassword);
        etemail= (EditText) findViewById(R.id.etemail);
        btnreg= (Button) findViewById(R.id.btnreg);
        btnlongin= (Button) findViewById(R.id.btnlogin);
        btnreg.setOnClickListener(this);
        btnlongin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnlogin:
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                break;
            case R.id.btnreg:
                String email = etemail.getText().toString();
                String pass = etpassword.getText().toString();
                if (email.isEmpty()) {
                    etemail.setError("This field can not be blank");
                        if (pass.isEmpty()) {
                            etpassword.setError("This field can not be blank");
                        }
                }else {
                    firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(MainActivity.this, "Successfully registered", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                String bad=task.getException().getMessage();
                                tvmessage.setText(bad);
                            }
                        }
                    });
                }
                break;

        }

    }
}