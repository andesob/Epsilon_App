package no.ntnu.epsilon_app.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class LoginViewModel extends ViewModel {
    //private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoggedInUser> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    /*LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }*/

    public LiveData<LoggedInUser> getLoginResult() {
        return loginResult;
    }

    public void login(long userid, String displayname, List<String> groups) {
        // can be launched in a separate asynchronous job
        LoggedInUser loggedInUser = loginRepository.login(userid, displayname, groups);

        loginResult.setValue(loggedInUser);
    }
}
