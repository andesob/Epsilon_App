package no.ntnu.epsilon_app.ui.news;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

import no.ntnu.epsilon_app.R;
import no.ntnu.epsilon_app.api.RetrofitClientInstance;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostNewsFragment extends Fragment {

    private PostNewsViewModel mViewModel;
    private View root;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.post_news_fragment, container, false);

        final EditText titleEditText = root.findViewById(R.id.postNewsTitleEditText);
        final EditText contentEditText = root.findViewById(R.id.postNewsContentEditText);
        final Button postButton = root.findViewById(R.id.postNewsButton);

        // TODO: Implement checks while entering text
        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };

        titleEditText.addTextChangedListener(afterTextChangedListener);
        contentEditText.addTextChangedListener(afterTextChangedListener);

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postNews(titleEditText.getText().toString(), contentEditText.getText().toString());

            }
        });
        return root;
    }

    private void postNews(String title, String content){
        Call<ResponseBody> call = RetrofitClientInstance.getSINGLETON().getAPI().postNews(title, content);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        NewsParser.parseNews(response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Navigation.findNavController(root).navigate(R.id.nav_newsfeed);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}