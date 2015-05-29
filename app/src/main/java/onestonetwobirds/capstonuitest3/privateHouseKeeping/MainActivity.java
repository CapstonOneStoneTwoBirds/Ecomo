package onestonetwobirds.capstonuitest3.privateHouseKeeping;

import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.SimpleDialog;
import com.rey.material.app.ToolbarManager;
import com.rey.material.util.ThemeUtil;

import com.rey.material.widget.FloatingActionButton;
import com.rey.material.widget.SnackBar;
import com.rey.material.widget.TabPageIndicator;

import java.lang.reflect.Field;
import java.util.ArrayList;

import onestonetwobirds.capstonuitest3.R;
import onestonetwobirds.capstonuitest3.groupHouseKeeping.GroupActivity;

public class MainActivity extends ActionBarActivity implements ToolbarManager.OnToolbarGroupChangedListener {

    // 기터브 커밋 ㅎㅎ

    private DrawerLayout dl_navigator;      // 우측에 리스트 화면 뜨는 거
    private FrameLayout fl_drawer;          // 프레임 레이아웃
    private ListView lv_drawer;
    private CustomViewPager vp;             // 뷰 페이저
    private TabPageIndicator tpi;           // 뷰 페이저 사용 시 프레그먼트 위의 탭 버튼(프레그먼트 화면 이동 버튼)

    private DrawerAdapter mDrawerAdapter;   // 사용자가 정의한 클래스, 아래에 정의 있음
    private PagerAdapter mPagerAdapter;     // 사용자가 정의한 클래스, 아래에 정의 있음

    private Toolbar mToolbar;               // 화면 상단의 액션바
    private ToolbarManager mToolbarManager;
    private SnackBar mSnackBar;

    private Tab[] mItems = new Tab[]{Tab.CURRENTCONDITION, Tab.CALENDAR, Tab.WIDGET};
    private Tab[] mItemsS = new Tab[]{Tab.PRIVATEINFO, Tab.MANUFACTURERS, Tab.LOGOUT};

    final private static int DIALOG_INSERT = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        dl_navigator = (DrawerLayout) findViewById(R.id.main_dl);
        fl_drawer = (FrameLayout) findViewById(R.id.main_fl_drawer);
        lv_drawer = (ListView) findViewById(R.id.main_lv_drawer);
        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        vp = (CustomViewPager) findViewById(R.id.main_vp);
        tpi = (TabPageIndicator) findViewById(R.id.main_tpi);
        mSnackBar = (SnackBar) findViewById(R.id.main_sn);

        FloatingActionButton InsertBtn = (FloatingActionButton)findViewById(R.id.insert_btn);

        InsertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v instanceof FloatingActionButton) {
                    FloatingActionButton bt = (FloatingActionButton) v;
                    bt.setLineMorphingState((bt.getLineMorphingState() + 1) % 2, true);
                }

                GoDialog(DIALOG_INSERT);

            }
        });


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

        mDrawerAdapter = new DrawerAdapter();
        lv_drawer.setAdapter(mDrawerAdapter);                       // DrawerLayout 보여줘

        mPagerAdapter = new PagerAdapter(getSupportFragmentManager(), mItems);
        vp.setAdapter(mPagerAdapter);
        tpi.setViewPager(vp);                                       // 뷰 페이저 보여줘
        tpi.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                mDrawerAdapter.setSelected(mItemsS[position]);
                mSnackBar.dismiss();
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }

        });

        mDrawerAdapter.setSelected(Tab.PRIVATEINFO);           // 디폴트 값 '개인정보수정' 설정
        vp.setCurrentItem(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mToolbarManager.createMenu(R.menu.menu_main_private);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mToolbarManager.onPrepareMenu();
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {       // 툴바 아이콘 눌렀을 때 이벤트
        switch (item.getItemId()) {
            case R.id.tb_group:
                Intent intent = new Intent(getApplicationContext(), GroupActivity.class);
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

        PRIVATEINFO("개인 정보 수정"),
        MANUFACTURERS("만든 이"),
        LOGOUT("로그아웃"),
        CURRENTCONDITION("지출 현황"),
        CALENDAR("Calendar"),
        WIDGET("Widget");

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
                v = LayoutInflater.from(MainActivity.this).inflate(R.layout.row_drawer, null);
                v.setOnClickListener(this);
            }

            v.setTag(position);
            Tab tab = (Tab) getItem(position);
            ((TextView) v).setText(tab.toString());

            if (tab == mSelectedTab) {           // 한개의 내용만 tab과 일치하여 if에 들어가고 나머지는 else (클릭된 리스트 내용)
                v.setBackgroundColor(ThemeUtil.colorPrimary(MainActivity.this, 0));
                ((TextView) v).setTextColor(0xFFFFFFFF);
            } else {
                v.setBackgroundResource(0);
                ((TextView) v).setTextColor(0xFF000000);
            }

            return v;
        }

        @Override
        public void onClick(View v) {
            int position = (Integer) v.getTag();
            vp.setCurrentItem(position);
            dl_navigator.closeDrawer(fl_drawer);
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
                        if (fragment instanceof CurrentConditionFragment)
                            setFragment(Tab.CURRENTCONDITION, fragment);
                        else if (fragment instanceof CalendarFragment)
                            setFragment(Tab.CALENDAR, fragment);
                        else if (fragment instanceof WidgetFragment)
                            setFragment(Tab.WIDGET, fragment);
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
                    case CURRENTCONDITION:
                        mFragments[position] = CurrentConditionFragment.newInstance();
                        break;
                    case CALENDAR:
                        mFragments[position] = CalendarFragment.newInstance();
                        break;
                    case WIDGET:
                        mFragments[position] = WidgetFragment.newInstance();
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

    protected void GoDialog(int id) {

        Dialog.Builder builder = null;
        switch (id) {
            case DIALOG_INSERT:
                builder = new SimpleDialog.Builder(R.style.SimpleDialogLight) {

                    @Override
                    protected void onBuildDone(Dialog dialog) {
                        dialog.layoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        PrepareDialog(DIALOG_INSERT, dialog);
                    }

                    @Override // 취소 or 뒤로가기 누르면 다시 원이 회전하도록 만드셈
                    public void onNegativeActionClicked(DialogFragment fragment) {
                        Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                        super.onNegativeActionClicked(fragment);
                    }
                };

                builder.title("어떤 방식으로 입력하시겠습니까?").negativeAction("취소")
                        .contentView(R.layout.insert_dialog);

                FragmentManager fm = getSupportFragmentManager();
                DialogFragment diaFM = DialogFragment.newInstance(builder);
                diaFM.show(fm, null);
                break;
        }

    }

    protected void PrepareDialog(int id, Dialog dialog) {
        switch (id) {
            case DIALOG_INSERT:
                final Dialog dialogD = (Dialog) dialog;
                ImageView InsertOCRBtn = (ImageView) dialogD.findViewById(R.id.insert_ocr_btn);
                ImageView InsertSpeechBtn = (ImageView) dialogD.findViewById(R.id.insert_speech_btn);
                ImageView InsertHandBtn = (ImageView) dialogD.findViewById(R.id.insert_hand_btn);

                InsertOCRBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "OCR", Toast.LENGTH_SHORT).show();
                    }
                });
                InsertSpeechBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "Speech", Toast.LENGTH_SHORT).show();
                    }
                });
                InsertHandBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "Hand", Toast.LENGTH_SHORT).show();
                    }
                });

                break;
        }
    }

}
