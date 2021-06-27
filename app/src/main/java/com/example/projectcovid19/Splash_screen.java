package com.example.projectcovid19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.os.Build.VERSION_CODES.M;

public class Splash_screen extends AppCompatActivity {
    private int time=4000;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mAuth=FirebaseAuth.getInstance();
        ImageView img = (ImageView)findViewById(R.id.img);
        Animation aniRotate = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_image);
        img.startAnimation(aniRotate);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser user=mAuth.getCurrentUser();
                if(user==null){
                    Intent intent = new Intent(Splash_screen.this, Login.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Intent mainIntent= new Intent(Splash_screen.this, MainActivity.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mainIntent);
                    finish();
                }
            }
        },time);
    }
}