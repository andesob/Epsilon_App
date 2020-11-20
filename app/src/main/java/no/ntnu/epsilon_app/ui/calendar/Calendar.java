package no.ntnu.epsilon_app.ui.calendar;

import com.google.android.gms.maps.model.LatLng;

public class Calendar {
    long id;
    String title;
    String description;

    LatitudeLongitude latLng;

    Time startTime;
    Time endTime;

    String address;

    public Calendar(long id, String title, String description, LatitudeLongitude latLng, Time startTime, Time endTime, String address) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.latLng = latLng;
        this.startTime = startTime;
        this.endTime = endTime;
        this.address = address;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LatitudeLongitude getLatLng() {
        return latLng;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public String getAddress() {
        return address;
    }
}
