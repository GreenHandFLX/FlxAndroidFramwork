package com.xinxi.ergatebesh;
import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.xinxi.ergatebesh.common.Base.MyAdapter;
import com.xinxi.ergatebesh.common.Base.Utility;
import com.xinxi.ergatebesh.common.MultiLanguage.MultiLanguageUtil;
import com.xinxi.ergatebesh.common.Base.BaseActivity;
import com.xinxi.ergatebesh.common.OkHttp.ApiAddressCommon;
import com.xinxi.ergatebesh.common.OkHttp.RequestUtils;
import com.xinxi.ergatebesh.common.ShareUtils;
import com.xinxi.ergatebesh.common.VideoPlayer.VideoPlayerActivityDemoActivity;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    private int savedLanguageType;
    private Button selectMul;
    private Button testokHttp;
    private Button sharePhoto;
    private Button downloadAndSaveImg1;
    private App mApp;
    private Button testVideoplayer;
    private SimpleDraweeView imgSplash;
    private ListView testlistview;
    private Button testphotoPermission;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mApp = (App) getApplication();
        savedMul();
        selectMul = findViewById(R.id.selectMul);
        testokHttp = findViewById(R.id.testokHttp);
        testVideoplayer = findViewById(R.id.testVideoplayer);

        sharePhoto = findViewById(R.id.sharePhoto);

        String imagPath = "/storage/emulated/0/Pictures/WeiXin/111.jpg";
        sharePhoto.setOnClickListener(v -> {
            ShareUtils.shareMsg("????????????","??????" + getResources().getString(R.string.app_name) + "?????????","??????????????????",imagPath,this);
        });

        selectMul.setOnClickListener(v -> {
            // ????????????
            MultiLanguageUtil.getInstance().updateLanguage(1);
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        testokHttp.setOnClickListener(v -> {
            Bundle data = new Bundle();
            data.putString("username", "mobileAdmin");
            data.putString("password", "Abc123456");
            data.putString("code", "mobile");
            new Thread(() -> {
                try {
                    // ??????????????????????????????
                    mApp.setI18n("zh_CN");
                    RequestUtils.i18n = mApp.getI18n();
                    String response = RequestUtils.requestAPI(ApiAddressCommon.api_login, data);
                    Log.i(TAG, "login ????????????: " + response);
                    Message msg = Message.obtain();
                    msg.what = 1;
                    Bundle msgData = new Bundle();
                    msgData.putString("responseBody", response);
                    msg.setData(msgData);
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        });


        testVideoplayer.setOnClickListener(v -> {
            Intent intent=new Intent(MainActivity.this, VideoPlayerActivityDemoActivity.class);
            startActivity(intent);
        });



        imgSplash = findViewById(R.id.img_splash);
        imgSplash.setImageURI("https://pic1.zhimg.com/v2-9639852750175df1b80ed995729e64e8.jpg");

        LineChart mLineChart = (LineChart) findViewById(R.id.lineChart);
        //????????????
        mLineChart.setDrawBorders(true);
        //????????????
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            entries.add(new Entry(i, (float) (Math.random()) * 80));
        }
        //??????LineDataSet???????????????
        LineDataSet lineDataSet = new LineDataSet(entries, "??????");
        LineData data = new LineData(lineDataSet);
        mLineChart.setData(data);





        // ?????????????????????new???????????????????????????????????????????????????
        ArrayList<ArrayList> testList = new ArrayList<>();
        ArrayList  testList1 = new ArrayList();
        ArrayList testList2 = new ArrayList();
        ArrayList  testList3 = new ArrayList();
        testList1.add("111");
        testList1.add("222");
        testList1.add(R.mipmap.btn_video_on);

        testList2.add("444");
        testList2.add("555");
        testList2.add(R.mipmap.img_play);

        testList3.add("777");
        testList3.add("888");
        testList3.add(R.mipmap.img_share);


        testList.add(testList1);
        testList.add(testList2);
        testList.add(testList3);

        testlistview = findViewById(R.id.testlistview);

        MyAdapter  myAdapter = new MyAdapter(testList, R.layout.activity_main_item) {
            @Override
            public void bindView(ViewHolder holder, Object obj) {
                ArrayList arrayList = (ArrayList)obj;
                ((TextView) holder.getView(R.id.testMyadapter)).setText(arrayList.get(0).toString());
                ((TextView) holder.getView(R.id.testMyadapter2)).setText(arrayList.get(1).toString());
                ((ImageView) holder.getView(R.id.testMyadapter3)).setImageResource(Integer.parseInt(arrayList.get(2).toString()));
            }
        };
        testlistview.setAdapter(myAdapter);
        // ???ScrollView ?????? ListView?????????????????????
        Utility.setListViewHeightBasedOnChildren(testlistview);
        // ????????????
        //myAdapter.remove(2);

        testphotoPermission = findViewById(R.id.testphotoPermission);
        testphotoPermission.setOnClickListener(v -> {
            // Android???????????????1 ?????????????????????requestCode
             int PERMISSION_CAMERA_REQUEST_CODE = 0x00000012;
             requestPermission(new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_CAMERA_REQUEST_CODE);
        });

    }

    public void savedMul(){
        // ??????????????????
        savedLanguageType = MultiLanguageUtil.getInstance().getLanguageType();
        System.out.println(savedLanguageType);
    }

    Handler handler = new Handler((msg) -> {
        try {
            Bundle data = msg.getData();
            if (msg.what == 1) {
                // code==200, ????????????????????????
                JSONObject responseBodyJson = new JSONObject(data.getString("responseBody"));
                String code = responseBodyJson.getString("code");
                if ("200".equals(code)) {
                    new MaterialDialog.Builder(this)
                            .iconRes(R.mipmap.icon_edit)
                            .title("??????XUI??????+OKHttp??????---OK")
                            .content("??????XUI??????+OKHttp??????")
                            .positiveText("??????")
                            .show();
                } else {
                //code == 400 500
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return true;
    });


    //   ??????webview
    //   https://blog.csdn.net/niubitianping/article/details/70919385


    // ??????????????????
    //https://www.jianshu.com/p/bcfe030b77db

}