package com.example.resolve;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.resolve.AdminPage.AminDataHelperClass;
import com.example.resolve.MainUser.UserLogin;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class DetailProblem extends AppCompatActivity {
    TextView name,subject,email,time,message;
    Button open,finish;
    EditText respose;
    LinearLayout linearLayout;
    String key,key3;
    String sendname;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("AllSolutions");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_problem);

        name=findViewById(R.id.name);
        subject=findViewById(R.id.subject);
        respose=findViewById(R.id.response);
        finish=findViewById(R.id.finish);
        email=findViewById(R.id.email);
        time=findViewById(R.id.time);
        message=findViewById(R.id.message);
        open=findViewById(R.id.open);
        linearLayout=findViewById(R.id.response_layout);

        Intent i=getIntent();
        key=i.getStringExtra("key");
        name.setText("Asked by: "+i.getStringExtra("name"));
        email.setText("Email: "+i.getStringExtra("email"));
        subject.setText(i.getStringExtra("subject"));
        time.setText("at: "+i.getStringExtra("time"));
        message.setText(i.getStringExtra("message"));

        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);

            }
        });
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String edit=respose.getText().toString().trim();
                if(edit.isEmpty()){
                    respose.setError("please explain.");

                }else{
                    respose.setError(null);
                    String key2=myRef.push().getKey();
                    Calendar calendar=Calendar.getInstance();
                    java.text.SimpleDateFormat simpleDateFormat=new java.text.SimpleDateFormat("dd-mm-yyyy hh:mm:ss a");
                    String dateTime=simpleDateFormat.format(calendar.getTime());

                    String email= FirebaseAuth.getInstance().getCurrentUser().getEmail();
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myAdmin = database.getReference("Admins");
                    myAdmin.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                                AminDataHelperClass aminDataHelperClass=dataSnapshot.getValue(AminDataHelperClass.class);
                                if(aminDataHelperClass.getEmail().equalsIgnoreCase(email)){
                                    key3=dataSnapshot.getKey();
                                    sendname=aminDataHelperClass.getName().toString();
                                }
                            }
                            AllsolnHelperClass allsolnHelperClass=new AllsolnHelperClass();

                            allsolnHelperClass.setEmail(email);
                            allsolnHelperClass.setName(sendname);
                            allsolnHelperClass.setSolution(edit);
                            allsolnHelperClass.setTime(dateTime);

                            myRef.child(key).child(key3).setValue(allsolnHelperClass);
                            Toast.makeText(DetailProblem.this, "Solution Submitted", Toast.LENGTH_SHORT).show();
                            respose.setText("");
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

            }
        });
    }
}