package no.ntnu.epsilon_app.ui.news;

import androidx.annotation.NonNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class News {
    private long newsId;
    private String title;
    private String contents;
    private LocalDateTime timeWritten;
    private LocalDateTime lastUpdated;

    public News(long id, String title, String contents, LocalDateTime timeWritten, LocalDateTime lastUpdated) {
        this.newsId = id;
        this.timeWritten = timeWritten;
        this.lastUpdated = lastUpdated;
        this.contents = contents;
        this.title = title;
    }

    public String getTimeWrittenAsString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

        return dtf.format(timeWritten);
    }

    public String getLastUpdatedAsString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

        return dtf.format(lastUpdated);
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
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

    public long getId(){
        return newsId;
    }
}
