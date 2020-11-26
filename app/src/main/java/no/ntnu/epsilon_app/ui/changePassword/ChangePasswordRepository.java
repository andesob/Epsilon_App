package no.ntnu.epsilon_app.ui.changePassword;

import androidx.lifecycle.MutableLiveData;
import no.ntnu.epsilon_app.api.RetrofitClientInstance;
import no.ntnu.epsilon_app.data.Result;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordRepository {

    private MutableLiveData<Response> changePasswordData = new MutableLiveData<>();

    public ChangePasswordRepository( ){
    }

    public MutableLiveData<Response> getChangePasswordData(String oldPassword, String newPassword) {
        Call<ResponseBody> call = RetrofitClientInstance.getSINGLETON().getAPI().changePassword(oldPassword, newPassword );
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

    public MutableLiveData<Response> getChangePasswordData() {
        return changePasswordData;
    }
}
