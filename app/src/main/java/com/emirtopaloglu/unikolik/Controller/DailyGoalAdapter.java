package com.emirtopaloglu.unikolik.Controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.emirtopaloglu.unikolik.Model.DailyGoal;
import com.emirtopaloglu.unikolik.databinding.DailyGoalRecyclerRowBinding;

import java.util.ArrayList;

public class DailyGoalAdapter extends RecyclerView.Adapter<DailyGoalAdapter.DailyGoalHolder> {

    private ArrayList<DailyGoal> dailyGoals;

    public DailyGoalAdapter(ArrayList<DailyGoal> dailyGoals) {
        this.dailyGoals = dailyGoals;
    }

    @NonNull
    @Override
    public DailyGoalHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DailyGoalRecyclerRowBinding binding = DailyGoalRecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new DailyGoalHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyGoalHolder holder, int position) {
        String date = dailyGoals.get(position).getDate();
        String lesson = dailyGoals.get(position).getLesson();
        String question = dailyGoals.get(position).getQuestion();

        holder.binding.recyclerLesson.setText((position + 1) + ". " + lesson);
        holder.binding.recyclerQuestion.setText(question + " soru");
        holder.binding.recyclerDesc.setText(date + " tarihinde " + lesson + " dersinden " + question + " soru çözdünüz." );

        holder.binding.mainLinearLayout.setOnClickListener(v -> {
            if (holder.binding.subLinearLayout.getVisibility() == View.VISIBLE) {
                holder.binding.subLinearLayout.setVisibility(View.GONE);
            } else {
                holder.binding.subLinearLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dailyGoals.size();
    }

    class DailyGoalHolder extends RecyclerView.ViewHolder {

        DailyGoalRecyclerRowBinding binding;

        public DailyGoalHolder(@NonNull DailyGoalRecyclerRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
