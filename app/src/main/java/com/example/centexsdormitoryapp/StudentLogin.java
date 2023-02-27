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

public class StudentLogin extends AppCompatActivity implements View.OnClickListener {

    EditText loginemail, loginpassword;
    TextView canceltext, gotoforget;
    Button loginstudent;
    private FirebaseAuth sl;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_login);

        sl=FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressBar);

        loginstudent=findViewById(R.id.loginstudent);
        loginstudent.setOnClickListener(this);

        loginemail=findViewById(R.id.loginemail);
        loginpassword=findViewById(R.id.loginpassword);

        canceltext=findViewById(R.id.cancellogin);
        canceltext.setOnClickListener(this);

        gotoforget=findViewById(R.id.gotoforget);
        gotoforget.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cancellogin:
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                break;
            case R.id.gotoforget:
                startActivity(new Intent(getApplicationContext(),ForgetActivity.class));
                break;
            case R.id.loginstudent:
                passStudent();
                break;
        }

    }

    private void passStudent() {
        String email = loginemail.getText().toString().trim();
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
        sl.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {

            //redirect to user profile
            if (task.isSuccessful()) {
                startActivity(new Intent(this, StudentDashboard.class));
                progressBar.setVisibility(View.GONE);
            }else {
                Toast.makeText(StudentLogin.this, "Wrong Password. Please Try Again", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}