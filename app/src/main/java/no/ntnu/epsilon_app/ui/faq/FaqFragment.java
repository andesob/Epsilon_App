package no.ntnu.epsilon_app.ui.faq;

import android.database.DataSetObserver;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import no.ntnu.epsilon_app.R;
import no.ntnu.epsilon_app.api.RetrofitClientInstance;
import no.ntnu.epsilon_app.tools.BottomSheetDialogAddFaq;
import no.ntnu.epsilon_app.tools.BottomSheetDialogEditFaq;
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
    private List<Faq> faqList;
    private ImageView fab;
    private boolean admin = true;
    private boolean clicked = false;



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
        fab = root.findViewById(R.id.faq_fab);
        ImageView addBtn = root.findViewById(R.id.addFaq);
        getFaqs();

        if(admin){
           fab.setVisibility(View.VISIBLE);
           addBtn.setVisibility(View.VISIBLE);
        }

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialogAddFaq bottomSheet = new BottomSheetDialogAddFaq();
                bottomSheet.show(getActivity().getSupportFragmentManager(),"ModalBottomSheet");

            }
        });

       fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicked = true;
                getFaqs();
            }
        });

        return root;
    }

    private void parseApiCall(List<Faq> ApiResponse) {

        for (int i = 0; i < ApiResponse.size(); i++) {
            List<String> answerList = new ArrayList<>();
            String question = ApiResponse.get(i).getQuestion();
            String answer = ApiResponse.get(i).getAnswer();
            System.out.println("id:" + ApiResponse.get(i).getId());
            //System.out.println(answer);
            answerList.add(answer);
            listDetails.put(question, answerList);
        }
    }

    private void getFaqs() {
        Call<List<Faq>> call = RetrofitClientInstance.getSINGLETON().getAPI().getFaqs();
        call.enqueue(new Callback<List<Faq>>() {
            @Override
            public void onResponse(Call<List<Faq>> call, Response<List<Faq>> response) {
                if (response.isSuccessful()) {
                   faqList = response.body();
                    parseApiCall(faqList);
                    if(listDetails.size() != 0) {
                        faqQuestions = new ArrayList<String>(listDetails.keySet());
                    }
                setViewAdapter(faqQuestions, listDetails, faqList);
                }
            }

            @Override
            public void onFailure(Call<List<Faq>> call, Throwable t) {

            }
        });

    }
/*
    public static void openModalBottomSheet() {
        BottomSheetDialog bottomSheet = new BottomSheetDialog();
        bottomSheet.show(getActivity().getSupportFragmentManager(),"ModalBottomSheet");
    }
 */
    private void setViewAdapter(List<String> questions,HashMap<String, List<String>> listDetails, List<Faq> faqList ) {

        ExpandableListView expandableListView = (ExpandableListView) getActivity().findViewById(R.id.expandableListView);
        FaqViewModel.CURRENT_ADAPTER = new FaqExpandableViewAdapter(getContext(), faqQuestions, listDetails, clicked, faqList);
        expandableListView.setAdapter(FaqViewModel.CURRENT_ADAPTER);


        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                //Toast.makeText(getContext(), " List Expanded.", Toast.LENGTH_SHORT).show();
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                //Toast.makeText(getContext(), " List Collapsed.", Toast.LENGTH_SHORT).show();

            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                //Toast.makeText(getContext(), "Child clicked", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

    }
}