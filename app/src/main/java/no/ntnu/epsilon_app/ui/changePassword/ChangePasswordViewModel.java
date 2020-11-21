package no.ntnu.epsilon_app.ui.changePassword;

import android.app.Application;
import android.text.TextUtils;
import android.util.Patterns;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import no.ntnu.epsilon_app.data.Result;

public class ChangePasswordViewModel extends ViewModel {

    private ChangePasswordRepository repository;

     public ChangePasswordViewModel(){
         this.repository = new ChangePasswordRepository();
     }
     public MutableLiveData<Result> getChangePasswordData(String oldPassword, String newPassword) {
         return repository.getChangePasswordData(oldPassword, newPassword);
     }

    public boolean isOldPwdValid(String oldPassword) {
        if (oldPassword.trim().length() < 5){
            return false;
        }
        else {
            return true;
        }
    }

    public boolean isNewPwdValid(String newPassword){
        if (newPassword == null || newPassword == null) {
            return false;
        }
        else {
            return newPassword != null && newPassword.trim().length() > 5;
        }
    }

    public boolean isPasswordEqual(String password1, String password2) {
        return password1.equals(password2);
    }

}
