package no.ntnu.epsilon_app.ui.calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import no.ntnu.epsilon_app.R;

public class CalendarAddFragment extends BottomSheetDialogFragment {

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

        return view;
    }
}
