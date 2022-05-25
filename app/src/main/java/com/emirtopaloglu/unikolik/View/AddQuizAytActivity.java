package com.emirtopaloglu.unikolik.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.emirtopaloglu.unikolik.R;
import com.emirtopaloglu.unikolik.databinding.ActivityAddQuizAytBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddQuizAytActivity extends AppCompatActivity {

    private ActivityAddQuizAytBinding binding;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    int aytEdebiyatDogru, aytEdebiyatYanlis, aytTarihBirDogru, aytTarihBirYanlis,
    aytCografyaBirDogru, aytCografyaBirYanlis, aytTarihIkiDogru, aytTarihIkiYanlis,
    aytCografyaIkiDogru, aytCografyaIkiYanlis, aytFelsefeDogru, aytFelsefeYanlis,
    aytDinDogru, aytDinYanlis, aytMatematikDogru, aytMatematikYanlis, aytFizikDogru,
    aytFizikYanlis, aytKimyaDogru, aytKimyaYanlis, aytBiyolojiDogru, aytBiyolojiYanlis;
    Double aytEdebiyatNet, aytTarihBirNet, aytCografyaBirNet, aytTarihIkiNet,
    aytCografyaIkiNet, aytFelsefeNet, aytDinNet, aytMatematikNet, aytFizikNet,
    aytKimyaNet, aytBiyolojiNet, aytSayNet, aytSozNet, aytEaNet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddQuizAytBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    public void addQuiz(View view) {
        if ( binding.aytEdebiyatDogru.getText().length() == 0 ) {
            aytEdebiyatDogru = 0;
        } else {
            aytEdebiyatDogru = Integer.parseInt(binding.aytEdebiyatDogru.getText().toString());
        }
        if ( binding.aytEdebiyatYanlis.getText().length() == 0 ) {
            aytEdebiyatYanlis = 0;
        } else {
            aytEdebiyatYanlis = Integer.parseInt(binding.aytEdebiyatYanlis.getText().toString());
        }
        if ( binding.aytTarihBirDogru.getText().length() == 0 ) {
            aytTarihBirDogru = 0;
        } else {
            aytTarihBirDogru = Integer.parseInt(binding.aytTarihBirDogru.getText().toString());
        }
        if ( binding.aytTarihBirYanlis.getText().length() == 0 ) {
            aytTarihBirYanlis = 0;
        } else {
            aytTarihBirYanlis = Integer.parseInt(binding.aytTarihBirYanlis.getText().toString());
        }
        if ( binding.aytCografyaBirDogru.getText().length() == 0 ) {
            aytCografyaBirDogru = 0;
        } else {
            aytCografyaBirDogru = Integer.parseInt(binding.aytCografyaBirDogru.getText().toString());
        }
        if ( binding.aytCografyaBirYanlis.getText().length() == 0 ) {
            aytCografyaBirYanlis = 0;
        } else {
            aytCografyaBirYanlis = Integer.parseInt(binding.aytCografyaBirYanlis.getText().toString());
        }
        if ( binding.aytTarihIkiDogru.getText().length() == 0 ) {
            aytTarihIkiDogru = 0;
        } else {
            aytTarihIkiDogru = Integer.parseInt(binding.aytTarihIkiDogru.getText().toString());
        }
        if ( binding.aytTarihIkiYanlis.getText().length() == 0 ) {
            aytTarihIkiYanlis = 0;
        } else {
            aytTarihIkiYanlis = Integer.parseInt(binding.aytTarihIkiYanlis.getText().toString());
        }
        if ( binding.aytCografyaIkiDogru.getText().length() == 0 ) {
            aytCografyaIkiDogru = 0;
        } else {
            aytCografyaIkiDogru = Integer.parseInt(binding.aytCografyaIkiDogru.getText().toString());
        }
        if ( binding.aytCografyaIkiYanlis.getText().length() == 0 ) {
            aytCografyaIkiYanlis = 0;
        } else {
            aytCografyaIkiYanlis = Integer.parseInt(binding.aytCografyaIkiYanlis.getText().toString());
        }
        if ( binding.aytFelsefeDogru.getText().length() == 0 ) {
            aytFelsefeDogru = 0;
        } else {
            aytFelsefeDogru = Integer.parseInt(binding.aytFelsefeDogru.getText().toString());
        }
        if ( binding.aytFelsefeYanlis.getText().length() == 0 ) {
            aytFelsefeYanlis = 0;
        } else {
            aytFelsefeYanlis = Integer.parseInt(binding.aytFelsefeYanlis.getText().toString());
        }
        if ( binding.aytDinDogru.getText().length() == 0 ) {
            aytDinDogru = 0;
        } else {
            aytDinDogru = Integer.parseInt(binding.aytDinDogru.getText().toString());
        }
        if ( binding.aytDinYanlis.getText().length() == 0 ) {
            aytDinYanlis = 0;
        } else {
            aytDinYanlis = Integer.parseInt(binding.aytDinYanlis.getText().toString());
        }
        if ( binding.aytMatematikDogru.getText().length() == 0 ) {
            aytMatematikDogru = 0;
        } else {
            aytMatematikDogru = Integer.parseInt(binding.aytMatematikDogru.getText().toString());
        }
        if ( binding.aytMatematikYanlis.getText().length() == 0 ) {
            aytMatematikYanlis = 0;
        } else {
            aytMatematikYanlis = Integer.parseInt(binding.aytMatematikYanlis.getText().toString());
        }
        if ( binding.aytFizikDogru.getText().length() == 0 ) {
            aytFizikDogru = 0;
        } else {
            aytFizikDogru = Integer.parseInt(binding.aytFizikDogru.getText().toString());
        }
        if ( binding.aytFizikYanlis.getText().length() == 0 ) {
            aytFizikYanlis = 0;
        } else {
            aytFizikYanlis = Integer.parseInt(binding.aytFizikYanlis.getText().toString());
        }
        if ( binding.aytKimyaDogru.getText().length() == 0 ) {
            aytKimyaDogru = 0;
        } else {
            aytKimyaDogru = Integer.parseInt(binding.aytKimyaDogru.getText().toString());
        }
        if ( binding.aytKimyaYanlis.getText().length() == 0 ) {
            aytKimyaYanlis = 0;
        } else {
            aytKimyaYanlis = Integer.parseInt(binding.aytKimyaYanlis.getText().toString());
        }
        if ( binding.aytBiyolojiDogru.getText().length() == 0 ) {
            aytBiyolojiDogru = 0;
        } else {
            aytBiyolojiDogru = Integer.parseInt(binding.aytBiyolojiDogru.getText().toString());
        }
        if ( binding.aytBiyolojiYanlis.getText().length() == 0 ) {
            aytBiyolojiYanlis = 0;
        } else {
            aytBiyolojiYanlis = Integer.parseInt(binding.aytBiyolojiYanlis.getText().toString());
        }

        aytEdebiyatNet = Double.valueOf(aytEdebiyatDogru) - (Double.valueOf(aytEdebiyatYanlis) / 4);
        aytTarihBirNet = Double.valueOf(aytTarihBirDogru) - (Double.valueOf(aytTarihBirYanlis) / 4);
        aytCografyaBirNet = Double.valueOf(aytCografyaBirDogru) - (Double.valueOf(aytCografyaBirYanlis) / 4);
        aytTarihIkiNet = Double.valueOf(aytTarihIkiDogru) - (Double.valueOf(aytTarihIkiYanlis) / 4);
        aytCografyaIkiNet = Double.valueOf(aytCografyaIkiDogru) - (Double.valueOf(aytCografyaIkiYanlis) / 4);
        aytFelsefeNet = Double.valueOf(aytFelsefeDogru) - (Double.valueOf(aytFelsefeYanlis) / 4);
        aytDinNet = Double.valueOf(aytDinDogru) - (Double.valueOf(aytDinYanlis) / 4);
        aytMatematikNet = Double.valueOf(aytMatematikDogru) - (Double.valueOf(aytMatematikYanlis) / 4);
        aytFizikNet = Double.valueOf(aytFizikDogru) - (Double.valueOf(aytFizikYanlis) / 4);
        aytKimyaNet = Double.valueOf(aytKimyaDogru) - (Double.valueOf(aytKimyaYanlis) / 4);
        aytBiyolojiNet = Double.valueOf(aytBiyolojiDogru) - (Double.valueOf(aytBiyolojiYanlis) / 4);

        aytSayNet = aytMatematikNet + aytFizikNet + aytKimyaNet + aytBiyolojiNet;
        aytSozNet = aytEdebiyatNet + aytTarihBirNet + aytTarihIkiNet + aytCografyaBirNet + aytCografyaIkiNet + aytFelsefeNet + aytDinNet;
        aytEaNet = aytEdebiyatNet + aytMatematikNet + aytTarihBirNet + aytCografyaBirNet;

        binding.progress.setVisibility(View.VISIBLE);
        binding.addButton.setVisibility(View.GONE);

        if ( aytEdebiyatDogru + aytEdebiyatYanlis > 24 ) {
            binding.aytEdebiyatYanlis.setError("Doğru ve yanlış sayısı toplamı 24'ten fazla olamaz.");
            return;
        }
        if ( aytTarihBirDogru + aytTarihBirYanlis > 10 ) {
            binding.aytTarihBirYanlis.setError("Doğru ve yanlış sayısı toplamı 10'dan fazla olamaz.");
            return;
        }
        if ( aytCografyaBirDogru + aytCografyaBirYanlis > 6 ) {
            binding.aytCografyaBirYanlis.setError("Doğru ve yanlış sayısı toplamı 6'dan fazla olamaz.");
            return;
        }
        if ( aytTarihIkiDogru + aytTarihIkiYanlis > 11 ) {
            binding.aytTarihIkiYanlis.setError("Doğru ve yanlış sayısı toplamı 11'den fazla olamaz.");
            return;
        }
        if ( aytCografyaIkiDogru + aytCografyaIkiYanlis > 11 ) {
            binding.aytCografyaIkiYanlis.setError("Doğru ve yanlış sayısı toplamı 11'den fazla olamaz.");
            return;
        }
        if ( aytFelsefeDogru + aytFelsefeYanlis > 12 ) {
            binding.aytFelsefeYanlis.setError("Doğru ve yanlış sayısı toplamı 12'den fazla olamaz.");
            return;
        }
        if ( aytDinDogru + aytDinYanlis > 6 ) {
            binding.aytDinYanlis.setError("Doğru ve yanlış sayısı toplamı 6'dan fazla olamaz.");
            return;
        }
        if ( aytMatematikDogru + aytMatematikYanlis > 40 ) {
            binding.aytMatematikYanlis.setError("Doğru ve yanlış sayısı toplamı 40'dan fazla olamaz.");
            return;
        }
        if ( aytFizikDogru + aytFizikYanlis > 14 ) {
            binding.aytFizikYanlis.setError("Doğru ve yanlış sayısı toplamı 14'den fazla olamaz.");
            return;
        }
        if ( aytKimyaDogru + aytKimyaYanlis > 13 ) {
            binding.aytKimyaYanlis.setError("Doğru ve yanlış sayısı toplamı 13'ten fazla olamaz.");
            return;
        }
        if ( aytBiyolojiDogru + aytBiyolojiYanlis > 13 ) {
            binding.aytBiyolojiYanlis.setError("Doğru ve yanlış sayısı toplamı 13'ten fazla olamaz.");
            return;
        }

        Date now = new Date();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy h:m");
        String date = df.format(now);

        Map<String, Object> data = new HashMap<>();
        data.put("aytEdebiyatNet", aytEdebiyatNet);
        data.put("aytEdebiyatDogru", aytEdebiyatDogru);
        data.put("aytEdebiyatYanlis", aytEdebiyatYanlis);
        data.put("aytTarihBirNet", aytTarihBirNet);
        data.put("aytTarihBirDogru", aytTarihBirDogru);
        data.put("aytTarihBirYanlis", aytTarihBirYanlis);
        data.put("aytCografyaBirNet", aytCografyaBirNet);
        data.put("aytCografyaBirDogru", aytCografyaBirDogru);
        data.put("aytCografyaBirYanlis", aytCografyaBirYanlis);
        data.put("aytTarihIkiNet", aytTarihIkiNet);
        data.put("aytTarihIkiDogru", aytTarihIkiDogru);
        data.put("aytTarihIkiYanlis", aytTarihIkiYanlis);
        data.put("aytCografyaIkiNet", aytCografyaIkiNet);
        data.put("aytCografyaIkiDogru", aytCografyaIkiDogru);
        data.put("aytCografyaIkiYanlis", aytCografyaIkiYanlis);
        data.put("aytFelsefeNet", aytFelsefeNet);
        data.put("aytFelsefeDogru", aytFelsefeDogru);
        data.put("aytFelsefeYanlis", aytFelsefeYanlis);
        data.put("aytDinNet", aytDinNet);
        data.put("aytDinDogru", aytDinDogru);
        data.put("aytDinYanlis", aytDinYanlis);
        data.put("aytMatematikNet", aytMatematikNet);
        data.put("aytMatematikDogru", aytMatematikDogru);
        data.put("aytMatematikYanlis", aytMatematikYanlis);
        data.put("aytFizikNet", aytFizikNet);
        data.put("aytFizikDogru", aytFizikDogru);
        data.put("aytFizikYanlis", aytFizikYanlis);
        data.put("aytKimyaNet", aytKimyaNet);
        data.put("aytKimyaDogru", aytKimyaDogru);
        data.put("aytKimyaYanlis", aytKimyaYanlis);
        data.put("aytBiyolojiNet", aytBiyolojiNet);
        data.put("aytBiyolojiDogru", aytBiyolojiDogru);
        data.put("aytBiyolojiYanlis", aytBiyolojiYanlis);
        data.put("aytSayNet", aytSayNet);
        data.put("aytSozNet", aytSozNet);
        data.put("aytEaNet", aytEaNet);
        data.put("serverDate", FieldValue.serverTimestamp());
        data.put("quizDate", date);
        data.put("userId", auth.getCurrentUser().getUid());

        db.collection("AytQuizzes").add(data)
                .addOnCompleteListener(task -> {
                    if ( task.isSuccessful() ) {
                        Intent backQuizzes = new Intent(AddQuizAytActivity.this, QuizTrackActivity.class);
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
        Intent backIntent = new Intent(AddQuizAytActivity.this, QuizTrackActivity.class);
        backIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(backIntent);
    }
}