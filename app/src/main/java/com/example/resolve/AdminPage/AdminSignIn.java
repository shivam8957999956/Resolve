package com.example.resolve.AdminPage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.resolve.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminSignIn extends AppCompatActivity {

    TextInputLayout nameAdmin,position,emailAddress,password,confirmPassword;


    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_sign_in);

        emailAddress=findViewById(R.id.email_address);
        password=findViewById(R.id.password);
        confirmPassword=findViewById(R.id.comfirm_password);
        nameAdmin=findViewById(R.id.admin_name);
        position=findViewById(R.id.admin_position);


    }

    public void adminSignIN(View v){


        String email=emailAddress.getEditText().getText().toString().toLowerCase().trim();
        String name=nameAdmin.getEditText().getText().toString().toLowerCase().trim();
        String pos=position.getEditText().getText().toString().toLowerCase().trim();
        String pass=password.getEditText().getText().toString().toLowerCase().trim();
        String conPass=confirmPassword.getEditText().getText().toString().toLowerCase().trim();
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

        if(conPass.compareTo(pass)!=0){

            confirmPassword.setError("mismatch password");
            return;
        }
        confirmPassword.setError(null);
        confirmPassword.setErrorEnabled(false);

        if(name.length()==0)
        {
            nameAdmin.setError("cannot be empty");
            return;
        }
        nameAdmin.setError(null);
        nameAdmin.setErrorEnabled(false);
        if(pos.length()==0)
        {
            position.setError("cannot be empty");
            return;
        }
        position.setError(null);
        position.setErrorEnabled(false);

        mAuth=FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(AdminSignIn.this, "Successfully registered", Toast.LENGTH_SHORT).show();
                    FirebaseAuth.getInstance().getCurrentUser()
                            .sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){

                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference("Admins");
                                String key=myRef.push().getKey();
                                AminDataHelperClass aminDataHelperClass=new AminDataHelperClass();
                                aminDataHelperClass.setEmail(email);
                                aminDataHelperClass.setName(name);
                                aminDataHelperClass.setPosition(pos);
                                myRef.child(key).setValue(aminDataHelperClass);



                                FirebaseAuth.getInstance().signOut();
                                Toast.makeText(AdminSignIn.this, "Verification mail  has been sent", Toast.LENGTH_LONG).show();
                                Intent i=new Intent(getApplicationContext(),AdminLogin.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
                                finish();
                            }else{
                                Toast.makeText(AdminSignIn.this, "Some error while sending mail", Toast.LENGTH_LONG).show();
                            }
                        }
                    });


                }else{
                    Toast.makeText(AdminSignIn.this, "error while registering", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

}