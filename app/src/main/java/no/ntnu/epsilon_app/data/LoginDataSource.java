package no.ntnu.epsilon_app.data;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import no.ntnu.epsilon_app.AfterLoginSplashActivity;
import no.ntnu.epsilon_app.LoginActivity;
import no.ntnu.epsilon_app.SharedUserPrefs;
import no.ntnu.epsilon_app.api.RetrofitClientInstance;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public LoggedInUser login(long userid, String displayname) {
        try {
            LoggedInUser loggedInUser = new LoggedInUser(userid, displayname);
            return loggedInUser;
        } catch (Exception e) {
            return null;
        }
    }

    public void logout() {

    }
}