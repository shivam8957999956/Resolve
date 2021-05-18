package com.example.resolve.AdminPage;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.resolve.AllProblemAdpater;
import com.example.resolve.AllProblemhelperClass;
import com.example.resolve.DetailProblem;
import com.example.resolve.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.leo.simplearcloader.SimpleArcLoader;

import java.util.ArrayList;
import java.util.List;

public class AdminAllProblems extends AppCompatActivity {

    Spinner spinner;
    SimpleArcLoader simpleArcLoader;
    RecyclerView recyclerView;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef,myRef2;

    LinearLayout linearLayout;
    List<String> categoryList,problemkey;
    ArrayAdapter<String> adapter;

    ArrayList<AllProblemhelperClass> adminAllProblems;
    AllProblemAdpater allProblemAdpater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_all_problems);

        spinner=findViewById(R.id.spinner);
        recyclerView=findViewById(R.id.recycler);
        myRef=database.getReference().child("AllProblems");

        linearLayout=findViewById(R.id.main);
        simpleArcLoader=findViewById(R.id.loader);
        simpleArcLoader.setVisibility(View.VISIBLE);
        simpleArcLoader.start();

        adminAllProblems=new ArrayList<AllProblemhelperClass>();
        categoryList = new ArrayList<String>();
        problemkey = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categoryList);
        spinner.setAdapter(adapter);


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fetchData();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int pos, long arg3) {
                // TODO Auto-generated method stub
                adminAllProblems.clear();
                myRef2=database.getReference().child("AllProblems").child(categoryList.get(pos));
                myRef2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot snapshot1:snapshot.getChildren()){
                            problemkey.add(snapshot1.getKey());
                            AllProblemhelperClass allProblemhelperClass=snapshot1.getValue(AllProblemhelperClass.class);
//                            Toast.makeText(AdminAllProblems.this, allProblemhelperClass.getName()
//                                    , Toast.LENGTH_SHORT).show();
                            adminAllProblems.add(allProblemhelperClass);
                        }
                        allProblemAdpater=new AllProblemAdpater(getApplicationContext(),adminAllProblems);
                        recyclerView.setAdapter(allProblemAdpater);
                        allProblemAdpater.notifyDataSetChanged();

                        simpleArcLoader.setVisibility(View.GONE);
                        simpleArcLoader.start();

                        allProblemAdpater.setOnItemClickListener(new AllProblemAdpater.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                Intent i=new Intent(getApplicationContext(), DetailProblem.class);
                                i.putExtra("time",adminAllProblems.get(position).getTime());
                                i.putExtra("message",adminAllProblems.get(position).getMessage());
                                i.putExtra("subject",adminAllProblems.get(position).getSubject());
                                i.putExtra("email",adminAllProblems.get(position).getEmail());
                                i.putExtra("name",adminAllProblems.get(position).getName());
                                i.putExtra("key",problemkey.get(position));
                                startActivity(i);

                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        simpleArcLoader.setVisibility(View.GONE);
                        simpleArcLoader.start();
                    }
                });




            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });




    }

    private void fetchData() {

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                categoryList.clear();
                for (DataSnapshot snapshot1:snapshot.getChildren()){
                categoryList.add(snapshot1.getKey());

                }
                linearLayout.setVisibility(View.VISIBLE);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }
}