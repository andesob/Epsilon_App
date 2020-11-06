package no.ntnu.epsilon_app.ui.calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import no.ntnu.epsilon_app.R;

public class CalendarAddFragment extends BottomSheetDialogFragment {

    private int count=0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(CalendarAddFragment.STYLE_NORMAL,R.style.SomeStyle);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar_add,container,false);
        final TimePicker startTimePicker = view.findViewById(R.id.setStartTime);
        startTimePicker.setIs24HourView(true);
        final TimePicker endTimePicker = view.findViewById(R.id.setEndTime);
        endTimePicker.setIs24HourView(true);

        final ImageView nextButton = view.findViewById(R.id.arrowRight);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count +=1;
                setPage(count);
            }
        });

        return view;
    }
    private void setPage(int page){
        switch (page){
            case 1:
               ConstraintLayout addCalendarPageOne = getView().findViewById(R.id.addCalendarPageOne);
               addCalendarPageOne.setVisibility(View.GONE);
               break;
            default:
        }
    }
}
