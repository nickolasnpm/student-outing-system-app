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

public class StudentRegister extends AppCompatActivity implements View.OnClickListener{

    private EditText editname, editaddress, editnric, editmobile, editroom, editid, editcontact, editphone, rgtemail, rgtpassword, rgtrepassword;
    private FirebaseAuth sr;
    private ProgressBar progressBar;

    String[] genders={"Male", "Female"};
    String[] races={"Malay", "Chinese", "Indian", "Sarawak Natives", "Sabah Natives", "Orang Asli", "Others"};
    String[] courses={"Android Mobile Apps Development", "IOT Fundamental Measurement Science", "Augmented Reality: EON XR Educator", "Certified Digital Marketing Professional", "EON Reality Advanced Certified Developer"};
    String[] locations={"Kuching (Main)", "Lundu (Branch)", "Dalat (Branch)", "Mukah (Branch)", "Lawas (Branch)", "Betong (Branch)"};
    String[] relations={"Mother/Guardian", "Father/Guardian", "Sibling", "Relative", "Spouse", "Friend"};

    AutoCompleteTextView editgender, editrace, editcourse, editlocation, editrelate;
    ArrayAdapter<String> adapterGenders, adapterRaces, adapterCourses, adapterLocations, adapterRelations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_register);

        sr=FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressBar);

        editname=findViewById(R.id.editname);
        editaddress=findViewById(R.id.editaddress);
        editnric=findViewById(R.id.editnric);
        editmobile=findViewById(R.id.editmobile);
        editroom=findViewById(R.id.editroom);
        editid=findViewById(R.id.editid);

        editcontact = findViewById(R.id.editcontact);
        editphone = findViewById(R.id.editphone);

        rgtemail = findViewById(R.id.editemail);
        rgtpassword = findViewById(R.id.editpassword);
        rgtrepassword = findViewById(R.id.editrepassword);

        Button registerbtn = findViewById(R.id.registerbtn);
        registerbtn.setOnClickListener(this);

        Button cancelregister = findViewById(R.id.cancelregister);
        cancelregister.setOnClickListener(this);

        Button clearbtn = findViewById(R.id.clearbtn);
        clearbtn.setOnClickListener(this);

        editgender=findViewById(R.id.editgender);
        adapterGenders=new ArrayAdapter<String>(this, R.layout.dropdown_gender, genders);
        editgender.setAdapter(adapterGenders);

        editgender.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String gender=parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Gender: "+gender, Toast.LENGTH_SHORT).show();
            }
        });

        editrace = findViewById(R.id.editrace);
        adapterRaces=new ArrayAdapter<String>(this, R.layout.dropdown_race, races);
        editrace.setAdapter(adapterRaces);

        editrace.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String ethnicity=parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Ethnicity: "+ethnicity, Toast.LENGTH_SHORT).show();
            }
        });

        editcourse = findViewById(R.id.editcourse);
        adapterCourses=new ArrayAdapter<String>(this, R.layout.dropdown_course, courses);
        editcourse.setAdapter(adapterCourses);

        editcourse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String course=parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Course: "+course, Toast.LENGTH_SHORT).show();
            }
        });

        editlocation = findViewById(R.id.editbranch);
        adapterLocations=new ArrayAdapter<String>(this, R.layout.dropdown_location, locations);
        editlocation.setAdapter(adapterLocations);

        editlocation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String location=parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Location: "+location, Toast.LENGTH_SHORT).show();
            }
        });
        editrelate = findViewById(R.id.editrelate);
        adapterRelations=new ArrayAdapter<String>(this, R.layout.dropdown_relation, relations);
        editrelate.setAdapter(adapterRelations);

        editrelate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String relation=parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Relation: "+relation, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.registerbtn:
                passStudent();
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

        editname.getText().clear();
        editaddress.getText().clear();
        editnric.getText().clear();
        editmobile.getText().clear();
        editroom.getText().clear();
        editid.getText().clear();

        editgender.getText().clear();
        editrace.getText().clear();
        editcourse.getText().clear();
        editlocation.getText().clear();

        editcontact.getText().clear();
        editphone.getText().clear();
        editrelate.getText().clear();

        rgtemail.getText().clear();
        rgtpassword.getText().clear();
        rgtrepassword.getText().clear();
    }

    public void passStudent() {

        String savename=editname.getText().toString().trim();
        String saveaddress=editaddress.getText().toString().trim();
        String savenric=editnric.getText().toString().trim();
        String savemobile=editmobile.getText().toString().trim();
        String saveroom=editroom.getText().toString().trim();
        String saveid=editid.getText().toString().trim();

        String savegender=editgender.getText().toString().trim();
        String saverace=editrace.getText().toString().trim();
        String savecourse=editcourse.getText().toString().trim();
        String savebranch=editlocation.getText().toString().trim();

        String savecontact=editcontact.getText().toString().trim();
        String savephone=editphone.getText().toString().trim();
        String saverelate=editrelate.getText().toString().trim();

        String saveemail=rgtemail.getText().toString().trim();
        String savepassword=rgtpassword.getText().toString().trim();
        String saverepassword=rgtrepassword.getText().toString().trim();

        if(savename.equals("")){
            editname.setError("Fullname is required");
            editname.requestFocus();
            return;
        }
        if(saveaddress.equals("")){
            editaddress.setError("Home address is required");
            editaddress.requestFocus();
            return;
        }
        if(savenric.equals("")){
            editnric.setError("IC number is required");
            editnric.requestFocus();
            return;
        }
        if(savenric.length() !=12){
            editnric.setError("Please provide valid IC Number");
            editnric.requestFocus();
            return;
        }
        if(savemobile.equals("")){
            editmobile.setError("Mobile number is required");
            editmobile.requestFocus();
            return;
        }
        if(savemobile.length() >11){
            editmobile.setError("Please provide valid mobile number");
            editmobile.requestFocus();
            return;
        }
        if(saveroom.equals("")){
            editroom.setError("Room number is required");
            editroom.requestFocus();
            return;
        }
        if(saveid.equals("")){
            editid.setError("Student ID is required");
            editid.requestFocus();
            return;
        }
        if(saveid.length() !=7){
            editid.setError("Please provide valid Student ID");
            editid.requestFocus();
            return;
        }
        if(savegender.equals("")){
            editgender.setError("Gender is required");
            editgender.requestFocus();
            return;
        }
        if(saverace.equals("")){
            editrace.setError("Race is required");
            editrace.requestFocus();
            return;
        }
        if(savecourse.equals("")){
            editcourse.setError("Course is required");
            editcourse.requestFocus();
            return;
        }
        if(savebranch.equals("")){
            editlocation.setError("Branch is required");
            editlocation.requestFocus();
            return;
        }
        if(savecontact.equals("")){
            editcontact.setError("Emergency contact is required");
            editcontact.requestFocus();
            return;
        }
        if(savephone.equals("")){
            editphone.setError("Emergency number is required");
            editphone.requestFocus();
            return;
        }
        if(savephone.length() >11){
            editphone.setError("Please provide valid mobile number");
            editphone.requestFocus();
            return;
        }
        if(saverelate.equals("")){
            editrelate.setError("Relationship is required");
            editrelate.requestFocus();
            return;
        }
        if (saveemail.equals("")) {
            rgtemail.setError("Email is required");
            rgtemail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(saveemail).matches()){
            rgtemail.setError("Please provide valid email");
            rgtemail.requestFocus();
            return;
        }
        if (savepassword.equals("")) {
            rgtpassword.setError("Password is required");
            rgtpassword.requestFocus();
            return;
        }
        if (savepassword.length() < 8) {
            rgtpassword.setError("Minimum password length should be 8 characters");
            rgtpassword.requestFocus();
            return;
        }
        if(saverepassword.equals("")){
            rgtrepassword.setError("Confirm password is required");
            rgtrepassword.requestFocus();
            return;
        }
        if(!saverepassword.equals(savepassword)){
            rgtrepassword.setError("Password is not matching");
            rgtrepassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        sr.createUserWithEmailAndPassword(saveemail,savepassword)
                .addOnCompleteListener(this, task -> {

                if(task.isSuccessful()){
                    StudentProfile student=new StudentProfile (savename, saveaddress, savenric, savemobile, saveroom, saveid, savegender, saverace, savecourse, savebranch, savecontact, savephone, saverelate, saveemail);

                    FirebaseDatabase.getInstance().getReference("Students")
                            .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                            .setValue(student)
                            .addOnCompleteListener(task1 -> {

                                if(task1.isSuccessful()){
                                    Toast.makeText(this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(),StudentDashboard.class));
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
    }
}