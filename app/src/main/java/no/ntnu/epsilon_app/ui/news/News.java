package no.ntnu.epsilon_app.ui.news;

import androidx.annotation.NonNull;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class News {
    private String title;
    private String contents;
    private Date timeWritten;
    private Date lastUpdated;

    public News(){
        timeWritten = new Date();
        lastUpdated = timeWritten;
    }

    public void setTitle(String title){
        this.title = title;
        lastUpdated = new Date();
    }

    public void setContents(String contents){
        this.contents = contents;
        lastUpdated = new Date();
    }

    public String getTimeWrittenAsString(){
        return DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, Locale.GERMANY).format(timeWritten);
    }

    public String getLastUpdatedAsString(){
        return DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, Locale.GERMANY).format(lastUpdated);
    }


    @NonNull
    @Override
    public String toString() {
        String toString = title + "\n"
                + contents
                + "\n\nTime written: " + getTimeWrittenAsString()
                + "\n\nLast updated: " + getLastUpdatedAsString();
        return toString;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }
}
