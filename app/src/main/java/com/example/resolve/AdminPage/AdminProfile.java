package com.example.resolve.AdminPage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.resolve.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.leo.simplearcloader.SimpleArcLoader;

import java.util.concurrent.atomic.DoubleAccumulator;

public class AdminProfile extends AppCompatActivity {

    String email;
    TextView name,Email,position;
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference myRef=database.getReference("Admins");
    LinearLayout linearLayout;
    SimpleArcLoader simpleArcLoader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);

        email= FirebaseAuth.getInstance().getCurrentUser().getEmail();
        linearLayout=findViewById(R.id.main);
        simpleArcLoader=findViewById(R.id.loader);
        name=findViewById(R.id.name);
        linearLayout.setVisibility(View.GONE);
        simpleArcLoader.start();
        Email=findViewById(R.id.email);
        position=findViewById(R.id.position);
        fetchData();
    }

    private void fetchData() {

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1:snapshot.getChildren()){
                    AminDataHelperClass aminDataHelperClass=snapshot1.getValue(AminDataHelperClass.class);
                    if(aminDataHelperClass.getEmail().equalsIgnoreCase(email)){
                        Email.setText(email);
                        name.setText(aminDataHelperClass.getName());
                        position.setText(aminDataHelperClass.getPosition());
                        simpleArcLoader.setVisibility(View.GONE);
                        linearLayout.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}