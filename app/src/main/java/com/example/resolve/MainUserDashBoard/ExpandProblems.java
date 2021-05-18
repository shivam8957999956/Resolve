package com.example.resolve.MainUserDashBoard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.resolve.AllsolnHelperClass;
import com.example.resolve.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ExpandProblems extends AppCompatActivity {

    String key;
    RecyclerView recyclerView;
    ExpandProblemAdapter expandProblemAdapter;
    ArrayList<AllsolnHelperClass> arrayList;
    TextView subject,time,message,category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand_problems);
        Intent i=getIntent();
        subject=findViewById(R.id.subject);
        category=findViewById(R.id.category);
        message=findViewById(R.id.message);
        time=findViewById(R.id.time);
        category.setText(i.getStringExtra("category"));
        subject.setText(i.getStringExtra("subject"));
        message.setText(i.getStringExtra("message"));
        time.setText(i.getStringExtra("time"));
        key=i.getStringExtra("key");


        recyclerView=findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrayList=new ArrayList<>();
        fetch();

    }

    private void fetch() {
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference myRef=database.getReference("AllSolutions");
        myRef.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot snapshot1:snapshot.getChildren()){
                        AllsolnHelperClass allsolnHelperClass=snapshot1.getValue(AllsolnHelperClass.class);
                        arrayList.add(allsolnHelperClass);
                    }
                    expandProblemAdapter=new ExpandProblemAdapter(getApplicationContext(),arrayList);
                    recyclerView.setAdapter(expandProblemAdapter);
                    expandProblemAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}