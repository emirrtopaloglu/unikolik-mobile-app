package com.emirtopaloglu.unikolik.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.emirtopaloglu.unikolik.R;
import com.emirtopaloglu.unikolik.databinding.ActivityAddQuizTytBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.SQLOutput;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddQuizTytActivity extends AppCompatActivity {

    private ActivityAddQuizTytBinding binding;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    int tytTurkceDogru, tytTurkceYanlis, tytSosyalDogru, tytSosyalYanlis, tytMatematikDogru,
    tytMatematikYanlis, tytFenDogru, tytFenYanlis;
    Double tytTurkceNet, tytSosyalNet, tytMatematikNet, tytFenNet, tytToplamNet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddQuizTytBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    public void addQuiz(View view) {
        if ( binding.tytTurkceDogru.getText().toString().length() == 0 ) {
            tytTurkceDogru = 0;
        } else {
            tytTurkceDogru = Integer.parseInt(binding.tytTurkceDogru.getText().toString());
        }
        if ( binding.tytTurkceYanlis.getText().toString().length() == 0 ) {
            tytTurkceYanlis = 0;
        } else {
            tytTurkceYanlis = Integer.parseInt(binding.tytTurkceYanlis.getText().toString());
        }
        if ( binding.tytSosyalDogru.getText().toString().length() == 0 ) {
            tytSosyalDogru = 0;
        } else {
            tytSosyalDogru = Integer.parseInt(binding.tytSosyalDogru.getText().toString());
        }
        if ( binding.tytSosyalYanlis.getText().toString().length() == 0 ) {
            tytSosyalYanlis = 0;
        } else {
            tytSosyalYanlis = Integer.parseInt(binding.tytSosyalYanlis.getText().toString());
        }
        if ( binding.tytMatematikDogru.getText().toString().length() == 0 ) {
            tytMatematikDogru = 0;
        } else {
            tytMatematikDogru = Integer.parseInt(binding.tytMatematikDogru.getText().toString());
        }
        if ( binding.tytMatematikYanlis.getText().toString().length() == 0 ) {
            tytMatematikYanlis = 0;
        } else {
            tytMatematikYanlis = Integer.parseInt(binding.tytMatematikYanlis.getText().toString());
        }
        if ( binding.tytFenDogru.getText().toString().length() == 0 ) {
            tytFenDogru = 0;
        } else {
            tytFenDogru = Integer.parseInt(binding.tytFenDogru.getText().toString());
        }
        if ( binding.tytFenYanlis.getText().toString().length() == 0 ) {
            tytFenYanlis = 0;
        } else {
            tytFenYanlis = Integer.parseInt(binding.tytFenYanlis.getText().toString());
        }

        tytTurkceNet = Double.valueOf(tytTurkceDogru) - (Double.valueOf(tytTurkceYanlis) / 4);
        tytMatematikNet = Double.valueOf(tytMatematikDogru) - (Double.valueOf(tytMatematikYanlis) / 4);
        tytSosyalNet = Double.valueOf(tytSosyalDogru) - (Double.valueOf(tytSosyalYanlis) / 4);
        tytFenNet = Double.valueOf(tytFenDogru) - (Double.valueOf(tytFenYanlis) / 4);

        tytToplamNet = tytTurkceNet + tytMatematikNet + tytSosyalNet + tytFenNet;

        binding.progress.setVisibility(View.VISIBLE);
        binding.addButton.setVisibility(View.GONE);

        if ( tytTurkceDogru + tytTurkceYanlis > 40 ) {
            binding.tytTurkceYanlis.setError("Doğru ve yanlış sayısı toplamı 40'tan fazla olamaz.");
            return;
        }
        if ( tytSosyalDogru + tytSosyalYanlis > 20 ) {
            binding.tytSosyalYanlis.setError("Doğru ve yanlış sayısı toplamı 20'den fazla olamaz.");
            return;
        }
        if ( tytMatematikDogru + tytMatematikYanlis > 40 ) {
            binding.tytTurkceYanlis.setError("Doğru ve yanlış sayısı toplamı 40'tan fazla olamaz.");
            return;
        }
        if ( tytFenDogru + tytFenYanlis > 40 ) {
            binding.tytSosyalYanlis.setError("Doğru ve yanlış sayısı toplamı 20'den fazla olamaz.");
            return;
        }

        Date now = new Date();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy h:m");
        String date = df.format(now);

        Map<String, Object> data = new HashMap<>();
        data.put("tytTurkceDogru", tytTurkceDogru);
        data.put("tytTurkceYanlis", tytTurkceYanlis);
        data.put("tytTurkceNet", tytTurkceNet);
        data.put("tytMatematikDogru", tytMatematikDogru);
        data.put("tytMatematikYanlis", tytMatematikYanlis);
        data.put("tytMatematikNet", tytMatematikNet);
        data.put("tytSosyalDogru", tytSosyalDogru);
        data.put("tytSosyalYanlis", tytSosyalYanlis);
        data.put("tytSosyalNet", tytSosyalNet);
        data.put("tytFenDogru", tytFenDogru);
        data.put("tytFenYanlis", tytFenYanlis);
        data.put("tytFenNet", tytFenNet);
        data.put("tytToplamNet", tytToplamNet);
        data.put("serverDate", FieldValue.serverTimestamp());
        data.put("quizDate", date);
        data.put("userId", auth.getCurrentUser().getUid());

        db.collection("TytQuizzes").add(data)
                .addOnCompleteListener(task -> {
                    if ( task.isSuccessful() ) {
                        Intent backQuizzes = new Intent(AddQuizTytActivity.this, QuizTrackActivity.class);
                        finish();
                        startActivity(backQuizzes);
                    } else {
                        Toast.makeText(this, "Lütfen tekrar deneyiniz", Toast.LENGTH_SHORT).show();
                        binding.progress.setVisibility(View.GONE);
                        binding.addButton.setVisibility(View.VISIBLE);
                    }
                });
    }

    public void backTo(View view) {
        Intent backIntent = new Intent(AddQuizTytActivity.this, QuizTrackActivity.class);
        backIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(backIntent);
    }
}