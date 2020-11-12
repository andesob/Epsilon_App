package no.ntnu.epsilon_app.ui.about_us;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import no.ntnu.epsilon_app.R;
import no.ntnu.epsilon_app.data.Image;
import no.ntnu.epsilon_app.ui.about_us.dummy.DummyContent.DummyItem;
import no.ntnu.epsilon_app.ui.news.News;
import no.ntnu.epsilon_app.ui.news.RecyclerViewAdapter;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class AboutUsItemRecyclerViewAdapter extends RecyclerView.Adapter<AboutUsItemRecyclerViewAdapter.ViewHolder> {

    private final List<DummyItem> mValues;
    private ItemClickListener mClickListener;

    public AboutUsItemRecyclerViewAdapter(List<DummyItem> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.about_us_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        System.out.println(position);
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).id);
        holder.mContentView.setText(mValues.get(position).content);


        Image image = AboutUsViewModel.IMAGE_LIST.get(0);
        holder.imageview.setImageBitmap(image.getBitmap());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    // convenience method for getting data at click position
    public DummyItem getItem(int id) {
        return mValues.get(id);
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public ImageView imageview;
        public DummyItem mItem;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.board_member_title);
            mContentView = (TextView) view.findViewById(R.id.board_member_name);
            imageview = (ImageView) view.findViewById((R.id.board_member_image));
            imageview.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}