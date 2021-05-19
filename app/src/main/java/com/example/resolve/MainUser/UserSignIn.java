package com.example.resolve.MainUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.resolve.MainUserDashBoard.profileHelpclass;
import com.example.resolve.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.leo.simplearcloader.SimpleArcLoader;

public class UserSignIn extends AppCompatActivity {


    //signIn credential
    TextInputLayout addmissionNumber,password,confirmPassword,emailAddress;


    private FirebaseAuth mAuth;


    SimpleArcLoader simpleArcLoader;


    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_in);
        textView=findViewById(R.id.text);
        addmissionNumber=findViewById(R.id.admission_number);
        emailAddress=findViewById(R.id.email_address);
        password=findViewById(R.id.password);
        confirmPassword=findViewById(R.id.comfirm_password);

        simpleArcLoader=findViewById(R.id.loader);

    }
    public  void userSignInFinalStep(View v){
//      startActivity(new Intent(getApplicationContext(),UserSignInFinal.class));
        String adNum=addmissionNumber.getEditText().getText().toString().trim().toLowerCase();
        String email=emailAddress.getEditText().getText().toString().trim().toLowerCase();
        String pass=password.getEditText().getText().toString();
        String conP=confirmPassword.getEditText().getText().toString();
        mAuth = FirebaseAuth.getInstance();
        simpleArcLoader.start();
        simpleArcLoader.setVisibility(View.VISIBLE);
        simpleArcLoader.setVisibility(View.VISIBLE);

   //     Toast.makeText(this, adNum+" "+email , Toast.LENGTH_SHORT).show();
        if(isValidEmail(email)) {
            simpleArcLoader.setVisibility(View.GONE);
            return;
        }
        if(isValidPassword(pass)) {
            simpleArcLoader.setVisibility(View.GONE);
            return;
        }
        if(isAdmission(adNum,email)) {
            simpleArcLoader.setVisibility(View.GONE);
            return;
        }
        if(!conP.equals(pass))
        {
            simpleArcLoader.setVisibility(View.GONE);

            confirmPassword.setError("please write same password");
            return;
        }
        confirmPassword.setError(null);
        confirmPassword.setErrorEnabled(false);

        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("TAG", "createUserWithEmail:onComplete:" + task.isSuccessful());

                        if (task.isSuccessful()) {
                           mAuth.getCurrentUser().sendEmailVerification()
                                   .addOnCompleteListener(new OnCompleteListener<Void>() {
                                       @Override
                                       public void onComplete(@NonNull Task<Void> task) {

                                           if(task.isSuccessful()){
                                               Toast.makeText(UserSignIn.this, "verification email has been sent", Toast.LENGTH_LONG).show();
                                               simpleArcLoader.setVisibility(View.GONE);
                                               FirebaseAuth.getInstance().signOut();
                                               addDataTofirebase();
                                               Intent i=new Intent(getApplicationContext(),UserLogin.class);
                                               i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                               startActivity(i);
                                               finish();
                                           }
                                           else{simpleArcLoader.setVisibility(View.GONE);

                                               Toast.makeText(UserSignIn.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                           }

                                       }
                                   });
                        }
                        else
                        {simpleArcLoader.setVisibility(View.GONE);

                            Toast.makeText(UserSignIn.this,"error", Toast.LENGTH_SHORT).show();
                            // successfully account created
                            // now the AuthStateListener runs the onAuthStateChanged callback
                        }

                        // ...
                    }
                });
    }

    private boolean isAdmission(String adNum,String email){

        if(email.indexOf(adNum)>0)
        {
            addmissionNumber.setError(null);
            addmissionNumber.setErrorEnabled(false);
            return false;
        }
        addmissionNumber.setError("invalid");

        return true;

    }

    private void addDataTofirebase() {
        String adNum=addmissionNumber.getEditText().getText().toString().toLowerCase();
        String email=emailAddress.getEditText().getText().toString().toLowerCase();
        String pass=password.getEditText().getText().toString();
        String conP=confirmPassword.getEditText().getText().toString();

        profileHelpclass profileHelpclass1=new profileHelpclass();
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("AllUsers");
        profileHelpclass1.setName("not set");
        profileHelpclass1.setEmail(email);
        profileHelpclass1.setAdmissionNumber(adNum);
        profileHelpclass1.setIDVerificationStatus("skipped");
        myRef.child(adNum).setValue(profileHelpclass1);

    }

    private boolean isValidPassword(String pass) {
        if(pass.length()>6)
        {
            password.setError(null);
            password.setErrorEnabled(false);
            return false;
        }
        password.setError("Password Length to short");
        return true;
    }

    private boolean isValidEmail(String email) {
        if(email.indexOf(".iitism.ac.in")>0){
            emailAddress.setError(null);
            emailAddress.setErrorEnabled(false);
            return false;

        }
        emailAddress.setError("this email not allowed");
        return false;

    }
}