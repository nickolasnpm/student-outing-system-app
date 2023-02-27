package com.example.centexsdormitoryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;

public class StatusDetails extends AppCompatActivity implements Serializable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.status_details);

        OutingList outingList = (OutingList) getIntent().getSerializableExtra("outingList");

        TextView namelist = findViewById(R.id.applicantname);
        TextView numberlist = findViewById(R.id.applicantnumber);
        TextView destinationlist = findViewById(R.id.destination);
        TextView reasonlist = findViewById(R.id.reason);
        TextView comeinlist = findViewById(R.id.datecomein);
        TextView gooutlist = findViewById(R.id.dategoout);
        TextView statuslist = findViewById(R.id.status);

        namelist.setText(outingList.getOutingname());
        numberlist.setText(outingList.getOutingnumber());
        destinationlist.setText(outingList.getOutingdestination());
        reasonlist.setText(outingList.getOutingreason());
        comeinlist.setText(outingList.getComeindate());
        gooutlist.setText(outingList.getGooutdate());
        statuslist.setText(outingList.getOutingstatus());

        Button backbtn = findViewById(R.id.backtodashboard);
        backbtn.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), StudentDashboard.class)));
    }
}