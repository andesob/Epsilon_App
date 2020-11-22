package no.ntnu.epsilon_app.ui.calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import no.ntnu.epsilon_app.MainActivity;
import no.ntnu.epsilon_app.R;
import no.ntnu.epsilon_app.api.RetrofitClientInstance;
import no.ntnu.epsilon_app.data.LoginDataSource;
import no.ntnu.epsilon_app.data.LoginRepository;
import no.ntnu.epsilon_app.ui.faq.dummy.TestData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CalendarFragment extends Fragment {


    private ArrayList<Calendar> calendarList = new ArrayList<>();
    private CalendarRecyclerViewAdapter calendarAdapter;
    RecyclerView recyclerView;
    private Button firstMonth,secondMonth,thirdMonth,fourthMonth,fifthMonth
            ,sixthMonth,seventhMonth,eightMonth,ninthMonth,tenthMonth
            ,eleventhMonth,twelthMonth;

    private ArrayList<Button> monthList;

    public CalendarFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        monthList = new ArrayList<>();

        monthList.add(firstMonth = view.findViewById(R.id.first));
        monthList.add(secondMonth = view.findViewById(R.id.second));
        monthList.add(thirdMonth = view.findViewById(R.id.third));
        monthList.add(fourthMonth = view.findViewById(R.id.fourth));
        monthList.add(fifthMonth = view.findViewById(R.id.fifth));
        monthList.add(sixthMonth = view.findViewById(R.id.sixth));
        monthList.add(seventhMonth = view.findViewById(R.id.seventh));
        monthList.add(eightMonth = view.findViewById(R.id.eight));
        monthList.add(ninthMonth = view.findViewById(R.id.ninth));
        monthList.add(tenthMonth = view.findViewById(R.id.tenth));
        monthList.add(eleventhMonth = view.findViewById(R.id.eleventh));
        monthList.add(twelthMonth = view.findViewById(R.id.twelth));

        for(Button b : monthList){
            final Button c = b;
            c.setText(sortMonths(monthList.indexOf(b)));
            c.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("click");
                    calendarAdapter = new CalendarRecyclerViewAdapter(getContext(),setList(CalendarViewModel.CALENDAR_LIST,monthList.indexOf(c)));
                    recyclerView.setAdapter(calendarAdapter);
                }
            });
        }






        recyclerView = view.findViewById(R.id.recyclerView);

        getCalendarItems();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        calendarAdapter = new CalendarRecyclerViewAdapter(getContext(),setList(CalendarViewModel.CALENDAR_LIST,0));
        recyclerView.setAdapter(calendarAdapter);
        ImageView floatingActionButton = view.findViewById(R.id.calendarEdditButton);

        LoginRepository loginRepository = LoginRepository.getInstance(new LoginDataSource());
        if(loginRepository.isAdmin()||loginRepository.isBoardmember()){
            floatingActionButton.setVisibility(View.VISIBLE);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalendarAddFragment calendarAddFragment = new CalendarAddFragment();
                calendarAddFragment.show(getActivity().getSupportFragmentManager(), "ModalBottomSheet");
            }
        });
        }



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
    private String sortMonths(int position){
        String[] months = {"Januar","Februar","Mars","April",
                "Mai","Juni","Juli","August","September","Oktober","November","Desember"};
        int monthIndex = LocalDate.now().getMonth().ordinal();
        Collections.rotate(Arrays.asList(months),-monthIndex);
        return months[position];

    }
    private ArrayList<Calendar> setList(ArrayList<Calendar> inputList,int position){
        ArrayList<Calendar> newList = new ArrayList<>();
        for(Calendar c : inputList){
            if((Integer.parseInt(c.getStartTimeParsed(1))) == ((position + LocalDate.now().getMonth().ordinal()) % 12)){
                System.out.println(c);
                newList.add(c);
            }
        }
        newList.sort((c1,c2)->(Integer.parseInt(c1.getStartTimeParsed(2))) > (Integer.parseInt(c2.getStartTimeParsed(2))) ? 1 :-1);
        return newList;
    }

    }


