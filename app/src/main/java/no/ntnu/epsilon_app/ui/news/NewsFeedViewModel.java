package no.ntnu.epsilon_app.ui.news;

import androidx.lifecycle.ViewModel;

public class NewsFeedViewModel extends ViewModel {

    public static News currentNews;

    public void setCurrentNews(News news){
        this.currentNews = news;
    }

    public News getCurrentNews(){
        return currentNews;
    }
}