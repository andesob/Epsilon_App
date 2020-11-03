package no.ntnu.epsilon_app.ui.faq;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import no.ntnu.epsilon_app.R;
import no.ntnu.epsilon_app.api.RetrofitClientInstance;
import no.ntnu.epsilon_app.ui.faq.dummy.TestData;
import no.ntnu.epsilon_app.ui.news.NewsParser;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A fragment representing a list of Items.
 */
public class FaqFragment extends Fragment {

    private List<String> faqQuestions;
    private HashMap<String, List<String>> listDetails;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FaqFragment() {
        listDetails = new HashMap<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_faq_item_list, container, false);


        Call<List<Faq>> call = RetrofitClientInstance.getSINGLETON().getAPI().getFaqs();
        call.enqueue(new Callback<List<Faq>>() {
            @Override
            public void onResponse(Call<List<Faq>> call, Response<List<Faq>> response) {
                if (response.isSuccessful()) {
                    List<String> questions = parseApiCall(response.body());
                        if(listDetails.size() != 0) {
                            faqQuestions = new ArrayList<String>(listDetails.keySet());
                        }
                    }
                            setViewAdapter(faqQuestions, listDetails);
                }

            @Override
            public void onFailure(Call<List<Faq>> call, Throwable t) {

            }
        });

        return root;
    }

    private List<String> parseApiCall(List<Faq> ApiResponse) {

            List<String> answerList = new ArrayList<>();
        for (int i = 0; i < ApiResponse.size(); i++) {
            String question = ApiResponse.get(i).getQuestion();
            String answer = ApiResponse.get(i).getAnswer();
            System.out.println(question);
            System.out.println(answer);
            answerList.add(answer);
            listDetails.put(question, answerList);
        }
        return  answerList;
    }


    private void setViewAdapter(List<String> questions,HashMap<String, List<String>> listDetails ){

        ExpandableListView expandableListView = (ExpandableListView) getActivity().findViewById(R.id.expandableListView);
        System.out.println(listDetails.get(0));
        ExpandableListAdapter expandableListAdapter = new FaqExpandableViewAdapter(getContext(), faqQuestions, listDetails);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getContext(), " List Expanded.", Toast.LENGTH_SHORT).show();
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getContext(), " List Collapsed.", Toast.LENGTH_SHORT).show();

            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(getContext(),"Child clicked", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }
}