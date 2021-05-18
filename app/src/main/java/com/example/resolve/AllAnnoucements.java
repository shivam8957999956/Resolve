package com.example.resolve;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AllAnnoucements extends AppCompatActivity {
    TextInputLayout title;
    EditText annouce;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_annoucements);

        title=findViewById(R.id.title);
        annouce=findViewById(R.id.annouce);

    }
    public  void submit(View v){

        String tit=title.getEditText().getText().toString().trim();
        String anun=annouce.getText().toString().trim();
        String email= FirebaseAuth.getInstance().getCurrentUser().getEmail();

        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference ref=database.getReference("AllAnuoncements");
        AnnouncementHelperClass announcementHelperClass=new AnnouncementHelperClass(email,tit,anun);
        String key=ref.push().getKey();
        ref.child(key).setValue(announcementHelperClass);
        Toast.makeText(this, "Submitted", Toast.LENGTH_SHORT).show();
        annouce.setText("");
    }
}