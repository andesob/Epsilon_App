package no.ntnu.epsilon_app.ui.register;

import android.text.TextUtils;
import android.util.Patterns;

import androidx.lifecycle.ViewModel;

public class RegisterViewModel extends ViewModel {

    public boolean isEmailNameValid(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();

    }

    public boolean isNameValid(String username){
        if (username == null) {
            return false;
        }
        else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    public boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }


}
