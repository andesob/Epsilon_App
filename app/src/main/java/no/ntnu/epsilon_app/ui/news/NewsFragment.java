package no.ntnu.epsilon_app.ui.news;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import no.ntnu.epsilon_app.R;

public class NewsFragment extends Fragment {


    public NewsFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_news, container, false);
        NewsFeedViewModel viewModel = new NewsFeedViewModel();
        News news = viewModel.getCurrentNews();
        TextView newsTitle = root.findViewById(R.id.newsTitleTextView);
        TextView newsContent = root.findViewById(R.id.newsTextView);
        TextView newsBottomText = root.findViewById(R.id.newsBottomTextView);

        newsTitle.setText(news.getTitle());
        newsContent.setText(news.getContents());
        String bottomText = "Written: " + news.getTimeWrittenAsString() +
                "\nLast updated: " + news.getLastUpdatedAsString();
        newsBottomText.setText(bottomText);

        return root;
    }
}