package com.emirtopaloglu.unikolik.Controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.emirtopaloglu.unikolik.Model.AytQuiz;
import com.emirtopaloglu.unikolik.databinding.QuizTrackAytRecyclerRowBinding;
import com.emirtopaloglu.unikolik.databinding.QuizTrackRecyclerRowBinding;

import java.util.ArrayList;

public class AytQuizAdapter extends RecyclerView.Adapter<AytQuizAdapter.AytQuizHolder> {

    String aytEdebiyatDogru, aytEdebiyatYanlis, aytTarihBirDogru, aytTarihBirYanlis,
            aytCografyaBirDogru, aytCografyaBirYanlis, aytTarihIkiDogru, aytTarihIkiYanlis,
            aytCografyaIkiDogru, aytCografyaIkiYanlis, aytFelsefeDogru, aytFelsefeYanlis,
            aytDinDogru, aytDinYanlis, aytMatematikDogru, aytMatematikYanlis, aytFizikDogru,
            aytFizikYanlis, aytKimyaDogru, aytKimyaYanlis, aytBiyolojiDogru, aytBiyolojiYanlis,
            aytEdebiyatNet, aytTarihBirNet, aytCografyaBirNet, aytTarihIkiNet,
            aytCografyaIkiNet, aytFelsefeNet, aytDinNet, aytMatematikNet, aytFizikNet,
            aytKimyaNet, aytBiyolojiNet, aytSayNet, aytSozNet, aytEaNet, quizDate;

    ArrayList<AytQuiz> quizzes;

    public AytQuizAdapter(ArrayList<AytQuiz> quizzes) {
        this.quizzes = quizzes;
    }

