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
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import no.ntnu.epsilon_app.R;
import no.ntnu.epsilon_app.ui.faq.dummy.TestData;

public class NewsFeedFragment extends Fragment implements RecyclerViewAdapter.ItemClickListener{

    private NewsFeedViewModel mViewModel;
    private List<String> listTitle;
    private HashMap<String, List<String>> listDetails;
    private ArrayList<News> newsList;
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

        testData testData = new testData();
        newsList = testData.getNewsList();

        recyclerView = root.findViewById(R.id.rvItems);
        LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(layoutManager);

        recyclerViewAdapter = new RecyclerViewAdapter(root.getContext(), newsList);
        recyclerViewAdapter.setClickListener(this);
        recyclerView.setAdapter(recyclerViewAdapter);

        /*ExpandableListView expandableListView = (ExpandableListView) root.findViewById(R.id.NewsFeedExpandableListView);
        //listDetails = TestData.getData();
        listTitle = new ArrayList<String>(listDetails.keySet());

        ExpandableListAdapter expandableListAdapter = new NewsFeedExpandableViewAdapter(root.getContext(), listTitle, listDetails);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(root.getContext(), " List Expanded.", Toast.LENGTH_SHORT).show();
                //Toast.makeText(getContext(), news.toString(), Toast.LENGTH_SHORT).show();
                Navigation.findNavController(root).navigate(R.id.nav_news);
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getContext(), " List Collapsed.", Toast.LENGTH_SHORT).show();

            }
        });

        //final Date date = new Date();
        //final DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG , Locale.GERMANY);
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(root.getContext(), "Child clicked", Toast.LENGTH_SHORT).show();
                //String currentDateTimeString = java.text.DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, Locale.GERMANY).format(new Date());
                //System.out.println("DATE: " + dateFormat.format(date));
                //Navigation.findNavController(root).navigate(R.id.);
                return false;
            }
        });*/
        return root;
    }


    @Override
    public void onItemClick(View view, int position) {
        News news = recyclerViewAdapter.getItem(position);
        NewsFeedViewModel viewModel = new NewsFeedViewModel();
        viewModel.setCurrentNews(news);
        Navigation.findNavController(root).navigate(R.id.nav_news);
    }
}