package onestonetwobirds.capstonuitest3.groupHouseKeeping.Member;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YeomJi on 2014. 12. 1..
 */
public class IconTextListAdapterMember extends BaseAdapter {

    private Context mContext;

    private List<IconTextItemMember> mItems = new ArrayList<IconTextItemMember>();

    public IconTextListAdapterMember(Context context) {
        mContext = context;
    }

    public void addItem(IconTextItemMember it) {
        mItems.add(it);
    }

    public void setListItems(List<IconTextItemMember> lit) {
        mItems = lit;
    }

    public int getCount() {
        return mItems.size();
    }

    public Object getItem(int position) {
        return mItems.get(position);
    }

    public boolean areAllItemsSelectable() {
        return false;
    }

    public boolean isSelectable(int position) {
        try {
            return mItems.get(position).isSelectable();
        } catch (IndexOutOfBoundsException ex) {
            return false;
        }
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        IconTextViewMember itemView;
        if (convertView == null) {
            itemView = new IconTextViewMember(mContext, mItems.get(position));
        } else {
            itemView = (IconTextViewMember) convertView;

            itemView.setIcon(mItems.get(position).getIcon());
            itemView.setText(0, mItems.get(position).getData(0));
            itemView.setText(1, mItems.get(position).getData(1));
        }

        return itemView;
    }
}
