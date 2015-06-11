package onestonetwobirds.capstonuitest3.groupHouseKeeping.Member;

import android.content.Context;
import android.util.Log;
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

    private List<IconTextItemMember> mItems = new ArrayList();

    public IconTextListAdapterMember(Context context) {
        mContext = context;
    }
    public void addItemOnFirst(IconTextItemMember it){
        List<IconTextItemMember> Items = new ArrayList();
        Items.add(it);
        for(int i = 0 ; i< mItems.size() ; i ++){
            Items.add(mItems.get(i));
        }
        mItems = Items;
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
        }

        return itemView;
    }
}
