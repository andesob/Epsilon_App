package no.ntnu.epsilon_app.ui.news;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Collections;

public class NewsFeedViewModel extends ViewModel {

    public static News CURRENT_NEWS;
    public static ArrayList<News> NEWS_LIST = new ArrayList<>();

    public static void sortList(){
        Collections.sort(NEWS_LIST, Collections.<News>reverseOrder());
    }
}