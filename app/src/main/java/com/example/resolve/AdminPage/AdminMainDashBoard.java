package com.example.resolve.AdminPage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.resolve.AllAnnoucements;
import com.example.resolve.AnnouncementAdapter;
import com.example.resolve.AnnouncementHelperClass;
import com.example.resolve.MainUser.UserLogin;
import com.example.resolve.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminMainDashBoard extends AppCompatActivity {



    AnnouncementAdapter announcementAdapter;
    ArrayList<AnnouncementHelperClass> arrayList;
    RecyclerView recyclerView;
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference ref=database.getReference("AllAnuoncements");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main_dash_board);


        arrayList=new ArrayList<>();
        recyclerView=findViewById(R.id.recycler);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);


        fetch();

    }

    private void fetch() {

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for (DataSnapshot snapshot1:snapshot.getChildren()){
                    AnnouncementHelperClass announcementHelperClass=snapshot1.getValue(AnnouncementHelperClass.class);
                    arrayList.add(announcementHelperClass);
                }

                announcementAdapter=new AnnouncementAdapter(getApplicationContext(),arrayList);
                recyclerView.setAdapter(announcementAdapter);
                announcementAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void signOut(View v) {
        FirebaseAuth.getInstance().signOut();
        SharedPreferences sharedPreferences=getSharedPreferences("loginCre",MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putString("id","");
        Intent i = new Intent(getApplicationContext(), UserLogin.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }
    public  void allProblem(View view){
        startActivity(new Intent(getApplicationContext(),AdminAllProblems.class));
    }public  void profile(View view){
        startActivity(new Intent(getApplicationContext(),AdminProfile.class));
    }public  void makeannounce(View view){
        startActivity(new Intent(getApplicationContext(), AllAnnoucements.class));
    }

}