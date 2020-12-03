package no.ntnu.epsilon_app.ui.register;

import android.text.TextUtils;

import androidx.lifecycle.ViewModel;

/**
 * The viewModel for the register activity.
 */
public class RegisterViewModel extends ViewModel {

    /**
     * Checks if the email is valid.
     *
     * @param email - the email
     * @return
     */
    public boolean isEmailNameValid(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();

    }

    /**
     * Checks if the username is valid.
     *
     * @param username - the username
     * @return
     */
    public boolean isNameValid(String username) {
        if (username == null) {
            return false;
        } else {
            return !username.trim().isEmpty();
        }
    }

    /**
     * Checks if the password is valid
     *
     * @param password - the password
     * @return
     */
    public boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }


}
