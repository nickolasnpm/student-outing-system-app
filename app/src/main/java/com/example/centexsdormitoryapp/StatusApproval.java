package com.example.centexsdormitoryapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class StatusApproval extends AppCompatActivity implements View.OnClickListener {

    private FirebaseFirestore db;
    private TextView namelist, numberlist, destinationlist, comeinlist, gooutlist, reasonlist;
    private OutingList outingList;

    String[] responses={"STATUS: APPROVED", "STATUS: REJECTED"};
    AutoCompleteTextView statuslist;
    ArrayAdapter<String> adapterlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.status_approval);

        outingList = (OutingList) getIntent().getSerializableExtra("outingList");

        namelist = findViewById(R.id.applicantname);
        numberlist = findViewById(R.id.applicantnumber);
        destinationlist = findViewById(R.id.destination);
        reasonlist = findViewById(R.id.reason);
        comeinlist = findViewById(R.id.datecomein);
        gooutlist = findViewById(R.id.dategoout);

        namelist.setText(outingList.getOutingname());
        numberlist.setText(outingList.getOutingnumber());
        destinationlist.setText(outingList.getOutingdestination());
        reasonlist.setText(outingList.getOutingreason());
        comeinlist.setText(outingList.getComeindate());
        gooutlist.setText(outingList.getGooutdate());

        Button updatebtn = findViewById(R.id.updatestatus);
        updatebtn.setOnClickListener(this);

        db = FirebaseFirestore.getInstance();

        statuslist=findViewById(R.id.status);
        statuslist.setText(outingList.getOutingstatus());

        adapterlist= new ArrayAdapter<>(this, R.layout.dropdown_response, responses);
        statuslist.setAdapter(adapterlist);

        statuslist.setOnItemClickListener((parent, view, position, id) -> {
            String response=parent.getItemAtPosition(position).toString();
            Toast.makeText(getApplicationContext(), "Response: "+response, Toast.LENGTH_SHORT).show();
        });

        Button backbtn = findViewById(R.id.backtodashboard);
        backbtn.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), AdminDashboard.class)));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.updatestatus) {
            updateApproval();
        }
    }

    public boolean validateInputError(String outingname, String outingnumber, String outingdestination, String outingreason, String comeindate, String gooutdate, String outingstatus) {

        if (outingname.equals("")) {
            namelist.setError("Applicant name required");
            namelist.requestFocus();
            return true;
        }
        if (outingnumber.equals("")) {
            numberlist.setError("Applicant number required");
            numberlist.requestFocus();
            return true;
        }
        if (outingdestination.equals("")) {
            destinationlist.setError("Outing destination required");
            destinationlist.requestFocus();
            return true;
        }
        if (outingreason.equals("")) {
            reasonlist.setError("Outing reason required");
            reasonlist.requestFocus();
            return true;
        }
        if (comeindate.equals("")) {
            comeinlist.setError("Come in date required");
            comeinlist.requestFocus();
            return true;
        }
        if (gooutdate.equals("")) {
            gooutlist.setError("Go out date required");
            gooutlist.requestFocus();
            return true;
        }
        if(outingstatus.equals("")){
            statuslist.setError("Status should not be disturbed");
            statuslist.requestFocus();
            return true;
        }

        return false;
    }

    private void updateApproval() {

        String userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        String outingname = namelist.getText().toString().trim();
        String outingnumber = numberlist.getText().toString().trim();
        String outingdestination = destinationlist.getText().toString().trim();
        String outingreason = reasonlist.getText().toString().trim();
        String comeindate = comeinlist.getText().toString().trim();
        String gooutdate = gooutlist.getText().toString().trim();
        String outingstatus = statuslist.getText().toString().trim();

        if (!validateInputError(outingname, outingnumber, outingdestination, outingreason, comeindate, gooutdate, outingstatus)) {

            OutingList p = new OutingList(
                    outingname, outingnumber, outingdestination, outingreason, comeindate, gooutdate, outingstatus, userId);
            db.collection("Result")
                    .document(outingList.getId())
                    .set(p)
                    .addOnSuccessListener(aVoid -> {
                        startActivity(new Intent(getApplicationContext(), AdminDashboard.class));
                        Toast.makeText(StatusApproval.this, "Response is given", Toast.LENGTH_SHORT).show();
                    });
        }
    }
}