    @NonNull
    @Override
    public AytQuizHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        QuizTrackAytRecyclerRowBinding binding = QuizTrackAytRecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new AytQuizHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AytQuizHolder holder, int position) {
        aytEdebiyatDogru = quizzes.get(position).getAytEdebiyatDogru();
        aytEdebiyatYanlis = quizzes.get(position).getAytEdebiyatYanlis();
        aytTarihBirDogru = quizzes.get(position).getAytTarihBirDogru();
        aytTarihBirYanlis = quizzes.get(position).getAytTarihBirYanlis();
        aytCografyaBirDogru = quizzes.get(position).getAytCografyaBirDogru();
        aytCografyaBirYanlis = quizzes.get(position).getAytCografyaBirYanlis();
        aytTarihIkiDogru = quizzes.get(position).getAytTarihIkiDogru();
        aytTarihIkiYanlis = quizzes.get(position).getAytTarihIkiYanlis();
        aytCografyaIkiDogru = quizzes.get(position).getAytCografyaIkiDogru();
        aytCografyaIkiYanlis = quizzes.get(position).getAytCografyaIkiYanlis();
        aytFelsefeDogru = quizzes.get(position).getAytFelsefeDogru();
        aytFelsefeYanlis = quizzes.get(position).getAytFelsefeYanlis();
        aytDinDogru = quizzes.get(position).getAytDinDogru();
        aytDinYanlis = quizzes.get(position).getAytDinYanlis();
        aytMatematikDogru = quizzes.get(position).getAytMatematikDogru();
        aytMatematikYanlis = quizzes.get(position).getAytMatematikYanlis();
        aytFizikDogru = quizzes.get(position).getAytFizikDogru();
        aytFizikYanlis = quizzes.get(position).getAytFizikYanlis();
        aytKimyaDogru = quizzes.get(position).getAytKimyaDogru();
        aytKimyaYanlis = quizzes.get(position).getAytKimyaYanlis();
        aytBiyolojiDogru = quizzes.get(position).getAytBiyolojiDogru();
        aytBiyolojiYanlis = quizzes.get(position).getAytBiyolojiYanlis();
        aytEdebiyatNet = quizzes.get(position).getAytEdebiyatNet();
        aytTarihBirNet = quizzes.get(position).getAytTarihBirNet();
        aytCografyaBirNet = quizzes.get(position).getAytCografyaBirNet();
        aytTarihIkiNet = quizzes.get(position).getAytTarihIkiNet();
        aytCografyaIkiNet = quizzes.get(position).getAytCografyaIkiNet();
        aytFelsefeNet = quizzes.get(position).getAytFelsefeNet();
        aytDinNet = quizzes.get(position).getAytDinNet();
        aytMatematikNet = quizzes.get(position).getAytMatematikNet();
        aytFizikNet = quizzes.get(position).getAytFizikNet();
        aytKimyaNet = quizzes.get(position).getAytKimyaNet();
        aytBiyolojiNet = quizzes.get(position).getAytBiyolojiNet();
        aytSayNet = quizzes.get(position).getAytSayNet();
        aytSozNet = quizzes.get(position).getAytSozNet();
        aytEaNet = quizzes.get(position).getAytEaNet();
        quizDate = quizzes.get(position).getQuizDate();

        holder.binding.quizTrackTitle.setOnClickListener(view -> {
            if ( holder.binding.quizTrackDetail.getVisibility() == View.GONE ) {
                holder.binding.quizTrackDetail.setVisibility(View.VISIBLE);
            } else {
                holder.binding.quizTrackDetail.setVisibility(View.GONE);
            }
        });

        holder.binding.title.setText(quizzes.size() - position + ". AYT Denemesi");
        holder.binding.quizdate.setText(quizDate);
        holder.binding.lesson1Title.setText("Edebiyat");
        holder.binding.lesson1Stats.setText(aytEdebiyatDogru + " D | " + aytEdebiyatYanlis + " Y | " + aytEdebiyatNet + " Net");
        holder.binding.lesson2Title.setText("Tarih - 1");
        holder.binding.lesson2Stats.setText(aytTarihBirDogru + " D | " + aytTarihBirYanlis + " Y | " + aytTarihBirNet + " Net");
        holder.binding.lesson3Title.setText("Coğrafya - 1");
        holder.binding.lesson3Stats.setText(aytCografyaBirDogru + " D | " + aytCografyaBirYanlis + " Y | " + aytCografyaBirNet + " Net");
        holder.binding.lesson4Title.setText("Tarih - 2");
        holder.binding.lesson4Stats.setText(aytTarihIkiDogru + " D | " + aytTarihIkiYanlis + " Y | " + aytTarihIkiNet + " Net");
        holder.binding.lesson5Title.setText("Coğrafya - 2");
        holder.binding.lesson5Stats.setText(aytCografyaIkiDogru + " D | " + aytCografyaIkiYanlis + " Y | " + aytCografyaIkiNet + " Net");
        holder.binding.lesson6Title.setText("Felsefe");
        holder.binding.lesson6Stats.setText(aytFelsefeDogru + " D | " + aytFelsefeYanlis + " Y | " + aytFelsefeNet + " Net");
        holder.binding.lesson7Title.setText("Din/Felsefe");
        holder.binding.lesson7Stats.setText(aytDinDogru + " D | " + aytDinYanlis + " Y | " + aytDinNet + " Net");
        holder.binding.lesson8Title.setText("Matematik");
        holder.binding.lesson8Stats.setText(aytMatematikDogru + " D | " + aytMatematikYanlis + " Y | " + aytMatematikNet + " Net");
        holder.binding.lesson9Title.setText("Fizik");
        holder.binding.lesson9Stats.setText(aytFizikDogru + " D | " + aytFizikYanlis + " Y | " + aytFizikNet + " Net");
        holder.binding.lesson10Title.setText("Kimya");
        holder.binding.lesson10Stats.setText(aytKimyaDogru + " D | " + aytKimyaYanlis + " Y | " + aytKimyaNet + " Net");
        holder.binding.lesson11Title.setText("Biyoloji");
        holder.binding.lesson11Stats.setText(aytBiyolojiDogru + " D | " + aytBiyolojiYanlis + " Y | " + aytBiyolojiNet + " Net");
        holder.binding.result1Stats.setText(aytSayNet + " Net");
        holder.binding.result2Stats.setText(aytEaNet + " Net");
        holder.binding.result3Stats.setText(aytSozNet + " Net");

    }

    @Override
    public int getItemCount() {
        return quizzes.size();
    }

    public class AytQuizHolder extends RecyclerView.ViewHolder {

        private QuizTrackAytRecyclerRowBinding binding;

        public AytQuizHolder(QuizTrackAytRecyclerRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
