package no.ntnu.epsilon_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import no.ntnu.epsilon_app.api.RetrofitClientInstance;
import no.ntnu.epsilon_app.data.ImageParser;
import no.ntnu.epsilon_app.data.User;
import no.ntnu.epsilon_app.data.UserParser;
import no.ntnu.epsilon_app.data.UserViewModel;
import no.ntnu.epsilon_app.ui.about_us.AboutUsParser;
import no.ntnu.epsilon_app.ui.calendar.Calendar;
import no.ntnu.epsilon_app.ui.calendar.CalendarViewModel;
import no.ntnu.epsilon_app.ui.news.NewsParser;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AfterLoginSplashActivity extends Activity {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 5000;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splashscreen_layout);
        fetchPictures();
        getNewsFeed();
        getAboutUsObjects();
        getCalendarItems();


        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(AfterLoginSplashActivity.this, MainActivity.class);
                AfterLoginSplashActivity.this.startActivity(mainIntent);
                AfterLoginSplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    private void getCalendarItems() {
        Call<List<Calendar>> call = RetrofitClientInstance.getSINGLETON().getAPI().getCalendarItems();
        call.enqueue(new Callback<List<Calendar>>() {
            @Override
            public void onResponse(Call<List<Calendar>> call, Response<List<Calendar>> response) {
                if (response.isSuccessful()) {
                    CalendarViewModel.CALENDAR_LIST = (ArrayList<Calendar>) response.body();
                }
            }

            @Override
            public void onFailure(Call<List<Calendar>> call, Throwable t) {
            }
        });
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

    private void getNewsFeed(){
        Call<ResponseBody> call = RetrofitClientInstance.getSINGLETON().getAPI().getNewsfeed();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        NewsParser.parseNewsFeed(response.body().string());
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

    private void getAboutUsObjects(){
        Call<ResponseBody> call = RetrofitClientInstance.getSINGLETON().getAPI().getAboutUsObjects();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        AboutUsParser.parseAboutUsList(response.body().string());
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