package no.ntnu.epsilon_app.tools;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import no.ntnu.epsilon_app.R;
import no.ntnu.epsilon_app.api.RetrofitClientInstance;
import no.ntnu.epsilon_app.ui.faq.Faq;
import no.ntnu.epsilon_app.ui.faq.FaqFragment;
import no.ntnu.epsilon_app.ui.faq.FaqViewModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BottomSheetDialogAddFaq extends BottomSheetDialogFragment {
    private EditText editFaqQuestion;
    private EditText editFaqAnswer;
    private FaqViewModel faqViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.add_faq_bottom_sheet_layout,
                container, false);

        final Button doneButton = root.findViewById(R.id.doneButtonAdd);
        final Button closeButton = root.findViewById(R.id.closeButtonAdd);
        editFaqQuestion = root.findViewById(R.id.addFaqQuestion);
        editFaqAnswer = root.findViewById(R.id.AddFaqAnswer);
        faqViewModel =  new ViewModelProvider(requireActivity()).get(FaqViewModel.class);


        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String question = editFaqQuestion.getText().toString();
                String answer = editFaqAnswer.getText().toString();
                if (!question.isEmpty()) {
                    faqViewModel.addFaq(question, answer).observe(getViewLifecycleOwner(), new Observer<Response>() {
                        @Override
                        public void onChanged(@NonNull Response response) {
                            if (response.isSuccessful()) {
                                faqViewModel.getFaqList();
                                dismiss();

                            } else {
                                Toast.makeText(getContext(), "Error: kunne ikke legge til", Toast.LENGTH_SHORT).show();
                            }
                        }

                    });
                }

                //getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

            }
        });
        return root;
    }
}


