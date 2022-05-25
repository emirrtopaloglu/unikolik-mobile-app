package com.emirtopaloglu.unikolik.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentPagerAdapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.emirtopaloglu.unikolik.Controller.QuizChoiceDialogFragment;
import com.emirtopaloglu.unikolik.Controller.ViewPagerAdapter;
import com.emirtopaloglu.unikolik.R;
import com.emirtopaloglu.unikolik.databinding.ActivityQuizTrackBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class QuizTrackActivity extends AppCompatActivity implements QuizChoiceDialogFragment.QuizChoiceListener {

    private ActivityQuizTrackBinding binding;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizTrackBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        bottomNavigationView = binding.BottomNavigationBar;
        bottomNavigationView.setSelectedItemId(R.id.tools);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    Intent gotoHome = new Intent(QuizTrackActivity.this, MainActivity.class);
                    startActivity(gotoHome);
                    break;
                case R.id.profile:
                    Intent goToProfile = new Intent(QuizTrackActivity.this, ProfileActivity.class);
                    startActivity(goToProfile);
                    break;
                case R.id.daily_goal:
                    Intent goToDailyGoal = new Intent(QuizTrackActivity.this, DailyGoalActivity.class);
                    startActivity(goToDailyGoal);
                    break;
                case R.id.social:
                    Intent gotoSocial = new Intent(QuizTrackActivity.this, FeedActivity.class);
                    startActivity(gotoSocial);
                    break;
                case R.id.tools:
                    Intent gotoTools = new Intent(QuizTrackActivity.this, ToolsActivity.class);
                    startActivity(gotoTools);
                    break;
            }
            return false;
        });

        binding.quizzesTabLayout.setupWithViewPager(binding.viewpager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPagerAdapter.addFragment(new TytQuizzesFragment(), "TYT Denemeleri");
        viewPagerAdapter.addFragment(new AytQuizzesFragment(), "AYT Denemeleri");
        binding.viewpager.setAdapter(viewPagerAdapter);
    }

    public void clickFab(View view) {
        DialogFragment fragment = new QuizChoiceDialogFragment();
        fragment.setCancelable(true);
        fragment.show(getSupportFragmentManager(), "Quiz Choice Dialog");
    }

    public void backTo(View view) {
        Intent backToTools = new Intent(QuizTrackActivity.this, ToolsActivity.class);
        backToTools.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(backToTools);
    }



    @Override
    public void onPositiveButtonClicked(String[] list, int position) {
        if ( position == 0 ) {
            Intent gotoTyt = new Intent(QuizTrackActivity.this, AddQuizTytActivity.class);
            gotoTyt.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(gotoTyt);
        } else if ( position == 1 ) {
            Intent gotoAyt = new Intent(QuizTrackActivity.this, AddQuizAytActivity.class);
            gotoAyt.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(gotoAyt);
        } else {
            Toast.makeText(this, "Bir hata meydana geldi, lütfen tekrar deneyin.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNegativeButtonClicked() { }
}