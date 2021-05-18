package com.example.resolve.MainUser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.resolve.MainUserDashBoard.MainUserDashBoard;
import com.example.resolve.R;
import com.google.firebase.auth.FirebaseAuth;

import java.security.PublicKey;

public class UserSignInFinal extends AppCompatActivity {

    public static TextView textView;

    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_in_final);
        textView=findViewById(R.id.text);

        linearLayout=findViewById(R.id.scannerId);

    }
    public void StartScanner(View v){
        startActivity(new Intent(getApplicationContext(),UserSignInScanner.class));
    }

    public  void hideScannerID(View v){
        linearLayout.setVisibility(View.GONE);
    }

    public void OpenMainDasBoard(View v){
        Intent i=new Intent(getApplicationContext(), MainUserDashBoard.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }

    public void signOut(View v){
        FirebaseAuth.getInstance().signOut();
        Intent i=new Intent(getApplicationContext(),UserLogin.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }
}
