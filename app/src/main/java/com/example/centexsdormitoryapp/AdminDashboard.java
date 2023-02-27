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

public class AdminDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_dashboard);

        //auto log-in once sign-up

        Button logoutbtn = findViewById(R.id.logoutbtn);
        Button checkallbtn = findViewById(R.id.checkallbtn);

        logoutbtn.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        });
        checkallbtn.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), CheckApplication.class)));

        FirebaseUser admin = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Admin");
        assert admin != null;
        String adminID = admin.getUid();

        final TextView savename= findViewById(R.id.adminname);
        final TextView savenumber=findViewById(R.id.adminnumber);
        final TextView savebranch=findViewById(R.id.adminbranch);
        final TextView saveemail= findViewById(R.id.adminemail);

        reference
                .child(adminID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                AdminProfile userProfile =snapshot.getValue(AdminProfile.class);

                if(userProfile !=null){

                    String namesave=userProfile.savename;
                    String branchsave=userProfile.savebranch;
                    String emailsave=userProfile.saveemail;
                    String numbersave=userProfile.savenumber;

                    savename.setText(namesave);
                    savebranch.setText(branchsave);
                    saveemail.setText(emailsave);
                    savenumber.setText(numbersave);
                }
    }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AdminDashboard.this, "Something wrong. Please try again", Toast.LENGTH_SHORT).show();
            }
        });
    }
}