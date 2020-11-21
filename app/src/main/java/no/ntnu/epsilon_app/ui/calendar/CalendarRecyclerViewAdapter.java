package no.ntnu.epsilon_app.ui.calendar;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.zip.Inflater;

import no.ntnu.epsilon_app.R;
import no.ntnu.epsilon_app.data.Image;
import no.ntnu.epsilon_app.ui.maps.MapFragment;

public class CalendarRecyclerViewAdapter extends RecyclerView.Adapter<CalendarRecyclerViewAdapter.ViewHolder> {

   private ArrayList<Calendar> calendarList;
   private Context context;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 69;

   public CalendarRecyclerViewAdapter(Context context,ArrayList<Calendar> calendarList){
       this.context = context;
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
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Calendar calendar = calendarList.get(position);
        holder.dateTextView.setText(getDayOfWeek(calendar.getStartTimeParsed(0),calendar.getStartTimeParsed(1),calendar.getStartTimeParsed(2)));
        holder.dateNumber.setText(calendar.getStartTimeParsed(2));
        holder.title.setText(calendar.getTitle());
        holder.time.setText(calendar.getTime(calendar.getStartTime(),calendar.getEndTime()));
        holder.description.setText(calendar.getDescription());
        holder.address.setText(calendar.getAddress());
        //Picasso.get().load("http://maps.google.com/maps/api/staticmap?center=" + calendar.getLatLng() + "&zoom=15&markers=" + calendar.getLatLng() + "&size=400x600&sensor=false&key=" + holder.mapView.getContext().getString(R.id.)).into(holder.mapView);

        holder.mapView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapFragment mapFragment = new MapFragment(calendar.splitLangLng(0),calendar.splitLangLng(1));
                mapFragment.show(((AppCompatActivity)v.getContext()).getSupportFragmentManager(),"ModalBottomSheet");
            }
        });

            holder.addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.getContext().checkSelfPermission(Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions((AppCompatActivity) context, new String[]{Manifest.permission.WRITE_CALENDAR}, PERMISSIONS_REQUEST_READ_CONTACTS);
                    } else {
                        long calId = 1;
                        long startMillis = 0;
                        long endMillis = 0;
                        android.icu.util.Calendar startTime = android.icu.util.Calendar.getInstance();
                        startTime.set(Integer.parseInt(calendar.getStartTimeParsed(0)),
                                Integer.parseInt(calendar.getStartTimeParsed(1)),
                                Integer.parseInt(calendar.getStartTimeParsed(2)),
                                Integer.parseInt(calendar.getStartTimeParsed(3)),
                                Integer.parseInt(calendar.getStartTimeParsed(4)));
                        startMillis = startTime.getTimeInMillis();
                        android.icu.util.Calendar endTime = android.icu.util.Calendar.getInstance();
                        endTime.set(Integer.parseInt(calendar.getEndTimeParsed(0)),
                                Integer.parseInt(calendar.getEndTimeParsed(1)),
                                Integer.parseInt(calendar.getEndTimeParsed(2)),
                                Integer.parseInt(calendar.getEndTimeParsed(3)),
                                Integer.parseInt(calendar.getEndTimeParsed(4)));
                        endMillis = endTime.getTimeInMillis();

                        ContentResolver cr = context.getContentResolver();
                        ContentValues values = new ContentValues();
                        values.put(CalendarContract.Events.DTSTART, startMillis);
                        values.put(CalendarContract.Events.DTEND, endMillis);
                        values.put(CalendarContract.Events.TITLE, calendar.getTitle());
                        values.put(CalendarContract.Events.EVENT_LOCATION, calendar.getAddress());
                        values.put(CalendarContract.Events.DESCRIPTION, calendar.getDescription());
                        values.put(CalendarContract.Events.CALENDAR_ID, calId);
                        TimeZone timeZone = TimeZone.getDefault();
                        values.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone.getID());
                        Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);

                        Toast.makeText(context, "Arrangemang lagret i din kalender", Toast.LENGTH_SHORT).show();
                    }

                }
            });

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
        ImageView mapView;
        Button addButton;
        ViewHolder(View view){
            super(view);

            dateTextView = view.findViewById(R.id.day);
            dateNumber = view.findViewById(R.id.dateNum);
            title = view.findViewById(R.id.calendarTitle);
            time = view.findViewById(R.id.time);
            description = view.findViewById(R.id.calendarDescription);
            address = view.findViewById(R.id.address);

            mapView = view.findViewById(R.id.mapStatic);

            expandableLayout = view.findViewById(R.id.expandableLayout);
            parentCard = view.findViewById(R.id.parentCard);
            parentCard.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Calendar calendar = calendarList.get(getAdapterPosition());
                    calendar.setExpanded(!calendar.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });

            addButton = view.findViewById(R.id.addButton);
      /*      mapView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    MapFragment mapFragment = new MapFragment();
                    mapFragment.show(((AppCompatActivity)v.getContext()).getSupportFragmentManager(),"ModalBottomSheet");
                }
            });*/
        }


    }
}
