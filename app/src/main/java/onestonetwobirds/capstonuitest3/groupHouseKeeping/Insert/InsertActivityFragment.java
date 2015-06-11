package onestonetwobirds.capstonuitest3.groupHouseKeeping.Insert;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.rey.material.app.DatePickerDialog;
import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;
import com.rey.material.widget.Button;
import com.rey.material.widget.SnackBar;

import org.apache.http.Header;

import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import onestonetwobirds.capstonuitest3.R;
import onestonetwobirds.capstonuitest3.httpClient.HttpClient;
import onestonetwobirds.capstonuitest3.privateHouseKeeping.Widget.WidgetFragment;

/**
 * Created by YeomJi on 15. 6. 2..
 */
public class InsertActivityFragment extends Fragment implements View.OnClickListener {

    String year, month, date, AMPM;
    String[] resultDateTime;

    Button InsertBtnDay, InsertOK, InsertCancel;
    TextView IsertTextDay;
    EditText InsertTitle, InsertMoney, InsertContent;

    SnackBar mSnackBar;

    Bundle bundle;

    private final String tag = "InsertActivity";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.group_insert_main, container, false);

        InsertBtnDay = (Button) v.findViewById(R.id.group_insert_btn_day);
        IsertTextDay = (TextView) v.findViewById(R.id.group_insert_text_day);
        InsertTitle = (EditText) v.findViewById(R.id.group_insert_title);
        InsertMoney = (EditText) v.findViewById(R.id.group_insert_money);
        InsertContent = (EditText) v.findViewById(R.id.group_insert_content);
        InsertOK = (Button) v.findViewById(R.id.group_insert_OK);
        InsertCancel = (Button) v.findViewById(R.id.group_insert_Cancel);

        Intent intent = getActivity().getIntent();
        bundle = intent.getExtras();

        InsertBtnDay.setOnClickListener(new View.OnClickListener() {
            Dialog.Builder builder = null;

            @Override
            public void onClick(View v) {
                builder = new DatePickerDialog.Builder() {
                    @Override
                    public void onPositiveActionClicked(DialogFragment fragment) {
                        DatePickerDialog dialog = (DatePickerDialog) fragment.getDialog();
                        String date = dialog.getFormattedDate(SimpleDateFormat.getDateInstance());
                        IsertTextDay.setText(date);
                        makeDate(date);
                        super.onPositiveActionClicked(fragment);
                    }

                    @Override
                    public void onNegativeActionClicked(DialogFragment fragment) {
                        super.onNegativeActionClicked(fragment);
                    }
                };
                builder.positiveAction("OK")
                        .negativeAction("CANCEL");

                DialogFragment fragment = DialogFragment.newInstance(builder);
                fragment.show(getFragmentManager(), null);
            }
        });
        InsertOK.setOnClickListener(this);
        InsertCancel.setOnClickListener(this);

        if (!(bundle == null)) {
            if (bundle.containsKey("year")) year = bundle.getString("year");
            else year = resultDateTime[0];
            if (bundle.containsKey("month")) month = bundle.getString("month");
            else month = resultDateTime[1];
            if (bundle.containsKey("date")) date = bundle.getString("date");
            else date = resultDateTime[2];
            if (bundle.containsKey("account")) InsertTitle.setText(bundle.getString("account"));
            if (bundle.containsKey("money")) InsertMoney.setText(bundle.getString("money"));

            if (bundle.containsKey("content")) InsertContent.setText(bundle.getString("content"));
        }

        if (year != null) {
            String setDate = year + ". " + month + ". " + date + ". ";
            IsertTextDay.setText(setDate);
        }

        mSnackBar = ((InsertActivity) getActivity()).getSnackBar();

        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onClick(final View v) {

        switch (v.getId()) {
            case R.id.group_insert_OK:

                if (InsertTitle.getText().toString().equals("")) // 누르면 이상한거 트는 문제 해결하셈
                    mSnackBar.applyStyle(R.style.SnackBarSingleLine)
                            .text("지출 물건을 입력해주세요.")
                            .duration(2000).show();
                else if (InsertMoney.getText().toString().equals(""))
                    mSnackBar.applyStyle(R.style.SnackBarSingleLine)
                            .text("금액을 입력해주세요.")
                            .duration(2000).show();
                else if (Integer.valueOf(InsertMoney.getText().toString()) <= 0)
                    mSnackBar.applyStyle(R.style.SnackBarSingleLine)
                            .text("잘못된 금액을 입력하셨습니다.")
                            .duration(2000).show();
                else if (year == null)
                    mSnackBar.applyStyle(R.style.SnackBarSingleLine)
                            .text("날짜를 입력해주세요.")
                            .duration(2000).show();
                else { // 가계 내역 입력 갱신
                    SharedPreferences mPreference = v.getContext().getSharedPreferences("myInfo", v.getContext().MODE_PRIVATE);

                    RequestParams param = new RequestParams();
                    param.put("groupid", mPreference.getString("group_id",""));
                    param.put("writer", mPreference.getString("email",""));
                    param.put("title", InsertTitle.getText().toString());
                    param.put("content", InsertContent.getText().toString());
                    param.put("price", InsertMoney.getText().toString());
                    param.put("year", year);
                    param.put("month", month);
                    param.put("day", date);
                    HttpClient.post("writeArticle/", param, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            if( new String(responseBody).equals("2")){
                                Log.e(tag, "New Article Success");
                                getActivity().finish();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            Log.e(tag, "New Article onFailure");
                        }
                    });

                    System.out.println("TestOK : " + year + " / " + month + " / " + date +
                            " / " + InsertTitle.getText().toString() + " / " + InsertMoney.getText().toString() +
                            " / " + InsertContent.getText().toString());

                }
                break;

            case R.id.group_insert_Cancel:
                //if(fragment != null) fragment.dismiss();
                getActivity().finish();
                break;
        }

/*
        DialogFragment fragment = DialogFragment.newInstance(builder);
        fragment.show(getFragmentManager(), null);
*/


    }


    // ex : 2015. 6. 18
    void makeDate(String Ddate) {
        String token;
        int position = 0;
        StringTokenizer st = new StringTokenizer(Ddate);
        while (st.hasMoreTokens()) {

            token = st.nextToken();
            if (token.endsWith(".")) token = token.replace(".", "");
            switch (position) {
                case 0:
                    year = token;
                    break;
                case 1:
                    month = token;
                    break;
                case 2:
                    date = token;
                    break;
                default:
                    break;
            }
            position++;
        }
    }

    public void getContent(String tt, int gg, int cc) {
        WidgetFragment.t = tt;
        WidgetFragment.g = gg;
        WidgetFragment.c = cc;

    }

}