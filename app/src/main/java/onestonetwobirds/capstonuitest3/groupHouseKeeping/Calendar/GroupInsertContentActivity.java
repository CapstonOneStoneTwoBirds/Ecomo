package onestonetwobirds.capstonuitest3.groupHouseKeeping.Calendar;

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
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.SimpleDialog;
import com.rey.material.app.ToolbarManager;
import com.rey.material.util.ThemeUtil;
import com.rey.material.widget.SnackBar;

import java.lang.reflect.Field;
import java.util.ArrayList;

import onestonetwobirds.capstonuitest3.R;
import onestonetwobirds.capstonuitest3.privateHouseKeeping.Main.CustomViewPager;
import onestonetwobirds.capstonuitest3.privateHouseKeeping.Main.PrivateMainActivity;
import onestonetwobirds.capstonuitest3.user.MyInformation.MyInfoActivity;

/**
 * Created by YeomJi on 15. 6. 8..
 */
public class GroupInsertContentActivity extends ActionBarActivity implements ToolbarManager.OnToolbarGroupChangedListener {

    private DrawerLayout dl_navigator;
    private FrameLayout fl_drawer;
    private ListView lv_drawer;
    private CustomViewPager vp;

    private DrawerAdapter mDrawerAdapter;
    private PagerAdapter mPagerAdapter;

    private Toolbar mToolbar;
    private ToolbarManager mToolbarManager;
    private SnackBar mSnackBar;

