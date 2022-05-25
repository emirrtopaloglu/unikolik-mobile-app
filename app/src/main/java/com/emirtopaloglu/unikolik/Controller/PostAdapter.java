package com.emirtopaloglu.unikolik.Controller;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.emirtopaloglu.unikolik.Model.Post;
import com.emirtopaloglu.unikolik.R;
import com.emirtopaloglu.unikolik.View.PostActivity;
import com.emirtopaloglu.unikolik.databinding.FeedRecyclerRowBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostHolder> {

    private ArrayList<Post> posts;
    private FirebaseAuth auth;
    private FirebaseFirestore db;

    public PostAdapter(ArrayList<Post> posts) {
        this.posts = posts;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FeedRecyclerRowBinding binding = FeedRecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        return new PostHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        String text = posts.get(position).getText();
        String username = posts.get(position).getUsername();
        String imageUri = posts.get(position).getImageUrl();
        String date = posts.get(position).getDate();
        String id = posts.get(position).getId();
        String userId = posts.get(position).getUser();

        Picasso.get().load(imageUri).into(holder.binding.feedPhoto);
        holder.binding.feedText.setText(text);
        holder.binding.feedUsername.setText(username);
        holder.binding.feedDate.setText(date);
        if ( auth.getCurrentUser().getUid().equals(userId) ) {
            holder.binding.feedMenu.setVisibility(View.VISIBLE);
            holder.binding.feedMenu.setOnClickListener(view -> {
                // Document Delete in Firestore
                db.collection("Posts").whereEqualTo("id", id).get().addOnCompleteListener(task -> {
                    if ( task.isSuccessful() ) {
                        for ( DocumentSnapshot documentSnapshot : task.getResult() ) {
                            documentSnapshot.getReference().delete().addOnCompleteListener(task1 -> {
                                if ( task1.isSuccessful() ) {
                                    Toast.makeText(holder.binding.getRoot().getContext(), "Gönderi başarıyla silindi", Toast.LENGTH_SHORT).show();
                                    // set notify changed
                                    posts.remove(position);
                                    notifyItemRemoved(position);
                                } else {
                                    Toast.makeText(holder.binding.getRoot().getContext(), "Gönderi silinemedi", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
                db.collection("Comments").whereEqualTo("post_id", id).get().addOnCompleteListener(task -> {
                    if ( task.isSuccessful() ) {
                        for ( DocumentSnapshot documentSnapshot : task.getResult() ) {
                            documentSnapshot.getReference().delete();
                        }
                    }
                });
            });
        } else {
            holder.binding.feedMenu.setVisibility(View.GONE);
        }
        holder.binding.feedComment.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), PostActivity.class);
            intent.putExtra("text", text);
            intent.putExtra("username", username);
            intent.putExtra("imageUri", imageUri);
            intent.putExtra("date", date);
            intent.putExtra("post_id", id);
            intent.putExtra("userId", userId);
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class PostHolder extends RecyclerView.ViewHolder {

        FeedRecyclerRowBinding binding;

        public PostHolder(@NonNull FeedRecyclerRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
