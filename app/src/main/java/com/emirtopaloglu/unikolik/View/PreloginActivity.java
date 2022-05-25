package com.emirtopaloglu.unikolik.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.emirtopaloglu.unikolik.databinding.ActivityPreloginBinding;
import com.google.firebase.auth.FirebaseAuth;

public class PreloginActivity extends AppCompatActivity {

    private ActivityPreloginBinding binding;
    private FirebaseAuth auth;
    private AppCompatButton login, signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPreloginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth = FirebaseAuth.getInstance();
        login = binding.loginButton;
        signup = binding.signupButton;

        if ( auth.getCurrentUser() != null ) {
            Intent checkLoggedIntent = new Intent(PreloginActivity.this, MainActivity.class);
            checkLoggedIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(checkLoggedIntent);
            finish();
        }

        login.setOnClickListener(v -> {
            Intent gotoLogin = new Intent(PreloginActivity.this, LoginActivity.class);
            gotoLogin.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(gotoLogin);
            finish();
        });

        signup.setOnClickListener(v -> {
            Intent gotoSignup = new Intent(PreloginActivity.this, SignupActivity.class);
            gotoSignup.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(gotoSignup);
            finish();
        });

    }

}