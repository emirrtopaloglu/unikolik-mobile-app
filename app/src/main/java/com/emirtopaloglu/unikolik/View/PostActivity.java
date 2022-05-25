package com.emirtopaloglu.unikolik.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.emirtopaloglu.unikolik.Controller.CommentAdapter;
import com.emirtopaloglu.unikolik.Model.Comment;
import com.emirtopaloglu.unikolik.R;
import com.emirtopaloglu.unikolik.databinding.ActivityPostBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PostActivity extends AppCompatActivity {

    private ActivityPostBinding binding;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private FirebaseStorage storage;
    BottomNavigationView bottomNavigationView;
    String text, username, imageUri, date, postId;
    CommentAdapter adapter;
    ArrayList<Comment> comments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();

        if ( auth.getCurrentUser() == null ) {
            Intent checkLoggedIntent = new Intent(PostActivity.this, PreloginActivity.class);
            checkLoggedIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(checkLoggedIntent);
            finish();
        }

        bottomNavigationView = binding.BottomNavigationBar;
        bottomNavigationView.setSelectedItemId(R.id.social);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    Intent gotoHome = new Intent(PostActivity.this, MainActivity.class);
                    startActivity(gotoHome);
                    break;
                case R.id.profile:
                    Intent goToProfile = new Intent(PostActivity.this, ProfileActivity.class);
                    startActivity(goToProfile);
                    break;
                case R.id.daily_goal:
                    Intent goToDailyGoal = new Intent(PostActivity.this, DailyGoalActivity.class);
                    startActivity(goToDailyGoal);
                    break;
                case R.id.social:
                    Intent gotoSocial = new Intent(PostActivity.this, FeedActivity.class);
                    startActivity(gotoSocial);
                    break;
            }
            return false;
        });

        comments = new ArrayList<>();

        binding.commentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CommentAdapter(comments);
        binding.commentsRecyclerView.setAdapter(adapter);

        getIntentData();
        setIntentData();
        getCommentData();

        adapter.notifyDataSetChanged();

    }

    public void backTo(View view) {
        Intent backIntent = new Intent(PostActivity.this, FeedActivity.class);
        backIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(backIntent);
        finish();
    }

    private void getIntentData() {
        Intent intent = getIntent();
        text = intent.getStringExtra("text");
        username = intent.getStringExtra("username");
        imageUri = intent.getStringExtra("imageUri");
        date = intent.getStringExtra("date");
        postId = intent.getStringExtra("post_id");
    }

    private void setIntentData() {
        binding.feedText.setText(text);
        binding.feedUsername.setText(username);
        Picasso.get().load(imageUri).into(binding.feedPhoto);
        binding.feedDate.setText(date);
    }

    private void getCommentData() {
        db.collection("Comments")
                .whereEqualTo("post_id", postId).orderBy("server_date", Query.Direction.DESCENDING).get()
                .addOnCompleteListener(task -> {
                    if ( task.isSuccessful() ) {
                        for ( DocumentSnapshot document : task.getResult() ) {
                            Map<String, Object> data = document.getData();
                            String username = (String) data.get("username");
                            String text = (String) data.get("text");
                            String date = (String) data.get("date");
                            String post_id = (String) data.get("post_id");

                            Comment comment = new Comment(username, text, date, post_id);
                            comments.add(comment);
                        }

                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(this, "Yorumlar alınamadı: " + task.getException(), Toast.LENGTH_SHORT).show();
                        System.out.println(task.getException().toString());
                    }
                });
    }

    public void sendComment(View view) {

        if ( binding.commentText.getText().length() == 0 ) {
            binding.commentText.setError("Yorum kısmı boş olamaz.");
            return;
        }

        binding.progress.setVisibility(View.VISIBLE);
        binding.commentButton.setVisibility(View.GONE);

        Date now = new Date();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy h:m");
        String date = df.format(now);

        Map<String, Object> data = new HashMap<>();
        if ( auth.getCurrentUser().getDisplayName().isEmpty() ) {
            data.put("username", auth.getCurrentUser().getEmail());
        } else {
            data.put("username", auth.getCurrentUser().getDisplayName());
        }
        data.put("user", auth.getCurrentUser().getUid());
        data.put("text", binding.commentText.getText().toString());
        data.put("server_date", FieldValue.serverTimestamp());
        data.put("date", date);
        data.put("post_id", postId);

        db.collection("Comments").add(data).addOnCompleteListener(task -> {
            if ( task.isSuccessful() ) {
                binding.commentText.setText("");
                Toast.makeText(this, "Yorumunuz gönderildi!", Toast.LENGTH_SHORT).show();
                Intent reset = new Intent(PostActivity.this, PostActivity.class);
                reset.putExtra("text", text);
                reset.putExtra("username", username);
                reset.putExtra("imageUri", imageUri);
                reset.putExtra("date", date);
                reset.putExtra("post_id", postId);
                finish();
                startActivity(reset);

                adapter.notifyDataSetChanged(); // It is not working
            } else {
                Toast.makeText(this, "Yorum gönderilirken hata: " + task.getException(), Toast.LENGTH_SHORT).show();
            }
            binding.progress.setVisibility(View.GONE);
            binding.commentButton.setVisibility(View.VISIBLE);
        });
    }

}