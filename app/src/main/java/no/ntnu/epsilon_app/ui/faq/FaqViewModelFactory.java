package no.ntnu.epsilon_app.ui.faq;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class FaqViewModelFactory implements ViewModelProvider.Factory {

    @NonNull private Application application;

    public FaqViewModelFactory(Application application){
        this.application = application;
    }


    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == FaqViewModel.class) {
            return (T) new FaqViewModel(application);
        }
        return null;
    }
}
