package no.ntnu.epsilon_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import java.io.IOException;

import no.ntnu.epsilon_app.api.RetrofitClientInstance;
import no.ntnu.epsilon_app.data.ImageParser;
import no.ntnu.epsilon_app.data.LoginViewModel;
import no.ntnu.epsilon_app.data.LoginViewModelFactory;
import no.ntnu.epsilon_app.data.User;
import no.ntnu.epsilon_app.data.UserParser;
import no.ntnu.epsilon_app.ui.about_us.AboutUsParser;
import no.ntnu.epsilon_app.ui.news.NewsParser;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    /**
     * Duration of wait
     **/
    private final int SPLASH_DISPLAY_LENGTH = 2000;
    private LoginViewModel loginViewModel;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splashscreen_layout);
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);
        isLoggedIn();
    }

    private void goToLogin() {
        /*
        New handler to go to the login screen after a few seconds
         */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(SplashActivity.this, LoginActivity.class);
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    private void isLoggedIn() {
        final SharedUserPrefs sharedUserPrefs = new SharedUserPrefs(this);
        final String token = sharedUserPrefs.getToken();

        System.out.println("TOKEN MAYNE: " + token);
        /*
        If user has no bearer token go to login screen
         */
        if (token.isEmpty() || token == null) {
            goToLogin();
        }

        /*
        Verify token, if response is unauthorized ie. the token is invalid go to login screen
         */
        Call<ResponseBody> call = RetrofitClientInstance.getSINGLETON().getAPI().verifyJwt(token);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        User user = UserParser.parseUser(response.body().string());
                        loginViewModel.login(user.getUserid(), user.getFirstName(), user.getGroups());

                        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
                        httpClient.addInterceptor(new Interceptor() {
                            @Override
                            public okhttp3.Response intercept(Chain chain) throws IOException {
                                Request request = chain.request().newBuilder().addHeader("Authorization", token).build();
                                return chain.proceed(request);
                            }
                        });

                        RetrofitClientInstance.addInterceptor(httpClient);

                        startActivity(new Intent(SplashActivity.this, AfterLoginSplashActivity.class));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("==============================================");
                    System.out.println("DU ER HER");
                    useRefreshToken();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
    private void useRefreshToken(){
        final SharedUserPrefs sharedUserPrefs2 = new SharedUserPrefs(this);
        final String refreshToken = sharedUserPrefs2.getRefreshToken();
        System.out.println("=====================================");
        System.out.println(refreshToken);

        if(refreshToken.isEmpty()||refreshToken==null){
            System.out.println("======================================");
            System.out.println("GÅ TIL LOGIN");
            goToLogin();
            return;
        }
        Call<ResponseBody> call = RetrofitClientInstance.getSINGLETON().getAPI().useRefreshToken(refreshToken);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    System.out.println("===============================");
                    System.out.println("REEEEEEEEEEEEEEEEEEEEEEEEEE");
                    final String token = response.headers().get("Authorization");
                    final String refreshToken = response.headers().get("refreshTokenHeader");

                    sharedUserPrefs2.setToken(token);
                    sharedUserPrefs2.setRefreshToken(refreshToken);

                    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
                    httpClient.addInterceptor(new Interceptor() {
                        @Override
                        public okhttp3.Response intercept(Chain chain) throws IOException {
                            Request request = chain.request().newBuilder().addHeader("Authorization", token).build();
                            return chain.proceed(request);
                        }
                    });

                    RetrofitClientInstance.addInterceptor(httpClient);
                    isLoggedIn();


                }
                else{
                    System.out.println("==========================");
                    System.out.println("FREEEEEEEEEEEEEEEEEEESH");
                    goToLogin();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}