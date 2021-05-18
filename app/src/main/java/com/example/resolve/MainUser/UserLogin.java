package com.example.resolve.MainUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.resolve.AdminPage.AdminLogin;
import com.example.resolve.MainUserDashBoard.MainUserDashBoard;
import com.example.resolve.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.leo.simplearcloader.SimpleArcLoader;

public class UserLogin extends AppCompatActivity {

    TextInputLayout addmissionNumber,password;
    Button loginBtn;


    SimpleArcLoader arcLoader;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        loginBtn=findViewById(R.id.loginBtn);
        addmissionNumber=findViewById(R.id.admission_number);
        password=findViewById(R.id.password);

        arcLoader=findViewById(R.id.loader);

    }
    public void openSignIn(View v){
        startActivity(new Intent(getApplicationContext(),UserSignIn.class));
    }



    public void completeLogin(View v){
        arcLoader.start();
        arcLoader.setVisibility(View.VISIBLE);

        String adNum=addmissionNumber.getEditText().getText().toString().toLowerCase().trim();
        String pass=password.getEditText().getText().toString().toLowerCase().trim();
        if(adNum.length()==0)
        {
            arcLoader.setVisibility(View.GONE);
            addmissionNumber.setError("enter the addmission number");
            return;
        }
        addmissionNumber.setError(null);
        addmissionNumber.setErrorEnabled(false);

        if(pass.length()<6)
        {
            arcLoader.setVisibility(View.GONE);
            password.setError("Too short password");
            return;
        }
        password.setError(null);
        password.setErrorEnabled(false);


        Query checkUser=FirebaseDatabase.getInstance().getReference("AllUsers").orderByKey().equalTo(adNum);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    String email=snapshot.child(adNum).child("email").getValue(String.class);
                    String idverfication=snapshot.child(adNum).child("idverificationStatus").getValue(String.class);

                    userLogin(email,pass,idverfication);


                    }
                    else{
                    arcLoader.setVisibility(View.GONE);
                    Toast.makeText(UserLogin.this,"You are Not registered", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                arcLoader.setVisibility(View.GONE);
            }
        });


    }

    private void userLogin(String email, String pass,String idverfication) {

        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    if(mAuth.getCurrentUser().isEmailVerified()){

                        SharedPreferences sharedPreferences=getSharedPreferences("loginCre",MODE_PRIVATE);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString("id",email);
                        editor.commit();

                        if(idverfication.equals("skipped")){
                            Toast.makeText(UserLogin.this, "Complete Your ID vefication IN profile", Toast.LENGTH_SHORT).show();
                        }

                                startActivity(new Intent(getApplicationContext(), MainUserDashBoard.class));
                                finish();

                    }else{
                        arcLoader.setVisibility(View.GONE);
                        SharedPreferences sharedPreferences=getSharedPreferences("loginCre",MODE_PRIVATE);
                        SharedPreferences.Editor editor =sharedPreferences.edit();
                        editor.putString("id","");
                        FirebaseAuth.getInstance().signOut();
                        editor.commit();
                        Toast.makeText(UserLogin.this, "Verify Your Email first\n the Link sent To your email!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    arcLoader.setVisibility(View.GONE);
                    Toast.makeText(UserLogin.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });


    }


    public void openAdminPage(View v){
        startActivity(new Intent(getApplicationContext(), AdminLogin.class));
        finish();
    }


}