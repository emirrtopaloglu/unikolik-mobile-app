package com.emirtopaloglu.unikolik.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.emirtopaloglu.unikolik.R;
import com.emirtopaloglu.unikolik.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    BottomNavigationView bottomNavigationView;
    int dailyGoal = 1, dailySolved = 0;
    double dailyProgress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        if ( auth.getCurrentUser() == null ) {
            Intent checkLoggedIntent = new Intent(MainActivity.this, PreloginActivity.class);
            checkLoggedIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(checkLoggedIntent);
            finish();
        }

        bottomNavigationView = binding.BottomNavigationBar;
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    Intent gotoHome = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(gotoHome);
                    break;
                case R.id.profile:
                    Intent goToProfile = new Intent(MainActivity.this, ProfileActivity.class);
                    startActivity(goToProfile);
                    break;
                case R.id.daily_goal:
                    Intent goToDailyGoal = new Intent(MainActivity.this, DailyGoalActivity.class);
                    startActivity(goToDailyGoal);
                    break;
                case R.id.social:
                    Intent gotoSocial = new Intent(MainActivity.this, FeedActivity.class);
                    startActivity(gotoSocial);
                    break;
                case R.id.tools:
                    Intent gotoTools = new Intent(MainActivity.this, ToolsActivity.class);
                    startActivity(gotoTools);
                    break;
            }
            return false;
        });

        if (auth.getCurrentUser().getDisplayName().isEmpty()) {
            binding.welcomeText.setText("Hoş geldiniz!");
        } else {
            binding.welcomeText.setText("Hoş geldin, " + auth.getCurrentUser().getDisplayName());
        }
        // Set Countdown
        Date today = new Date();
        Date yksDate = new Date(122, 05, 18);
        DateFormat df = new SimpleDateFormat("D");
        int countdown = Integer.parseInt(df.format(yksDate)) - Integer.parseInt(df.format(today));
        binding.yksCountdownTextView.setText((countdown / 30) + " ay " + ( countdown % 30 ) + " gün");

        // Set Daily Goal
        db.collection("UserSettings").document(auth.getCurrentUser().getUid())
                .get().addOnCompleteListener(task -> {
                    if ( task.isSuccessful() ) {
                        DocumentSnapshot data = task.getResult();
                        if ( data.exists() ) {
                            dailyGoal = Integer.parseInt(data.getData().get("daily_goal").toString());
                            binding.dailyGoalTotalTextView.setText("Hedef: " + dailyGoal);
                        } else {
                            binding.dailyGoalTotalTextView.setText("Hedef: 0");
                        }
                    } else {
                        Toast.makeText(this, "Veriler alınırken bir hata oluştu: " + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                });

        DateFormat getToday = new SimpleDateFormat("dd/MM/yyyy");

        db.collection("DailySolved")
                .whereEqualTo("date", getToday.format(today))
                .whereEqualTo("user", auth.getCurrentUser().getUid())
                .get().addOnCompleteListener(task -> {
                    if ( task.isSuccessful() ) {
                        QuerySnapshot data = task.getResult();
                        if ( data.isEmpty() ) {
                            binding.dailyGoalSolvedTextView.setText("Çözülen: 0");
                        } else {
                            for ( QueryDocumentSnapshot document : data ) {
                                int question = Integer.parseInt(document.getData().get("question").toString());
                                dailySolved += question;
                                binding.dailyGoalSolvedTextView.setText("Çözülen: " + dailySolved);
                            }
                        }
                        dailyProgress = (dailySolved * 100) / dailyGoal;
                        binding.DailyQuestionGoalProgressBar.setProgress((int) Math.round(dailyProgress));
                        binding.dailyGoalPercentTextView.setText((int) Math.round(dailyProgress) + "%");
                    } else {
                        Toast.makeText(this, "Veriler alınırken bir hata oluştu: " + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void gotoYksPuan(View view) {
        Intent gotoYks = new Intent(MainActivity.this, YksCalculatorActivity.class);
        gotoYks.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(gotoYks);
    }

    public void gotoQuizTrack(View view) {
        Intent gotoQuiz = new Intent(MainActivity.this, QuizTrackActivity.class);
        gotoQuiz.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(gotoQuiz);
    }

    public void gotoStats(View view) {
        Intent gotoStats = new Intent(MainActivity.this, StatsActivity.class);
        gotoStats.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(gotoStats);
    }

    public void gotoWebsite(View view) {
        Intent gotoWebsite = new Intent(Intent.ACTION_VIEW, Uri.parse("https://unikolik.com/"));
        startActivity(gotoWebsite);
    }
}