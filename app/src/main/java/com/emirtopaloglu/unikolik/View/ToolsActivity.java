package com.emirtopaloglu.unikolik.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.emirtopaloglu.unikolik.R;
import com.emirtopaloglu.unikolik.databinding.ActivityToolsBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class ToolsActivity extends AppCompatActivity {

    private ActivityToolsBinding binding;
    private FirebaseAuth auth;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityToolsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth = FirebaseAuth.getInstance();

        bottomNavigationView = binding.BottomNavigationBar;
        bottomNavigationView.setSelectedItemId(R.id.tools);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    Intent gotoHome = new Intent(ToolsActivity.this, MainActivity.class);
                    startActivity(gotoHome);
                    break;
                case R.id.profile:
                    Intent goToProfile = new Intent(ToolsActivity.this, ProfileActivity.class);
                    startActivity(goToProfile);
                    break;
                case R.id.daily_goal:
                    Intent goToDailyGoal = new Intent(ToolsActivity.this, DailyGoalActivity.class);
                    startActivity(goToDailyGoal);
                    break;
                case R.id.social:
                    Intent gotoSocial = new Intent(ToolsActivity.this, FeedActivity.class);
                    startActivity(gotoSocial);
                    break;
                case R.id.tools:
                    Intent gotoTools = new Intent(ToolsActivity.this, ToolsActivity.class);
                    startActivity(gotoTools);
                    break;
            }
            return false;
        });

    }

    public void gotoYksPuan(View view) {
        Intent gotoYks = new Intent(ToolsActivity.this, YksCalculatorActivity.class);
        gotoYks.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(gotoYks);
    }

    public void gotoQuizTrack(View view) {
        Intent gotoQuiz = new Intent(ToolsActivity.this, QuizTrackActivity.class);
        gotoQuiz.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(gotoQuiz);
    }

    public void gotoStats(View view) {
        Intent gotoStats = new Intent(ToolsActivity.this, StatsActivity.class);
        gotoStats.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(gotoStats);
    }

    public void gotoCountdown(View view) {
        Intent gotoCountdown = new Intent(ToolsActivity.this, CountdownActivity.class);
        gotoCountdown.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(gotoCountdown);
    }

    public void gotoSpotify(View view) {
        Intent gotoSpotify = new Intent(ToolsActivity.this, SpotifyActivity.class);
        gotoSpotify.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(gotoSpotify);
    }

    public void gotoReport(View view) {
        Intent gotoReport = new Intent(ToolsActivity.this, ReportActivity.class);
        gotoReport.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(gotoReport);
    }
}