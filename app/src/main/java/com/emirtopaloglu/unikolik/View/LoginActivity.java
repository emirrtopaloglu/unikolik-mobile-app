package com.emirtopaloglu.unikolik.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.emirtopaloglu.unikolik.R;
import com.emirtopaloglu.unikolik.databinding.ActivityLoginBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private FirebaseAuth auth;
    private GoogleSignInOptions gso;
    private GoogleSignInClient gsc;
    private EditText email, password;
    private AppCompatButton loginButton;
    private ImageView loginGoogle;
    private TextView signupText;
    private ProgressBar progress;
    private String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.client_id))
                .requestEmail()
                .build();

        gsc = GoogleSignIn.getClient(this, gso);

        auth = FirebaseAuth.getInstance();

        email = binding.loginEmail;
        password = binding.loginPassword;
        loginButton = binding.loginButton;
        loginGoogle = binding.loginGoogle;
        signupText = binding.loginSignupText;
        progress = binding.progress;

        progress.setVisibility(View.GONE);

        signupText.setOnClickListener(v -> {
            Intent signupIntent = new Intent(LoginActivity.this, SignupActivity.class);
            signupIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(signupIntent);
            finish();
        });

        loginButton.setOnClickListener(v -> {
            if ( email.length() == 0 ) {
                email.setError("Email alanı boş olamaz!");
                return;
            }

            if ( password.length() == 0 ) {
                password.setError("Şifre alanı boş olamaz!");
                return;
            }
            progress.setVisibility(View.VISIBLE);
            loginButton.setVisibility(View.GONE);

            auth.signInWithEmailAndPassword(email.getText().toString().trim(), password.getText().toString().trim())
                    .addOnCompleteListener(task -> {
                        if ( task.isSuccessful() ) {
                            loginSuccessful();
                        } else {
                            Toast.makeText(this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "signInWithEmailAndPassword:failed" + task.getException());
                            progress.setVisibility(View.GONE);
                            loginButton.setVisibility(View.VISIBLE);
                        }
                    });
        });

        loginGoogle.setOnClickListener(v -> {
            Intent signInIntent = gsc.getSignInIntent();
            startActivityForResult(signInIntent, 1000);
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = auth.getCurrentUser();
        if ( user != null ) {
            loginSuccessful();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == 1000) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = auth.getCurrentUser();
                            loginSuccessful();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());

                        }
                    }
                });
    }

    private void loginSuccessful() {
        Toast.makeText(this, "Giriş başarılı.", Toast.LENGTH_SHORT).show();
        Intent gotoMain = new Intent(LoginActivity.this, MainActivity.class);
        gotoMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(gotoMain);
        finish();
    }

}