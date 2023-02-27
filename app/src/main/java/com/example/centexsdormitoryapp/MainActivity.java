package com.example.centexsdormitoryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button student1=findViewById(R.id.button_first);
        student1.setOnClickListener(this);

        Button student2=findViewById(R.id.button_third);
        student2.setOnClickListener(this);

        Button admin1=findViewById(R.id.button_second);
        admin1.setOnClickListener(this);

        Button admin2=findViewById(R.id.button_fourth);
        admin2.setOnClickListener(this);

    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.button_first:
                startActivity(new Intent(this, StudentLogin.class));
                break;
            case R.id.button_second:
                startActivity(new Intent(this, AdminLogin.class));
                break;
            case R.id.button_third:
                startActivity(new Intent(this, StudentRegister.class));
                break;
            case R.id.button_fourth:
                startActivity(new Intent(this, AdminRegister.class));
                break;
        }
    }
}