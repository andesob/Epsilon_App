package no.ntnu.epsilon_app;

import android.accounts.AccountAuthenticatorResponse;
import android.accounts.NetworkErrorException;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
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
import androidx.annotation.StringRes;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.button.MaterialButtonToggleGroup;

import java.io.IOException;
import java.sql.SQLOutput;

import javax.sql.DataSource;

import no.ntnu.epsilon_app.api.EpsilonAPI;
import no.ntnu.epsilon_app.api.RetrofitClientInstance;
import no.ntnu.epsilon_app.data.ImageParser;
import no.ntnu.epsilon_app.data.LoggedInUser;
import no.ntnu.epsilon_app.data.LoginDataSource;
import no.ntnu.epsilon_app.data.LoginRepository;
import no.ntnu.epsilon_app.data.LoginViewModel;
import no.ntnu.epsilon_app.data.LoginViewModelFactory;
import no.ntnu.epsilon_app.data.User;
import no.ntnu.epsilon_app.data.UserParser;
import no.ntnu.epsilon_app.data.UserViewModel;
import no.ntnu.epsilon_app.ui.register.RegisterActivity;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String FACEBOOK_ID = "1007001496115565";
    private static final String FACEBOOK_URL = "https://www.facebook.com/EpsilonAalesund";
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

        isLoggedIn();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editTextPassword);
        final Button loginButton = findViewById(R.id.loginButton);
        final ProgressBar progressBar = findViewById(R.id.progressBar);
        final ImageButton imageButton = findViewById(R.id.facebookLink);
        final TextView registerUserText = findViewById(R.id.registerUserText);

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
                startActivity(newFaceBookIntent(getPackageManager()));
            }

        });

        registerUserText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });


        loginViewModel.getLoginResult().observe(this, new Observer<LoggedInUser>() {
            @Override
            public void onChanged(@Nullable LoggedInUser loggedInUser) {
                if (loggedInUser == null) {
                    showLoginFailed();
                } else {
                    updateUiWithUser(loggedInUser);
                }

                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                finish();
            }
        });

    }

    private void loginUser() {

        final String email = editEmail.getText().toString().trim();
        final String pwd = editPassword.getText().toString().trim();

        final SharedUserPrefs sharedUserPrefs = new SharedUserPrefs(this);

        if (email.isEmpty()) {
            editEmail.setError("Vennligst tast inn en epost unge mann");
            editPassword.requestFocus();

            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail.setError("Vennligst tast inn en gyldig epost unge mann");
            return;
        }
        if (pwd.isEmpty()) {
            editPassword.setError("Please fill in a password");
            editPassword.requestFocus();
            return;
        }
        Call<ResponseBody> call = RetrofitClientInstance.getSINGLETON().getAPI().loginUser(email, pwd);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String token = response.headers().get("Authorization");
                        User user = UserParser.parseUser(response.body().string());
                        loginViewModel.login(user.getUserid(), user.getFirstName());

                        sharedUserPrefs.setToken(token);
                        startActivity(new Intent(LoginActivity.this, AfterLoginSplashActivity.class));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    showLoginFailed();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void updateUiWithUser(LoggedInUser loggedInUser) {
        String welcome = getString(R.string.welcome) + loggedInUser.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed() {
        String errorString = "Email or password is incorrect, please try again.";
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
                        loginViewModel.login(user.getUserid(), user.getFirstName());
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

    private static Intent newFaceBookIntent(PackageManager pm) {
        try {
            ApplicationInfo applicationInfo = pm.getApplicationInfo("com.facebook.katana", 0);
            if (applicationInfo.enabled) {
                return new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/" + FACEBOOK_ID));
            }
        } catch (PackageManager.NameNotFoundException e) {

        }
        return new Intent(Intent.ACTION_VIEW, Uri.parse(FACEBOOK_URL));
    }
}

