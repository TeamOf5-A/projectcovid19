package com.example.projectcovid19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class User_Profile extends AppCompatActivity {
    private EditText personname, Email, state, pincode, city;
    private Button submit;

    private FirebaseAuth mAuth;
    private static final String KEY_NAMEOF_PERSON = "nameofperson";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_STATE = "state";
    private static final String KEY_PINCODE = "Pincode";
    private static final String KEY_CITY = "CITY";
    private static final String KEY_PN = "pn";
    private FirebaseUser firebaseUser;
    private String PinCode;
    private FirebaseFirestore db;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        personname = findViewById(R.id.nameofuser);
        Email = findViewById(R.id.Email);
        state = findViewById(R.id.state);
        pincode = findViewById(R.id.pincode);
        city = findViewById(R.id.city);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        submit = findViewById(R.id.submit);

        progressBar  = findViewById(R.id.progress_bar);
        PinCode = pincode.getText().toString();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_baseline_west));
        toolbar.setTitle("Enter The Details");
        toolbar.setTitleTextColor(Color.BLACK);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                Intent intent = new Intent(User_Profile.this, Question.class);
                startActivity(intent);
            }
        });
        upload();
    }
    private void upload() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String nameofperson = personname.getText().toString();
                String email = Email.getText().toString();
                String States = state.getText().toString();
                String Pincode = pincode.getText().toString();
                String CITY = city.getText().toString();
                if (TextUtils.isEmpty(nameofperson)) {
                    personname.setError("Enter the name");
                } else if (TextUtils.isEmpty(email)) {
                    Email.setError("Please enter Course Duration");
                }else if (TextUtils.isEmpty(States)) {
                    state.setError("Please enter Course Duration");
                }else if (TextUtils.isEmpty(Pincode)) {
                    pincode.setError("Please enter Course Duration");
                }else if (TextUtils.isEmpty(CITY)) {
                    city.setError("Please enter Course Duration");
                }

                else {
                    // calling method to add data to Firebase Firestore.
                    String Uid=firebaseUser.getUid();
                    String pn = firebaseUser.getPhoneNumber();
                    Map<String,Object> data = new HashMap<>();
                    data.put(KEY_NAMEOF_PERSON,nameofperson);
                    data.put(KEY_EMAIL,email);
                    data.put(KEY_STATE,States);
                    data.put(KEY_PINCODE,Pincode);
                    data.put(KEY_CITY,CITY);
                    data.put(KEY_PN,pn);
                    db.collection("User_profile").document("User").collection("Users").document(Uid).set(data)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(User_Profile.this, "success", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(User_Profile.this,MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(User_Profile.this, "Failed due to "+e, Toast.LENGTH_SHORT).show();
                        }
                    });



                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        setResult(RESULT_OK, new Intent().putExtra("EXIT", true));
                        finish();
                    }

                }).create().show();
    }
}