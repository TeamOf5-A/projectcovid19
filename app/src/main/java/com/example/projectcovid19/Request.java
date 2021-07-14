package com.example.projectcovid19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import static android.content.ContentValues.TAG;

public class Request extends AppCompatActivity {
    private FloatingActionButton btnadd;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore db;
    String user = "User";
    private TextView city1;
    private TextView pincode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        btnadd = findViewById(R.id.btn_add);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        city1 = findViewById(R.id.city1);
        pincode = findViewById(R.id.pincode);
        String Uid = firebaseUser.getUid();
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Request.this, Post_Request.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        db.collection("User").document("User profile").collection("Users").document(Uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {
                        String account_type = task.getResult().getString("Account");
                        try {
                            if (account_type.equals(user)) {
                                db.collection("User").document("User profile").collection("Users").document(Uid).get()
                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()){
                                                    if(task.getResult().exists()){
                                                        String city = task.getResult().getString("CITY");
                                                        String Pincode = task.getResult().getString("Pincode");
                                                        try {
                                                            city1.setText(city);
                                                            pincode.setText(Pincode);

                                                        }catch (Exception e){
                                                            Log.d(TAG, "get failed with "+ e);
                                                        }
                                                    }
                                                }
                                            }
                                        });

                            } else {
                                db.collection("Hospital").document("Hospital Profile").collection("Users").document(Uid).get()
                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()){
                                                    if(task.getResult().exists()){
                                                        String city = task.getResult().getString("CITY");
                                                        String Pincode = task.getResult().getString("Pincode");
                                                        try {
                                                            city1.setText(city);
                                                            pincode.setText(Pincode);

                                                        }catch (Exception e){
                                                            Log.d(TAG, "get failed with "+ e);
                                                        }
                                                    }
                                                }
                                            }
                                        });

                            }

                        } catch (Exception e) {
                            Log.d(TAG, "get failed with " + e);
                        }
                    }
                }
            }
        });

    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Request.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}
