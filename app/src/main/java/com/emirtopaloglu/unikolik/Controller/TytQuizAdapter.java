package com.emirtopaloglu.unikolik.Controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.emirtopaloglu.unikolik.Model.TytQuiz;
import com.emirtopaloglu.unikolik.databinding.CommentRecyclerRowBinding;
import com.emirtopaloglu.unikolik.databinding.QuizTrackRecyclerRowBinding;

import java.util.ArrayList;

public class TytQuizAdapter extends RecyclerView.Adapter<TytQuizAdapter.TytQuizHolder> {

    String tytTurkceDogru, tytTurkceYanlis, tytSosyalDogru, tytSosyalYanlis,
            tytMatematikDogru, tytMatematikYanlis, tytFenDogru, tytFenYanlis,
            tytTurkceNet, tytSosyalNet, tytMatematikNet, tytFenNet, tytToplamNet;
    ArrayList<TytQuiz> quizzes;

    public TytQuizAdapter(ArrayList<TytQuiz> quizzes) {
        this.quizzes = quizzes;
    }

    @NonNull
    @Override
    public TytQuizAdapter.TytQuizHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        QuizTrackRecyclerRowBinding binding = QuizTrackRecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new TytQuizHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TytQuizAdapter.TytQuizHolder holder, int position) {
        tytTurkceDogru = quizzes.get(position).getTytTurkceDogru();
        tytTurkceYanlis = quizzes.get(position).getTytTurkceYanlis();
        tytSosyalDogru = quizzes.get(position).getTytSosyalDogru();
        tytSosyalYanlis = quizzes.get(position).getTytSosyalYanlis();
        tytMatematikDogru = quizzes.get(position).getTytMatematikDogru();
        tytMatematikYanlis = quizzes.get(position).getTytMatematikYanlis();
        tytFenDogru = quizzes.get(position).getTytFenDogru();
        tytFenYanlis = quizzes.get(position).getTytFenYanlis();
        tytTurkceNet = quizzes.get(position).getTytTurkceNet();
        tytSosyalNet = quizzes.get(position).getTytSosyalNet();
        tytMatematikNet = quizzes.get(position).getTytMatematikNet();
        tytFenNet = quizzes.get(position).getTytFenNet();
        tytToplamNet = quizzes.get(position).getTytToplamNet();

        holder.binding.quizTrackTitle.setOnClickListener(view -> {
            if ( holder.binding.quizTrackDetail.getVisibility() == View.GONE ) {
                holder.binding.quizTrackDetail.setVisibility(View.VISIBLE);
            } else {
                holder.binding.quizTrackDetail.setVisibility(View.GONE);
            }
        });

        holder.binding.title.setText(quizzes.size() - position + ". TYT Denemesi");
        holder.binding.toplamnet.setText(quizzes.get(position).getTytToplamNet() + " net");
        holder.binding.dateDate.setText(quizzes.get(position).getQuizDate());
        holder.binding.lesson1Title.setText("Türkçe");
        holder.binding.lesson1Stats.setText(tytTurkceDogru + " D | " + tytTurkceYanlis + " Y | " + tytTurkceNet + " Net");
        holder.binding.lesson2Title.setText("Sosyal Bilimler");
        holder.binding.lesson2Stats.setText(tytSosyalDogru + " D | " + tytSosyalYanlis + " Y | " + tytSosyalNet + " Net");
        holder.binding.lesson3Title.setText("Temel Matematik");
        holder.binding.lesson3Stats.setText(tytMatematikDogru + " D | " + tytMatematikYanlis + " Y | " + tytMatematikNet + " Net");
        holder.binding.lesson4Title.setText("Fen Bilimleri");
        holder.binding.lesson4Stats.setText(tytFenDogru + " D | " + tytFenYanlis + " Y | " + tytFenNet + " Net");
    }

    @Override
    public int getItemCount() {
        return quizzes.size();
    }

    public class TytQuizHolder extends RecyclerView.ViewHolder {

        private QuizTrackRecyclerRowBinding binding;

        public TytQuizHolder(QuizTrackRecyclerRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
