package com.eyecool.visitor.sysinner;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.eyecool.visitor.sysinner.adapter.VisitorInfoAdapter;
import com.eyecool.visitor.sysinner.utils.DataBaseUtil;
import com.eyecool.visitor.sysinner.utils.ToastUtil;
import com.eyecool.visitorsys.db.VisitorLog;
import com.eyecool.visitorsys.db.VisitorLogDao;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created date: 2017/6/14
 * Author:  Leslie
 * 这个是来访记录登记表界面
 */

public class VisitorRecordActivity extends Activity {
    private static final String TAG = "VisitorRecordActivity";
    @Bind(R.id.tv_home_page)
    TextView tvHomePage;
    @Bind(R.id.tv_prepage)
    TextView tvPrepage;
    @Bind(R.id.tv_first)
    TextView tvFirst;
    @Bind(R.id.tv_second)
    TextView tvSecond;
    @Bind(R.id.tv_third)
    TextView tvThird;
    @Bind(R.id.tv_fourth)
    TextView tvFourth;
    @Bind(R.id.tv_fifth)
    TextView tvFifth;
    @Bind(R.id.tv_next_page)
    TextView tvNextPage;
    @Bind(R.id.tv_end_page)
    TextView tvEndPage;
    @Bind(R.id.recycleView)
    RecyclerView recycleView;
    //记录的页数
    private int recordPageCount = -1;
    //当前所显示的页
    private int currentVisiablePageNum = 1;
    private List<VisitorLog> visitorInfoList = new ArrayList<>();
    private VisitorLogDao visitorLogTable;
    private int tableInfoCount;
    private VisitorInfoAdapter infoAdapter;

