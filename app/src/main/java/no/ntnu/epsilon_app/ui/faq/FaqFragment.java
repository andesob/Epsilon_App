package no.ntnu.epsilon_app.ui.faq;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import no.ntnu.epsilon_app.R;
import no.ntnu.epsilon_app.tools.BottomSheetDialogAddFaq;
import no.ntnu.epsilon_app.tools.BottomSheetDialogEditFaq;
import retrofit2.Response;


/**
 * A fragment representing a list of Items.
 */
public class FaqFragment extends Fragment implements FaqExpandableViewAdapter.FaqExpandableViewClickListener {

    private FaqViewModel faqViewModel;
    private  FaqExpandableViewAdapter faqAdapter;
    private AlertDialog.Builder builder;
    private boolean clicked = false;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FaqFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        final View root = inflater.inflate(R.layout.fragment_faq_item_list, container, false);
        ImageView fab = root.findViewById(R.id.faq_fab);
        faqViewModel =  new ViewModelProvider(requireActivity()).get(FaqViewModel.class);
        ImageView addBtn = root.findViewById(R.id.addFaq);
        builder = new AlertDialog.Builder(getContext(), R.style.LightDialogTheme);

        getFaq();

        boolean admin = true;
        if(admin){
           fab.setVisibility(View.VISIBLE);
           addBtn.setVisibility(View.VISIBLE);
        }

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialogAddFaq bottomSheet = new BottomSheetDialogAddFaq();
                bottomSheet.show(requireActivity().getSupportFragmentManager(),"ModalBottomSheet");

            }
        });

       fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicked = true;
                getFaq();
            }
        });

        return root;
    }

    public void getFaq(){

        faqViewModel.getFaqList().observe(getViewLifecycleOwner(), new Observer<List<Faq>>() {
            @Override
            public void onChanged(@NonNull List<Faq> faqs) {
               HashMap<String, List<String>> listDetails = parseApiCall(faqs);
                    if (listDetails.size() != 0) {
                        ArrayList<String> faqQuestions = new ArrayList<>(listDetails.keySet());
                        setViewAdapter(faqQuestions, listDetails, faqs);
                    }
                }
        });
    }

    private HashMap<String, List<String>> parseApiCall(List<Faq> ApiResponse) {
         HashMap<String, List<String>> hashMap = new HashMap<>();
        for (int i = 0; i < ApiResponse.size(); i++) {
            List<String> answerList = new ArrayList<>();
            String question = ApiResponse.get(i).getQuestion();
            String answer = ApiResponse.get(i).getAnswer();
            answerList.add(answer);
            hashMap.put(question, answerList);
        }
        return hashMap;
    }

    private void createAlertBox(final long faqId) {
        //Setting message manually and performing action on button click
        builder.setMessage("Vil du slette dette ofte stilte spørsmålet?")
                .setCancelable(true)
                .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        faqViewModel.deleteFaq(faqId).observe(getViewLifecycleOwner(), new Observer<Response>() {
                            @Override
                            public void onChanged(@NonNull Response response) {
                                getFaq();
                            }
                        });
                    }
                })
                .setNegativeButton("Nei", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
                alert.setTitle("Slett et ofte stilt spørsmål");
                alert.show();
    }

    private void setViewAdapter(List<String> questions,HashMap<String, List<String>> listDetails, List<Faq> faqList ) {

        ExpandableListView expandableListView = (ExpandableListView) getActivity().findViewById(R.id.expandableListView);
        if (clicked) {
            expandableListView.setGroupIndicator(null);
        }
        faqAdapter = new FaqExpandableViewAdapter(getContext(), questions, listDetails, clicked, faqList);
        expandableListView.setAdapter(faqAdapter);
        faqAdapter.setTrashClickListener(this);
        faqAdapter.setEditClickListener(this);

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
            }
        });
        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {

            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                return false;
            }
        });

    }

    @Override
    public void onTrashClick(View view, int position, long faqId) {
        createAlertBox(faqId);
    }

    @Override
    public void onEditClick(View view, int position, Faq faq) {
        BottomSheetDialogEditFaq bottomSheet = new BottomSheetDialogEditFaq(faq);
        bottomSheet.show(getActivity().getSupportFragmentManager(),"ModalBottomSheet");
    }

}