package com.example.resolve;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

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





    }
}