package com.emirtopaloglu.unikolik.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.emirtopaloglu.unikolik.databinding.ActivityReportBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ReportActivity extends AppCompatActivity {

    private ActivityReportBinding binding;
    private FirebaseAuth auth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReportBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        binding.submitButton.setOnClickListener(view1 -> {

            if ( binding.reportEditText.getText().length() == 0 ) {
                binding.reportEditText.setError("Lütfen burayı boş bırakmayın.");
                return;
            }
            if ( binding.reportEditText.getText().length() > 300 ) {
                binding.reportEditText.setError("En fazla 300 karakter kullanabilirsiniz.");
                return;
            }

            binding.progress.setVisibility(View.VISIBLE);
            binding.submitButton.setVisibility(View.GONE);

            Map<String, Object> data = new HashMap<>();
            data.put("reportText", binding.reportEditText.getText().toString());
            data.put("userId", auth.getCurrentUser().getUid());
            data.put("date", FieldValue.serverTimestamp());

            db.collection("Reports").add(data)
                    .addOnCompleteListener(task -> {
                        if ( task.isSuccessful() ) {
                            Toast.makeText(this, "Mesaj gönderildi.", Toast.LENGTH_SHORT).show();
                            binding.reportEditText.setText("");
                        } else {
                            Toast.makeText(this, "Mesajınız gönderilemedi.", Toast.LENGTH_SHORT).show();
                        }
                        binding.progress.setVisibility(View.GONE);
                        binding.submitButton.setVisibility(View.VISIBLE);
                    });
        });

    }

    public void backTo(View view) {
        Intent backTo = new Intent(ReportActivity.this, ToolsActivity.class);
        backTo.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(backTo);
    }
}