package no.ntnu.epsilon_app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.io.IOException;

import no.ntnu.epsilon_app.api.RetrofitClientInstance;
import no.ntnu.epsilon_app.data.LoggedInUser;
import no.ntnu.epsilon_app.data.LoginViewModel;
import no.ntnu.epsilon_app.data.LoginViewModelFactory;
import no.ntnu.epsilon_app.data.User;
import no.ntnu.epsilon_app.data.UserParser;
import no.ntnu.epsilon_app.tools.EpsilonFacebookIntent;
import no.ntnu.epsilon_app.ui.register.RegisterActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    private EditText editEmail;
    private EditText editPassword;
    private Button loginButton;
    private Context context = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        //Unnecessary????
        //isLoggedIn();


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editTextPassword);
        final Button loginButton = findViewById(R.id.loginButton);
        final ProgressBar progressBar = findViewById(R.id.progressBar);
        final ImageButton imageButton = findViewById(R.id.facebookLink);
        final TextView registerUserText = findViewById(R.id.registerUserText);
        final TextView forgotPasswordText = findViewById(R.id.forgotPasswordText);

        progressBar.setVisibility(View.GONE);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                loginButton.setVisibility(View.INVISIBLE);

                new CountDownTimer(1500, 1000) {

                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        loginUser();
                        progressBar.setVisibility(View.INVISIBLE);
                        loginButton.setVisibility(View.VISIBLE);
                    }
                }.start();
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(EpsilonFacebookIntent.newFaceBookIntent(getPackageManager()));
            }

        });

        registerUserText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        forgotPasswordText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
            }
        });


        loginViewModel.getLoginResult().observe(this, new Observer<LoggedInUser>() {
            @Override
            public void onChanged(@Nullable LoggedInUser loggedInUser) {
                if (loggedInUser == null) {
                    setResult(Activity.RESULT_OK);
                    //Complete and destroy login activity once successful
                    finish();
                } else {
                    updateUiWithUser(loggedInUser);
                }

            }
        });

    }

    private void loginUser() {
        final String email = editEmail.getText().toString().trim();
        final String pwd = editPassword.getText().toString().trim();

        final SharedUserPrefs sharedUserPrefs = new SharedUserPrefs(this);

        if (email.isEmpty()) {
            editEmail.setError("Vennligst tast inn en epost");
            editPassword.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail.setError("Vennligst tast inn en gyldig epost");
            editEmail.requestFocus();
            return;
        }
        if (pwd.isEmpty()) {
            editPassword.setError("Vennlist tast inn et passord");
            editPassword.requestFocus();
            return;
        }

        Call<ResponseBody> call = RetrofitClientInstance.getSINGLETON().getAPI().loginUser(email, pwd);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Intent intent = new Intent(LoginActivity.this, TwoFactorActivity.class);
                    intent.putExtra("email", email);
                    intent.putExtra("pwd", pwd);
                    startActivity(intent);
                } else {
                    showLoginFailed(response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();

            }
        });
    }


    private void updateUiWithUser(LoggedInUser loggedInUser) {
        String welcome = getString(R.string.welcome) + loggedInUser.getDisplayName();
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(int responsecode) {
        String errorString = "";
        switch (responsecode) {
            case 400:

                errorString = "Eposten er ikke verifisert. Vennligst verifiser eposten og prøv på nytt.";
                break;

            case 401:
                errorString = "Eposten eller passordet er feil, vennligst prøv på nytt.";
                break;

            default:
                errorString = "Noe gikk galt, vennligst prøv på nytt.";
        }
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    private void isLoggedIn() {
        final SharedUserPrefs sharedUserPrefs = new SharedUserPrefs(this);
        String token = sharedUserPrefs.getToken();
        Call<ResponseBody> call = RetrofitClientInstance.getSINGLETON().getAPI().verifyJwt(token);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        User user = UserParser.parseUser(response.body().string());
                        loginViewModel.login(user.getUserid(), user.getFirstName(), user.getGroups());
                        startActivity(new Intent(LoginActivity.this, AfterLoginSplashActivity.class));
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

