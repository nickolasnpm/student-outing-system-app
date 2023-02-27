package com.example.centexsdormitoryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class CheckApplication extends AppCompatActivity {

    private AdminAdapter adapter;
    private List<OutingList> outingLists;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_application);

        RecyclerView recyclerView = findViewById(R.id.recyclerView1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        outingLists=new ArrayList<>();
        adapter=new AdminAdapter(this,outingLists);

        recyclerView.setAdapter(adapter);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Outing")
                .orderBy("gooutdate", Query.Direction.ASCENDING)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    if(!queryDocumentSnapshots.isEmpty()){
                        List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();

                        for(DocumentSnapshot d: list){

                            OutingList p=d.toObject(OutingList.class);
                            assert p != null;
                            p.setId(d.getId());
                            outingLists.add(p);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });

        Button backbtn = findViewById(R.id.backtodashboard);
        backbtn.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), AdminDashboard.class)));

    }
}