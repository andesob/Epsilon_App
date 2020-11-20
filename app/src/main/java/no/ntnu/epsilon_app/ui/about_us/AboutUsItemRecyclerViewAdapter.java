package no.ntnu.epsilon_app.ui.about_us;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import no.ntnu.epsilon_app.R;
import no.ntnu.epsilon_app.data.Image;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link AboutUsObject}.
 * TODO: Replace the implementation with code for your data type.
 */
public class AboutUsItemRecyclerViewAdapter extends RecyclerView.Adapter<AboutUsItemRecyclerViewAdapter.ViewHolder> {

    private List<AboutUsObject> aboutUsObjects;
    private ItemClickListener mClickListener;

    public AboutUsItemRecyclerViewAdapter(List<AboutUsObject> aboutUsObjects) {
        this.aboutUsObjects = aboutUsObjects;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.about_us_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Image image = null;
        holder.aboutUsObject = aboutUsObjects.get(position);

        for (Image i : AboutUsViewModel.IMAGE_LIST) {
            if (i.getUserId() == holder.aboutUsObject.getUserid()) {
                image = i;
                break;
            }
        }

        holder.titleTV.setText(aboutUsObjects.get(position).getPosition());
        holder.nameTV.setText(aboutUsObjects.get(position).getName());
        holder.emailTV.setText(aboutUsObjects.get(position).getEmail());

        if (image != null) {
            holder.imageview.setImageBitmap(image.getBitmap());
        }
    }

    @Override
    public int getItemCount() {
        return aboutUsObjects.size();
    }

    // convenience method for getting data at click position
    public AboutUsObject getItem(int id) {
        return aboutUsObjects.get(id);
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }


    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;
        public final TextView titleTV;
        public final TextView nameTV;
        public final TextView emailTV;
        public ImageView imageview;
        public AboutUsObject aboutUsObject;

        ViewHolder(View view) {
            super(view);
            mView = view;
            titleTV = (TextView) view.findViewById(R.id.board_member_title);
            nameTV = (TextView) view.findViewById(R.id.board_member_name);
            emailTV = (TextView) view.findViewById(R.id.board_member_contact_info);
            imageview = (ImageView) view.findViewById((R.id.board_member_image));
            imageview.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }

        @Override
        public String toString() {
            return super.toString() + " '" + nameTV.getText() + "'";
        }
    }
}