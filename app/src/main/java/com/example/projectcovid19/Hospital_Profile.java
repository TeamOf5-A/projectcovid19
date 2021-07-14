package com.example.projectcovid19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hospital_Profile extends AppCompatActivity {
    private EditText hospitalname, personname, Email, regno, pincode, city;
    private Button submit;

    private FirebaseAuth mAuth;
    private static final String KEY_HOSPITAL_NAME = "nameofhospital";
    private static final String KEY_NAMEOF_PERSON = "nameofperson";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_REGNO = "Hospitalregno";
    private static final String KEY_PINCODE = "Pincode";
    private static final String Key_accounttype = "Account";

    private static final String KEY_CITY = "CITY";
    private FirebaseUser firebaseUser;
    private String PinCode;
    private FirebaseFirestore db;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_profile);
        hospitalname = findViewById(R.id.nameofhospital);
        personname = findViewById(R.id.personname);
        Email = findViewById(R.id.Email);
        regno = findViewById(R.id.Regno);
        pincode = findViewById(R.id.pincode);
        city = findViewById(R.id.city);
        submit = findViewById(R.id.submit);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();

        progressBar  = findViewById(R.id.progress_bar);
        PinCode = pincode.getText().toString();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_baseline_west));
        toolbar.setTitle("Enter The Details");
        toolbar.setTitleTextColor(Color.BLACK);

      //  getDataFromPinCode(PinCode);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                Intent intent = new Intent(Hospital_Profile.this, Question.class);
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
                String nameofhospital = hospitalname.getText().toString();
                String nameofperson = personname.getText().toString();
                String email = Email.getText().toString();
                String Hospitalregno = regno.getText().toString();
                String Pincode = pincode.getText().toString();
                String CITY = city.getText().toString();
                if (TextUtils.isEmpty(nameofhospital)) {
                    hospitalname.setError("Please enter Course Name");
                } else if (TextUtils.isEmpty(nameofperson)) {
                    personname.setError("Please enter Course Description");
                } else if (TextUtils.isEmpty(email)) {
                    Email.setError("Please enter Course Duration");
                }
                else if (TextUtils.isEmpty(Hospitalregno)) {
                    regno.setError("Please enter Course Duration");
                }
                else if (TextUtils.isEmpty(Pincode)) {
                    pincode.setError("Please enter Course Duration");
                }

                else if (TextUtils.isEmpty(CITY)) {
                    city.setError("Please enter Course Duration");
                }
                else {
                    // calling method to add data to Firebase Firestore.
                    String Uid=firebaseUser.getUid();
                    Map<String,Object> data = new HashMap<>();
                    data.put(KEY_HOSPITAL_NAME,nameofhospital);
                    data.put(KEY_NAMEOF_PERSON,nameofperson);
                    data.put(KEY_EMAIL,email);
                    data.put(KEY_REGNO,Hospitalregno);
                    data.put(KEY_PINCODE,Pincode);
                    data.put(KEY_CITY,CITY);
                    data.put(Key_accounttype,"Hospital");
                    db.collection("Hospital").document("Hospital Profile").collection("Users").document(Uid).set(data)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(Hospital_Profile.this, "success", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Hospital_Profile.this,MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(Hospital_Profile.this, "Failed due to "+e, Toast.LENGTH_SHORT).show();
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