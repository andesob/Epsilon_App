package no.ntnu.epsilon_app.ui.faq;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import retrofit2.Response;

public class FaqViewModel extends AndroidViewModel {

    private FaqRepository repository;

    public FaqViewModel(Application application) {
        super(application);
        this.repository = new FaqRepository(application);
    }

    public MutableLiveData<List<Faq>> getFaqList() {
        return repository.getFaqs();
    }

    public MutableLiveData<Response> addFaq(String question, String answer) {
        return repository.addFaq(question, answer);
    }

    public MutableLiveData<Response> editFaq(String question, String answer, long id) {
        return repository.editFaq(question, answer, id);
    }

    public MutableLiveData<Response> deleteFaq(long id) {
        return repository.deleteFaq(id);
    }

    public MutableLiveData<Response> getAddFaqLiveData() {
        return repository.getAddFaqLiveData();
    }

    public MutableLiveData<Response> getEditFaqLiveData() {
        return repository.getEditFaqLiveData();
    }

    public MutableLiveData<Response> getDeleteFaqLiveData() {
        return repository.getDeleteFaqLiveData();
    }

}
