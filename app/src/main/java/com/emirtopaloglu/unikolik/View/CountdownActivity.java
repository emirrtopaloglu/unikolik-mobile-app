package com.emirtopaloglu.unikolik.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PowerManager;
import android.os.Vibrator;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.emirtopaloglu.unikolik.R;
import com.emirtopaloglu.unikolik.databinding.ActivityCountdownBinding;

public class CountdownActivity extends AppCompatActivity {

    private ActivityCountdownBinding binding;
    private int selectedTimer;
    private static long START_TIME_IN_MILLIS = 9900000;
    private CountDownTimer timer;
    private boolean timerRunning;
    private long timeLeftInMillis  = START_TIME_IN_MILLIS;
    private final long[] vibrate = {1000, 1000, 1000, 1000, 1000};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCountdownBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.timer, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.timerSpinner.setAdapter(spinnerAdapter);
        binding.timerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTimer = parent.getSelectedItemPosition();
                if ( selectedTimer == 0 ) {
                    START_TIME_IN_MILLIS = 9900000;
                    timeLeftInMillis  = START_TIME_IN_MILLIS;
                    updateCountDownText();
                } else {
                    START_TIME_IN_MILLIS = 10800000;
                    timeLeftInMillis  = START_TIME_IN_MILLIS;
                    updateCountDownText();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                binding.timerSpinner.setSelection(0);
            }
        });

        binding.timerStart.setOnClickListener(view1 -> {
            if (timerRunning) {
                pauseTimer();
            } else {
                startTimer();
            }
        });

        binding.timerReset.setOnClickListener(view12 -> {
            resetTimer();
        });

        updateCountDownText();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("Time End", "Time End", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

    }

    private void startTimer() {
        timer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long l) {
                timeLeftInMillis = l;
                updateCountDownText();
                binding.timerSpinner.setEnabled(false);
            }

            @Override
            public void onFinish() {
                timerRunning = false;
                binding.timerStart.setText("Başlat");
                binding.timerStart.setEnabled(false);
                binding.timerReset.setEnabled(true);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(CountdownActivity.this, "Time End");
                builder.setContentTitle("Süre Bitti!");
                builder.setContentText("Deneme çözme süren sona erdi. Şimdi sıra netlerini kontrol etmekte! Netlerini kontrol ettikten sonra Deneme Takibi'ne eklemeyi unutma!");
                builder.setSmallIcon(R.drawable.ic_uniyardim);
                builder.setLights(Color.BLUE, 3000, 3000);
                builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
                builder.setVibrate(vibrate);
                builder.setStyle(new NotificationCompat.BigTextStyle()
                        .setSummaryText("Deneme çözme süren sona erdi. Şimdi sıra netlerini kontrol etmekte! Netlerini kontrol ettikten sonra Deneme Takibi'ne eklemeyi unutma!"));
                builder.setAutoCancel(true);

                Vibrator vibrator = (Vibrator) CountdownActivity.this.getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(500);
                MediaPlayer player;
                player = MediaPlayer.create(CountdownActivity.this, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
                player.start();

                PowerManager powerManager = (PowerManager) CountdownActivity.this.getSystemService(Context.POWER_SERVICE);
                boolean isScreenOn = Build.VERSION.SDK_INT >= 20 ? powerManager.isInteractive() : powerManager.isScreenOn();
                if ( !isScreenOn ) {
                    PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "Unikolik:CountdownTimer::NotificationWakeUp");
                    wakeLock.acquire(3000);
                }

                Intent notifyIntent = new Intent(CountdownActivity.this, CountdownActivity.class);
                PendingIntent contentIntent = PendingIntent.getActivity(CountdownActivity.this, 0, notifyIntent, PendingIntent.FLAG_CANCEL_CURRENT);
                notifyIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                builder.setContentIntent(contentIntent);

                NotificationManagerCompat compat = NotificationManagerCompat.from(CountdownActivity.this);
                compat.notify(20062020, builder.build());

            }
        }.start();

        timerRunning = true;
        binding.timerStart.setText("Durdur");
        binding.timerReset.setEnabled(false);
    }

    private void pauseTimer() {
        timer.cancel();
        timerRunning = false;
        binding.timerStart.setText("Başlat");
        binding.timerReset.setEnabled(true);
    }

    private void resetTimer() {
        timeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        binding.timerStart.setEnabled(true);
        binding.timerReset.setEnabled(false);
        binding.timerSpinner.setEnabled(true);
    }

    private void updateCountDownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeleftFormatted = String.format("%02d:%02d", minutes, seconds);
        binding.timerText.setText(timeleftFormatted);
    }

    public void backTo(View view) {
        Intent backTo = new Intent(CountdownActivity.this, ToolsActivity.class);
        backTo.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(backTo);
    }
}