package com.emirtopaloglu.unikolik.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.emirtopaloglu.unikolik.R;
import com.emirtopaloglu.unikolik.databinding.ActivityProfileBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    private ActivityProfileBinding binding;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseFirestore db;
    BottomNavigationView bottomNavigationView;
    String daily_goal, university_goal, program_goal;
    Spinner dailyGoalSpinner, universitySpinner, programSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Firebase Initialize
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Spinner Initializing
        dailyGoalSpinner = binding.dailyGoalSpinner;
        ArrayAdapter<CharSequence> dailygoalAdapter = ArrayAdapter.createFromResource(this, R.array.daily_goals, android.R.layout.simple_spinner_item);
        dailygoalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dailyGoalSpinner.setAdapter(dailygoalAdapter);
        dailyGoalSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                daily_goal = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                dailyGoalSpinner.setSelection(0);
            }
        });

        universitySpinner = binding.universityGoalSpinner;
        ArrayAdapter<CharSequence> universityAdapter = ArrayAdapter.createFromResource(this, R.array.university_list, android.R.layout.simple_spinner_item);
        universityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        universitySpinner.setAdapter(universityAdapter);
        universitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                university_goal = parent.getItemAtPosition(position).toString();
            }

            @RequiresApi(api = Build.VERSION_CODES.S)
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                universitySpinner.setSelection(0);
            }
        });

        programSpinner = binding.programGoalSpinner;
        ArrayAdapter<CharSequence> programAdapter = ArrayAdapter.createFromResource(this, R.array.program_list, android.R.layout.simple_spinner_item);
        programAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        programSpinner.setAdapter(programAdapter);
        programSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                program_goal = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                programSpinner.setSelection(0);
            }
        });

        // Bottom Navigation Bar
        bottomNavigationView = binding.BottomNavigationBar;
        bottomNavigationView.setSelectedItemId(R.id.profile);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    Intent gotoHome = new Intent(ProfileActivity.this, MainActivity.class);
                    startActivity(gotoHome);
                    break;
                case R.id.profile:
                    Intent goToProfile = new Intent(ProfileActivity.this, ProfileActivity.class);
                    startActivity(goToProfile);
                    break;
                case R.id.daily_goal:
                    Intent goToDailyGoal = new Intent(ProfileActivity.this, DailyGoalActivity.class);
                    startActivity(goToDailyGoal);
                    break;
                case R.id.social:
                    Intent gotoSocial = new Intent(ProfileActivity.this, FeedActivity.class);
                    startActivity(gotoSocial);
                    break;
                case R.id.tools:
                    Intent gotoTools = new Intent(ProfileActivity.this, ToolsActivity.class);
                    startActivity(gotoTools);
                    break;
            }
            return false;
        });

        // Get Settings
        DocumentReference getSettings = db.collection("UserSettings").document(auth.getCurrentUser().getUid());
        getSettings.get().addOnCompleteListener(task -> {
            if ( task.isSuccessful() ) {
                DocumentSnapshot document = task.getResult();

                if ( document.exists() ) {

                    String getGoal = (String) document.getData().get("daily_goal");
                    String getUniversity = (String) document.getData().get("university_goal");
                    String getProgram = (String) document.getData().get("program_goal");

                    int dailyGoalSpinnerPosition = dailygoalAdapter.getPosition(getGoal);
                    int universitySpinnerPosition = universityAdapter.getPosition(getUniversity);
                    int programSpinnerPosition = programAdapter.getPosition(getProgram);

                    dailyGoalSpinner.setSelection(dailyGoalSpinnerPosition);
                    universitySpinner.setSelection(universitySpinnerPosition);
                    programSpinner.setSelection(programSpinnerPosition);

                }

            } else {
                Intent intent = new Intent(ProfileActivity.this, ProfileActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Toast.makeText(this, "Bir hata oluştu", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(intent);
            }
        });

        // Logout
        binding.logout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(ProfileActivity.this, "Çıkış yaptınız", Toast.LENGTH_SHORT).show();
            Intent logoutIntent = new Intent(ProfileActivity.this, PreloginActivity.class);
            logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(logoutIntent);
        });
    }


    // Save Settings
    public void saveSettings(View view) {
        binding.progress.setVisibility(View.VISIBLE);
        binding.saveButton.setVisibility(View.GONE);

        Map<String, Object> data = new HashMap<>();
        data.put("daily_goal", daily_goal);
        data.put("university_goal", university_goal);
        data.put("program_goal", program_goal);

        db.collection("UserSettings").document(auth.getCurrentUser().getUid())
                .set(data)
                .addOnCompleteListener(task -> {
                    binding.progress.setVisibility(View.GONE);
                    binding.saveButton.setVisibility(View.VISIBLE);
                    if ( task.isSuccessful() ) {
                        Toast.makeText(this, "Ayarlar kaydedildi!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Hata oluştu: " + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}