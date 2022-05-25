package com.emirtopaloglu.unikolik.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.emirtopaloglu.unikolik.databinding.ActivityStatsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class StatsActivity extends AppCompatActivity {

    private ActivityStatsBinding binding;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    Double tytMatematikNet = 0.0, tytTurkceNet = 0.0, tytSosyalNet = 0.0, tytFenNet = 0.0, tytToplamNet = 0.0,
            aytEdebiyatNet = 0.0, aytTarihBirNet = 0.0, aytCografyaBirNet = 0.0, aytTarihIkiNet = 0.0, ortTytNet = 0.0,
            aytCografyaIkiNet = 0.0, aytFelsefeNet = 0.0, aytDinNet = 0.0, aytMatematikNet = 0.0, aytFizikNet = 0.0,
            aytKimyaNet = 0.0, aytBiyolojiNet = 0.0, ortTytMatematikNet = 0.0, ortTytTurkceNet = 0.0, ortTytSosyalNet = 0.0,
            ortTytFenNet = 0.0, ortAytEdebiyatNet = 0.0, ortAytTarihBirNet = 0.0, ortAytTarihIkiNet = 0.0,
            ortAytCografyaBirNet = 0.0, ortAytCografyaIkiNet = 0.0, ortAytFelsefeNet = 0.0, ortAytDinNet = 0.0,
            ortAytMatematikNet = 0.0, ortAytFizikNet = 0.0, ortAytKimyaNet = 0.0, ortAytBiyolojiNet = 0.0,
            aytSayNet = 0.0, aytSozNet = 0.0, aytEaNet = 0.0, ortAytSayNet = 0.0, ortAytSozNet = 0.0, ortAytEaNet = 0.0;
    int tytDenemeSayisi, aytDenemeSayisi, toplamGunlukSoru;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStatsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Get Data
        getTytQuizData();
        getAytQuizData();
        getDailySolvedData();
    }

    private void getTytQuizData() {
        db.collection("TytQuizzes")
                .whereEqualTo("userId", auth.getCurrentUser().getUid())
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        tytDenemeSayisi = task.getResult().size();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Map<String, Object> data = document.getData();
                            tytTurkceNet += Double.valueOf((String) data.get("tytTurkceNet").toString());
                            tytMatematikNet += Double.valueOf((String) data.get("tytMatematikNet").toString());
                            tytSosyalNet += Double.valueOf((String) data.get("tytSosyalNet").toString());
                            tytFenNet += Double.valueOf((String) data.get("tytFenNet").toString());
                            tytToplamNet += Double.valueOf((String) data.get("tytToplamNet").toString());
                        }
                        // Calc Averages
                        ortTytNet = tytToplamNet / tytDenemeSayisi;
                        ortTytMatematikNet = tytMatematikNet / tytDenemeSayisi;
                        ortTytTurkceNet = tytTurkceNet  / tytDenemeSayisi;
                        ortTytSosyalNet = tytSosyalNet  / tytDenemeSayisi;
                        ortTytFenNet  = tytFenNet  / tytDenemeSayisi;

                        // Set TextViews
                        binding.toplamTytDeneme.setText(String.valueOf(tytDenemeSayisi));
                        if (ortTytNet.isNaN()) {
                            binding.ortTytNet.setText("0");
                        } else {
                            binding.ortTytNet.setText(ortTytNet.toString());
                        }
                        if (ortTytMatematikNet.isNaN()) {
                            binding.ortTytMatematikNet.setText("0");
                        } else {
                            binding.ortTytMatematikNet.setText(ortTytMatematikNet.toString());
                        }
                        if (ortTytTurkceNet.isNaN()) {
                            binding.ortTytTurkceNet.setText("0");
                        } else {
                            binding.ortTytTurkceNet.setText(ortTytTurkceNet.toString());
                        }
                        if (ortTytFenNet.isNaN()) {
                            binding.ortTytFenNet.setText("0");
                        } else {
                            binding.ortTytFenNet.setText(ortTytFenNet.toString());
                        }
                        if (ortTytSosyalNet.isNaN()) {
                            binding.ortTytSosyalNet.setText("0");
                        } else {
                            binding.ortTytSosyalNet.setText(ortTytSosyalNet.toString());
                        }
                    } else {
                        Toast.makeText(this, "TYT Deneme verileri alınamadı.", Toast.LENGTH_SHORT).show();
                        Log.e("StatsActivity::getTytQuizData", task.getException().toString());
                    }
                });
    }

    private void getAytQuizData() {
        db.collection("AytQuizzes")
                .whereEqualTo("userId", auth.getCurrentUser().getUid())
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        aytDenemeSayisi = task.getResult().size();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Map<String, Object> data = document.getData();
                            aytEdebiyatNet += Double.valueOf((String) data.get("aytEdebiyatNet").toString());
                            aytTarihBirNet += Double.valueOf((String) data.get("aytTarihBirNet").toString());
                            aytTarihIkiNet += Double.valueOf((String) data.get("aytTarihIkiNet").toString());
                            aytCografyaBirNet += Double.valueOf((String) data.get("aytCografyaBirNet").toString());
                            aytCografyaIkiNet += Double.valueOf((String) data.get("aytCografyaIkiNet").toString());
                            aytFelsefeNet += Double.valueOf((String) data.get("aytFelsefeNet").toString());
                            aytDinNet += Double.valueOf((String) data.get("aytDinNet").toString());
                            aytMatematikNet += Double.valueOf((String) data.get("aytMatematikNet").toString());
                            aytFizikNet += Double.valueOf((String) data.get("aytFizikNet").toString());
                            aytKimyaNet += Double.valueOf((String) data.get("aytKimyaNet").toString());
                            aytBiyolojiNet += Double.valueOf((String) data.get("aytBiyolojiNet").toString());
                            aytSayNet += Double.valueOf((String) data.get("aytSayNet").toString());
                            aytSozNet += Double.valueOf((String) data.get("aytSozNet").toString());
                            aytEaNet += Double.valueOf((String) data.get("aytEaNet").toString());
                        }
                        // Calc Averages
                        ortAytEdebiyatNet = aytEdebiyatNet / aytDenemeSayisi;
                        ortAytTarihBirNet = aytTarihBirNet / aytDenemeSayisi;
                        ortAytTarihIkiNet = aytTarihIkiNet / aytDenemeSayisi;
                        ortAytCografyaBirNet = aytCografyaBirNet / aytDenemeSayisi;
                        ortAytCografyaIkiNet = aytCografyaIkiNet / aytDenemeSayisi;
                        ortAytFelsefeNet = aytFelsefeNet / aytDenemeSayisi;
                        ortAytDinNet = aytDinNet / aytDenemeSayisi;
                        ortAytMatematikNet = aytMatematikNet / aytDenemeSayisi;
                        ortAytFizikNet = aytFizikNet / aytDenemeSayisi;
                        ortAytKimyaNet = aytKimyaNet / aytDenemeSayisi;
                        ortAytBiyolojiNet = aytBiyolojiNet / aytDenemeSayisi;
                        ortAytSayNet = aytSayNet / aytDenemeSayisi;
                        ortAytSozNet = aytSozNet / aytDenemeSayisi;
                        ortAytEaNet = aytEaNet / aytDenemeSayisi;

                        // Set TextViews
                        binding.toplamAytDeneme.setText(String.valueOf(aytDenemeSayisi));

                        if (ortAytEdebiyatNet.isNaN()) {
                            binding.ortAytEdebiyatNet.setText("0");
                        } else {
                            binding.ortAytEdebiyatNet.setText(ortAytEdebiyatNet.toString());
                        }
                        if (ortAytTarihBirNet.isNaN()) {
                            binding.ortAytTarihBirNet.setText("0");
                        } else {
                            binding.ortAytTarihBirNet.setText(ortAytTarihBirNet.toString());
                        }
                        if (ortAytCografyaBirNet.isNaN()) {
                            binding.ortAytCografyaBirNet.setText("0");
                        } else {
                            binding.ortAytCografyaBirNet.setText(ortAytCografyaBirNet.toString());
                        }
                        if (ortAytTarihIkiNet.isNaN()) {
                            binding.ortAytTarihIkiNet.setText("0");
                        } else {
                            binding.ortAytTarihIkiNet.setText(ortAytTarihIkiNet.toString());
                        }
                        if (ortAytCografyaIkiNet.isNaN()) {
                            binding.ortAytCografyaIkiNet.setText("0");
                        } else {
                            binding.ortAytCografyaIkiNet.setText(ortAytCografyaIkiNet.toString());
                        }
                        if (ortAytFelsefeNet.isNaN()) {
                            binding.ortAytFelsefeNet.setText("0");
                        } else {
                            binding.ortAytFelsefeNet.setText(ortAytFelsefeNet.toString());
                        }
                        if (ortAytDinNet.isNaN()) {
                            binding.ortAytDinFelsefeNet.setText("0");
                        } else {
                            binding.ortAytDinFelsefeNet.setText(ortAytDinNet.toString());
                        }
                        if (ortAytMatematikNet.isNaN()) {
                            binding.ortAytMatematikNet.setText("0");
                        } else {
                            binding.ortAytMatematikNet.setText(ortAytMatematikNet.toString());
                        }
                        if (ortAytFizikNet.isNaN()) {
                            binding.ortAytFizikNet.setText("0");
                        } else {
                            binding.ortAytFizikNet.setText(ortAytFizikNet.toString());
                        }
                        if (ortAytKimyaNet.isNaN()) {
                            binding.ortAytKimyaNet.setText("0");
                        } else {
                            binding.ortAytKimyaNet.setText(ortAytKimyaNet.toString());
                        }
                        if (ortAytBiyolojiNet.isNaN()) {
                            binding.ortAytBiyolojiNet.setText("0");
                        } else {
                            binding.ortAytBiyolojiNet.setText(ortAytBiyolojiNet.toString());
                        }
                        if (ortAytSayNet.isNaN()) {
                            binding.ortAytSayNet.setText("0");
                        } else {
                            binding.ortAytSayNet.setText(ortAytSayNet.toString());
                        }
                        if (ortAytSozNet.isNaN()) {
                            binding.ortAytSozNet.setText("0");
                        } else {
                            binding.ortAytSozNet.setText(ortAytSozNet.toString());
                        }
                        if (ortAytEaNet.isNaN()) {
                            binding.ortAytEaNet.setText("0");
                        } else {
                            binding.ortAytEaNet.setText(ortAytEaNet.toString());
                        }
                    } else {
                        Toast.makeText(this, "AYT Deneme verileri alınamadı.", Toast.LENGTH_SHORT).show();
                        Log.e("StatsActivity::getAytQuizData", task.getException().toString());
                    }
                });
    }

    private void getDailySolvedData() {
        db.collection("DailySolved")
                .whereEqualTo("user", auth.getCurrentUser().getUid())
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Map<String, Object> data = document.getData();
                            toplamGunlukSoru += Integer.valueOf((String) data.get("question").toString());
                        }
                        binding.toplamSoru.setText(String.valueOf(toplamGunlukSoru));
                    } else {
                        Toast.makeText(this, "Günlük Soru verileri alınamadı.", Toast.LENGTH_SHORT).show();
                        Log.e("StatsActivity::getDailySolvedData", task.getException().toString());
                    }
                });
    }

    public void backTo(View view) {
        Intent backTo = new Intent(StatsActivity.this, ToolsActivity.class);
        backTo.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(backTo);
    }
}