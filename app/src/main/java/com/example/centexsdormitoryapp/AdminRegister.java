package com.example.centexsdormitoryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


import java.util.Objects;

public class AdminRegister extends AppCompatActivity implements View.OnClickListener {

    private EditText adminname, adminnumber, adminemail, adminid, adminpassword, adminrepassword;
    private FirebaseAuth ar;
    private ProgressBar progressBar;

    String[] branches={"Kuching (Main)", "Lundu (Branch)", "Dalat (Branch)", "Mukah (Branch)", "Lawas (Branch)", "Betong (Branch)"};
    AutoCompleteTextView autoadminbranch;
    ArrayAdapter<String> adapterBranches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_register);

        ar = FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressBar);
        //Edittext
        adminname = findViewById(R.id.adminname);
        adminnumber = findViewById(R.id.adminnumber);
        adminemail = findViewById(R.id.adminemail);
        adminid = findViewById(R.id.adminid);
        adminpassword = findViewById(R.id.adminpassword);
        adminrepassword = findViewById(R.id.adminrepassword);

        //button
        Button registerbtn = findViewById(R.id.registerbtn);
        registerbtn.setOnClickListener(this);

        Button cancelregister = findViewById(R.id.cancelregister);
        cancelregister.setOnClickListener(this);

        Button clearbtn = findViewById(R.id.clearbtn);
        clearbtn.setOnClickListener(this);

        //dropdown select branch

        autoadminbranch=findViewById(R.id.autoadminbranch);
        adapterBranches=new ArrayAdapter<String>(this, R.layout.dropdown_branch, branches);
        autoadminbranch.setAdapter(adapterBranches);

        autoadminbranch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               String branch=parent.getItemAtPosition(position).toString();
               Toast.makeText(getApplicationContext(), "Branch: "+branch, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.registerbtn:
                passAdmin();
                break;
            case R.id.clearbtn:
                clearInfo();
                break;
            case R.id.cancelregister:
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                break;
        }
    }

    private void clearInfo() {

        adminname.getText().clear();
        adminnumber.getText().clear();
        adminemail.getText().clear();
        adminid.getText().clear();
        adminpassword.getText().clear();
        adminrepassword.getText().clear();

    }

    private void passAdmin() {

        String savename=adminname.getText().toString().trim();
        String savenumber=adminnumber.getText().toString().trim();
        String savebranch=autoadminbranch.getText().toString().trim();
        String saveemail=adminemail.getText().toString().trim();
        String saveadminid=adminid.getText().toString().trim();
        String savepassword=adminpassword.getText().toString().trim();
        String saverepassword=adminrepassword.getText().toString().trim();

        if(savename.equals("")){
            adminname.setError("Fullname is required");
            adminname.requestFocus();
            return;
        }
        if(savenumber.equals("")){
            adminnumber.setError("Mobile Number is required");
            adminnumber.requestFocus();
            return;
        }
        if(savenumber.length() >13){
            adminnumber.setError("Please provide valid mobile number");
            adminnumber.requestFocus();
            return;
        }
        if(savebranch.equals("")){
            autoadminbranch.setError("Branch is required");
            autoadminbranch.requestFocus();
            return;
        }
        if (saveemail.equals("")) {
            adminemail.setError("Email is required");
            adminemail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(saveemail).matches()){
            adminemail.setError("Please provide valid email");
            adminemail.requestFocus();
            return;
        }
        if (saveadminid.equals("")){
            adminid.setError("Admin ID is required");
            adminid.requestFocus();
            return;
        }
        if (savepassword.equals("")) {
            adminpassword.setError("Password is required");
            adminpassword.requestFocus();
            return;
        }
        if (savepassword.length() < 8) {
            adminpassword.setError("Minimum password length should be 8 characters");
            adminpassword.requestFocus();
            return;
        }
        if(saverepassword.equals("")){
            adminrepassword.setError("Confirm password is required");
            adminrepassword.requestFocus();
            return;
        }
        if(!saverepassword.equals(savepassword)){
            adminrepassword.setError("Password is not matching");
            adminrepassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        if(saveadminid.equals("adminid1234")){

            ar.createUserWithEmailAndPassword(saveemail,savepassword)
                    .addOnCompleteListener(this, task -> {

                        if(task.isSuccessful()){
                            AdminProfile admin=new AdminProfile (savename, savenumber, savebranch, saveemail);

                            FirebaseDatabase.getInstance().getReference("Admin")
                                    .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                                    .setValue(admin)
                                    .addOnCompleteListener(task1 -> {

                                if(task1.isSuccessful()){
                                    Toast.makeText(this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(),AdminDashboard.class));
                                    progressBar.setVisibility(View.GONE);
                                }else{
                                    Toast.makeText(this, "Failed to register", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            });
                        }else{
                            Toast.makeText(this, "Failed to register", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                            });
        }else{
            Toast.makeText(this, "Admin ID wrong. Please get the correct Id", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }
}