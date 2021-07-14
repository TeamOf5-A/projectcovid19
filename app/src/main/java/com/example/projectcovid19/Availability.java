package com.example.projectcovid19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import static android.content.ContentValues.TAG;

public class Availability extends AppCompatActivity {
    private FloatingActionButton btnadd;
    private TextView City,pincode;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_availability);
       btnadd =  findViewById(R.id.btn_add);
       City= findViewById(R.id.city);
       pincode = findViewById(R.id.pincode);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        String Uid = firebaseUser.getUid();
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Availability.this, Post_Availability.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        db.collection("Hospital").document("Hospital Profile").collection("Users").document(Uid).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if(task.isSuccessful()){

                            if(task.getResult().exists()){
                                String city = task.getResult().getString("CITY");
                                String Pincode = task.getResult().getString("Pincode");

                                try {
                                    City.setText(city);
                                    pincode.setText(Pincode);

                                }catch (Exception e){
                                    Log.d(TAG, "get failed with "+ e);
                                }
                            }

                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                            Toast.makeText(Availability.this, "(FIRESTORE Retrieve Error) : " , Toast.LENGTH_LONG).show();

                        }

                    }
                });

    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Availability.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}