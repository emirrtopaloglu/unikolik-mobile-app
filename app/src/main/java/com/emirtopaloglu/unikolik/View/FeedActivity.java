package com.emirtopaloglu.unikolik.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.emirtopaloglu.unikolik.Controller.PostAdapter;
import com.emirtopaloglu.unikolik.Model.Post;
import com.emirtopaloglu.unikolik.R;
import com.emirtopaloglu.unikolik.databinding.ActivityFeedBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.Map;

public class FeedActivity extends AppCompatActivity {

    private ActivityFeedBinding binding;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private FirebaseStorage storage;
    private FloatingActionButton fab;
    ArrayList<Post> posts;
    PostAdapter adapter;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFeedBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        posts = new ArrayList<>();

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();

        if ( auth.getCurrentUser() == null ) {
            Intent checkLoggedIntent = new Intent(FeedActivity.this, PreloginActivity.class);
            checkLoggedIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(checkLoggedIntent);
            finish();
        }

        fab = binding.fab;
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(FeedActivity.this, AddPostActivity.class);
            startActivity(intent);
        });
        
        getFeedData();

        bottomNavigationView = binding.BottomNavigationBar;
        bottomNavigationView.setSelectedItemId(R.id.social);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    Intent gotoHome = new Intent(FeedActivity.this, MainActivity.class);
                    startActivity(gotoHome);
                    break;
                case R.id.profile:
                    Intent goToProfile = new Intent(FeedActivity.this, ProfileActivity.class);
                    startActivity(goToProfile);
                    break;
                case R.id.daily_goal:
                    Intent goToDailyGoal = new Intent(FeedActivity.this, DailyGoalActivity.class);
                    startActivity(goToDailyGoal);
                    break;
                case R.id.social:
                    Intent gotoSocial = new Intent(FeedActivity.this, FeedActivity.class);
                    startActivity(gotoSocial);
                    break;
                case R.id.tools:
                    Intent gotoTools = new Intent(FeedActivity.this, ToolsActivity.class);
                    startActivity(gotoTools);
                    break;
            }
            return false;
        });

        binding.feedRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PostAdapter(posts);
        binding.feedRecyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }

    private void getFeedData() {

        db.collection("Posts").orderBy("server_date", Query.Direction.DESCENDING).get().addOnCompleteListener(task -> {
            if ( task.isSuccessful() ) {

                for ( DocumentSnapshot document : task.getResult() ) {
                    if ( document.exists() ) {
                        Map<String, Object> data = document.getData();
                        String imageUrl = (String) data.get("imageUrl");
                        String text = (String) data.get("text");
                        String user = (String) data.get("user");
                        String username = (String) data.get("username");
                        String date = (String) data.get("date");
                        String id = (String) data.get("id");

                        Post post = new Post(date, imageUrl, text, user, username, id);
                        posts.add(post);
                    } else {
                        Toast.makeText(this, "Veri bulunamadı.", Toast.LENGTH_SHORT).show();
                    }
                }

                adapter.notifyDataSetChanged();

            } else {
                Toast.makeText(FeedActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}