    private Tab[] mItems = new Tab[]{Tab.INSERTCONTENT};
    private Tab[] mItemsS = new Tab[]{Tab.PRIVATEINFO, Tab.MANUFACTURERS, Tab.LOGOUT};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.group_insert_content_main);

        dl_navigator = (DrawerLayout) findViewById(R.id.group_dl);
        fl_drawer = (FrameLayout) findViewById(R.id.group_fl_drawer);
        lv_drawer = (ListView) findViewById(R.id.group_lv_drawer);
        mToolbar = (Toolbar) findViewById(R.id.group_toolbar);
        vp = (CustomViewPager) findViewById(R.id.group_vp);
        mSnackBar = (SnackBar) findViewById(R.id.group_sn);

        mToolbarManager = new ToolbarManager(this, mToolbar, 0, R.style.ToolbarRippleStyle, R.anim.abc_fade_in, R.anim.abc_fade_out);
        mToolbarManager.setNavigationManager(new ToolbarManager.BaseNavigationManager(R.style.NavigationDrawerDrawable, this, mToolbar, dl_navigator) {
            @Override
            public void onNavigationClick() {                   // Toolbar가 클릭되었을 때
                if (mToolbarManager.getCurrentGroup() != 0)
                    mToolbarManager.setCurrentGroup(0);
                else
                    dl_navigator.openDrawer(Gravity.START);     // 우측의 리스트를 띄운다.
            }

            @Override
            public boolean isBackState() {
                return super.isBackState() || mToolbarManager.getCurrentGroup() != 0;
            }

            @Override
            protected boolean shouldSyncDrawerSlidingProgress() {
                return super.shouldSyncDrawerSlidingProgress() && mToolbarManager.getCurrentGroup() == 0;
            }

        });
        mToolbarManager.registerOnToolbarGroupChangedListener(this);

        mPagerAdapter = new PagerAdapter(getSupportFragmentManager(), mItems);
        vp.setAdapter(mPagerAdapter);
        //tpi.setViewPager(vp);
        mDrawerAdapter = new DrawerAdapter();
        lv_drawer.setAdapter(mDrawerAdapter);                       // DrawerLayout 보여줘


        mDrawerAdapter.setSelected(Tab.PRIVATEINFO);           // 디폴트 값 progress로 설정

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mToolbarManager.createMenu(R.menu.menu_main_group);
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
            case R.id.tb_private:
                Intent intent = new Intent(getApplicationContext(), PrivateMainActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.fade, R.anim.hold);
                break;
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

    public SnackBar getSnackBar() {
        return mSnackBar;
    }

    public enum Tab { // 툴바 내용 버튼 각각의 내용
        INSERTCONTENT("INSERTCONTENT"),
        PRIVATEINFO("개인 정보"),
        MANUFACTURERS("만든 이"),
        LOGOUT("로그아웃");
        private final String name;

        private Tab(String s) {
            name = s;
        }

        public boolean equalsName(String otherName) {
            return (otherName != null) && name.equals(otherName);
        }

        public String toString() {
            return name;
        }

    }

    class DrawerAdapter extends BaseAdapter implements View.OnClickListener {

        private Tab mSelectedTab;

        public void setSelected(Tab tab) {                  // 탭 or 우측의 리스트가 눌린 위치로 값을 설정한다.
            if (tab != mSelectedTab) {
                mSelectedTab = tab;
                notifyDataSetInvalidated();
            }
        }

        public Tab getSelectedTab() {
            return mSelectedTab;
        }

        @Override
        public int getCount() {
            return mItemsS.length;
        }

        @Override
        public Object getItem(int position) {
            return mItemsS[position];
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {        // 클릭된 값이 없을 경우
                v = LayoutInflater.from(GroupInsertContentActivity.this).inflate(R.layout.row_drawer, null);
                v.setOnClickListener(this);
            }

            v.setTag(position);
            Tab tab = (Tab) getItem(position);
            ((TextView) v).setText(tab.toString());

            if (tab == mSelectedTab) {           // 한개의 내용만 tab과 일치하여 if에 들어가고 나머지는 else (클릭된 리스트 내용)
                v.setBackgroundColor(ThemeUtil.colorPrimary(GroupInsertContentActivity.this, 0));
                ((TextView) v).setTextColor(0xFFFFFFFF);
            } else {
                v.setBackgroundResource(0);
                ((TextView) v).setTextColor(0xFF000000);
            }

            return v;
        }

        @Override
        public void onClick(View v) { // 텝이 클릭되었을 때
            int position = (Integer) v.getTag();
            switch (position) {
                case 0:
                    Intent intent = new Intent(getApplicationContext(), MyInfoActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.rightin, R.anim.rightout);
                    break;
                case 1:
                    Dialog.Builder builder = new SimpleDialog.Builder(R.style.SimpleDialog) {

                        @Override
                        protected void onBuildDone(Dialog dialog) {
                            dialog.layoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        }

                        @Override // 취소 or 뒤로가기 누르면 다시 원이 회전하도록 만드셈
                        public void onNegativeActionClicked(DialogFragment fragment) {
                            super.onNegativeActionClicked(fragment);
                        }
                    };

                    ((SimpleDialog.Builder)builder).message("Produce by Ecomo Company").negativeAction("OK");

                    FragmentManager fm = getSupportFragmentManager();
                    DialogFragment diaFM = DialogFragment.newInstance(builder);
                    diaFM.show(fm, null);
                    break;
                case 2:
                    break;
            }
        }
    }


    private static class PagerAdapter extends FragmentStatePagerAdapter {

        Fragment[] mFragments;
        Tab[] mTabs;

        private static final Field sActiveField;

        static {
            Field f = null;
            try {                           // 처음 실행 할 떼
                Class<?> c = Class.forName("android.support.v4.app.FragmentManagerImpl");
                f = c.getDeclaredField("mActive");
                f.setAccessible(true);
                System.out.println("PagerAdapter Class");
            } catch (Exception e) {
            }

            sActiveField = f;
        }


        public PagerAdapter(FragmentManager fm, Tab[] tabs) {   // 각각의 프레그먼트들을 화면에 뿌려줌
            super(fm);
            mTabs = tabs;
            mFragments = new Fragment[mTabs.length];


            //dirty way to get reference of cached fragment
            try {
                ArrayList<Fragment> mActive = (ArrayList<Fragment>) sActiveField.get(fm); // 프레그먼트들을 arraylist에 넣음
                if (mActive != null) {
                    for (Fragment fragment : mActive) {
                        if (fragment instanceof GroupInsertContentFragment)
                            setFragment(Tab.INSERTCONTENT, fragment);

                    }
                }
            } catch (Exception e) {
            }
        }

        private void setFragment(Tab tab, Fragment f) {
            for (int i = 0; i < mTabs.length; i++)
                if (mTabs[i] == tab) {
                    mFragments[i] = f;
                    System.out.println("PagerAdapter setFragment for if");
                    break;
                }
        }

        @Override
        public Fragment getItem(int position) {
            if (mFragments[position] == null) {
                switch (mTabs[position]) {
                    case INSERTCONTENT:
                        mFragments[position] = GroupInsertContentFragment.newInstance();
                        break;
                }
            }
            return mFragments[position];
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabs[position].toString().toUpperCase();
        }

        @Override
        public int getCount() {
            return mFragments.length;
        }
    }
}