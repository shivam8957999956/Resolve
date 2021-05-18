package com.example.resolve.MainUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.resolve.MainUserDashBoard.UserProfile;
import com.example.resolve.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class UserSignInScanner extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    ZXingScannerView scannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);


        Dexter.withContext(getApplicationContext())
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        scannerView.startCamera();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    @Override
    public void handleResult(Result rawResult) {
      //  UserProfile.verfi.setText(rawResult.getText());
        String email= FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String adMn=email.substring(email.indexOf('.')+1,email.indexOf('@'));

        String res=rawResult.getText();
        String name=res.substring(0,res.indexOf('\n'));
        String remain=res.substring(res.indexOf('\n')+1);
        String ad=remain.substring(0,remain.indexOf('\n')).toLowerCase();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("AllUsers");

        if(ad.equalsIgnoreCase(adMn))
        {
            myRef.child(adMn).child("idverificationStatus").setValue("verified");
            myRef.child(adMn).child("name").setValue(name);
            UserProfile.name.setText(name);
            UserProfile.verfi.setText("verified");

            UserProfile.stat.setText("Verification Completed");
            UserProfile.scanner.setVisibility(View.GONE);

        }else{
            UserProfile.stat.setText("Admission Number Miss match found scan properly!");

        }

        onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }
}