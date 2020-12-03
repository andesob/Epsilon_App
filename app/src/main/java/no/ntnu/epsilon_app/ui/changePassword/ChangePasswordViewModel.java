package no.ntnu.epsilon_app.ui.changePassword;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import retrofit2.Response;


/**
 * The viewModel for the change password fragment.
 */
public class ChangePasswordViewModel extends ViewModel {

    private ChangePasswordRepository repository;

    public ChangePasswordViewModel() {
        this.repository = new ChangePasswordRepository();
    }

    /**
     * Sends and API call with the new and old password.
     *
     * @param oldPassword - the old password
     * @param newPassword - the new password
     * @return - The mutable live data object
     */
    public MutableLiveData<Response> getChangePasswordData(String oldPassword, String newPassword) {
        return repository.getChangePasswordData(oldPassword, newPassword);
    }

    /**
     * Returns
     *
     * @return - the mutable live data object without sending an API call.
     */
    public MutableLiveData<Response> getChangePasswordData() {
        return repository.getChangePasswordData();
    }

    /**
     * Checks if the old password is valid
     *
     * @param oldPassword - the old password
     * @return
     */
    public boolean isOldPwdValid(String oldPassword) {
        if (oldPassword.trim().length() < 5) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Check if the new password is valid.
     *
     * @param newPassword - the new password
     * @return
     */
    public boolean isNewPwdValid(String newPassword) {
        if (newPassword == null) {
            return false;
        } else {
            return newPassword != null && newPassword.trim().length() > 5;
        }
    }

    /**
     * check if the two password is equal.
     *
     * @param password1 - the first new password
     * @param password2 - the second new password
     * @return
     */
    public boolean isPasswordEqual(String password1, String password2) {
        return password1.equals(password2);
    }
}
