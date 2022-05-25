package com.emirtopaloglu.unikolik.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.emirtopaloglu.unikolik.Controller.DailyGoalAdapter;
import com.emirtopaloglu.unikolik.Model.DailyGoal;
import com.emirtopaloglu.unikolik.R;
import com.emirtopaloglu.unikolik.databinding.ActivityDailyGoalBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DailyGoalActivity extends AppCompatActivity {

    private ActivityDailyGoalBinding binding;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    BottomNavigationView bottomNavigationView;
    ArrayList<DailyGoal> dailyGoals;
    DailyGoalAdapter dailyGoalAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDailyGoalBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        dailyGoals = new ArrayList<>();

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        if ( auth.getCurrentUser() == null ) {
            Intent checkLoggedIntent = new Intent(this, PreloginActivity.class);
            checkLoggedIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(checkLoggedIntent);
            finish();
        }

        getDailyGoalData();

        binding.dailyGoalRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        dailyGoalAdapter = new DailyGoalAdapter(dailyGoals);
        binding.dailyGoalRecyclerView.setAdapter(dailyGoalAdapter);

        bottomNavigationView = binding.BottomNavigationBar;
        bottomNavigationView.setSelectedItemId(R.id.daily_goal);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    Intent gotoHome = new Intent(DailyGoalActivity.this, MainActivity.class);
                    startActivity(gotoHome);
                    break;
                case R.id.profile:
                    Intent goToProfile = new Intent(DailyGoalActivity.this, ProfileActivity.class);
                    startActivity(goToProfile);
                    break;
                case R.id.daily_goal:
                    Intent goToDailyGoal = new Intent(DailyGoalActivity.this, DailyGoalActivity.class);
                    startActivity(goToDailyGoal);
                    break;
                case R.id.social:
                    Intent gotoSocial = new Intent(DailyGoalActivity.this, FeedActivity.class);
                    startActivity(gotoSocial);
                    break;
                case R.id.tools:
                    Intent gotoTools = new Intent(DailyGoalActivity.this, ToolsActivity.class);
                    startActivity(gotoTools);
                    break;
            }
            return false;
        });

        Spinner spinner = binding.dailyGoalLessonsSpinner;
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.lessons, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String spinnerSelected = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void addtoDailyGoal(View view) {
        String lesson = binding.dailyGoalLessonsSpinner.getSelectedItem().toString();
        String question = binding.dailyGoalQuestionNumber.getText().toString();

        if ( lesson.isEmpty() ) {
            Toast.makeText(this, "Lütfen bir ders seçiniz.", Toast.LENGTH_SHORT).show();
            return;
        }
        if ( question.length() == 0 ) {
            binding.dailyGoalQuestionNumber.setError("Lütfen bir sayı giriniz.");
            return;
        }
        if ( Integer.parseInt(question) > 1000 ) {
            binding.dailyGoalQuestionNumber.setError("Tek sefer 1000'den az soru girebilirsiniz.");
            return;
        }

        binding.progress.setVisibility(View.VISIBLE);
        binding.addtoDailyGoalButton.setVisibility(View.GONE);

        Date today = new Date();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Map<String, Object> data = new HashMap<>();
        data.put("lesson", lesson);
        data.put("question", question);
        data.put("date", df.format(today));
        data.put("user", auth.getCurrentUser().getUid());
        data.put("server_date", FieldValue.serverTimestamp());

        db.collection("DailySolved").document()
                .set(data)
                .addOnCompleteListener(task -> {
                    if ( task.isSuccessful() ) {
                        Toast.makeText(this, "Sorular başarıyla eklendi.", Toast.LENGTH_SHORT).show();
                        dailyGoalAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(this, "Hata oluştu: " + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                });

        binding.progress.setVisibility(View.GONE);
        binding.addtoDailyGoalButton.setVisibility(View.VISIBLE);
        binding.dailyGoalQuestionNumber.setText("");
    }

    private void getDailyGoalData() {

        db.collection("DailySolved").orderBy("server_date", Query.Direction.DESCENDING).whereEqualTo("user", auth.getCurrentUser().getUid())
                .get().addOnCompleteListener(task -> {
                    if ( task.isSuccessful() ) {
                        if ( task.getResult().isEmpty() ) {
                            Toast.makeText(this, "Veri bulunamadı.", Toast.LENGTH_SHORT).show();
                        } else {
                            for ( QueryDocumentSnapshot document : task.getResult() ) {
                                Map<String, Object> data = document.getData();
                                String date = (String) data.get("date");
                                String lesson = (String) data.get("lesson");
                                String question = (String) data.get("question");
                                String user = (String) data.get("user");

                                DailyGoal dailyGoal = new DailyGoal(date, lesson, question, user);
                                dailyGoals.add(dailyGoal);
                            }
                            dailyGoalAdapter.notifyDataSetChanged();
                        }
                    } else {
                        Toast.makeText(this, "Veriler alınırken bir hata oluştu: " + task.getException(), Toast.LENGTH_SHORT).show();
                        System.out.println(task.getException());
                    }
                });

    }
}