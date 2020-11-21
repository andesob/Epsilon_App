package no.ntnu.epsilon_app.ui.ask_question;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import no.ntnu.epsilon_app.MainActivity;
import no.ntnu.epsilon_app.R;
import no.ntnu.epsilon_app.api.RetrofitClientInstance;
import no.ntnu.epsilon_app.ui.faq.Faq;
import no.ntnu.epsilon_app.ui.faq.FaqExpandableViewAdapter;
import no.ntnu.epsilon_app.ui.faq.dummy.TestData;
import no.ntnu.epsilon_app.ui.news.NewsParser;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ask_question_fragment extends Fragment {

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */

    private EditText questionInput;

    public ask_question_fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_ask_question, container, false);
        final Button submitButton = root.findViewById(R.id.SubmitButton);
        questionInput = root.findViewById(R.id.editTextQuestionInput);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String question =  questionInput.getText().toString();
                sendQuestion(question);
                Navigation.findNavController(root).navigate(R.id.nav_newsfeed);
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

    private void sendQuestion(final String question) {
        Call<ResponseBody> call = RetrofitClientInstance.getSINGLETON().getAPI().askQuestion(question);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    String success = "Question sent";
                    Toast.makeText(getContext(),success , Toast.LENGTH_SHORT).show();
                    }
                else{
                    ((MainActivity)getActivity()).goToSplashScreen();
                    }
                }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                String error = "An error occurred while sending the question";
                Toast.makeText(getContext(),error , Toast.LENGTH_SHORT).show();
            }
        });

    }
}