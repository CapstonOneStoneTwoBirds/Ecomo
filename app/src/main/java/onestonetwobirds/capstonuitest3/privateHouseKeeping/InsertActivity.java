package onestonetwobirds.capstonuitest3.privateHouseKeeping;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.rey.material.app.ToolbarManager;
import com.rey.material.util.ThemeUtil;
import com.rey.material.widget.SnackBar;

import java.lang.reflect.Field;
import java.util.ArrayList;

import onestonetwobirds.capstonuitest3.R;

/**
 * Created by YeomJi on 15. 5. 31..
 */

public class InsertActivity  extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_main);

        LinearLayout insertLinear = (LinearLayout) findViewById(R.id.insert_linear);
        insertLinear.setAlpha((float) 0.6);





    }
}



/*
public class InsertActivity  extends ActionBarActivity  implements ToolbarManager.OnToolbarGroupChangedListener  {

    private Toolbar mToolbar;               // 화면 상단의 액션바
    private ToolbarManager mToolbarManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.insert_main);


        mToolbar = (Toolbar) findViewById(R.id.insert_toolbar);
        setSupportActionBar(mToolbar);
        //mToolbar.getBackground().setAlpha(20);


        mToolbarManager = new ToolbarManager(this, mToolbar, 0, R.style.ToolbarRippleStyle, R.anim.abc_fade_in, R.anim.abc_fade_out);
        //mToolbar.setNavigationIcon(R.drawable.hand);

        mToolbarManager.registerOnToolbarGroupChangedListener(this);




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mToolbarManager.createMenu(R.menu.menu_insert);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mToolbarManager.onPrepareMenu();
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.tb_new:                                 // 새로 고침
                //mToolbarManager.setCurrentGroup(0);
                break;
        }

        return true;

    }

    @Override
    public void onToolbarGroupChanged(int oldGroupId, int groupId) {
        mToolbarManager.notifyNavigationStateChanged();
    }
}*/