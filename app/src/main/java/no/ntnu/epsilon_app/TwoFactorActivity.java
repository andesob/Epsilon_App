package no.ntnu.epsilon_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;

import no.ntnu.epsilon_app.api.RetrofitClientInstance;
import no.ntnu.epsilon_app.data.LoginViewModel;
import no.ntnu.epsilon_app.data.LoginViewModelFactory;
import no.ntnu.epsilon_app.data.User;
import no.ntnu.epsilon_app.data.UserParser;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TwoFactorActivity extends AppCompatActivity {
    private EditText editTwoFactor;
    private LoginViewModel loginViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_factor);
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        editTwoFactor = findViewById(R.id.twoFactorEditText);
        final Button sendButton = findViewById(R.id.twoFactorButton);
        final ProgressBar progressBar = findViewById(R.id.progressBar);
        TextView twoFactorTV = findViewById(R.id.twoFactorText);
        progressBar.setVisibility(View.GONE);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                sendButton.setVisibility(View.INVISIBLE);
                checkTwoFactor();
            }
        });



    }

    private void checkTwoFactor(){
        Bundle extras = getIntent().getExtras();
        String email = "";
        String pwd = "";
        String twofactor = editTwoFactor.getText().toString().trim();

        final SharedUserPrefs sharedUserPrefs = new SharedUserPrefs(this);

        if (extras != null){
            email = extras.getString("email");
            pwd = extras.getString("pwd");
        }

        if (twofactor.isEmpty()) {
            editTwoFactor.setError("Vennligst tast inn en kode.");
            editTwoFactor.requestFocus();
            return;
        }


        Call<ResponseBody> call = RetrofitClientInstance.getSINGLETON().getAPI().checkTwoFactor(email, pwd, twofactor);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        final String token = response.headers().get("Authorization");
                        User user = UserParser.parseUser(response.body().string());
                        loginViewModel.login(user.getUserid(), user.getFirstName(), user.getGroups());
                        sharedUserPrefs.setToken(token);

                        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
                        httpClient.addInterceptor(new Interceptor() {
                            @Override
                            public okhttp3.Response intercept(Chain chain) throws IOException {
                                Request request = chain.request().newBuilder().addHeader("Authorization", token).build();
                                return chain.proceed(request);
                            }
                        });

                        RetrofitClientInstance.addInterceptor(httpClient);

                        startActivity(new Intent(TwoFactorActivity.this, AfterLoginSplashActivity.class));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}