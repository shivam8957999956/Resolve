package com.example.resolve.MainUserDashBoard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.resolve.MainUser.UserLogin;
import com.example.resolve.MainUser.UserSignInScanner;
import com.example.resolve.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class UserProfile extends AppCompatActivity {

    LinearLayout linearLayout;

    public  static String f="";
    String email,adMn;
    public static TextView name,emailT,admT,verfi,stat;
    public static Button scanner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        email=FirebaseAuth.getInstance().getCurrentUser().getEmail();
        adMn=email.substring(email.indexOf('.')+1,email.indexOf('@'));
        scanner=findViewById(R.id.start_scanner);
        name=findViewById(R.id.name);
        stat=findViewById(R.id.stat);
        emailT=findViewById(R.id.email);
        admT=findViewById(R.id.admission_number);
        verfi=findViewById(R.id.verifyID);
        emailT.setText(email);
        admT.setText(adMn);
        linearLayout=findViewById(R.id.main);
        linearLayout.setVisibility(View.GONE);
        scanner.setVisibility(View.GONE);

        fetchData();

        scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), UserSignInScanner.class));

            }
        });



    }

    private void fetchData() {

        Query checkUser= FirebaseDatabase.getInstance().getReference("AllUsers").orderByKey().equalTo(adMn);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                 //   String email=snapshot.child(adMn).child("email").getValue(String.class);
                    verfi.setText(snapshot.child(adMn).child("idverificationStatus").getValue(String.class));
                    name.setText(snapshot.child(adMn).child("name").getValue(String.class));
                    if(verfi.getText().equals("skipped"))
                        scanner.setVisibility(View.VISIBLE);

                    linearLayout.setVisibility(View.VISIBLE);

                }
                else{

                    Toast.makeText(UserProfile.this,"You are Not registered", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();


    }
}