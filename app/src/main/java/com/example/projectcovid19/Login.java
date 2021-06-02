package com.example.projectcovid19;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        /*ShapeableImageView imageView = findViewById(R.id.imageview);
          float radius = getResources().getDimension(R.dimen.default_corner_radius);
          imageView.setShapeAppearanceModel(imageView.getShapeAppearanceModel()
               .toBuilder()
               .setBottomRightCorner(CornerFamily.ROUNDED,radius)
               .setBottomLeftCorner(CornerFamily.ROUNDED,radius)
               .build());*/

    }
}