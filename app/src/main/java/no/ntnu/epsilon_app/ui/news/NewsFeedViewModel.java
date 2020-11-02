package no.ntnu.epsilon_app.ui.news;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class NewsFeedViewModel extends ViewModel {

    public static News CURRENT_NEWS;
    public static ArrayList<News> NEWS_LIST = new ArrayList<>();
}