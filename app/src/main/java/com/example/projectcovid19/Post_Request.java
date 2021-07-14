package com.example.projectcovid19;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class Post_Request extends AppCompatActivity {
    Spinner Spinner;

    private TextView Datepicker;
    String user = "User";
    private String selectedCountry = null;
    private Button submit;
    DatePickerDialog datePickerDialog;
    Context context;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private FirebaseUser firebaseUser;
    private static final String KEYspinner = "request";
    private static final String KEYdate = "date";
    private static final String KEYUsername = "username";
    private static final String KEYPincode = "Pincode";
    private static final String KEYCity = "CITY";

    String[] request = {"Need covid-19 medicines", "Need Vaccine", "Need Food", "Need Hospital consultancy", "Need Oxygen Bed"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_request);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        Spinner = findViewById(R.id.spinner);
        submit = findViewById(R.id.submit);
        progressBar = findViewById(R.id.progress_bar);
        Spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());

        //Dynamically generate a spinner data 
        createSpinnerDropDown();
        Datepicker = findViewById(R.id.Date);
        Datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(Post_Request.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                Datepicker.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        updateHospitalRequest();
    }

    private void updateHospitalRequest() {
        String Uid = firebaseUser.getUid();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String requesttext = Spinner.getSelectedItem().toString();
                String date = Datepicker.getText().toString();
                if (TextUtils.isEmpty(requesttext)) {
                    Toast.makeText(context, "Select the request you needed", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(date)) {
                    Datepicker.setError("Please enter Non Icu Beds");
                } else {
                    // calling method to add data to Firebase Firestore.

                    Map<String, Object> data = new HashMap<>();
                    data.put(KEYspinner, requesttext);
                    data.put(KEYdate, date);
                    db.collection("User").document("User profile").collection("Users").document(Uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                if (task.getResult().exists()) {
                                    String account_type = task.getResult().getString("Account");
                                    String username = task.getResult().getString("nameofperson");
                                    String city = task.getResult().getString("CITY");
                                    String Pincode = task.getResult().getString("Pincode");
                                    try {
                                        if (account_type.equals(user)) {
                                            db.collection("User").document("Request").collection(Uid).add(data)
                                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                        @Override
                                                        public void onSuccess(DocumentReference documentReference) {
                                                            progressBar.setVisibility(View.INVISIBLE);

                                                            Toast.makeText(Post_Request.this, "successfully posted ", Toast.LENGTH_SHORT).show();
                                                            Intent intent = new Intent(Post_Request.this, Request.class);
                                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                            startActivity(intent);
                                                            finish();
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    progressBar.setVisibility(View.INVISIBLE);
                                                    Toast.makeText(Post_Request.this, "Failed due to " + e, Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }

                                    } catch (Exception e) {
                                        Log.d(TAG, "get failed with " + e);
                                    }
                                }else {
                                    db.collection("Hospital").document("Request").collection(Uid).add(data)
                                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                @Override
                                                public void onSuccess(DocumentReference documentReference) {
                                                    progressBar.setVisibility(View.INVISIBLE);

                                                    Toast.makeText(Post_Request.this, "successfully posted ", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(Post_Request.this, Request.class);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            progressBar.setVisibility(View.INVISIBLE);
                                            Toast.makeText(Post_Request.this, "Failed due to " + e, Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        }
                    });


                }
            }
        });

    }

    private void createSpinnerDropDown() {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, request);
        //set the view for the Drop down list
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //set the ArrayAdapter to the spinner
        Spinner.setAdapter(dataAdapter);
        //attach the listener to the spinner
        Spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());

    }

    public class MyOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

            String selectedItem = parent.getItemAtPosition(pos).toString();

            //check which spinner triggered the listener
            switch (parent.getId()) {
                //country spinner
                case R.id.spinner:
                    //make sure the country was already selected during the onCreate
                    if (selectedCountry != null) {
                        Toast.makeText(parent.getContext(), "Request you selected is " + selectedItem,
                                Toast.LENGTH_LONG).show();
                    }
                    selectedCountry = selectedItem;
                    break;

            }


        }

        public void onNothingSelected(AdapterView<?> parent) {
            // Do nothing.
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Post_Request.this, Request.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


}