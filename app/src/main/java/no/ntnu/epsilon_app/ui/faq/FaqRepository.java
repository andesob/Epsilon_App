package no.ntnu.epsilon_app.ui.faq;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import no.ntnu.epsilon_app.api.RetrofitClientInstance;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FaqRepository {

    private MutableLiveData<List<Faq>> faqListLiveData = new MutableLiveData<>();
    private MutableLiveData<Response> editFaqLiveData = new MutableLiveData<>();
    private MutableLiveData<Response> addFaqLiveData = new MutableLiveData<>();
    private MutableLiveData<Response> deleteFaqLiveData = new MutableLiveData<>();
    private Application application;

    public FaqRepository(Application application ){
        this.application = application;
    }

    public MutableLiveData<List<Faq>> getFaqs() {
        Call<List<Faq>> call = RetrofitClientInstance.getSINGLETON().getAPI().getFaqs();
        call.enqueue(new Callback<List<Faq>>() {
            @Override
            public void onResponse(Call<List<Faq>> call, Response<List<Faq>> response) {
                if (response.isSuccessful()) {
                    List<Faq> faqList = response.body();
                    if(faqList != null ) {
                        faqListLiveData.setValue(faqList);
                    }
                }

            }
            @Override
            public void onFailure(Call<List<Faq>> call, Throwable t) {

            }
        });
        return faqListLiveData;
    }


    //Todo change the returnvalue

    public MutableLiveData<Response> addFaq(String question, String answer) {
        Call<ResponseBody> call = RetrofitClientInstance.getSINGLETON().getAPI().add_faqs(question, answer);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    addFaqLiveData.setValue(response);
                } else {
                    addFaqLiveData.setValue(response);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //Response.error(1,)
            }
        });
        return addFaqLiveData;
    }

    //TODO change the returnvalue

    public MutableLiveData<Response> editFaq(String question, String answer, long id) {
        Call<ResponseBody> call = RetrofitClientInstance.getSINGLETON().getAPI().edit_faq(question, answer, id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    editFaqLiveData.setValue(response);
                }
                else {
                    editFaqLiveData.setValue(response);

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
        return editFaqLiveData;
    }

    //TODO change the returnvalue

    public MutableLiveData<Response> deleteFaq(long id) {
        Call<ResponseBody> call = RetrofitClientInstance.getSINGLETON().getAPI().delete_faq(id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    deleteFaqLiveData.setValue(response);
                }
                else {
                    deleteFaqLiveData.setValue(response);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
        return deleteFaqLiveData;
    }
}
