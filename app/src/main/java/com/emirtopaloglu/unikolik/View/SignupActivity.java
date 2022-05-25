package com.emirtopaloglu.unikolik.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.emirtopaloglu.unikolik.databinding.ActivitySignupBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {

    private ActivitySignupBinding binding;
    private EditText name, email, password, passwordAgain;
    private AppCompatButton signup;
    private ImageView signupGoogle;
    private TextView loginText;
    private ProgressBar progress;
    private FirebaseAuth auth;
    private String TAG = "SignupActivity";
    private GoogleSignInOptions gso;
    private GoogleSignInClient gsc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth = FirebaseAuth.getInstance();

        name = binding.signupName;;
        email = binding.signupEmail;
        password = binding.signupPassword;
        passwordAgain = binding.signupPasswordAgain;
        signup = binding.signupButton;
        signupGoogle = binding.signupGoogleButton;
        loginText = binding.signupLoginText;
        progress = binding.progress;

        progress.setVisibility(View.GONE);

        loginText.setOnClickListener(v -> {
            Intent loginIntent = new Intent(SignupActivity.this, LoginActivity.class);
            loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(loginIntent);
            finish();
        });

        signup.setOnClickListener(v -> {
            if ( name.getText().length() == 0 ) {
                name.setError("Ad kısmı boş olamaz.");
                return;
            }
            if ( email.getText().length() == 0 ) {
                email.setError("E-mail kısmı boş olamaz.");
                return;
            }
            if ( !isValidEmail(email.getText().toString().trim()) ) {
                email.setError("Geçerli bir e-mail girin.");
                return;
            }
            if ( password.getText().length() == 0 || password.getText().length() < 8 ) {
                password.setError("Şifre en az 8 karakterli olmalıdır.");
                return;
            }
            if (!password.getText().toString().equals(passwordAgain.getText().toString())) {
                password.setError("Şifreler eşleşmiyor.");
                passwordAgain.setError("Şifreler eşleşmiyor.");
                return;
            }
            progress.setVisibility(View.VISIBLE);
            signup.setVisibility(View.GONE);

            auth.createUserWithEmailAndPassword(email.getText().toString().trim(), password.getText().toString().trim())
                    .addOnCompleteListener(task -> {
                        if ( task.isSuccessful() ) {
                            Toast.makeText(this, "Kayıt başarılı! Lütfen giriş yapınız.", Toast.LENGTH_LONG).show();
                            Intent gotoLogin = new Intent(SignupActivity.this, LoginActivity.class);
                            gotoLogin.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(gotoLogin);
                            finish();
                        } else {
                            Toast.makeText(this, "Hata oluştu!", Toast.LENGTH_LONG).show();
                            Log.e(TAG, "Kayıt olurken bir hata oluştu: " + task.getException());
                            progress.setVisibility(View.GONE);
                            signup.setVisibility(View.VISIBLE);
                        }
                    });

        });

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this, gso);

        signupGoogle.setOnClickListener(v -> {
            Intent signUpIntent = gsc.getSignInIntent();
            startActivityForResult(signUpIntent, 1000);
        });
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( requestCode == 1000 ) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            } catch (ApiException e) {
                Toast.makeText(this, "Bir şeyler yanlış gitti!", Toast.LENGTH_SHORT).show();
                Log.e("SignUpActivity", e.getLocalizedMessage());
            }
        }
    }
}