package no.ntnu.epsilon_app.ui.calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.zip.Inflater;

import no.ntnu.epsilon_app.R;

public class CalendarRecyclerViewAdapter extends RecyclerView.Adapter<CalendarRecyclerViewAdapter.ViewHolder> {

   private ArrayList<Calendar> calendarList;

   public CalendarRecyclerViewAdapter(ArrayList<Calendar> calendarList){
       this.calendarList = calendarList;
   }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_expandable_item,parent,false);
       ViewHolder holder = new ViewHolder(view);
       return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Calendar calendar = calendarList.get(position);
        holder.dateTextView.setText(getDayOfWeek(calendar.getStartTimeParsed(0),calendar.getStartTimeParsed(1),calendar.getStartTimeParsed(2)));
        holder.dateNumber.setText(calendar.getStartTimeParsed(2));
        holder.title.setText(calendar.getTitle());
        holder.time.setText(calendar.getTime(calendar.getStartTime(),calendar.getEndTime()));
        holder.description.setText(calendar.getDescription());
        holder.address.setText(calendar.getAddress());
        System.out.println(calendar.getAddress());

        boolean isExpanded = calendarList.get(position).isExpanded();
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return calendarList.size();
    }

    private String getDayOfWeek(String startYear,String startMonth,String startDay) {
        int year = Integer.parseInt(startYear);
        int month = Integer.parseInt(startMonth);
        int date = Integer.parseInt(startDay);
        LocalDate d = LocalDate.of(year, month, date);
        DayOfWeek dow = d.getDayOfWeek();
        String s = dow.getDisplayName(TextStyle.SHORT, Locale.US);
        return s;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ConstraintLayout expandableLayout;
        ConstraintLayout parentCard;
        TextView dateTextView, dateNumber, title, time, description,address;
        ViewHolder(View view){
            super(view);

            dateTextView = view.findViewById(R.id.day);
            dateNumber = view.findViewById(R.id.dateNum);
            title = view.findViewById(R.id.calendarTitle);
            time = view.findViewById(R.id.time);
            description = view.findViewById(R.id.calendarDescription);
            address = view.findViewById(R.id.address);
            expandableLayout = view.findViewById(R.id.expandableLayout);
            parentCard = view.findViewById(R.id.parentCard);
            parentCard.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    System.out.println("click");
                    Calendar calendar = calendarList.get(getAdapterPosition());
                    calendar.setExpanded(!calendar.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }
}
