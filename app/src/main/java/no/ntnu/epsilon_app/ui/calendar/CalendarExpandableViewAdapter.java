/*
package no.ntnu.epsilon_app.ui.calendar;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.icu.util.Calendar;
import android.net.Uri;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import no.ntnu.epsilon_app.R;
import no.ntnu.epsilon_app.ui.maps.MapFragment;

public class CalendarExpandableViewAdapter extends BaseExpandableListAdapter {

    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 69;
    private Context context;
    private List<String> expandableListTitle;
    private HashMap<String, List<String>> expandableListDetail;


    public CalendarExpandableViewAdapter(Context context,List<String> expandableListTitle,
                                         HashMap<String,List<String>> expandableListDetail){
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.expandableListDetail.get
                (this.expandableListTitle.get(listPosition)).get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandendListPosition) {
        return  expandendListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String expandedListText = (String) getChild(listPosition,expandedListPosition);
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.calendar_items_child,null);
        }
        TextView expandedListTextView = (TextView) convertView.findViewById(R.id.expandedListItem);
        expandedListTextView.setText(expandedListText);

        String latEiffelTower = "62.472673";
        String lngEiffelTower = "6.230659";

        ImageView mapView = (ImageView) convertView.findViewById(R.id.mapStatic);
        Picasso.get().load("http://maps.google.com/maps/api/staticmap?center=" + latEiffelTower + "," + lngEiffelTower + "&zoom=15&markers=" +
                latEiffelTower+","+lngEiffelTower+"&size=400x600&sensor=false&key=AIzaSyB2qCCyXeJ67iEyBOT4E_PdXqgbzJ7fF4E").into(mapView);

        Button loginButton = (Button) convertView.findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                if(context.checkSelfPermission(Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions((AppCompatActivity)context,new String[]{Manifest.permission.WRITE_CALENDAR},PERMISSIONS_REQUEST_READ_CONTACTS);
                } else{
                long calId = 1;
                long startMillis = 0;
                long endMillis = 0;
                Calendar startTime = Calendar.getInstance();
                startTime.set(2020,11,24,17,0);
                startMillis = startTime.getTimeInMillis();
                Calendar endTime = Calendar.getInstance();
                endTime.set(2020,11,27,23,59);
                endMillis = endTime.getTimeInMillis();

                ContentResolver cr = context.getContentResolver();
                ContentValues values = new ContentValues();
                values.put(CalendarContract.Events.DTSTART,startMillis);
                values.put(CalendarContract.Events.DTEND,endMillis);
                values.put(CalendarContract.Events.TITLE,"JULEAFTEN");
                values.put(CalendarContract.Events.DESCRIPTION,"GAVAWOOOOOOOOOOO");
                values.put(CalendarContract.Events.CALENDAR_ID,calId);
                TimeZone timeZone = TimeZone.getDefault();
                values.put(CalendarContract.Events.EVENT_TIMEZONE,timeZone.getID());
                Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI,values);

                Toast.makeText(context,"Arrangemang lagret i din kalender",Toast.LENGTH_SHORT).show();
                }
            }
        });
        mapView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapFragment mapFragment = new MapFragment();
                mapFragment.show(((AppCompatActivity)context).getSupportFragmentManager(),"ModalBottomSheet");
            }
        });

        return convertView;
    }
    @Override
    public int getChildrenCount(int listPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .size();
    }
    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.calendar_item_parent, null);
        }

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
}

*/
