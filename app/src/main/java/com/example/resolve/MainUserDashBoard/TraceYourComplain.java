package com.example.resolve.MainUserDashBoard;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.resolve.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.leo.simplearcloader.SimpleArcLoader;

import java.util.ArrayList;
import java.util.List;

public class TraceYourComplain extends AppCompatActivity {

    RecyclerView recyclerView;
    String email,addNm;
    TextView textView;
    ArrayList<UserComplainHelperClass> arrayList;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    UserComplainAdapterClass userComplainAdapterClass;

    SimpleArcLoader simpleArcLoader;
    LinearLayout l;

    List<String> key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trace_your_complain);
        recyclerView=findViewById(R.id.recycler);
        l=findViewById(R.id.main);
        simpleArcLoader=findViewById(R.id.loader);
        textView=findViewById(R.id.text);
        email= FirebaseAuth.getInstance().getCurrentUser().getEmail();
        addNm=email.substring(email.indexOf('.')+1,email.indexOf('@'));
        arrayList=new ArrayList<>();
        key=new ArrayList<>();
        myRef= database.getReference("AllUsers").child(addNm).child("cuurComplains");


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        simpleArcLoader.setVisibility(View.VISIBLE);
        simpleArcLoader.start();

        fetchData();




    }

    private void fetchData() {

        // Write a message to the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();

                for(DataSnapshot snapshot1:snapshot.getChildren()){
                    UserComplainHelperClass userComplainHelperClass=snapshot1.getValue(UserComplainHelperClass.class);
                    arrayList.add(userComplainHelperClass);

                    key.add(snapshot1.getKey());


                }
                simpleArcLoader.setVisibility(View.GONE);
                userComplainAdapterClass=new UserComplainAdapterClass(getApplicationContext(),arrayList);
                recyclerView.setAdapter(userComplainAdapterClass);
                userComplainAdapterClass.notifyDataSetChanged();
                userComplainAdapterClass.setOnItemClickListener(new UserComplainAdapterClass.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Intent i=new Intent(getApplicationContext(),ExpandProblems.class);
                        i.putExtra("category",arrayList.get(position).getCategory());
                        i.putExtra("message",arrayList.get(position).getMessage());
                        i.putExtra("status",arrayList.get(position).getStatus());
                        i.putExtra("time",arrayList.get(position).getTime());
                        i.putExtra("subject",arrayList.get(position).getSubject());
                        i.putExtra("key",key.get(position));
                        startActivity(i);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                simpleArcLoader.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();


    }
}