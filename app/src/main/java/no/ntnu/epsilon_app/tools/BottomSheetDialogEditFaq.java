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
import no.ntnu.epsilon_app.ui.faq.Faq;
import no.ntnu.epsilon_app.ui.faq.FaqViewModel;

/**
 * A bottom sheet dialog for editing a faq.
 */

public class BottomSheetDialogEditFaq extends BottomSheetDialogFragment {
    private EditText editFaqQuestion;
    private EditText editFaqAnswer;
    private FaqViewModel faqViewModel;
    private Faq currentFaq;

    public BottomSheetDialogEditFaq(Faq faq) {
        this.currentFaq = faq;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.edit_faq_bottom_sheet_layout,
                container, false);

        final Button doneButton = root.findViewById(R.id.doneButton);
        final Button closeButton = root.findViewById(R.id.closeButton);
        editFaqQuestion = root.findViewById(R.id.editFaqQuestion);
        editFaqAnswer = root.findViewById(R.id.editFaqAnswer);
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
                long id = currentFaq.getId();
                faqViewModel.editFaq(question, answer, id);
                dismiss();
            }
        });

        setText();
        //getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        return root;
    }

    private void setText() {
        if (currentFaq != null) {
            editFaqQuestion.setText(currentFaq.getQuestion());
            editFaqAnswer.setText(currentFaq.getAnswer());
        }
    }
}


