package onestonetwobirds.capstonuitest3.groupHouseKeeping.Announce;

/**
 * Created by YeomJi on 15. 6. 10..
 */
import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import onestonetwobirds.capstonuitest3.R;


public class AnnounceListAdapter extends BaseAdapter {

    final String TAG = "MainListAdapter";

    public Context mContext;
    private ArrayList<ExamEntity> mExamItemArrayList;
    private LayoutInflater mLayoutInflater;
    int resource;

    public AnnounceListAdapter(Context context,
                               ArrayList<ExamEntity> mExamItemArrayList, int resource) {
        mContext = context;
        this.mExamItemArrayList = mExamItemArrayList;
        this.mLayoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.resource = resource;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mExamItemArrayList.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        Holder holder = new Holder();
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(resource, null);

            // find resource

            holder.tv_title = (TextView) convertView
                    .findViewById(R.id.lf_tv_announce_title);
            holder.tv_place = (TextView) convertView
                    .findViewById(R.id.lf_tv_announce_place);
            holder.tv_content = (TextView) convertView
                    .findViewById(R.id.lf_tv_announce_content);
            convertView.setTag(holder);

        } else {
            holder = (Holder) convertView.getTag();
        }

        // set content
        String title = mExamItemArrayList.get(position).title;
        String place = mExamItemArrayList.get(position).place;
        String content = mExamItemArrayList.get(position).content;
        holder.tv_title.setText(title);
        holder.tv_place.setText(place);
        holder.tv_content.setText(content);

        // set click listener
        /*
        holder.tv_title.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Toast.makeText(mContext,
                        mExamItemArrayList.get(position).title,
                        Toast.LENGTH_SHORT).show();

            }
        });

        holder.tv_content.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,
                        mExamItemArrayList.get(position).content,
                        Toast.LENGTH_SHORT).show();

            }
        });
        */

        return convertView;
    }

    private class Holder {
        TextView tv_title, tv_place, tv_content;
    }

}