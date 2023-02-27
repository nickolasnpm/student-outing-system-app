package com.example.centexsdormitoryapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class TryCheckStatus extends AppCompatActivity implements FirebaseAuth.AuthStateListener, TryAdapter.NoteListener {

    RecyclerView recyclerView;
    TryAdapter tryAdapter;
    View tryStatusDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.try_check_status);

        recyclerView=findViewById(R.id.recyclerView);

        Button backbtn = findViewById(R.id.backtodashboard);
        backbtn.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), StudentDashboard.class)));

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();

        FirebaseAuth.getInstance().removeAuthStateListener(this);

        if (tryAdapter !=null){
            tryAdapter.stopListening();
        }
    }

    @SuppressWarnings("StringOperationCanBeSimplified")
    @SuppressLint("InflateParams")
    @Override
    public void viewapplicationstatus(DocumentSnapshot snapshot) {

        OutingList outingList = snapshot.toObject(OutingList.class);
        tryStatusDetails = getLayoutInflater().inflate(R.layout.try_status_details, null);

        TextView namelist = tryStatusDetails.findViewById(R.id.applicantname);
        TextView numberlist = tryStatusDetails.findViewById(R.id.applicantnumber);
        TextView destinationlist = tryStatusDetails.findViewById(R.id.destination);
        TextView reasonlist = tryStatusDetails.findViewById(R.id.reason);
        TextView comeinlist = tryStatusDetails.findViewById(R.id.datecomein);
        TextView gooutlist = tryStatusDetails.findViewById(R.id.dategoout);
        TextView statuslist = tryStatusDetails.findViewById(R.id.status);

        assert outingList != null;
        namelist.setText(outingList.getOutingname().toString());
        numberlist.setText(outingList.getOutingnumber().toString());
        destinationlist.setText(outingList.getOutingdestination().toString());
        reasonlist.setText(outingList.getOutingreason().toString());
        comeinlist.setText(outingList.getComeindate().toString());
        gooutlist.setText(outingList.getGooutdate().toString());
        statuslist.setText(outingList.getOutingstatus().toString());

        new AlertDialog.Builder(this)
                .setTitle("Application Status")
                .setView(tryStatusDetails)
                .setPositiveButton("Okay", null)
                .show();

    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

        if(firebaseAuth.getCurrentUser() != null){
            clickRecyclerView(firebaseAuth.getCurrentUser());
        }
    }

    private void clickRecyclerView(FirebaseUser currentUser) {

        Query query = FirebaseFirestore.getInstance()
                .collection("Outing")
                .whereEqualTo("userId", currentUser.getUid())
                .orderBy("gooutdate", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<OutingList> options = new FirestoreRecyclerOptions.Builder<OutingList>()
                .setQuery(query, OutingList.class)
                .build();

        tryAdapter = new TryAdapter(options, this);
        recyclerView.setAdapter(tryAdapter);

        tryAdapter.startListening();
    }
}
