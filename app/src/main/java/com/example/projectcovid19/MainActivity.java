package com.example.projectcovid19;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    private LinearLayout available,request,profile;
    private ImageView logout;
    private TextView faq,nameoftheperson,City,Pincode;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private String name,pincode,city,userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        request = findViewById(R.id.request);
        logout = findViewById(R.id.logout);
        available = findViewById(R.id.availability);
        faq = findViewById(R.id.faq);
        nameoftheperson=findViewById(R.id.name);
        City = findViewById(R.id.city);
        Pincode =findViewById(R.id.pincode);
        db = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        userid = firebaseUser.getUid();
       logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(MainActivity.this,Login.class);
                startActivity(i);
                finish();

            }
        });

       request.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent i = new Intent(MainActivity.this,Request.class);
               startActivity(i);

           }
       });
        available.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Availability.class);
                startActivity(i);

            }
        });

     faq.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent i = new Intent(MainActivity.this,Faq_Page.class);
             startActivity(i);
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