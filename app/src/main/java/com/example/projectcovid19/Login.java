package com.example.projectcovid19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.tabs.TabLayout;

public class Login extends AppCompatActivity {

    private ExtendedFloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
       /* ShapeableImageView imageView = findViewById(R.id.imageview);
          float radius = getResources().getDimension(R.dimen.default_corner_radius);
          imageView.setShapeAppearanceModel(imageView.getShapeAppearanceModel()
               .toBuilder()

                  .setAllCorners(CornerFamily.ROUNDED,radius)
               .build());*/
        floatingActionButton = findViewById(R.id.login_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,Otp_screen.class);
                startActivity(intent);
            }
        });


    }
}