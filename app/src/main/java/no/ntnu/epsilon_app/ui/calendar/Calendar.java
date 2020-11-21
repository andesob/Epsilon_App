package no.ntnu.epsilon_app.ui.calendar;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Calendar {
    long id;
    String title;
    String description;

    String latLng;

    String startTime;
    String endTime;

    String address;
    private boolean expanded;

    public Calendar(long id, String title, String description, String latLng, String startTime, String endTime, String address) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.latLng = latLng;
        this.startTime = startTime;
        this.endTime = endTime;
        this.address = address;
        this.expanded = false;
    }

    public long getId() {
        return id;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLatLng() {
        return latLng;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getAddress() {
        return address;
    }

    public String getStartTimeParsed(int poistion) {
        String[] arr = getStartTime().split(",");
        return arr[poistion];
    }
    public String getEndTimeParsed(int poistion) {
        String[] arr = getEndTime().split(",");
        return arr[poistion];
    }

    public String getTime(String startTime,String endTime){
        String time = getStartTimeParsed(3) + ":" +
                getStartTimeParsed(4) + " - " +
                getEndTimeParsed(3)+":"+
                getEndTimeParsed(4);
        return time;
    }

    public String splitLangLng(int position){
        String[] arr = getLatLng().split(",");
        return arr[position];
    }
}
