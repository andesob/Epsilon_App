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
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

import no.ntnu.epsilon_app.R;
import no.ntnu.epsilon_app.api.RetrofitClientInstance;
import no.ntnu.epsilon_app.ui.faq.Faq;
import no.ntnu.epsilon_app.ui.faq.FaqViewModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BottomSheetDialogEditFaq extends BottomSheetDialogFragment {
    private EditText editFaqQuestion;
    private EditText editFaqAnswer;
    private FaqViewModel faqViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.edit_faq_bottom_sheet_layout,
                container, false);

        final Button doneButton = root.findViewById(R.id.doneButton);
        final Button closeButton = root.findViewById(R.id.closeButton);
        editFaqQuestion = root.findViewById(R.id.editFaqQuestion);
        editFaqAnswer = root.findViewById(R.id.editFaqAnswer);
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
                long id = FaqViewModel.SELECTED_FAQ.getId() ;

                faqViewModel.editFaq(question, answer,id).observe(getViewLifecycleOwner(), new Observer<Response>() {

                    @Override
                    public void onChanged(@NonNull Response response) {
                        if (response.isSuccessful()) {
                            new CountDownTimer(300, 1000){
                                @Override
                                public void onTick(long l) {
                                }
                                @Override
                                public void onFinish() {
                                  faqViewModel.getFaqList();
                                    dismiss();
                                }

                            }.start();

                        } else {
                            Toast.makeText(getContext(), "Error: kunne ikke endre", Toast.LENGTH_SHORT).show();
                        }
                    }

                });
            }
        });
        setText();
        //getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        return root;
    }


    private void setText() {
        if (FaqViewModel.SELECTED_FAQ != null) {
            editFaqQuestion.setText(FaqViewModel.SELECTED_FAQ.getQuestion());
            editFaqAnswer.setText(FaqViewModel.SELECTED_FAQ.getAnswer());

        }
    }
}