    private AlertDialog dialog;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor_record);
        ButterKnife.bind(this);

        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private void initData() {
        mContext = this;

        //1.给集合添加第 0  个元素
        visitorInfoList.add(new VisitorLog(null
                , "user_id"
                , "家长姓名"
                , "phone"
                , "状态"
                , "学生班级"
                , "登记时间"
                , "match_socre"
                , "match_img"
                , "feature_img"
                , "接访老师"
                , "学生姓名"
                , "card_id"));
        //2.读取 来访记录表，把前10个赋值给 这个集合 visitorInfoList
        visitorLogTable = DataBaseUtil.getVisitorLogTable();
        tableInfoCount = (int) visitorLogTable.count();
        if (tableInfoCount >= 10) {
            for (int i = 0; i < 10; i++) {
                visitorInfoList.add(i + 1, visitorLogTable.loadByRowId(i + 1));
            }
            //把第一页设置为 蓝色
            tvFirst.setTextColor(Color.RED);
            currentVisiablePageNum = 1;
        } else {
            for (int i = 0; i < tableInfoCount; i++) {
                visitorInfoList.add(i + 1, visitorLogTable.loadByRowId(i + 1));

            }
        }

        //3.计算记录的页数，根据页数控制 五个页码控件的显示
        for (int i = 0; i <= tableInfoCount / 10; i++) {
            recordPageCount = i + 1;
            if (tableInfoCount != 0 && tableInfoCount % 10 == 0) {
                recordPageCount--;
            }
        }
        switch (recordPageCount) {
            case 1:
                //只显示 1 控件
                tvSecond.setVisibility(View.GONE);
                tvThird.setVisibility(View.GONE);
                tvFourth.setVisibility(View.GONE);
                tvFifth.setVisibility(View.GONE);
                break;
            case 2:
                //只显示 1 2 控件
                tvThird.setVisibility(View.GONE);
                tvFourth.setVisibility(View.GONE);
                tvFifth.setVisibility(View.GONE);
                break;
            case 3:
                //只显示 1 2 3 控件
                tvFourth.setVisibility(View.GONE);
                tvFifth.setVisibility(View.GONE);
                break;
            case 4:
                //只显示 1 2 3 4 控件
                tvFifth.setVisibility(View.GONE);
                break;
            case 5:
                //不需要隐藏
                break;
        }


        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recycleView.setLayoutManager(gridLayoutManager);                                    //设置布局管理器
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);                          //设置为垂直布局
        infoAdapter = new VisitorInfoAdapter(this, visitorInfoList);
        recycleView.setAdapter(infoAdapter);

        infoAdapter.setItemClickListener(new VisitorInfoAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position == 0) {
                    return;
                }
                //点击 item 的具体操作还没有实现。。。。   需要弹出一个对话框

                showVisitorLogInfoOnDialog(visitorInfoList.get(position));
            }
        });
    }

    private void showVisitorLogInfoOnDialog(VisitorLog visitorLog) {
        dialog = new AlertDialog.Builder(this).create();
        dialog.show();
        Window window = dialog.getWindow();
        //设置背景变灰色
        Window mWindow = this.getWindow();
        window.setBackgroundDrawableResource(android.R.color.transparent);
        WindowManager.LayoutParams lp = mWindow.getAttributes();
        lp.dimAmount = 0f;
        window.setContentView(R.layout.dialog_main);
        ImageView ivScene = (ImageView) window.findViewById(R.id.iv_scene);
        ImageView ivIdcard = (ImageView) window.findViewById(R.id.iv_idcard);
        ivScene.setImageBitmap(BitmapFactory.decodeFile(visitorLog.getMatch_img()));
        ivIdcard.setImageBitmap(BitmapFactory.decodeFile(visitorLog.getFeature_img()));
        Button bt_dialog_close = (Button) window.findViewById(R.id.bt_dialog_close);
        bt_dialog_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    @OnClick({R.id.bt_visitorRecord_back, R.id.tv_home_page, R.id.tv_prepage, R.id.tv_first
            , R.id.tv_second, R.id.tv_third, R.id.tv_fourth, R.id.tv_fifth, R.id.tv_next_page
            , R.id.tv_end_page,R.id.iv_clear_record})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_visitorRecord_back:
                finish();
                break;
            case R.id.tv_home_page:
                currentVisiablePageNum = 1;
                reFillInfo(currentVisiablePageNum);
                break;
            case R.id.tv_prepage:
                if (currentVisiablePageNum == 1) {
                    ToastUtil.showToast(this, "到头了！");
                    return;
                }
                currentVisiablePageNum--;

                //把数据库表中第 currentVisiablePageNum 个 10个记录 保存到集合中
                reFillInfo(currentVisiablePageNum);
                break;
            case R.id.tv_first:

                break;
            case R.id.tv_second:
                break;
            case R.id.tv_third:
                break;
            case R.id.tv_fourth:
                break;
            case R.id.tv_fifth:
                break;
            case R.id.tv_next_page:
                currentVisiablePageNum++;
                if (currentVisiablePageNum > recordPageCount) {
                    ToastUtil.showToast(this, "到头了");
                    return;
                }
                reFillInfo(currentVisiablePageNum);
                break;
            case R.id.tv_end_page:
                currentVisiablePageNum = recordPageCount;
                reFillInfo(currentVisiablePageNum);
                break;
            case R.id.iv_clear_record:
                final ArrayList<String> choices = new ArrayList<>();
                final String[] choicesStr = new String[]{"访客记录","模拟库"};
                AlertDialog dialog = new AlertDialog.Builder(mContext)
                        .setTitle("清除记录")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                for (int i = 0; i < choices.size(); i++) {
                                    if (choices.get(i).equals("0")) {
                                        DataBaseUtil.session.getVisitorLogDao().deleteAll();
                                        visitorInfoList.clear();
                                        initData();
                                    } else {
                                        DataBaseUtil.session.getCustomerInfoDao().deleteAll();
                                    }
                                }
                            }
                        })
                        .setMultiChoiceItems(choicesStr, null, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                //添加和移除要用非int型数据，要不然，调用 remove的时候会误认为是 index ，从而有可能引起 索引越界异常
                                if (isChecked) {
                                    choices.add(which + "");
                                } else {
                                    choices.remove(which + "");
                                }
                                for (String choice : choices) {
                                    Log.d(TAG, "onClick: -----------------当前选择了：" + choice);
                                }
                            }
                        }).create();
                dialog.show();

                break;
        }
    }

    /**
     * 重新往集合里面填充数据，并且刷新表格,注意序号也会改变
     *
     * @param currentVisiablePageNum
     */
    private void reFillInfo(int currentVisiablePageNum) {
        Log.d(TAG, "reFillInfo: ------------------currentVisiablePageNum =" + currentVisiablePageNum);
        int j = 1;
        if (tableInfoCount >= 10 * currentVisiablePageNum) {
            for (int i = 10 * (currentVisiablePageNum - 1); i < 10 * currentVisiablePageNum; i++) {
                visitorInfoList.add(j, visitorLogTable.loadByRowId(i + 1));

                if (visitorInfoList.size() > 11) {
                    visitorInfoList.remove(11);

                }
                j++;
            }

        } else {
            visitorInfoList.clear();
            for (int i = 0; i < 11; i++) {
            }
            visitorInfoList.add(new VisitorLog(null
                    , "user_id"
                    , "家长姓名"
                    , "phone"
                    , "状态"
                    , "学生班级"
                    , "登记时间"
                    , "match_socre"
                    , "match_img"
                    , "feature_img"
                    , "接访老师"
                    , "学生姓名"
                    , "card_id"));
            for (int i = 10 * (currentVisiablePageNum - 1); i < tableInfoCount; i++) {
                    Log.d(TAG, "reFillInfo: --------------i = " + i);
                    Log.d(TAG, "reFillInfo: --------------tableInfoCount = " + tableInfoCount);
                    visitorInfoList.add(j, visitorLogTable.loadByRowId(i + 1));
                    Log.d(TAG, "reFillInfo: ----------------------重新添加元素：" + j);
                    j++;
            }
        infoAdapter = new VisitorInfoAdapter(this, visitorInfoList);

        recycleView.setAdapter(infoAdapter);
            infoAdapter.setItemClickListener(new VisitorInfoAdapter.onItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    if (position == 0) {
                        return;
                    }
                    //点击 item 的具体操作还没有实现。。。。   需要弹出一个对话框

                    showVisitorLogInfoOnDialog(visitorInfoList.get(position));
                }
            });

        }
        //如果 currentVisiablePageNum <= 5 时
        if (currentVisiablePageNum <= 5) {

        } else {
            int diff = currentVisiablePageNum - 5;
            tvFirst.setText(1 + diff + "");
            tvSecond.setText(2 + diff + "");
            tvThird.setText(3 + diff + "");
            tvFourth.setText(4 + diff + "");
            tvFifth.setText(5 + diff + "");
        }
        //如果 currentVisiablePageNum <= 5 时
        switch (currentVisiablePageNum) {
            case 1:
                //把第一页设置为 红色
                tvFirst.setTextColor(Color.RED);
                tvSecond.setTextColor(Color.BLACK);
                tvThird.setTextColor(Color.BLACK);
                tvFourth.setTextColor(Color.BLACK);
                tvFifth.setTextColor(Color.BLACK);
                break;
            case 2:
                tvFirst.setTextColor(Color.BLACK);
                tvSecond.setTextColor(Color.RED);
                tvThird.setTextColor(Color.BLACK);
                tvFourth.setTextColor(Color.BLACK);
                tvFifth.setTextColor(Color.BLACK);
                break;
            case 3:
                Log.d(TAG, "reFillInfo: ---------------------3!");
                tvFirst.setTextColor(Color.BLACK);
                tvSecond.setTextColor(Color.BLACK);
                tvThird.setTextColor(Color.RED);
                tvFourth.setTextColor(Color.BLACK);
                tvFifth.setTextColor(Color.BLACK);
                break;
            case 4:
                tvFirst.setTextColor(Color.BLACK);
                tvSecond.setTextColor(Color.BLACK);
                tvThird.setTextColor(Color.BLACK);
                tvFourth.setTextColor(Color.RED);
                tvFifth.setTextColor(Color.BLACK);
                break;
            case 5:
                tvFirst.setTextColor(Color.BLACK);
                tvSecond.setTextColor(Color.BLACK);
                tvThird.setTextColor(Color.BLACK);
                tvFourth.setTextColor(Color.BLACK);
                tvFifth.setTextColor(Color.RED);
                break;
        }
        Log.d(TAG, "reFillInfo: --------------------集合长度：" + visitorInfoList.size());
        infoAdapter.notifyDataSetChanged();


        for (VisitorLog visitorLog : visitorInfoList) {
            Log.d(TAG, "reFillInfo: ------------------item = " + visitorLog.getId());
        }
/*
        infoAdapter = new VisitorInfoAdapter(this, visitorInfoList);

        recycleView.setAdapter(infoAdapter);
        infoAdapter.notifyItemRangeChanged(1,10);*/

    }
}
