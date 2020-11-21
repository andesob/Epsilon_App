package no.ntnu.epsilon_app.ui.changePassword;

import androidx.lifecycle.MutableLiveData;
import no.ntnu.epsilon_app.api.RetrofitClientInstance;
import no.ntnu.epsilon_app.data.Result;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordRepository {

    private MutableLiveData<Result> changePasswordData = new MutableLiveData<>();

    public ChangePasswordRepository( ){
    }

    public MutableLiveData<Result> getChangePasswordData(String oldPassword, String newPassword) {
        Call<ResponseBody> call = RetrofitClientInstance.getSINGLETON().getAPI().changePassword(oldPassword, newPassword );
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    changePasswordData.setValue(new Result.Success(response));
                } else {
                    changePasswordData.setValue(new Result.Error(response.body()));
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Result result = new Result.Error("OnFailure called");
                changePasswordData.setValue(result);
            }
        });
        return changePasswordData;
    }

    public MutableLiveData<Result> getChangePasswordData() {
        return changePasswordData;
    }
}
