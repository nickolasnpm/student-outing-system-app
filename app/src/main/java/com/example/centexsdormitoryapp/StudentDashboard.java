package com.example.centexsdormitoryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_dashboard);

        //auto log-in once sign-up

        Button logoutbtn = findViewById(R.id.logoutbtn);
        Button applyouting = findViewById(R.id.applyouting);
        Button checkstatus = findViewById(R.id.checkstatus);
        Button scankit = findViewById(R.id.scankit);

        logoutbtn.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        });

        applyouting.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), ApplyOuting.class)));

        checkstatus.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), TryCheckStatus.class)));

        scankit.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), ScanKit.class)));

        FirebaseUser student = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Students");
        assert student != null;
        String studentID = student.getUid();

        final TextView savename= findViewById(R.id.editname);
        final TextView saveaddress= findViewById(R.id.editaddress);
        final TextView savenric= findViewById(R.id.editnric);
        final TextView savemobile= findViewById(R.id.editmobile);
        final TextView saveroom= findViewById(R.id.editroom);
        final TextView saveid=findViewById(R.id.editid);

        final TextView savegender= findViewById(R.id.editgender);
        final TextView saverace= findViewById(R.id.editrace);
        final TextView savecourse= findViewById(R.id.editcourse);
        final TextView savebranch= findViewById(R.id.editbranch);

        final TextView savecontact= findViewById(R.id.editcontact);
        final TextView savephone= findViewById(R.id.editphone);
        final TextView saverelate= findViewById(R.id.editrelate);

        final TextView saveemail= findViewById(R.id.editemail);

        reference.child(studentID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                StudentProfile userProfile =snapshot.getValue(StudentProfile.class);

                if(userProfile !=null){

                    //retrieve data from class

                    String namesave=userProfile.savename;
                    String addresssave=userProfile.saveaddress;
                    String nricsave=userProfile.savenric;
                    String mobilesave=userProfile.savemobile;
                    String roomsave=userProfile.saveroom;
                    String idsave=userProfile.saveid;

                    String gendersave=userProfile.savegender;
                    String racesave=userProfile.saverace;
                    String coursesave=userProfile.savecourse;
                    String branchsave=userProfile.savebranch;

                    String contactsave=userProfile.savecontact;
                    String phonesave=userProfile.savephone;
                    String relatesave=userProfile.saverelate;

                    String emailsave=userProfile.saveemail;

                    //display all data in xml

                    savename.setText(namesave);
                    saveaddress.setText(addresssave);
                    savenric.setText(nricsave);
                    savemobile.setText(mobilesave);
                    saveroom.setText(roomsave);
                    saveid.setText(idsave);

                    savegender.setText(gendersave);
                    saverace.setText(racesave);
                    savecourse.setText(coursesave);
                    savebranch.setText(branchsave);

                    savecontact.setText(contactsave);
                    savephone.setText(phonesave);
                    saverelate.setText(relatesave);

                    saveemail.setText(emailsave);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(StudentDashboard.this, "Something wrong. Please try again", Toast.LENGTH_SHORT).show();
            }
        });

    }

}