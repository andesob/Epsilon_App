package no.ntnu.epsilon_app.tools;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import no.ntnu.epsilon_app.R;
import no.ntnu.epsilon_app.ui.faq.FaqViewModel;

/**
 * A bottom sheet dialog for adding a faq to the database
 */
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
        faqViewModel = new ViewModelProvider(requireActivity()).get(FaqViewModel.class);


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
                    faqViewModel.addFaq(question, answer);
                    dismiss();
                }

            }
        });
        return root;

    }
}



