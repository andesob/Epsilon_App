package no.ntnu.epsilon_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import no.ntnu.epsilon_app.api.RetrofitClientInstance;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText emailEditText;
    private Button forgotPasswordButton;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        emailEditText = findViewById(R.id.forgotPasswordEmailET);
        forgotPasswordButton = findViewById(R.id.forgotPasswordButton);
        progressBar = findViewById(R.id.progressBar);

        progressBar.setVisibility(View.GONE);

        forgotPasswordButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                forgotPasswordButton.setVisibility(View.INVISIBLE);

                String email = emailEditText.getText().toString();
                if (email.isEmpty()) {
                    emailEditText.setError("Vennligst tast inn en epost");
                    emailEditText.requestFocus();
                    return;
                }

                sendForgotPasswordRequest(email);

            }
        });

    }

    private void sendForgotPasswordRequest(String email) {
        Call<ResponseBody> call = RetrofitClientInstance.getSINGLETON().getAPI().forgotPassword(email);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (progressBar != null) {
                    progressBar.setVisibility(View.INVISIBLE);
                }

                if (response.isSuccessful()) {
                    Toast.makeText(getBaseContext(), "Nytt passord er sendt på epost.", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class));
                } else {
                    Toast.makeText(getBaseContext(), "Noe gikk galt, vennligst sjekk at du har skrevet inn riktig epost, og prøv på nytt", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}