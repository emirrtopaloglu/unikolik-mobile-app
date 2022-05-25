package com.emirtopaloglu.unikolik.View;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.emirtopaloglu.unikolik.Controller.TytQuizAdapter;
import com.emirtopaloglu.unikolik.Model.TytQuiz;
import com.emirtopaloglu.unikolik.R;
import com.emirtopaloglu.unikolik.databinding.FragmentTytQuizzesBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class TytQuizzesFragment extends Fragment {

    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private FragmentTytQuizzesBinding binding;
    private Context context;
    TytQuizAdapter adapter;
    ArrayList<TytQuiz> quizzes;
    String tytTurkceDogru, tytTurkceYanlis, tytSosyalDogru, tytSosyalYanlis,
            tytMatematikDogru, tytMatematikYanlis, tytFenDogru, tytFenYanlis,
            tytTurkceNet, tytSosyalNet, tytMatematikNet, tytFenNet, tytToplamNet, quizDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTytQuizzesBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        context = container.getContext();

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        quizzes = new ArrayList<>();

        binding.quizTrackRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new TytQuizAdapter(quizzes);
        binding.quizTrackRecyclerView.setAdapter(adapter);

        getQuizData();

        return view;
    }

    private void getQuizData() {
        db.collection("TytQuizzes")
                .whereEqualTo("userId", auth.getCurrentUser().getUid())
                .orderBy("serverDate", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(task -> {
                    if ( task.isSuccessful() ) {
                        for ( DocumentSnapshot document : task.getResult() ) {
                            Map<String, Object> data = document.getData();
                            tytTurkceDogru = (String) data.get("tytTurkceDogru").toString();
                            tytTurkceYanlis = (String) data.get("tytTurkceYanlis").toString();
                            tytSosyalDogru = (String) data.get("tytSosyalDogru").toString();
                            tytSosyalYanlis = (String) data.get("tytSosyalYanlis").toString();
                            tytMatematikDogru = (String) data.get("tytMatematikDogru").toString();
                            tytMatematikYanlis = (String) data.get("tytMatematikYanlis").toString();
                            tytFenDogru = (String) data.get("tytFenDogru").toString();
                            tytFenYanlis = (String) data.get("tytFenYanlis").toString();

                            tytTurkceNet = (String) data.get("tytTurkceNet").toString();
                            tytSosyalNet = (String) data.get("tytSosyalNet").toString();
                            tytMatematikNet = (String) data.get("tytMatematikNet").toString();
                            tytFenNet = (String) data.get("tytFenNet").toString();
                            tytToplamNet = (String) data.get("tytToplamNet").toString();

                            quizDate = (String) data.get("quizDate").toString();

                            TytQuiz quiz = new TytQuiz(tytTurkceDogru, tytTurkceYanlis, tytSosyalDogru, tytSosyalYanlis,
                                    tytMatematikDogru, tytMatematikYanlis, tytFenDogru, tytFenYanlis,
                                    tytTurkceNet, tytSosyalNet, tytMatematikNet, tytFenNet, tytToplamNet, quizDate);

                            quizzes.add(quiz);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(context, "Veriler alınamadı: " + task.getException(), Toast.LENGTH_SHORT).show();
                        System.out.println(task.getException().toString());
                    }
                });
    }

}