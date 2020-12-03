package no.ntnu.epsilon_app.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import no.ntnu.epsilon_app.LoginActivity;
import no.ntnu.epsilon_app.R;
import no.ntnu.epsilon_app.api.RetrofitClientInstance;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * The register a new user activity
 */
public class RegisterActivity extends AppCompatActivity {

    public static boolean isLoggedIn = false;
    private RegisterViewModel registerViewModel = new RegisterViewModel();

    private EditText registerTextFirstName;
    private EditText registerTextLastName;
    private EditText registerTextEmail;
    private EditText registerTextPassword;
    private ProgressBar progressBar;
    private Button registerButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setInitialViews();
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        progressBar.setVisibility(View.GONE);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                registerButton.setVisibility(View.INVISIBLE);

                new CountDownTimer(1500, 1000) {
                    @Override
                    public void onTick(long l) {
                    }

                    @Override
                    public void onFinish() {

                        String firstName = registerTextFirstName.getText().toString();
                        String lastName = registerTextLastName.getText().toString();
                        String email = registerTextEmail.getText().toString();
                        String password = registerTextPassword.getText().toString();

                        if (validateInput(firstName, lastName, email, password)) {
                            registerNewUser(firstName, lastName, password, email);
                        } else {
                            if (progressBar != null) {
                                progressBar.setVisibility(View.INVISIBLE);
                                registerButton.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }.start();
            }
        });
    }

    /**
     * Finds and sets the view for the activity.
     */
    private void setInitialViews() {
        registerTextFirstName = findViewById(R.id.registerTextFirstName);
        registerTextLastName = findViewById(R.id.registerTextLastName);
        registerTextEmail = findViewById(R.id.registerTextEmail);
        registerTextPassword = findViewById(R.id.registerTextPassword);
        progressBar = findViewById(R.id.progressBar);
        registerButton = findViewById(R.id.registerButton);
    }

    /**
     * Makes an api for creating a new user and handles the resposne from the server.
     *
     * @param firstName - the first name.
     * @param lastName  - the last name.
     * @param password  - the password.
     * @param email     - the email.
     */
    private void registerNewUser(String firstName, String lastName, String password, String email) {
        Call<ResponseBody> call = RetrofitClientInstance.getSINGLETON().getAPI().createUser(firstName, lastName, password, email);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (progressBar != null) {
                    progressBar.setVisibility(View.INVISIBLE);
                    registerButton.setVisibility(View.VISIBLE);
                }
                if (response.isSuccessful()) {
                    Toast.makeText(getBaseContext(), R.string.added, Toast.LENGTH_SHORT).show();

                    isLoggedIn = true;
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                } else {
                    Toast.makeText(getBaseContext(), R.string.error_cant_add, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getBaseContext(), R.string.error_cant_add, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Checks if the input is valid
     *
     * @param firstName - the first name
     * @param lastName  - the last name
     * @param email     - the email
     * @param password  - the password
     * @return
     */
    private boolean validateInput(String firstName, String lastName, String email, String password) {

        if (!registerViewModel.isNameValid(firstName)) {
            registerTextFirstName.requestFocus();
            registerTextFirstName.setError(getText(R.string.please_provide_valid_first_name));
            return false;
        } else if (!registerViewModel.isNameValid(lastName)) {
            registerTextLastName.requestFocus();
            registerTextLastName.setError(getText(R.string.please_provide_valid_last_name));
            return false;
        } else if (!registerViewModel.isEmailNameValid(email)) {
            registerTextEmail.requestFocus();
            registerTextEmail.setError(getText(R.string.please_provide_valid_email));
            return false;
        } else if (!registerViewModel.isPasswordValid(password)) {
            registerTextPassword.requestFocus();
            registerTextPassword.setError(getText(R.string.password_must_be_longer_than_5_characters));
            return false;
        }
        return true;
    }

}

