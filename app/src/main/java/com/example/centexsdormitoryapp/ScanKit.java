package com.example.centexsdormitoryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ScanKit extends AppCompatActivity {

    TextView dormregister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_kit);

        dormregister=findViewById(R.id.dormregister1);
        dormregister.setOnClickListener(view -> gotourl());

        Button backbtn = findViewById(R.id.backtodashboard);
        backbtn.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), StudentDashboard.class)));
    }

    private void gotourl() {
        Uri url=Uri.parse("https://linktr.ee/archigosnikolas.com");
        startActivity(new Intent(Intent.ACTION_VIEW, url));
    }
}