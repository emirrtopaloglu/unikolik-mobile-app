package com.emirtopaloglu.unikolik.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.emirtopaloglu.unikolik.Controller.SpotifyAdapter;
import com.emirtopaloglu.unikolik.Model.Spotify;
import com.emirtopaloglu.unikolik.R;
import com.emirtopaloglu.unikolik.databinding.ActivitySpotifyBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class SpotifyActivity extends AppCompatActivity {

    private ActivitySpotifyBinding binding;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    String image, title, creator, uri;
    ArrayList<Spotify> spotifyArrayList;
    SpotifyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySpotifyBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        spotifyArrayList = new ArrayList<>();

        getSpotifyData();

        binding.spotifyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SpotifyAdapter(spotifyArrayList);
        binding.spotifyRecyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }

    private void getSpotifyData() {
        db.collection("Playlists").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document: task.getResult()) {
                            Map<String, Object> data = document.getData();
                            image = (String) data.get("playlistImageUri").toString();
                            uri = (String) data.get("playlistUri").toString();
                            creator = (String) data.get("playlistCreator").toString();
                            title = (String) data.get("playlistTitle").toString();

                            Spotify spotify = new Spotify(image, title, creator, uri);
                            spotifyArrayList.add(spotify);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(this, "Playlist verileri alınamadı.", Toast.LENGTH_SHORT).show();
                        Log.w("SpotifyActivity", task.getException().toString());
                    }
                });
    }

    public void backTo(View view) {
        Intent backTo = new Intent(SpotifyActivity.this, ToolsActivity.class);
        backTo.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(backTo);
    }
}