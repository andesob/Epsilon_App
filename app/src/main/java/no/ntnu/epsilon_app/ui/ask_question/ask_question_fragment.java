package no.ntnu.epsilon_app.ui.ask_question;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;

import no.ntnu.epsilon_app.R;
import no.ntnu.epsilon_app.ui.faq.FaqExpandableViewAdapter;
import no.ntnu.epsilon_app.ui.faq.dummy.TestData;


public class ask_question_fragment extends Fragment {

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ask_question_fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_ask_question, container, false);
        final EditText questionInput = root.findViewById(R.id.editTextQuestionInput);
        final Button submitButton = root.findViewById(R.id.SubmitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String question =  questionInput.getText().toString();
                Toast.makeText(root.getContext(), question, Toast.LENGTH_SHORT).show();
            }
        });


        questionInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                   questionInput.setGravity(Gravity.CENTER);
                   questionInput.setHint("Ask question");
                } else {
                    questionInput.setGravity(Gravity.START);
                    questionInput.setHint("");
                }
            }
        });

        return root;
    }
}