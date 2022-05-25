package com.emirtopaloglu.unikolik.View;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.emirtopaloglu.unikolik.R;
import com.emirtopaloglu.unikolik.databinding.ActivityAddPostBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddPostActivity extends AppCompatActivity {

    private ActivityAddPostBinding binding;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    ActivityResultLauncher<Intent> activityResultLauncher;
    ActivityResultLauncher<String> permissionLauncher;
    Uri imageData;
    Bitmap selectedImage;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddPostBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        if ( auth.getCurrentUser() == null ) {
            Intent checkLoggedIntent = new Intent(AddPostActivity.this, PreloginActivity.class);
            checkLoggedIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(checkLoggedIntent);
            finish();
        }

        bottomNavigationView = binding.BottomNavigationBar;
        bottomNavigationView.setSelectedItemId(R.id.social);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    Intent gotoHome = new Intent(AddPostActivity.this, MainActivity.class);
                    startActivity(gotoHome);
                    break;
                case R.id.profile:
                    Intent goToProfile = new Intent(AddPostActivity.this, ProfileActivity.class);
                    startActivity(goToProfile);
                    break;
                case R.id.daily_goal:
                    Intent goToDailyGoal = new Intent(AddPostActivity.this, DailyGoalActivity.class);
                    startActivity(goToDailyGoal);
                    break;
                case R.id.social:
                    Intent gotoSocial = new Intent(AddPostActivity.this, FeedActivity.class);
                    startActivity(gotoSocial);
                    break;
            }
            return false;
        });

        registerLauncher();

    }

    public void sharePost(View view) {

        binding.progress.setVisibility(View.VISIBLE);
        binding.submitButton.setVisibility(View.GONE);

        if ( binding.postText.getText().length() == 0 ) {
            binding.postText.setError("Lütfen bir açıklama girin.");
            return;
        }

        if ( imageData != null ) {
            UUID uuid = UUID.randomUUID();
            String imageName = "post_images/" + uuid + ".jpg";

            storageReference.child(imageName).putFile(imageData).addOnCompleteListener(task -> {
                if ( task.isSuccessful() ) {
                    // image uploaded
                    StorageReference uploadedImage = storage.getReference(imageName);
                    uploadedImage.getDownloadUrl().addOnCompleteListener(task1 -> {
                        if ( task1.isSuccessful() ) {

                            String downloadUrl = task1.getResult().toString();
                            String text = binding.postText.getText().toString();
                            String user = auth.getCurrentUser().getUid();
                            Date now = new Date();
                            DateFormat df = new SimpleDateFormat("dd/MM/yyyy h:m");
                            String date = df.format(now);

                            Map<String, Object> data = new HashMap<>();
                            data.put("imageUrl", downloadUrl);
                            data.put("text", text);
                            data.put("user", user);
                            data.put("server_date", FieldValue.serverTimestamp());
                            data.put("date", date);
                            data.put("commentCount", 0);
                            data.put("id", uuid.toString());

                            if ( auth.getCurrentUser().getDisplayName().isEmpty() ) {
                                data.put("username", auth.getCurrentUser().getEmail());
                            } else {
                                data.put("username", auth.getCurrentUser().getDisplayName());
                            }

                            db.collection("Posts").add(data).addOnCompleteListener(task2 -> {
                                if ( task2.isSuccessful() ) {
                                    Intent intent = new Intent(AddPostActivity.this, FeedActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    Toast.makeText(this, "Gönderi paylaşıldı", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(this, "Bir hata oluştu: " + task2.getException().toString(), Toast.LENGTH_SHORT).show();
                                    binding.progress.setVisibility(View.GONE);
                                    binding.submitButton.setVisibility(View.VISIBLE);
                                }
                            });
                        } else {
                            binding.progress.setVisibility(View.GONE);
                            binding.submitButton.setVisibility(View.VISIBLE);
                            Toast.makeText(AddPostActivity.this, task1.getException().toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    binding.progress.setVisibility(View.GONE);
                    binding.submitButton.setVisibility(View.VISIBLE);
                    Toast.makeText(AddPostActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    public void selectImage(View view) {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE) ) {
                Snackbar.make(view, "Resim yüklemek için izin vermelisiniz.", Snackbar.LENGTH_INDEFINITE).setAction("İzin Ver", v -> {
                    permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
                }).show();
            } else {
                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        } else {
            Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            activityResultLauncher.launch(intentToGallery);
        }

    }

    public void registerLauncher() {
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if ( result.getResultCode() == RESULT_OK ) {
                Intent intentFromResult = result.getData();
                if ( intentFromResult != null ) {
                    imageData = intentFromResult.getData();
                    binding.selectPhoto.setImageURI(imageData);

                    try {

                        if ( Build.VERSION.SDK_INT >= 28 ) {
                            ImageDecoder.Source source = ImageDecoder.createSource(AddPostActivity.this.getContentResolver(), imageData);
                            selectedImage = ImageDecoder.decodeBitmap(source);
                            binding.selectPhoto.setImageBitmap(selectedImage);
                        } else {
                            selectedImage = MediaStore.Images.Media.getBitmap(AddPostActivity.this.getContentResolver(), imageData);
                            binding.selectPhoto.setImageBitmap(selectedImage);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        permissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), result -> {
            if ( result ) {
                Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityResultLauncher.launch(intentToGallery);
            } else {
                Toast.makeText(AddPostActivity.this, "İzin gerekli!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void backTo(View view) {
        Intent backIntent = new Intent(AddPostActivity.this, FeedActivity.class);
        backIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(backIntent);
        finish();
    }

}