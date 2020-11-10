package no.ntnu.epsilon_app;

import android.accounts.AccountAuthenticatorResponse;
import android.accounts.NetworkErrorException;
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
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButtonToggleGroup;

import java.io.IOException;
import java.sql.SQLOutput;

import no.ntnu.epsilon_app.api.EpsilonAPI;
import no.ntnu.epsilon_app.api.RetrofitClientInstance;
import no.ntnu.epsilon_app.data.ImageParser;
import no.ntnu.epsilon_app.ui.register.RegisterActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String FACEBOOK_ID = "1007001496115565";
    private static final String FACEBOOK_URL="https://www.facebook.com/EpsilonAalesund";

    private EditText editEmail;
    private EditText editPassword;
    private Button loginButton;
    private Context context = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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

                new CountDownTimer(1500,1000){

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

    }

    private void loginUser() {

        String email = editEmail.getText().toString().trim();
        String pwd = editPassword.getText().toString().trim();

        final SharedUserPrefs sharedUserPrefs = new SharedUserPrefs(this);

        if(email.isEmpty()){
            editEmail.setError("Vennligst tast inn en epost unge mann");
            editPassword.requestFocus();

            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editEmail.setError("Vennligst tast inn en gyldig epost unge mann");
            return;
        }
        if (pwd.isEmpty()) {
            editPassword.setError("Please fill in a password");
            editPassword.requestFocus();
            return;
        }
        Call<ResponseBody> call = RetrofitClientInstance.getSINGLETON().getAPI().loginUser(email,pwd);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try{
                        Toast.makeText(context,"Login Success",Toast.LENGTH_SHORT).show();
                        sharedUserPrefs.setToken(response.body().string());
                        System.out.println("YOUR HERE");
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));

                    }
                    catch (IOException e){}
                }
                else{Toast.makeText(context,"Login Failed",Toast.LENGTH_SHORT).show();}
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });



    }

    private void isLoggedIn(){
        final SharedUserPrefs sharedUserPrefs = new SharedUserPrefs(this);
        String token = "Bearer " + sharedUserPrefs.getToken();
        Call<ResponseBody> call = RetrofitClientInstance.getSINGLETON().getAPI().verifyJwt(token);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    System.out.println("LOGIN SUCK");
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));

                }
                else{
                    System.out.println("LOGIN FAIL");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private static Intent newFaceBookIntent(PackageManager pm){
        try{
            ApplicationInfo applicationInfo = pm.getApplicationInfo("com.facebook.katana",0);
            if(applicationInfo.enabled){
                return new Intent(Intent.ACTION_VIEW,Uri.parse("fb://page/"+FACEBOOK_ID));
            }
        }catch (PackageManager.NameNotFoundException e){

        }
        return new Intent(Intent.ACTION_VIEW,Uri.parse(FACEBOOK_URL));
    }
}

