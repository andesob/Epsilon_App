package no.ntnu.epsilon_app.ui.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import no.ntnu.epsilon_app.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<String> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private List<News> newsList;

    // data is passed into the constructor
    public RecyclerViewAdapter(Context context, List<News> newsList) {
        this.mInflater = LayoutInflater.from(context);
        this.newsList = newsList;
    }

    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.cards_view, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String title = newsList.get(position).getTitle();
        String daysPassedString = "";
        long daysPassed = ChronoUnit.DAYS.between(newsList.get(position).getLastUpdated(), LocalDateTime.now());

        if (daysPassed == 0) {
            daysPassedString = "Today";
        } else {
            daysPassedString = daysPassed + " days ago";
        }

        holder.newsFeedTitle.setText(title);
        holder.newsFeedDatePassed.setText(daysPassedString);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return newsList.size();
    }

    // convenience method for getting data at click position
    public News getItem(int id) {
        return newsList.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView newsFeedTitle;
        TextView newsFeedDatePassed;

        ViewHolder(View itemView) {
            super(itemView);
            newsFeedTitle = itemView.findViewById(R.id.newsFeedTitle);
            newsFeedDatePassed = itemView.findViewById(R.id.timePassed);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }
}

