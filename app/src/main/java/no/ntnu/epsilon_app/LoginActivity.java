package no.ntnu.epsilon_app;

import android.accounts.AccountAuthenticatorResponse;
import android.accounts.NetworkErrorException;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import no.ntnu.epsilon_app.api.RetrofitClientInstance;
import no.ntnu.epsilon_app.data.ImageParser;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    public static boolean isLoggedIn = false;
    private static final String FACEBOOK_ID = "1007001496115565";
    private static final String FACEBOOK_URL="https://www.facebook.com/EpsilonAalesund";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        final EditText editPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        final EditText editPassword = findViewById(R.id.editTextPassword);
        final Button loginButton = findViewById(R.id.loginButton);
        final ProgressBar progressBar = findViewById(R.id.progressBar);
        final ImageButton imageButton = findViewById(R.id.facebookLink);

        fetchPictures();

        progressBar.setVisibility(View.GONE);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                loginButton.setVisibility(View.INVISIBLE);
                loginUser();
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //newFaceBookIntent(getPackageManager(),"https://www.facebook.com/EpsilonAalesund")
                startActivity(newFaceBookIntent(getPackageManager()));
                //Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("fb://facewebmodal/f?href=EpsilonAalesund"));
                //startActivity(intent);
                }

        });
    }

    private void loginUser() {
        isLoggedIn = true;
        startActivity(new Intent(LoginActivity.this,MainActivity.class));

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

    private void fetchPictures(){
        Call<ResponseBody> call = RetrofitClientInstance.getSINGLETON().getAPI().getUserPictures();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        ImageParser.parseImageList(response.body().string());
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

