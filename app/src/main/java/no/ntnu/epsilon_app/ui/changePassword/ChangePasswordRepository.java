package no.ntnu.epsilon_app.ui.changePassword;

import androidx.lifecycle.MutableLiveData;

import no.ntnu.epsilon_app.api.RetrofitClientInstance;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * The repository for the change password repository
 */

public class ChangePasswordRepository {


    private MutableLiveData<Response> changePasswordData = new MutableLiveData<>();

    public ChangePasswordRepository() {
    }

    /**
     * Sends an API call to the server and tries to change the password. When an reposne is returned, the value
     * of the mutable live data object changes and notifies the onChange observers.
     *
     * @param oldPassword
     * @param newPassword
     * @return
     */
    public MutableLiveData<Response> getChangePasswordData(String oldPassword, String newPassword) {
        Call<ResponseBody> call = RetrofitClientInstance.getSINGLETON().getAPI().changePassword(oldPassword, newPassword);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    changePasswordData.setValue(response);
                } else {
                    changePasswordData.setValue(response);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                changePasswordData.setValue(null);
            }
        });
        return changePasswordData;
    }

    /**
     * Returns the mutable live data without sending the API call.
     *
     * @return - the mutable live data
     */
    public MutableLiveData<Response> getChangePasswordData() {
        return changePasswordData;
    }
}
