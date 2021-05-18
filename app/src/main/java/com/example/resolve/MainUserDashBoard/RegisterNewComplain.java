package com.example.resolve.MainUserDashBoard;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.resolve.AllProblemhelperClass;
import com.example.resolve.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class RegisterNewComplain extends AppCompatActivity {

    Spinner spinner;
    int positionSpinner = 0;
    TextInputLayout name, subject;
    EditText text;
    String categoryName = "No categories";

    String[] arraySpinner = new String[]{"select the category of problems",
            "Ask to all",
            "Administrative relevant issues",
            "Safety and security of students",
            "Day-to-day non-academic problems of the students",
            "Solving academic grievances of the students",
            "Cleanliness and Hygiene on the college campus",
            "Monitoring work of hostel employees",
            "Transportation facilities",
            "Sports and Co-curricular activities",
            "Redressal of basic amenities in hostel",
            "Cleanliness and hygiene in hostel and campus",
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new_complain);

        name = findViewById(R.id.applicant_name);
        subject = findViewById(R.id.subject);
        text = findViewById(R.id.text_message);


        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int pos, long arg3) {
                // TODO Auto-generated method stub

                positionSpinner = pos;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

    }

    public void preview(View v) {

        String Nam, sub, mes;
        Nam = name.getEditText().getText().toString().trim();
        sub = subject.getEditText().getText().toString().trim();
        mes = text.getText().toString().trim();

        if (Nam.isEmpty()) {
            name.setError("Enter the name");
            return;
        }
        name.setError(null);
        name.setErrorEnabled(false);
        if (positionSpinner == 0) {
            Toast.makeText(this, "Select the Categories!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (sub.isEmpty()) {
            subject.setError("Enter the subject");
            return;
        }
        subject.setError(null);
        subject.setErrorEnabled(false);
        if (mes.isEmpty()) {
            text.setError("Enter the message");
            return;
        }
        text.setError(null);

        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(RegisterNewComplain.this);

        // Set the message show for the Alert time
        builder.setMessage("Name : "+Nam + "\n\nsubject : " + sub + "\n\nCategory : "
                + arraySpinner[positionSpinner]+"\n\nMessage : "+mes);

        // Set Alert Title
        builder.setTitle("Preview");

        // Set Cancelable false
        // for when the user clicks on the outside
        // the Dialog Box then it will remain show
        builder.setCancelable(false);

        // Set the positive button with yes name
        // OnClickListener method is use of
        // DialogInterface interface.

        builder
                .setPositiveButton(
                        "Yes",
                        new DialogInterface
                                .OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {

                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference("AllProblems");
                                String key=myRef.push().getKey();
                                Toast.makeText(RegisterNewComplain.this, "complain registered", Toast.LENGTH_SHORT).show();
                                String email= FirebaseAuth.getInstance().getCurrentUser().getEmail();

                                Calendar calendar=Calendar.getInstance();
                                java.text.SimpleDateFormat simpleDateFormat=new java.text.SimpleDateFormat("dd-mm-yyyy hh:mm:ss a");
                                String dateTime=simpleDateFormat.format(calendar.getTime());





                                Long tsLong = System.currentTimeMillis();
                                String ts = tsLong.toString();
                                String AdmNo=email.substring(email.indexOf('.')+1,email.indexOf("@"));

                                AllProblemhelperClass allProblemhelperClass=new AllProblemhelperClass();
                                allProblemhelperClass.setEmail(email);
                                allProblemhelperClass.setMessage(mes);
                                allProblemhelperClass.setName(Nam);
                                allProblemhelperClass.setStatus("not executed");
                                allProblemhelperClass.setTime(dateTime);
                                allProblemhelperClass.setSubject(sub);
                                myRef.child(arraySpinner[positionSpinner]).child(key).setValue(allProblemhelperClass);

                                DatabaseReference databaseReference=database.getReference("AllUsers");
                                String key1=key;

                                UserComplainHelperClass userComplainHelperClass=new UserComplainHelperClass();
                                userComplainHelperClass.setCategory(arraySpinner[positionSpinner]);
                                userComplainHelperClass.setSubject(sub);

                                userComplainHelperClass.setTime(dateTime);
                                userComplainHelperClass.setMessage(mes);
                                userComplainHelperClass.setStatus("not executed");
                                databaseReference.child(AdmNo).child("cuurComplains").child(key1).setValue(userComplainHelperClass);


                            }
                        });

        builder
                .setNegativeButton(
                        "No",
                        new DialogInterface
                                .OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {

                                dialog.cancel();
                            }
                        });
        AlertDialog alertDialog = builder.create();


        alertDialog.show();

    }
}