package no.ntnu.epsilon_app.ui.calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import no.ntnu.epsilon_app.R;
import no.ntnu.epsilon_app.ui.faq.dummy.TestData;

public class CalendarFragment extends Fragment {

    private List<String> listTitle;
    private HashMap<String, List<String>> listDetails;

    public CalendarFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calendar,container,false);

        ExpandableListView expandableListView = (ExpandableListView) view.findViewById(R.id.expandableListView);

        listDetails = TestData.getData();
        listTitle = new ArrayList<>(listDetails.keySet());

        FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.calendarEdditButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalendarAddFragment calendarAddFragment = new CalendarAddFragment();
                calendarAddFragment.show(getActivity().getSupportFragmentManager(),"ModalBottomSheet");
            }
        });


        ExpandableListAdapter expandableListAdapter = new CalendarExpandableViewAdapter
                                                            (view.getContext(),listTitle,listDetails);
        expandableListView.setAdapter((expandableListAdapter));

        return view;
    }
}
