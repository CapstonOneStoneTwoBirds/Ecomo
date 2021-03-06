package onestonetwobirds.capstonuitest3.groupHouseKeeping.Main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.SimpleDialog;
import com.rey.material.app.ToolbarManager;
import com.rey.material.util.ThemeUtil;

import com.rey.material.widget.FloatingActionButton;
import com.rey.material.widget.SnackBar;
import com.rey.material.widget.TabPageIndicator;

import org.apache.http.Header;

import java.lang.reflect.Field;
import java.util.ArrayList;

import onestonetwobirds.capstonuitest3.R;
import onestonetwobirds.capstonuitest3.groupHouseKeeping.Announce.GroupAnnounceFragment;
import onestonetwobirds.capstonuitest3.groupHouseKeeping.Calendar.GroupCalendarFragment;
import onestonetwobirds.capstonuitest3.groupHouseKeeping.Calendar.GroupInsertContentActivity;
import onestonetwobirds.capstonuitest3.groupHouseKeeping.Member.GroupMemberFragment;
import onestonetwobirds.capstonuitest3.httpClient.HttpClient;
import onestonetwobirds.capstonuitest3.groupHouseKeeping.Insert.InsertActivity;
import onestonetwobirds.capstonuitest3.privateHouseKeeping.Main.CustomViewPager;
import onestonetwobirds.capstonuitest3.privateHouseKeeping.Main.PrivateMainActivity;
import onestonetwobirds.capstonuitest3.user.MyInfoActivity;
import onestonetwobirds.capstonuitest3.user.StartActivity;

public class InGroupActivity extends ActionBarActivity implements ToolbarManager.OnToolbarGroupChangedListener {

    // 기터브 커밋 ㅎㅎ
    private String tag = "InGroupActivity";
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

    private Tab[] mItems = new Tab[]{Tab.ANNOUNCE, Tab.CALENDAR, Tab.MEMBER};
    private Tab[] mItemsS = new Tab[]{Tab.PRIVATEINFO, Tab.MANUFACTURERS, Tab.GROUPOUT, Tab.LOGOUT};

