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
                    System.out.println("JAAAAAAAAAAAAAAAAAAAA");
                    //calendarList = (ArrayList<Calendar>) response.body();
                    //System.out.println(calendarList.get(0));
                    CalendarViewModel.CALENDAR_LIST = (ArrayList<Calendar>) response.body();
                }
                else{
                    ((MainActivity)getActivity()).goToSplashScreen();
                }
            }

            @Override
            public void onFailure(Call<List<Calendar>> call, Throwable t) {
                System.out.println("FUUUUUUUUUUUUUUCK");
                System.out.println(t.getCause().toString());
            }
        });
    }

    /* private void jsonParser(List<Calendar> inputList){
         for(int i = 0; i <inputList.size(); i++){
             List<String> parentList = new ArrayList<>();
             List<String> childList = new ArrayList<>();
             String title = inputList.get(i).getTitle();
             String startDay = inputList.get(i).getStartTimeParsed(2);
             String startMonth = inputList.get(i).getStartTimeParsed(1);
             String startYear = inputList.get(i).getStartTimeParsed(0);
             String dayOfWeek = getDayOfWeek(startYear,startMonth,startDay);
             parentList.addAll(Arrays.asList(startDay,dayOfWeek,title));

             String description = inputList.get(i).getDescription();
             String startHour = inputList.get(i).getStartTimeParsed(3);
             String startMinute = inputList.get(i).getStartTimeParsed(4);
             String endHour = inputList.get(i).getEndTimeParsed(3);
             String endMinute = inputList.get(i).getEndTimeParsed(4);
             String address = inputList.get(i).getAddress();
             childList.addAll(Arrays.asList(description,startHour,startMinute,endHour,endMinute,address));
             System.out.println(startYear);
             listDetails.put(parentList,childList);

         }*/
    //}
    private String getDayOfWeek(String startYear, String startMonth, String startDay) {
        int year = Integer.parseInt(startYear);
        int month = Integer.parseInt(startMonth);
        int date = Integer.parseInt(startDay);
        LocalDate d = LocalDate.of(year, month, date);
        DayOfWeek dow = d.getDayOfWeek();
        String s = dow.getDisplayName(TextStyle.SHORT, Locale.US);
        System.out.println(s);
        return s;
    }

    /*private void setViewAdapter(List<List<String>> parent,HashMap<String,List<String>> child){
        ExpandableListView expandableListView = (ExpandableListView) getActivity().findViewById(R.id.expandableListView);
        calendarAdapter = new CalendarExpandableViewAdapter(getContext(),parent,child);
        expandableListView.setAdapter(calendarAdapter);
    }*/

}
