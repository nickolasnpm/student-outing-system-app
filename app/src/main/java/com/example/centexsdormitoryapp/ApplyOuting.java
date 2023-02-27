package com.example.centexsdormitoryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Objects;

public class ApplyOuting extends AppCompatActivity implements View.OnClickListener {

    private FirebaseFirestore db;
    private EditText applicantname, applicantnumber, destination, reason, dategoout, datecomein, userId;
    private TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apply_outing);

        applicantname = findViewById(R.id.applicantname);
        applicantnumber = findViewById(R.id.applicantnumber);
        destination = findViewById(R.id.destination);
        reason=findViewById(R.id.reason);
        dategoout = findViewById(R.id.dategoout);
        datecomein = findViewById(R.id.datecomein);
        status = findViewById(R.id.status);

        Calendar calendar = Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);

        dategoout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(
                        ApplyOuting.this, new DatePickerDialog.OnDateSetListener() {
                    private DatePicker datePicker;
                    private int year;
                    private int month;
                    private int day;

                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        this.datePicker = datePicker;
                        this.year = year;
                        this.month = month;
                        this.day = day;
                        month=month+1;
                        String date=day+"/"+month+"/"+year;
                        dategoout.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });
        datecomein.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(
                        ApplyOuting.this, new DatePickerDialog.OnDateSetListener() {
                    private DatePicker datePicker;
                    private int year;
                    private int month;
                    private int day;

                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        this.datePicker = datePicker;
                        this.year = year;
                        this.month = month;
                        this.day = day;
                        month=month+1;
                        String date=day+"/"+month+"/"+year;
                        datecomein.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        Button submitbtn = findViewById(R.id.submitbtn);
        submitbtn.setOnClickListener(this);

        Button clearbtn = findViewById(R.id.clearbtn);
        clearbtn.setOnClickListener(this);

        Button cancelout = findViewById(R.id.cancelout);
        cancelout.setOnClickListener(this);

        db = FirebaseFirestore.getInstance();

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancelout:
                startActivity(new Intent(getApplicationContext(), StudentDashboard.class));
                break;
            case R.id.clearbtn:
                clearInfo();
                break;
            case R.id.submitbtn:
                submitapplication();
                break;
        }
    }

    public boolean validateInputError(String outingname, String outingnumber, String outingdestination, String outingreason, String comeindate, String gooutdate, String outingstatus) {

        if (outingname.equals("")) {
            applicantname.setError("Applicant name required");
            applicantname.requestFocus();
            return true;
        }
        if (outingnumber.equals("")) {
            applicantnumber.setError("Applicant number required");
            applicantnumber.requestFocus();
            return true;
        }
        if(outingnumber.length() >13){
            applicantnumber.setError("Please provide valid mobile number");
            applicantnumber.requestFocus();
            return true;
        }
        if (outingdestination.equals("")) {
            destination.setError("Outing destination required");
            destination.requestFocus();
            return true;
        }
        if (outingreason.equals("")) {
            reason.setError("Outing reason required");
            reason.requestFocus();
            return true;
        }
        if (comeindate.equals("")) {
            datecomein.setError("Come in date required");
            datecomein.requestFocus();
            return true;
        }
        if (gooutdate.equals("")) {
            dategoout.setError("Go out date required");
            dategoout.requestFocus();
            return true;
        }
        if (outingstatus.equals("")){
            status.setError("Status should not disturbed");
            status.requestFocus();
            return true;
        }
        return false;
    }

    private void submitapplication() {

        String userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        String outingname = applicantname.getText().toString().trim();
        String outingnumber = applicantnumber.getText().toString().trim();
        String outingdestination = destination.getText().toString().trim();
        String outingreason = reason.getText().toString().trim();
        String comeindate = datecomein.getText().toString().trim();
        String gooutdate = dategoout.getText().toString().trim();
        String outingstatus = status.getText().toString().trim();

        if (!validateInputError(outingname, outingnumber, outingdestination, outingreason, comeindate, gooutdate, outingstatus)) {

            OutingList outingList = new OutingList(
                    outingname, outingnumber, outingdestination, outingreason, comeindate, gooutdate, outingstatus, userId);

            CollectionReference application = db.collection("Outing");

                        application.add(outingList)
                                .addOnSuccessListener(documentReference -> {
                                    Intent intent = new Intent(ApplyOuting.this, StudentDashboard.class);
                                    startActivity(intent);
                                    finish();

                                    Toast.makeText(ApplyOuting.this, "Application Accepted", Toast.LENGTH_SHORT).show();
                                })
                                .addOnFailureListener(e ->
                                        Toast.makeText(ApplyOuting.this, "Something wrong. Please try again", Toast.LENGTH_SHORT).show());
                    }
        }

        private void clearInfo () {

            applicantname.getText().clear();
            applicantnumber.getText().clear();
            destination.getText().clear();
            reason.getText().clear();
            dategoout.getText().clear();
            datecomein.getText().clear();
        }
}