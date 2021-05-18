package com.example.resolve.MainUserDashBoard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.resolve.MainUser.UserLogin;
import com.example.resolve.R;
import com.google.firebase.auth.FirebaseAuth;

public class MainUserDashBoard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_user_dash_board);
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

    public void registerNewComplains(View v) {
        startActivity(new Intent(getApplicationContext(), RegisterNewComplain.class));
    }

    public void traceYourComplain(View v) {
        startActivity(new Intent(getApplicationContext(), TraceYourComplain.class));
    }
public void profile(View v) {
        startActivity(new Intent(getApplicationContext(), UserProfile.class));
    }


}