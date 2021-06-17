package com.example.projectcovid19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.executor.TaskExecutor;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Login extends AppCompatActivity {

    private ExtendedFloatingActionButton floatingActionButton;
    private EditText etDigit1, etDigit2, etDigit3, etDigit4;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
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
        mAuth = FirebaseAuth.getInstance();

        final EditText inputMobile = findViewById(R.id.mobileno);
        Button buttonGetotp = findViewById(R.id.login_button);
         progressBar = findViewById(R.id.progressBar);
        buttonGetotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputMobile.getText().toString().trim().isEmpty()) {
                    Toast.makeText(Login.this, "Enter Mobile", Toast.LENGTH_LONG).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" + inputMobile.getText().toString(),
                        60,
                        TimeUnit.SECONDS,
                        Login.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                progressBar.setVisibility(View.GONE);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String login_buttonId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(login_buttonId, forceResendingToken);

                                Intent intent = new Intent(getApplicationContext(), Otp_screen.class);
                                intent.putExtra("mobileno", inputMobile.getText().toString());
                                intent.putExtra("login_buttonId", login_buttonId);
                                startActivity(intent);
                            }
                        }
                );

            }
        });
    }
}