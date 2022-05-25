package com.emirtopaloglu.unikolik.Controller;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.emirtopaloglu.unikolik.Model.Spotify;
import com.emirtopaloglu.unikolik.databinding.SpotifyRecyclerRowBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SpotifyAdapter extends RecyclerView.Adapter<SpotifyAdapter.SpotifyHolder> {

    private ArrayList<Spotify> spotifyArrayList;

    public SpotifyAdapter(ArrayList<Spotify> spotifyArrayList) {
        this.spotifyArrayList = spotifyArrayList;
    }

    @NonNull
    @Override
    public SpotifyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SpotifyRecyclerRowBinding binding = SpotifyRecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SpotifyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SpotifyHolder holder, int position) {
        holder.binding.playlistTitle.setText(spotifyArrayList.get(position).getTitle());
        holder.binding.playlistCreator.setText(spotifyArrayList.get(position).getCreator());
        Picasso.get().load(spotifyArrayList.get(position).getImage()).into(holder.binding.playlistImage);
        holder.binding.playlistRow.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(spotifyArrayList.get(position).getUri()));
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return spotifyArrayList.size();
    }

    class SpotifyHolder extends RecyclerView.ViewHolder {

        SpotifyRecyclerRowBinding binding;

        public SpotifyHolder(@NonNull SpotifyRecyclerRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
