package onestonetwobirds.capstonuitest3.groupHouseKeeping.Member;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import onestonetwobirds.capstonuitest3.R;


/**
 * Created by YeomJi on 2014. 12. 1..
 */
public class IconTextViewMember extends LinearLayout {

    /**
     * Icon
     */
    private ImageView mIcon;

    /**
     * TextView 01
     */
    private TextView mText01;


    /**
     * TextView 03
     */

    public IconTextViewMember(Context context, IconTextItemMember aItem) {
        super(context);

        // Layout Inflation
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.member_list, this, true);

        // Set Icon
        mIcon = (ImageView) findViewById(R.id.iconItemMember);
        Log.e("IconTextViewMember", "mIcon : " + mIcon.toString());
        mIcon.setImageBitmap(aItem.getIcon());

        // Set Text 01
        mText01 = (TextView) findViewById(R.id.dataItem01Member);
        Log.e("IconTextViewMember", "mText01 : " + mText01.toString());
        mText01.setText(aItem.getData(0));

    }

    /**
     * set Text
     *
     * @param index
     * @param data
     */
    public void setText(int index, String data) {
        if (index == 0) {
            mText01.setText(data);
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * set Icon
     *
     * @param icon
     */
    public void setIcon(Bitmap icon) {
        mIcon.setImageBitmap(icon);
    }
}
