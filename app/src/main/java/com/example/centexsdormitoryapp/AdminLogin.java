package com.example.centexsdormitoryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class AdminLogin extends AppCompatActivity implements View.OnClickListener {

    EditText loginemail, loginid, loginpassword;
    TextView canceltext, gotoforgot;
    Button loginadmin;
    private FirebaseAuth al;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_login);

        al=FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressBar);

        loginadmin=findViewById(R.id.loginadmin);
        loginadmin.setOnClickListener(this);

        loginemail=findViewById(R.id.loginemail);
        loginid=findViewById(R.id.loginid);
        loginpassword=findViewById(R.id.loginpassword);

        canceltext=findViewById(R.id.cancellogin);
        canceltext.setOnClickListener(this);

        gotoforgot=findViewById(R.id.gotoforgot);
        gotoforgot.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cancellogin:
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                break;
            case R.id.gotoforgot:
                startActivity(new Intent(getApplicationContext(),ForgotActivity.class));
                break;
            case R.id.loginadmin:
                passAdmin();
                break;
        }
    }

    private void passAdmin() {

        String email = loginemail.getText().toString().trim();
        String adminid=loginid.getText().toString().trim();
        String password = loginpassword.getText().toString().trim();

        if (email.equals("")) {
            loginemail.setError("Email is required");
            loginemail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            loginemail.setError("Please provide valid email");
            loginemail.requestFocus();
            return;
        }
        if(adminid.equals("")){
            loginid.setError("Admin ID is required");
            loginid.requestFocus();
            return;
        }
        if (password.equals("")) {
            loginpassword.setError("Password is required");
            loginpassword.requestFocus();
            return;
        }
        if (password.length() < 8) {
            loginpassword.setError("Min password length should be 8 characters");
            loginpassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        if(adminid.equals("admincda1234")){

            al.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {

                //ridirect to user profile
                if (task.isSuccessful()) {
                    startActivity(new Intent(this, AdminDashboard.class));
                    progressBar.setVisibility(View.GONE);
                }else {
                    Toast.makeText(AdminLogin.this, "Wrong Password. Please Try Again", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            });
        }else{
            Toast.makeText(this, "Admin ID wrong. Please get the correct Id", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }
}