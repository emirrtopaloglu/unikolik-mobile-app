package com.emirtopaloglu.unikolik.Controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.emirtopaloglu.unikolik.Model.Comment;
import com.emirtopaloglu.unikolik.databinding.CommentRecyclerRowBinding;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentHolder> {

    ArrayList<Comment> comments;

    public CommentAdapter(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    @NonNull
    @Override
    public CommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CommentRecyclerRowBinding binding = CommentRecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CommentHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentHolder holder, int position) {
        String username = comments.get(position).getUsername();
        String text = comments.get(position).getText();
        String date = comments.get(position).getDate();
        String post_id = comments.get(position).getPostId();

        holder.binding.commentText.setText(text);
        holder.binding.commentUsername.setText(username);
        holder.binding.commentDate.setText(date);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class CommentHolder extends RecyclerView.ViewHolder {
        private CommentRecyclerRowBinding binding;

        public CommentHolder(CommentRecyclerRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
