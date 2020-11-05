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

public class BottomSheetDialogAddFaq extends BottomSheetDialogFragment {
    private EditText editFaqQuestion;
    private EditText editFaqAnswer;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.add_faq_bottom_sheet_layout,
                container, false);

        final Button doneButton = root.findViewById(R.id.doneButtonAdd);
        final Button closeButton = root.findViewById(R.id.closeButtonAdd);
        editFaqQuestion = root.findViewById(R.id.addFaqQuestion);
        editFaqAnswer = root.findViewById(R.id.AddFaqAnswer);


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
                if(question != null && answer != null) {
                    addFaq(question, answer);
                }
            }
        });
        //getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        return root;
    }



    private void addFaq(String question, String answer) {
        Call<ResponseBody> call = RetrofitClientInstance.getSINGLETON().getAPI().add_faqs(question, answer);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    System.out.println(response.body());
                    dismiss();
                    Toast.makeText(getContext(), "Lagt til", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getContext(), "Error: kunne ikke legge til", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "Error: kunne ikke legge til", Toast.LENGTH_SHORT).show();
            }
        });
    }
}


