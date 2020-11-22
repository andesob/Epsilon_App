package no.ntnu.epsilon_app.ui.calendar;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.CalendarContract;
import android.view.ContextThemeWrapper;
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
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;
import java.util.TimeZone;

import no.ntnu.epsilon_app.R;
import no.ntnu.epsilon_app.api.RetrofitClientInstance;
import no.ntnu.epsilon_app.data.LoginDataSource;
import no.ntnu.epsilon_app.data.LoginRepository;
import no.ntnu.epsilon_app.ui.maps.MapFragment;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CalendarRecyclerViewAdapter extends RecyclerView.Adapter<CalendarRecyclerViewAdapter.ViewHolder> {

   private ArrayList<Calendar> calendarList;
   private CalendarFragment calendarFragment;
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
       calendarFragment = new CalendarFragment();
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
        Picasso.get().load("http://maps.google.com/maps/api/staticmap?center=" + calendar.getLatLng() + "&zoom=15&markers=" + calendar.getLatLng() + "&size=400x600&sensor=false&key=" + holder.mapView.getContext().getString(R.string.MAPS_API_KEY)).into(holder.mapView);

        LoginRepository loginRepository = LoginRepository.getInstance(new LoginDataSource());
        if(loginRepository.isAdmin()||loginRepository.isBoardmember()){
            holder.deleteButton.setVisibility(View.VISIBLE);
        }

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(context,R.style.myDialog));
                builder.setMessage("Vil du slette dette arrangementet");
                builder.setCancelable(true);
                builder.setNegativeButton("Nei", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteCalendarItem(calendar.getId());
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }

        });

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

                        long eventId = Long.parseLong(uri.getLastPathSegment());

                        ContentValues reminder = new ContentValues();
                        reminder.put(CalendarContract.Reminders.EVENT_ID,eventId);
                        reminder.put(CalendarContract.Reminders.MINUTES,1440);
                        reminder.put(CalendarContract.Reminders.METHOD,CalendarContract.Reminders.METHOD_ALERT);
                        cr.insert(CalendarContract.Reminders.CONTENT_URI, reminder);

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
        int month = Integer.parseInt(startMonth) + 1;
        int date = Integer.parseInt(startDay);
        LocalDate d = LocalDate.of(year, month, date);
        DayOfWeek dow = d.getDayOfWeek();
        String s = dow.getDisplayName(TextStyle.SHORT, Locale.US);
        return s;
    }

    private void deleteCalendarItem(long id){
        System.out.println("=============================");
        System.out.println(id);
        Call<ResponseBody> call = RetrofitClientInstance.getSINGLETON().getAPI().deleteCalendarItem(id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){

                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        ConstraintLayout expandableLayout;
        ConstraintLayout parentCard;
        TextView dateTextView, dateNumber, title, time, description,address;
        ImageView mapView,deleteButton;
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
            deleteButton = view.findViewById(R.id.deleteBtn);

        }


    }
}