    final private static int DIALOG_INSERT = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.in_group_main);

        String group_id = getIntent().getStringExtra("group_id");
        Log.e(tag, "group_id : " + group_id);

        SharedPreferences mPreference = getSharedPreferences("myInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreference.edit();
        editor.putString("group_id", group_id);
        editor.commit();

        dl_navigator = (DrawerLayout) findViewById(R.id.main_dl);
        fl_drawer = (FrameLayout) findViewById(R.id.main_fl_drawer);
        lv_drawer = (ListView) findViewById(R.id.main_lv_drawer);
        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        vp = (CustomViewPager) findViewById(R.id.main_vp);
        tpi = (TabPageIndicator) findViewById(R.id.main_tpi);
        mSnackBar = (SnackBar) findViewById(R.id.main_sn);

        FloatingActionButton InsertBtn = (FloatingActionButton) findViewById(R.id.insert_btn);

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
                mDrawerAdapter.setSelected(mItems[position]);
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
        mToolbarManager.createMenu(R.menu.menu_main_group);
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
            case R.id.tb_private:
                Intent intent = new Intent(getApplicationContext(), PrivateMainActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.fade, R.anim.hold);
                break;
            case R.id.tb_new:                                 // 새로 고침
                //mToolbarManager.setCurrentGroup(0);
                startActivity(getIntent());
                finish();
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

        PRIVATEINFO("개인 정보"),
        MANUFACTURERS("만든 이"),
        GROUPOUT("그룹 탈퇴"),
        LOGOUT("로그아웃"),
        ANNOUNCE("ANNOUNCE"),
        CALENDAR("CALENDAR"),
        MEMBER("MEMBER");


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

        public void setSelected(Tab tab2) {                  // 탭 or 우측의 리스트가 눌린 위치로 값을 설정한다.
            if (tab2 != mSelectedTab) {
                mSelectedTab = tab2;
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
                v = LayoutInflater.from(InGroupActivity.this).inflate(R.layout.row_drawer, null);
                v.setOnClickListener(this);
            }

            v.setTag(position);

            Tab tab = (Tab) getItem(position);

            ((TextView) v).setText(tab.toString());

            if (tab == mSelectedTab) {           // 한개의 내용만 tab과 일치하여 if에 들어가고 나머지는 else (클릭된 리스트 내용)
                v.setBackgroundColor(ThemeUtil.colorPrimary(InGroupActivity.this, 0));
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

                    ((SimpleDialog.Builder) builder).message("Produce by Ecomo Company").negativeAction("OK");

                    FragmentManager fm = getSupportFragmentManager();
                    DialogFragment diaFM = DialogFragment.newInstance(builder);
                    diaFM.show(fm, null);
                    break;
                case 2: // 그룹 탈퇴
                    Log.e("InGroupActivirty", "LOGOUT");
                    Dialog.Builder builder2 = new SimpleDialog.Builder(R.style.SimpleDialog) {

                        @Override
                        protected void onBuildDone(Dialog dialog) {
                            dialog.layoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        }

                        @Override
                        public void onPositiveActionClicked(DialogFragment fragment) { // OK 버튼 눌렀을 때 액션 취하기(추가된 데이터 리스트에 띄우기)
                            SharedPreferences mPreference = getSharedPreferences("myInfo", MODE_PRIVATE);
                            String email = mPreference.getString("email", "");
                            String group_id = getIntent().getStringExtra("group_id");
                            RequestParams param = new RequestParams();
                            param.put("email", email);
                            param.put("group_id", group_id);
                            HttpClient.post("leaveGroup/", param, new AsyncHttpResponseHandler() {
                                @Override
                                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                    String code = new String(responseBody);
                                    switch (code) {
                                        case "success":
                                            Toast toastView = Toast.makeText(getApplicationContext(),
                                                    "Success", Toast.LENGTH_LONG);
                                            toastView.setGravity(Gravity.CENTER, 40, 25);
                                            toastView.show();
                                            Intent intent = new Intent(getApplicationContext(), GroupMainActivity.class);
                                            startActivity(intent);
                                            break;
                                        case "impossible":
                                            toastView = Toast.makeText(getApplicationContext(),
                                                    "Owner can't get out of group", Toast.LENGTH_LONG);
                                            toastView.setGravity(Gravity.CENTER, 40, 25);
                                            toastView.show();
                                            break;
                                    }
                                }

                                @Override
                                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                                }
                            });
                            onResume();
                            super.onPositiveActionClicked(fragment);
                        }

                        @Override
                        public void onNegativeActionClicked(DialogFragment fragment) {
                            super.onNegativeActionClicked(fragment);
                        }
                    };

                    builder2.title("그룹 탈퇴")
                            .positiveAction("OK")
                            .negativeAction("CANCEL")
                            .contentView(R.layout.leave_member_dialog);

                    FragmentManager fm1 = getSupportFragmentManager();
                    DialogFragment diaFM1 = DialogFragment.newInstance(builder2);
                    diaFM1.show(fm1, null);

                    break;
                case 3: // 로그아웃
                    Log.e("InGroupActivirty", "LOGOUT");
                    Dialog.Builder builder1 = new SimpleDialog.Builder(R.style.SimpleDialog) {

                        @Override
                        protected void onBuildDone(Dialog dialog) {
                            dialog.layoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        }

                        @Override
                        public void onPositiveActionClicked(DialogFragment fragment) { // OK 버튼 눌렀을 때 액션 취하기(추가된 데이터 리스트에 띄우기)
                            SharedPreferences mPreference = getSharedPreferences("myInfo", MODE_PRIVATE);
                            SharedPreferences.Editor editor = mPreference.edit();
                            editor.clear();
                            editor.commit();
                            startActivity(new Intent(getApplicationContext(), StartActivity.class));
                            // 여기에다 코딩

                            onResume();
                            super.onPositiveActionClicked(fragment);
                        }

                        @Override
                        public void onNegativeActionClicked(DialogFragment fragment) {
                            super.onNegativeActionClicked(fragment);
                        }
                    };

                    builder1.title("로그아웃")
                            .positiveAction("OK")
                            .negativeAction("CANCEL")
                            .contentView(R.layout.logout_dialog);

                    FragmentManager fm2 = getSupportFragmentManager();
                    DialogFragment diaFM2 = DialogFragment.newInstance(builder1);
                    diaFM2.show(fm2, null);
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
                        if (fragment instanceof GroupAnnounceFragment)
                            setFragment(Tab.ANNOUNCE, fragment);
                        else if (fragment instanceof GroupCalendarFragment)
                            setFragment(Tab.CALENDAR, fragment);
                        else if (fragment instanceof GroupMemberFragment)
                            setFragment(Tab.MEMBER, fragment);

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
                    case ANNOUNCE:
                        mFragments[position] = GroupAnnounceFragment.newInstance();
                        break;
                    case CALENDAR:
                        mFragments[position] = GroupCalendarFragment.newInstance();
                        break;
                    case MEMBER:
                        mFragments[position] = GroupMemberFragment.newInstance();
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

        Intent intent = new Intent(getApplicationContext(), InsertActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade, R.anim.hold);

    }

}
