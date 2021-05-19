package com.example.resolve.AdminPage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.resolve.MainUser.UserLogin;
import com.example.resolve.MainUserDashBoard.MainUserDashBoard;
import com.example.resolve.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.leo.simplearcloader.SimpleArcLoader;

public class AdminLogin extends AppCompatActivity {

    SimpleArcLoader simpleArcLoader;


    TextInputLayout emailAddress,password;
    FirebaseAuth mAuth;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        simpleArcLoader=findViewById(R.id.loader);
        emailAddress=findViewById(R.id.email_address);
        password=findViewById(R.id.password);
        sharedPreferences=getSharedPreferences("loginCre",MODE_PRIVATE);
    }

    public void completeLogin(View v) {

        String email=emailAddress.getEditText().getText().toString().toLowerCase().trim();
        String pass=password.getEditText().getText().toString().toLowerCase().trim();

        if(email.indexOf("@iitism.ac.in")<0)
        {
            emailAddress.setError("This email domain not allowed");
            return;
        }
        emailAddress.setError(null);
        emailAddress.setErrorEnabled(false);

            if(pass.length()<6)
            {
                password.setError("to short password");
                return;
            }
        password.setError(null);
        password.setErrorEnabled(false);

        simpleArcLoader.start();
        simpleArcLoader.setVisibility(View.VISIBLE);

        mAuth=FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    simpleArcLoader.setVisibility(View.GONE);

                if(FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()){
                    SharedPreferences.Editor editor =sharedPreferences.edit();
                    editor.putString("id",email);

                    editor.commit();
                    startActivity(new Intent(getApplicationContext(), AdminMainDashBoard.class));
                    finish();

                }else{
                    FirebaseAuth.getInstance().signOut();
                    SharedPreferences.Editor editor =sharedPreferences.edit();
                    editor.putString("id","");
                    editor.commit();
                    Toast.makeText(AdminLogin.this, "Verify your Email first!", Toast.LENGTH_SHORT).show();
                }
                }else{
                    simpleArcLoader.setVisibility(View.GONE);
                    Toast.makeText(AdminLogin.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void BackToStudent(View v) {
        startActivity(new Intent(
                getApplicationContext(), UserLogin.class
        ));
        finish();
    }

    public void openSignIn(View v) {
        startActivity(new Intent(
                getApplicationContext(), AdminSignIn.class
        ));

    }
}