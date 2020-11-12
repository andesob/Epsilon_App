package no.ntnu.epsilon_app.ui.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;

import no.ntnu.epsilon_app.R;
import no.ntnu.epsilon_app.api.RetrofitClientInstance;
import no.ntnu.epsilon_app.data.LoginDataSource;
import no.ntnu.epsilon_app.data.LoginRepository;
import no.ntnu.epsilon_app.data.User;
import no.ntnu.epsilon_app.data.UserParser;
import no.ntnu.epsilon_app.data.UserViewModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsFeedFragment extends Fragment implements RecyclerViewAdapter.ItemClickListener {

    private RecyclerViewAdapter recyclerViewAdapter;
    private View root;

    public NewsFeedFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.news_feed_fragment, container, false);

        getNewsfeed();
        getUsers();

        LoginRepository loginRepository = LoginRepository.getInstance(new LoginDataSource());

        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToPostNewsFragment();
            }
        });

        RecyclerView recyclerView = root.findViewById(R.id.rvItems);
        LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapter = new RecyclerViewAdapter(root.getContext(), NewsFeedViewModel.NEWS_LIST);
        recyclerViewAdapter.setClickListener(this);
        recyclerView.setAdapter(recyclerViewAdapter);

        return root;
    }

    private void getNewsfeed() {
        Call<ResponseBody> call = RetrofitClientInstance.getSINGLETON().getAPI().getNewsfeed();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        NewsParser.parseNewsFeed(response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }


    @Override
    public void onItemClick(View view, int position) {
        NewsFeedViewModel.CURRENT_NEWS = recyclerViewAdapter.getItem(position);
        Navigation.findNavController(root).navigate(R.id.nav_news);
    }

    private void goToPostNewsFragment() {
        Navigation.findNavController(root).navigate(R.id.nav_post_news);
    }

    private void getUsers(){
        Call<ResponseBody> call = RetrofitClientInstance.getSINGLETON().getAPI().getUsers();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        UserParser.parseUserList(response.body().string());
                        for (User user : UserViewModel.USER_LIST){
                            System.out.println("USER: " + user.getFirstName());
                            for (String s : user.getGroups()){
                                System.out.println("GROUP: " + s);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}