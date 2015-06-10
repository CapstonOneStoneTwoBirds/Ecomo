package onestonetwobirds.capstonuitest3.groupHouseKeeping.Main;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YeomJi on 2014. 12. 1..
 */
public class IconTextListAdapterGroup extends BaseAdapter {

    private Context mContext;

    private List<IconTextItemGroup> mItems = new ArrayList();

    public IconTextListAdapterGroup(Context context) {
        mContext = context;
    }

    public void addItem(IconTextItemGroup it) {
        mItems.add(it);
    }

    public void setListItems(List<IconTextItemGroup> lit) {
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
        IconTextViewGroup itemView;
        if (convertView == null) {
            itemView = new IconTextViewGroup(mContext, mItems.get(position));
        } else {
            itemView = (IconTextViewGroup) convertView;

            itemView.setIcon(mItems.get(position).getIcon());
            itemView.setText(0, mItems.get(position).getData(0));
        }

        return itemView;
    }
}
