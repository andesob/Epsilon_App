package no.ntnu.epsilon_app.ui.calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import no.ntnu.epsilon_app.MainActivity;
import no.ntnu.epsilon_app.R;
import no.ntnu.epsilon_app.api.RetrofitClientInstance;
import no.ntnu.epsilon_app.ui.faq.dummy.TestData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CalendarFragment extends Fragment {


    private ArrayList<Calendar> calendarList = new ArrayList<>();
    private CalendarRecyclerViewAdapter calendarAdapter;
    RecyclerView recyclerView;

    public CalendarFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        //listDetails = TestData.getData();
        // listTitle = new ArrayList<String>(listDetails.keySet());

        getCalendarItems();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        calendarAdapter = new CalendarRecyclerViewAdapter(getContext(),CalendarViewModel.CALENDAR_LIST);
        recyclerView.setAdapter(calendarAdapter);

        FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.calendarEdditButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalendarAddFragment calendarAddFragment = new CalendarAddFragment();
                calendarAddFragment.show(getActivity().getSupportFragmentManager(), "ModalBottomSheet");
            }
        });


//        ExpandableListAdapter expandableListAdapter = new CalendarExpandableViewAdapter
//                                                            (view.getContext(),listTitle,listDetails);
//        expandableListView.setAdapter((expandableListAdapter));

        return view;
    }

    private void getCalendarItems() {
        Call<List<Calendar>> call = RetrofitClientInstance.getSINGLETON().getAPI().getCalendarItems();
        call.enqueue(new Callback<List<Calendar>>() {
            @Override
            public void onResponse(Call<List<Calendar>> call, Response<List<Calendar>> response) {
                if (response.isSuccessful()) {
                    CalendarViewModel.CALENDAR_LIST = (ArrayList<Calendar>) response.body();
                }
                else{
                    ((MainActivity)getActivity()).goToSplashScreen();
                }
            }

            @Override
            public void onFailure(Call<List<Calendar>> call, Throwable t) {
            }
        });
    }

}
