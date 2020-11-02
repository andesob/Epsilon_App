package no.ntnu.epsilon_app.ui.news;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import no.ntnu.epsilon_app.R;
import no.ntnu.epsilon_app.api.RetrofitClientInstance;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsFeedFragment extends Fragment implements RecyclerViewAdapter.ItemClickListener {

    private NewsFeedViewModel mViewModel;
    private ArrayList<News> newsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private View root;

    public NewsFeedFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(NewsFeedViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.news_feed_fragment, container, false);

        //mViewModel = ViewModelProviders.of(this).get(NewsFeedViewModel.class);

        testData testData = new testData();
        //newsList = testData.getNewsList();
        newsList = NewsFeedViewModel.NEWS_LIST;


        Call<ResponseBody> call = RetrofitClientInstance.getSINGLETON().getAPI().getNewsfeed();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        parseNews(response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

        recyclerView = root.findViewById(R.id.rvItems);
        LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(layoutManager);

        recyclerViewAdapter = new RecyclerViewAdapter(root.getContext(), newsList);
        recyclerViewAdapter.setClickListener(this);
        recyclerView.setAdapter(recyclerViewAdapter);
        return root;
    }


    @Override
    public void onItemClick(View view, int position) {
        News news = recyclerViewAdapter.getItem(position);
        NewsFeedViewModel.CURRENT_NEWS = news;
        Navigation.findNavController(root).navigate(R.id.nav_news);
    }


    private void parseNews(String jsonLine) {
        JsonElement jsonElement = new JsonParser().parse(jsonLine);
        JsonArray jsonArray = jsonElement.getAsJsonArray();

        for (JsonElement element : jsonArray) {
            JsonObject object = element.getAsJsonObject();

            String timeWrittenString = object.get("timeWritten").getAsString();
            String lastUpdatedString = object.get("lastUpdated").getAsString();
            String title = object.get("title").getAsString();
            String content = object.get("newsContent").getAsString();
            long newsId = object.get("newsfeedObjectId").getAsLong();

            LocalDateTime timeWritten = LocalDateTime.parse(timeWrittenString);
            LocalDateTime lastUpdated = LocalDateTime.parse(lastUpdatedString);

            boolean exists = false;
            for (News news : newsList) {
                if (news.getId() == newsId && news.getLastUpdated().toString().equals(lastUpdated.toString())) {
                    exists = true;
                    break;
                }
            }

            if (!exists) {
                News news = new News(newsId, title, content, timeWritten, lastUpdated);
                newsList.add(news);
            }
        }

        NewsFeedViewModel.NEWS_LIST = newsList;
    }
}