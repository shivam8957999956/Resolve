package com.example.resolve;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.resolve.AdminPage.AdminMainDashBoard;
import com.example.resolve.MainUser.UserLogin;
import com.example.resolve.MainUser.UserSignInFinal;
import com.example.resolve.MainUserDashBoard.MainUserDashBoard;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(FirebaseAuth.getInstance().getCurrentUser()!=null)
                {
                    String firebaseUser=FirebaseAuth.getInstance().getCurrentUser().getEmail();
                    SharedPreferences sharedPreferences=getSharedPreferences("loginCre",MODE_PRIVATE);
                    String email=sharedPreferences.getString("id","");
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("id",firebaseUser);
                    editor.commit();
                    //  Toast.makeText(MainActivity.this, email, Toast.LENGTH_SHORT).show();
                    if(email.indexOf(".iitism.ac.in")>0) {
                        startActivity(new Intent(getApplicationContext(), MainUserDashBoard.class));
                        finish();
                    }else{
                        startActivity(new Intent(getApplicationContext(), AdminMainDashBoard.class));
                        finish();
                    }
                }else {
                    startActivity(new Intent(getApplicationContext(), UserLogin.class));
                    finish();
                }
            }
        },2000);

    }
}