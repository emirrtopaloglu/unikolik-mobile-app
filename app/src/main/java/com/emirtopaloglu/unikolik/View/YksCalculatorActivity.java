package com.emirtopaloglu.unikolik.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.emirtopaloglu.unikolik.R;
import com.emirtopaloglu.unikolik.databinding.ActivityYksCalculatorBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class YksCalculatorActivity extends AppCompatActivity {

    private ActivityYksCalculatorBinding binding;
    private FirebaseAuth auth;
    BottomNavigationView bottomNavigationView;
    Double diplomaPuani = 0.0, tytTurkceDogru = 0.0, tytTurkceYanlis = 0.0, tytSosyalDogru = 0.0, tytSosyalYanlis = 0.0,
    tytMatematikDogru = 0.0, tytMatematikYanlis = 0.0, tytFenDogru = 0.0, tytFenYanlis = 0.0,
    aytEdebiyatDogru = 0.0, aytEdebiyatYanlis = 0.0,
    aytCografyaBirDogru = 0.0, aytCografyaBirYanlis = 0.0, aytTarihBirDogru = 0.0, aytTarihBirYanlis = 0.0,
    aytCografyaIkiDogru = 0.0, aytCografyaIkiYanlis = 0.0, aytTarihIkiDogru = 0.0, aytTarihIkiYanlis = 0.0,
    aytFelsefeDogru = 0.0, aytFelsefeYanlis = 0.0, aytDinDogru = 0.0, aytDinYanlis = 0.0,
    aytMatematikDogru = 0.0, aytMatematikYanlis = 0.0, aytFizikDogru = 0.0, aytFizikYanlis = 0.0,
    aytKimyaDogru = 0.0, aytKimyaYanlis = 0.0, aytBiyolojiDogru = 0.0, aytBiyolojiYanlis = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityYksCalculatorBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth = FirebaseAuth.getInstance();

        bottomNavigationView = binding.BottomNavigationBar;
        bottomNavigationView.setSelectedItemId(R.id.tools);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    Intent gotoHome = new Intent(YksCalculatorActivity.this, MainActivity.class);
                    startActivity(gotoHome);
                    break;
                case R.id.profile:
                    Intent goToProfile = new Intent(YksCalculatorActivity.this, ProfileActivity.class);
                    startActivity(goToProfile);
                    break;
                case R.id.daily_goal:
                    Intent goToDailyGoal = new Intent(YksCalculatorActivity.this, DailyGoalActivity.class);
                    startActivity(goToDailyGoal);
                    break;
                case R.id.social:
                    Intent gotoSocial = new Intent(YksCalculatorActivity.this, FeedActivity.class);
                    startActivity(gotoSocial);
                    break;
                case R.id.tools:
                    Intent gotoTools = new Intent(YksCalculatorActivity.this, ToolsActivity.class);
                    startActivity(gotoTools);
                    break;
            }
            return false;
        });

        binding.calcButton.setOnClickListener(view1 -> calcYksPuan());
    }

    public void backTo(View view) {
        Intent backToTools = new Intent(YksCalculatorActivity.this, ToolsActivity.class);
        backToTools.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(backToTools);
    }

    public void calcYksPuan() {
        if ( binding.diplomaPuani.getText().toString().length() == 0 ) {
            diplomaPuani = 0.0;
        } else {
            diplomaPuani = Double.valueOf(binding.diplomaPuani.getText().toString());
        }

        if ( binding.tytTurkceDogru.getText().toString().length() == 0 ) {
            tytTurkceDogru = 0.0;
        } else {
            tytTurkceDogru = Double.valueOf(binding.tytTurkceDogru.getText().toString());
        }

        if ( binding.tytTurkceYanlis.getText().toString().length() == 0 ) {
            tytTurkceYanlis = 0.0;
        } else {
            tytTurkceYanlis = Double.valueOf(binding.tytTurkceYanlis.getText().toString());
        }

        if ( binding.tytMatematikDogru.getText().toString().length() == 0 ) {
            tytMatematikDogru = 0.0;
        } else {
            tytMatematikDogru = Double.valueOf(binding.tytMatematikDogru.getText().toString());
        }

        if ( binding.tytMatematikYanlis.getText().toString().length() == 0 ) {
            tytMatematikYanlis = 0.0;
        } else {
            tytMatematikYanlis = Double.valueOf(binding.tytMatematikYanlis.getText().toString());
        }

        if ( binding.tytSosyalDogru.getText().toString().length() == 0 ) {
            tytSosyalDogru = 0.0;
        } else {
            tytSosyalDogru = Double.valueOf(binding.tytSosyalDogru.getText().toString());
        }

        if ( binding.tytSosyalYanlis.getText().toString().length() == 0 ) {
            tytSosyalYanlis = 0.0;
        } else {
            tytSosyalYanlis = Double.valueOf(binding.tytSosyalYanlis.getText().toString());
        }

        if ( binding.tytFenDogru.getText().toString().length() == 0 ) {
            tytFenDogru = 0.0;
        } else {
            tytFenDogru = Double.valueOf(binding.tytFenDogru.getText().toString());
        }

        if ( binding.tytFenYanlis.getText().toString().length() == 0 ) {
            tytFenYanlis = 0.0;
        } else {
            tytFenYanlis = Double.valueOf(binding.tytFenYanlis.getText().toString());
        }

        if ( binding.aytEdebiyatDogru.getText().toString().length() == 0 ) {
            aytEdebiyatDogru = 0.0;
        } else {
            aytEdebiyatDogru = Double.valueOf(binding.aytEdebiyatDogru.getText().toString());
        }

        if ( binding.aytEdebiyatYanlis.getText().toString().length() == 0 ) {
            aytEdebiyatYanlis = 0.0;
        } else {
            aytEdebiyatYanlis = Double.valueOf(binding.aytEdebiyatYanlis.getText().toString());
        }

        if ( binding.aytTarihBirDogru.getText().toString().length() == 0 ) {
            aytTarihBirDogru = 0.0;
        } else {
            aytTarihBirDogru = Double.valueOf(binding.aytTarihBirDogru.getText().toString());
        }

        if ( binding.aytTarihBirYanlis.getText().toString().length() == 0 ) {
            aytTarihBirYanlis = 0.0;
        } else {
            aytTarihBirYanlis = Double.valueOf(binding.aytTarihBirYanlis.getText().toString());
        }

        if ( binding.aytTarihIkiDogru.getText().toString().length() == 0 ) {
            aytTarihIkiDogru = 0.0;
        } else {
            aytTarihIkiDogru = Double.valueOf(binding.aytTarihIkiDogru.getText().toString());
        }

        if ( binding.aytTarihIkiYanlis.getText().toString().length() == 0 ) {
            aytTarihIkiYanlis = 0.0;
        } else {
            aytTarihIkiYanlis = Double.valueOf(binding.aytTarihIkiYanlis.getText().toString());
        }

        if ( binding.aytCografyaBirDogru.getText().toString().length() == 0 ) {
            aytCografyaBirDogru = 0.0;
        } else {
            aytCografyaBirDogru = Double.valueOf(binding.aytCografyaBirDogru.getText().toString());
        }

        if ( binding.aytCografyaBirYanlis.getText().toString().length() == 0 ) {
            aytCografyaBirYanlis = 0.0;
        } else {
            aytCografyaBirYanlis = Double.valueOf(binding.aytCografyaBirYanlis.getText().toString());
        }

        if ( binding.aytCografyaIkiDogru.getText().toString().length() == 0 ) {
            aytCografyaIkiDogru = 0.0;
        } else {
            aytCografyaIkiDogru = Double.valueOf(binding.aytCografyaIkiDogru.getText().toString());
        }

        if ( binding.aytCografyaIkiYanlis.getText().toString().length() == 0 ) {
            aytCografyaIkiYanlis = 0.0;
        } else {
            aytCografyaIkiYanlis = Double.valueOf(binding.aytCografyaIkiYanlis.getText().toString());
        }

        if ( binding.aytFelsefeDogru.getText().toString().length() == 0 ) {
            aytFelsefeDogru = 0.0;
        } else {
            aytFelsefeDogru = Double.valueOf(binding.aytFelsefeDogru.getText().toString());
        }

        if ( binding.aytFelsefeYanlis.getText().toString().length() == 0 ) {
            aytFelsefeYanlis = 0.0;
        } else {
            aytFelsefeYanlis = Double.valueOf(binding.aytFelsefeYanlis.getText().toString());
        }

        if ( binding.aytDinDogru.getText().toString().length() == 0 ) {
            aytDinDogru = 0.0;
        } else {
            aytDinDogru = Double.valueOf(binding.aytDinDogru.getText().toString());
        }

        if ( binding.aytDinYanlis.getText().toString().length() == 0 ) {
            aytDinYanlis = 0.0;
        } else {
            aytDinYanlis = Double.valueOf(binding.aytDinYanlis.getText().toString());
        }

        if ( binding.aytMatematikDogru.getText().toString().length() == 0 ) {
            aytMatematikDogru = 0.0;
        } else {
            aytMatematikDogru = Double.valueOf(binding.aytMatematikDogru.getText().toString());
        }

        if ( binding.aytMatematikYanlis.getText().toString().length() == 0 ) {
            aytMatematikYanlis = 0.0;
        } else {
            aytMatematikYanlis = Double.valueOf(binding.aytMatematikYanlis.getText().toString());
        }

        if ( binding.aytFizikDogru.getText().toString().length() == 0 ) {
            aytFizikDogru = 0.0;
        } else {
            aytFizikDogru = Double.valueOf(binding.aytFizikDogru.getText().toString());
        }

        if ( binding.aytFizikYanlis.getText().toString().length() == 0 ) {
            aytFizikYanlis = 0.0;
        } else {
            aytFizikYanlis = Double.valueOf(binding.aytFizikYanlis.getText().toString());
        }

        if ( binding.aytKimyaDogru.getText().toString().length() == 0 ) {
            aytKimyaDogru = 0.0;
        } else {
            aytKimyaDogru = Double.valueOf(binding.aytKimyaDogru.getText().toString());
        }

        if ( binding.aytKimyaYanlis.getText().toString().length() == 0 ) {
            aytKimyaYanlis = 0.0;
        } else {
            aytKimyaYanlis = Double.valueOf(binding.aytKimyaYanlis.getText().toString());
        }

        if ( binding.aytBiyolojiDogru.getText().toString().length() == 0 ) {
            aytBiyolojiDogru = 0.0;
        } else {
            aytBiyolojiDogru = Double.valueOf(binding.aytBiyolojiDogru.getText().toString());
        }

        if ( binding.aytBiyolojiYanlis.getText().toString().length() == 0 ) {
            aytBiyolojiYanlis = 0.0;
        } else {
            aytBiyolojiYanlis = Double.valueOf(binding.aytBiyolojiYanlis.getText().toString());
        }

        if ( diplomaPuani < 50 || diplomaPuani > 100 ) {
            binding.diplomaPuani.setError("Diploma puanı 50 ve 100 arasında olmalıdır.");
            return;
        }

        if ( binding.diplomaPuani.getText().length() == 0 ) {
            binding.diplomaPuani.setError("Diploma puanı boş olamaz.");
            return;
        }

        if ( tytTurkceDogru + tytTurkceYanlis > 40 ) {
            binding.tytTurkceYanlis.setError("Doğru yanlış sayısı toplamı 40'tan büyük olamaz.");
            return;
        }

        if ( tytMatematikDogru + tytMatematikYanlis > 40 ) {
            binding.tytMatematikYanlis.setError("Doğru yanlış sayısı toplamı 40'tan büyük olamaz.");
            return;
        }

        if ( tytSosyalDogru + tytSosyalYanlis > 20 ) {
            binding.tytSosyalYanlis.setError("Doğru yanlış sayısı toplamı 20'den büyük olamaz.");
            return;
        }

        if ( tytFenDogru + tytFenYanlis > 20 ) {
            binding.tytFenYanlis.setError("Doğru yanlış sayısı toplamı 20'den büyük olamaz.");
            return;
        }

        if ( aytEdebiyatDogru + aytEdebiyatYanlis > 24 ) {
            binding.aytEdebiyatYanlis.setError("Doğru yanlış sayısı toplamı 24'ten büyük olamaz.");
            return;
        }

        if ( aytTarihBirDogru + aytTarihBirYanlis > 10 ) {
            binding.aytTarihBirYanlis.setError("Doğru yanlış sayısı toplamı 10'dan büyük olamaz.");
            return;
        }

        if ( aytCografyaBirDogru + aytCografyaBirYanlis > 6 ) {
            binding.aytCografyaBirYanlis.setError("Doğru yanlış sayısı toplamı 6'dan büyük olamaz.");
            return;
        }

        if ( aytTarihIkiDogru + aytTarihIkiYanlis > 11 ) {
            binding.aytTarihIkiYanlis.setError("Doğru yanlış sayısı toplamı 11'den büyük olamaz.");
            return;
        }

        if ( aytCografyaIkiDogru + aytCografyaIkiYanlis > 11 ) {
            binding.aytCografyaIkiYanlis.setError("Doğru yanlış sayısı toplamı 11'den büyük olamaz.");
            return;
        }

        if ( aytFelsefeDogru + aytFelsefeYanlis > 12 ) {
            binding.aytFelsefeYanlis.setError("Doğru yanlış sayısı toplamı 12'den büyük olamaz.");
            return;
        }

        if ( aytDinDogru + aytDinYanlis > 6 ) {
            binding.aytDinYanlis.setError("Doğru yanlış sayısı toplamı 6'dan büyük olamaz.");
            return;
        }

        if ( aytMatematikDogru + aytMatematikYanlis > 40 ) {
            binding.aytMatematikYanlis.setError("Doğru yanlış sayısı toplamı 40'dan büyük olamaz.");
            return;
        }

        if ( aytFizikDogru + aytFizikYanlis > 14 ) {
            binding.aytFizikYanlis.setError("Doğru yanlış sayısı toplamı 14'ten büyük olamaz.");
            return;
        }

        if ( aytKimyaDogru + aytKimyaYanlis > 13 ) {
            binding.aytKimyaYanlis.setError("Doğru yanlış sayısı toplamı 13'ten büyük olamaz.");
            return;
        }
        if ( aytBiyolojiDogru + aytBiyolojiYanlis > 13 ) {
            binding.aytBiyolojiYanlis.setError("Doğru yanlış sayısı toplamı 13'ten büyük olamaz.");
            return;
        }

        binding.result1.setVisibility(View.VISIBLE);
        binding.result2.setVisibility(View.VISIBLE);
        binding.result3.setVisibility(View.VISIBLE);
        binding.result4.setVisibility(View.VISIBLE);
        binding.result5.setVisibility(View.VISIBLE);
        binding.result6.setVisibility(View.VISIBLE);

        // Katsayılar

        Double tytTurkceMatNetPuan = 3.3;
        Double tytSosFenNetPuan = 3.4;

        Double aytTytTurkceMatNetPuan = 1.32;
        Double aytTytSosFenNetPuan = 1.36;

        Double aytMatEdbNetPuan = 3.0;
        Double aytFizikNetPuan = 2.85;
        Double aytKimBiyoNetPuan = 3.07;

        Double aytTarih1NetPuan = 2.80;
        Double aytCog1NetPuan = 3.33;
        Double aytTarih2NetPuan = 2.91;
        Double aytCog2NetPuan = 2.91;
        Double aytFelNetPuan = 3.0;
        Double aytDinNetPuan = 3.33;

        // Net Hesaplamaları

        Double tytTurkceNet = tytTurkceDogru - (tytTurkceYanlis / 4);
        Double tytMatematikNet = tytMatematikDogru - (tytMatematikYanlis / 4);
        Double tytSosyalNet = tytSosyalDogru - (tytSosyalYanlis / 4);
        Double tytFenNet = tytFenDogru - (tytFenYanlis / 4);

        Double aytEdebiyatNet = aytEdebiyatDogru - (aytEdebiyatYanlis / 4);
        Double aytMatematikNet = aytMatematikDogru - (aytMatematikYanlis / 4);
        Double aytCografyaBirNet = aytCografyaBirDogru - (aytCografyaBirYanlis / 4);
        Double aytCografyaIkiNet = aytCografyaIkiDogru - (aytCografyaIkiYanlis / 4);
        Double aytTarihBirNet = aytTarihBirDogru - (aytTarihBirYanlis / 4);
        Double aytTarihIkiNet = aytTarihIkiDogru - (aytTarihIkiYanlis / 4);
        Double aytFelsefeNet = aytFelsefeDogru - (aytFelsefeYanlis / 4);
        Double aytDinNet = aytDinDogru - (aytDinYanlis / 4);
        Double aytFizikNet = aytFizikDogru - (aytFizikYanlis / 4);
        Double aytKimyaNet = aytKimyaDogru - (aytKimyaYanlis / 4);
        Double aytBiyolojiNet = aytBiyolojiDogru - (aytBiyolojiYanlis / 4);

        // Puan Hesaplamaları

        Double tytTurkcePuan = tytTurkceNet * tytTurkceMatNetPuan;
        Double tytMatematikPuan = tytMatematikNet * tytTurkceMatNetPuan;
        Double tytSosyalPuan = tytSosyalNet * tytSosFenNetPuan;
        Double tytFenPuan = tytFenNet * tytSosFenNetPuan;

        Double aytTytTurkcePuan = tytTurkceNet * aytTytTurkceMatNetPuan;
        Double aytTytMatematikPuan = tytMatematikNet * aytTytTurkceMatNetPuan;
        Double aytTytSosyalPuan = tytSosyalNet * aytTytSosFenNetPuan;
        Double aytTytFenPuan = tytFenNet * aytTytSosFenNetPuan;

        Double aytEdebiyatPuan = aytEdebiyatNet * aytMatEdbNetPuan;
        Double aytMatematikPuan = aytMatematikNet * aytMatEdbNetPuan;
        Double aytCografyaBirPuan = aytCografyaBirNet * aytCog1NetPuan;
        Double aytCografyaIkiPuan = aytCografyaIkiNet * aytCog2NetPuan;
        Double aytTarihBirPuan = aytTarihBirNet * aytTarih1NetPuan;
        Double aytTarihIkiPuan = aytTarihIkiNet * aytTarih2NetPuan;
        Double aytFelsefePuan = aytFelsefeNet * aytFelNetPuan;
        Double aytDinPuan = aytDinNet * aytDinNetPuan;
        Double aytFizikPuan = aytFizikNet * aytFizikNetPuan;
        Double aytKimyaPuan = aytKimyaNet * aytKimBiyoNetPuan;
        Double aytBiyolojiPuan = aytBiyolojiNet * aytKimBiyoNetPuan;

        // Diploma Puanı
        if ( binding.obpCheckbox.isChecked() ) {
            diplomaPuani = Double.valueOf(binding.diplomaPuani.getText().toString()) / 2;
        } else {
            diplomaPuani = Double.valueOf(binding.diplomaPuani.getText().toString());
        }

        // TYT Puanı Hesaplama
        Double tytHamPuan = tytTurkcePuan + tytMatematikPuan + tytSosyalPuan + tytFenPuan + 100;
        binding.tytHamPuan.setText(String.format("%.2f", tytHamPuan));
        Double tytYerPuan = tytHamPuan + (diplomaPuani * 0.6);
        binding.tytYerPuan.setText(String.format("%.2f", tytYerPuan));

        // AYT Puanı Hesaplama
        Double aytTytPuan = aytTytTurkcePuan + aytTytMatematikPuan + aytTytSosyalPuan + aytTytFenPuan;

        Double aytSayHamPuan = aytTytPuan + aytMatematikPuan + aytFizikPuan +
                aytKimyaPuan + aytBiyolojiPuan + 100;
        binding.sayHamPuan.setText(String.format("%.2f", aytSayHamPuan));
        Double aytSayYerPuan = aytSayHamPuan + (diplomaPuani * 0.6);
        binding.sayYerPuan.setText(String.format("%.2f", aytSayYerPuan));

        Double aytEaHamPuan = aytTytPuan + aytEdebiyatPuan + aytTarihBirPuan +
                aytCografyaBirPuan + aytMatematikPuan + 100;
        binding.eaHamPuan.setText(String.format("%.2f", aytEaHamPuan));
        Double aytEaYerPuan = aytEaHamPuan + (diplomaPuani * 0.6);
        binding.eaYerPuan.setText(String.format("%.2f", aytEaYerPuan));

        Double aytSozHamPuan = aytTytPuan + aytEdebiyatPuan + aytTarihBirPuan + aytCografyaBirPuan
                + aytTarihIkiPuan + aytCografyaIkiPuan + aytFelsefePuan + aytDinPuan + 100;
        binding.sozHamPuan.setText(String.format("%.2f", aytSozHamPuan));
        Double aytSozYerPuan = aytSozHamPuan + (diplomaPuani * 0.6);
        binding.sozYerPuan.setText(String.format("%.2f", aytSozYerPuan));
    }
}