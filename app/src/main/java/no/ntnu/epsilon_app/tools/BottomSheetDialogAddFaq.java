package no.ntnu.epsilon_app.tools;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import no.ntnu.epsilon_app.R;
import no.ntnu.epsilon_app.api.RetrofitClientInstance;
import no.ntnu.epsilon_app.ui.faq.FaqViewModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BottomSheetDialogEditFaq extends BottomSheetDialogFragment {
    private EditText editFaqQuestion;
    private EditText editFaqAnswer;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.edit_faq_bottom_sheet_layout,
                container, false);

        final Button doneButton = root.findViewById(R.id.doneButton);
        final Button closeButton = root.findViewById(R.id.closeButton);
        editFaqQuestion = root.findViewById(R.id.editFaqQuestion);
        editFaqAnswer = root.findViewById(R.id.editFaqAnswer);


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
                editFaq(question, answer, id);

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


    private void editFaq(String question, String answer, long id) {
        Call<ResponseBody> call = RetrofitClientInstance.getSINGLETON().getAPI().edit_faq(question, answer, id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    System.out.println(response.body());
                    dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "Could not change the FAQ", Toast.LENGTH_SHORT).show();
            }
        });
    }
}


