package com.example.centexsdormitoryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class ForgetActivity extends AppCompatActivity {

    FirebaseAuth fa;
    EditText registered_email;
    Button reset_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        registered_email = findViewById(R.id.register_email);
        reset_password = findViewById(R.id.reset);

        fa = FirebaseAuth.getInstance();

        reset_password.setOnClickListener(view -> resetpassword());
    }

    private void resetpassword() {
        String resetemail = registered_email.getText().toString().trim();

        if (resetemail.equals("")) {
            registered_email.setError("Email is required");
            registered_email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(resetemail).matches()) {
            registered_email.setError("Please provide valid email");
            registered_email.requestFocus();
            return;
        }
        fa.sendPasswordResetEmail(resetemail).addOnCompleteListener(task -> {

            if (task.isSuccessful()) {
                Toast.makeText(ForgetActivity.this, "Check your email to reset password", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ForgetActivity.this, "Something wrong. Please try again", Toast.LENGTH_SHORT).show();
            }
        });
    }
}