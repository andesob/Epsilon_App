package no.ntnu.epsilon_app.ui.faq;


import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import no.ntnu.epsilon_app.R;
import no.ntnu.epsilon_app.tools.BottomSheetDialogEditFaq;

public class FaqExpandableViewAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> expandableListTitle;
    private HashMap<String, List<String>> expandableListDetail;
    private List<Faq> faqList;
    private boolean admin;
    private FaqExpandableViewClickListener trashClickListener;
    private FaqExpandableViewClickListener faqEditClickListener;

    public FaqExpandableViewAdapter(Context context, List<String> expandableListTitle,
                                    HashMap<String, List<String>> expandableListDetail, boolean admin, List<Faq> faqList) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
        this.admin = admin;
        this.faqList = faqList;

    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String expandedListText = (String) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.faq_items_child, null);
        }
        TextView expandedListTextView = (TextView) convertView
                .findViewById(R.id.expandedListItem);
        expandedListTextView.setText(expandedListText);
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

    public interface FaqExpandableViewClickListener {
        void onTrashClick(View view, int position, long faqId);
        void onEditClick(View view, int position, Faq faq);
    }

    public void setEditClickListener(FaqExpandableViewClickListener faqExpandableViewClickListener){
        this.faqEditClickListener = faqExpandableViewClickListener;
    }
    public void setTrashClickListener(FaqExpandableViewClickListener faqExpandableViewClickListener){
        this.trashClickListener = faqExpandableViewClickListener;
    }


    @Override
    public View getGroupView(final int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        final String listTitle = (String) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.faq_list_parent, null);

        }


        TextView listTitleTextView = (TextView) convertView.findViewById(R.id.listTitle);
        listTitleTextView.setText(listTitle);

        if (admin){
        Button editButton = convertView.findViewById(R.id.faqEditButton);
            editButton.setVisibility(View.VISIBLE);
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String answer = expandableListDetail.get(listTitle).get(0);
                    long id = findFaqId(faqList, listTitle);
                    Faq selectedFaq = new Faq(id, listTitle, answer);

                    if(faqEditClickListener != null) faqEditClickListener.onEditClick(view, listPosition, selectedFaq);
                }
            });
            ImageView trashIcon = convertView.findViewById(R.id.faqTrash);
            trashIcon.setVisibility(View.VISIBLE);
            trashIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    long id = findFaqId(faqList, listTitle);
                    if(trashClickListener != null) trashClickListener.onTrashClick(view, listPosition, id);
                }
            });

        }
        return convertView;
    }

    private long findFaqId(List<Faq> faqs, String searchWords) {
        long id = -1;

        for(int i = 0; i < faqs.size(); i++){
            System.out.println("question: " + faqs.get(i).getQuestion());
            if (faqs.get(i).getQuestion().contentEquals(searchWords)){
                id = faqs.get(i).getId();
                System.out.println("id:" + faqs.get(i).getQuestion());
                return  id;
            }
        }
        return id;

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