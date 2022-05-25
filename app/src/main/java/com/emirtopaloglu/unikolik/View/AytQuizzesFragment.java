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

import com.emirtopaloglu.unikolik.Controller.AytQuizAdapter;
import com.emirtopaloglu.unikolik.Model.AytQuiz;
import com.emirtopaloglu.unikolik.R;
import com.emirtopaloglu.unikolik.databinding.FragmentAytQuizzesBinding;
import com.emirtopaloglu.unikolik.databinding.FragmentTytQuizzesBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class AytQuizzesFragment extends Fragment {

    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private FragmentAytQuizzesBinding binding;
    private Context context;
    AytQuizAdapter adapter;
    ArrayList<AytQuiz> quizzes;
    String aytEdebiyatDogru, aytEdebiyatYanlis, aytTarihBirDogru, aytTarihBirYanlis,
            aytCografyaBirDogru, aytCografyaBirYanlis, aytTarihIkiDogru, aytTarihIkiYanlis,
            aytCografyaIkiDogru, aytCografyaIkiYanlis, aytFelsefeDogru, aytFelsefeYanlis,
            aytDinDogru, aytDinYanlis, aytMatematikDogru, aytMatematikYanlis, aytFizikDogru,
            aytFizikYanlis, aytKimyaDogru, aytKimyaYanlis, aytBiyolojiDogru, aytBiyolojiYanlis,
            aytEdebiyatNet, aytTarihBirNet, aytCografyaBirNet, aytTarihIkiNet,
            aytCografyaIkiNet, aytFelsefeNet, aytDinNet, aytMatematikNet, aytFizikNet,
            aytKimyaNet, aytBiyolojiNet, aytSayNet, aytSozNet, aytEaNet, quizDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAytQuizzesBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        context = container.getContext();

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        quizzes = new ArrayList<>();

        getQuizData();

        binding.quizTrackRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new AytQuizAdapter(quizzes);
        binding.quizTrackRecyclerView.setAdapter(adapter);


        return view;
    }

    private void getQuizData() {
        db.collection("AytQuizzes")
                .whereEqualTo("userId", auth.getCurrentUser().getUid())
                .orderBy("serverDate", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(task -> {
                    if ( task.isSuccessful() ) {
                        System.out.println(task.getResult());
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Map<String, Object> data = document.getData();
                            aytEdebiyatDogru = (String) data.get("aytEdebiyatDogru").toString();
                            aytEdebiyatYanlis = (String) data.get("aytEdebiyatYanlis").toString();
                            aytTarihBirDogru = (String) data.get("aytTarihBirDogru").toString();
                            aytTarihBirYanlis = (String) data.get("aytTarihBirYanlis").toString();
                            aytCografyaBirDogru = (String) data.get("aytCografyaBirDogru").toString();
                            aytCografyaBirYanlis = (String) data.get("aytCografyaBirYanlis").toString();
                            aytTarihIkiDogru = (String) data.get("aytTarihIkiDogru").toString();
                            aytTarihIkiYanlis = (String) data.get("aytTarihIkiYanlis").toString();
                            aytCografyaIkiDogru = (String) data.get("aytCografyaIkiDogru").toString();
                            aytCografyaIkiYanlis = (String) data.get("aytCografyaIkiYanlis").toString();
                            aytFelsefeDogru = (String) data.get("aytFelsefeDogru").toString();
                            aytFelsefeYanlis = (String) data.get("aytFelsefeYanlis").toString();
                            aytDinDogru = (String) data.get("aytDinDogru").toString();
                            aytDinYanlis = (String) data.get("aytDinYanlis").toString();
                            aytMatematikDogru = (String) data.get("aytMatematikDogru").toString();
                            aytMatematikYanlis = (String) data.get("aytMatematikYanlis").toString();
                            aytFizikDogru = (String) data.get("aytFizikDogru").toString();
                            aytFizikYanlis = (String) data.get("aytFizikYanlis").toString();
                            aytKimyaDogru = (String) data.get("aytKimyaDogru").toString();
                            aytKimyaYanlis = (String) data.get("aytKimyaYanlis").toString();
                            aytBiyolojiDogru = (String) data.get("aytBiyolojiDogru").toString();
                            aytBiyolojiYanlis = (String) data.get("aytBiyolojiYanlis").toString();
                            aytEdebiyatNet = (String) data.get("aytEdebiyatNet").toString();
                            aytTarihBirNet = (String) data.get("aytTarihBirNet").toString();
                            aytCografyaBirNet = (String) data.get("aytCografyaBirNet").toString();
                            aytTarihIkiNet = (String) data.get("aytTarihIkiNet").toString();
                            aytCografyaIkiNet = (String) data.get("aytCografyaIkiNet").toString();
                            aytFelsefeNet = (String) data.get("aytFelsefeNet").toString();
                            aytDinNet = (String) data.get("aytDinNet").toString();
                            aytMatematikNet = (String) data.get("aytMatematikNet").toString();
                            aytFizikNet = (String) data.get("aytFizikNet").toString();
                            aytKimyaNet = (String) data.get("aytKimyaNet").toString();
                            aytBiyolojiNet = (String) data.get("aytBiyolojiNet").toString();
                            aytSayNet = (String) data.get("aytSayNet").toString();
                            aytSozNet = (String) data.get("aytSozNet").toString();
                            aytEaNet = (String) data.get("aytEaNet").toString();
                            quizDate = (String) data.get("quizDate").toString();

                            AytQuiz aytQuiz = new AytQuiz(aytEdebiyatDogru, aytEdebiyatYanlis, aytTarihBirDogru, aytTarihBirYanlis, aytCografyaBirDogru,
                                    aytCografyaBirYanlis, aytTarihIkiDogru, aytTarihIkiYanlis, aytCografyaIkiDogru, aytCografyaIkiYanlis, aytFelsefeDogru,
                                    aytFelsefeYanlis, aytDinDogru, aytDinYanlis, aytMatematikDogru, aytMatematikYanlis, aytFizikDogru, aytFizikYanlis,
                                    aytKimyaDogru, aytKimyaYanlis, aytBiyolojiDogru, aytBiyolojiYanlis, aytEdebiyatNet, aytTarihBirNet, aytCografyaBirNet,
                                    aytTarihIkiNet, aytCografyaIkiNet, aytFelsefeNet, aytDinNet, aytMatematikNet, aytFizikNet, aytKimyaNet, aytBiyolojiNet,
                                    aytSayNet, aytSozNet, aytEaNet, quizDate);

                            quizzes.add(aytQuiz);
                        }

                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(context, "Veriler alınamadı: " + task.getException(), Toast.LENGTH_SHORT).show();
                        System.out.println(task.getException().toString());
                    }
                });
    }

}