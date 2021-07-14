package com.example.projectcovid19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class Post_Availability extends AppCompatActivity {
    private EditText icubeds, nonicubeds, vaccines, drugs, oxygenBeds, o2concentration, Plasmadonors;
    private Button submit,edit,delete;
    private static final String KEYIcubeds = "Icubeds";
    private static final String KEYNonIcubeds = "nonIcubeds";
    private static final String KEYVaccines = "Vaccines";
    private static final String KEYDrugs = "Drugs";
    private static final String KEYOxygenBeds = "Oxygenbeds";
    private static final String KEYO2concentration = "o2conc";
    private static final String KEYPlasmaDonors = "plasmadonors";

    private FirebaseAuth mAuth;


    private static final String KEY_CITY = "CITY";
    private FirebaseUser firebaseUser;
    private String PinCode;
    private FirebaseFirestore db;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_availability);
        icubeds = findViewById(R.id.icubeds);
        nonicubeds = findViewById(R.id.nonicu);
        vaccines = findViewById(R.id.vaccines);
        drugs = findViewById(R.id.coviddrugs);
        oxygenBeds = findViewById(R.id.o2beds);
        o2concentration = findViewById(R.id.o2conc);
        Plasmadonors = findViewById(R.id.plasmadonors);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
         submit = findViewById(R.id.submit);
         edit = findViewById(R.id.Edit);
         delete = findViewById(R.id.Delete);
        progressBar = findViewById(R.id.progress_bar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Post Availability");
        toolbar.setTitleTextColor(Color.BLACK);
        upload();


    }

    private void upload() {
        String Uid = firebaseUser.getUid();




            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressBar.setVisibility(View.VISIBLE);
                    String Icubeds = icubeds.getText().toString();
                    String NonIcubeds = nonicubeds.getText().toString();
                    String Vaccines = vaccines.getText().toString();
                    String Drugs = drugs.getText().toString();
                    String OxygenBeds = oxygenBeds.getText().toString();
                    String O2concentration = o2concentration.getText().toString();
                    String plasmadonors = Plasmadonors.getText().toString();

                    if (TextUtils.isEmpty(Icubeds)) {
                        icubeds.setError("Please enter No of Icu Beds");
                    } else if (TextUtils.isEmpty(NonIcubeds)) {
                        nonicubeds.setError("Please enter Non Icu Beds");
                    } else if (TextUtils.isEmpty(Vaccines)) {
                        vaccines.setError("Please enter No of vaccines");
                    } else if (TextUtils.isEmpty(Drugs)) {
                        drugs.setError("Please enter is Drugs Available");
                    } else if (TextUtils.isEmpty(OxygenBeds)) {
                        oxygenBeds.setError("Please enter No of OxygenBeds");
                    } else if (TextUtils.isEmpty(O2concentration)) {
                        o2concentration.setError("Please enter amount of oxygen Concentration");
                    } else if (TextUtils.isEmpty(plasmadonors)) {
                        Plasmadonors.setError("Please enter is Plasma Donors available");
                    } else {
                        // calling method to add data to Firebase Firestore.

                        Map<String, Object> data = new HashMap<>();
                        data.put(KEYIcubeds, Icubeds);
                        data.put(KEYNonIcubeds, NonIcubeds);
                        data.put(KEYVaccines, Vaccines);
                        data.put(KEYDrugs, Drugs);
                        data.put(KEYOxygenBeds, OxygenBeds);
                        data.put(KEYO2concentration, O2concentration);
                        data.put(KEYPlasmaDonors, plasmadonors);
                        db.collection("Hospital").document("available posts").collection("users").document(Uid).set(data)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        progressBar.setVisibility(View.INVISIBLE);

                                        Toast.makeText(Post_Availability.this, "successfully posted ", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Post_Availability.this, Availability.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(Post_Availability.this, "Failed due to " + e, Toast.LENGTH_SHORT).show();
                            }
                        });


                    }
                }
            });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                icubeds.setEnabled(true);
                nonicubeds.setEnabled(true);
                nonicubeds.setEnabled(true);
                vaccines.setEnabled(true);
                drugs.setEnabled(true);
                oxygenBeds.setEnabled(true);
                o2concentration.setEnabled(true);
                Plasmadonors.setEnabled(true);
                edit.setVisibility(View.GONE);
                submit.setVisibility(View.VISIBLE);



                }

        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                icubeds.setEnabled(true);
                nonicubeds.setEnabled(true);
                nonicubeds.setEnabled(true);
                vaccines.setEnabled(true);
                drugs.setEnabled(true);
                oxygenBeds.setEnabled(true);
                o2concentration.setEnabled(true);
                Plasmadonors.setEnabled(true);
                edit.setVisibility(View.GONE);
                submit.setVisibility(View.VISIBLE);
                delete.setVisibility(View.GONE);
                db.collection("Hospital").document("available posts").collection("users").document(Uid).delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                progressBar.setVisibility(View.INVISIBLE);
                                Log.d(TAG, "DocumentSnapshot successfully deleted!");
                                Toast.makeText(Post_Availability.this, "successfully deleted ", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Post_Availability.this, Availability.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Log.w(TAG, "Error deleting document", e);
                        Toast.makeText(Post_Availability.this, "Failed due to " + e, Toast.LENGTH_SHORT).show();
                    }
                });


            }

        });

        db.collection("Hospital").document("available posts").collection("users").document(Uid).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if(task.isSuccessful()){

                            if(task.getResult().exists()){
                                String Icubeds2 = task.getResult().getString("Icubeds");
                                String NonIcubeds2 = task.getResult().getString("nonIcubeds");
                                String Vaccines2 = task.getResult().getString("Vaccines");
                                String Drugs2 = task.getResult().getString("Drugs");
                                String OxygenBeds2 = task.getResult().getString("Oxygenbeds");
                                String O2concentration2 = task.getResult().getString("o2conc");
                                String plasmadonors2 = task.getResult().getString("plasmadonors");
                                try {
                                    icubeds.setText(Icubeds2);
                                    nonicubeds.setText(NonIcubeds2);
                                    vaccines.setText(Vaccines2);
                                    drugs.setText(Drugs2);
                                    oxygenBeds.setText(OxygenBeds2);
                                    o2concentration.setText(O2concentration2);
                                    Plasmadonors.setText(plasmadonors2);

                                }catch (Exception e){
                                    Log.d(TAG, "get failed with "+ e);
                                }
                                icubeds.setEnabled(false);
                                nonicubeds.setEnabled(false);
                                nonicubeds.setEnabled(false);
                                vaccines.setEnabled(false);
                                drugs.setEnabled(false);
                                oxygenBeds.setEnabled(false);
                                o2concentration.setEnabled(false);
                                Plasmadonors.setEnabled(false);
                                submit.setVisibility(View.GONE);
                                edit.setVisibility(View.VISIBLE);
                                delete.setVisibility(View.VISIBLE);

                            }else{
                                submit.setVisibility(View.VISIBLE);
                                delete.setVisibility(View.GONE);
                                edit.setVisibility(View.GONE);
                                icubeds.setEnabled(true);
                                nonicubeds.setEnabled(true);
                                nonicubeds.setEnabled(true);
                                vaccines.setEnabled(true);
                                drugs.setEnabled(true);
                                oxygenBeds.setEnabled(true);
                                o2concentration.setEnabled(true);
                                Plasmadonors.setEnabled(true);

                            }

                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                            Toast.makeText(Post_Availability.this, "(FIRESTORE Retrieve Error) : " , Toast.LENGTH_LONG).show();

                        }



                    }
                });



    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Post_Availability.this, Availability.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    }
