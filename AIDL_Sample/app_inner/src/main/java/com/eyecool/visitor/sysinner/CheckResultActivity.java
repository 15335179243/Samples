package com.eyecool.visitor.sysinner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextClock;
import android.widget.TextView;

import com.eyecool.visitor.sys.bean.CustomerInfo;
import com.eyecool.visitor.sys.bean.IDCardInfo;
import com.eyecool.visitor.sysinner.interfaces.Constants;
import com.eyecool.visitor.sysinner.utils.DataBaseUtil;
import com.eyecool.visitor.sysinner.utils.PrinterUtil;
import com.eyecool.visitor.sysinner.utils.TimeUtil;
import com.eyecool.visitor.sysinner.utils.ToastUtil;
import com.eyecool.visitorsys.db.VisitorLog;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.eyecool.visitor.sysinner.interfaces.Constants.USER_TYPE_BLACKLIST;
import static com.eyecool.visitor.sysinner.interfaces.Constants.USER_TYPE_REFUSE;
import static com.eyecool.visitor.sysinner.utils.DataBaseUtil.session;


/**
 * Created date: 2017/6/15
 * Author:  Leslie
 * 比对结果页面,共有6中情况。
 * 1.访客登记成功
 * 2.访客未预约
 * 3.访客被教师拒绝
 * 4.访客在黑名单中
 * 5.学生认证...目前没有实现
 * 6.教工认证...目前没有实现
 * 7.从未登记
 */

public class CheckResultActivity extends Activity {
    private static final String TAG = "CheckResultActivity";
    @Bind(R.id.tv_result_state)
    TextView tvResultState;
    @Bind(R.id.tv_clock)
    TextClock tvClock;
    @Bind(R.id.iv_scene_img)
    ImageView ivSceneImg;
    @Bind(R.id.tv_scene_photo_descripiton)
    TextView tvScenePhotoDescripiton;
    @Bind(R.id.fl_img1_container)
    FrameLayout flImg1Container;
    @Bind(R.id.iv_register_img)
    ImageView ivRegisterImg;
    @Bind(R.id.tv_register_photo_descripiton)
    TextView tvRegisterPhotoDescripiton;
    @Bind(R.id.fl_img2_container)
    FrameLayout flImg2Container;
    @Bind(R.id.iv_stu_img)
    ImageView ivStuImg;
    @Bind(R.id.tv_stu_photo_descripiton)
    TextView tvStuPhotoDescripiton;
    @Bind(R.id.fl_img3_container)
    FrameLayout flImg3Container;
    @Bind(R.id.tv_stu_parent_info)
    TextView tvStuParentInfo;
    @Bind(R.id.tv_stu_info)
    TextView tvStuInfo;
    @Bind(R.id.tv_stu_teacher_info)
    TextView tvStuTeacherInfo;
    @Bind(R.id.ll_call_teacher)
    LinearLayout llCallTeacher;
    @Bind(R.id.tv_allow)
    TextView tvAllow;
    @Bind(R.id.tv_refuse)
    TextView tvRefuse;
    @Bind(R.id.ll_print_password)
    LinearLayout llPrintPassword;
    @Bind(R.id.tv_tech_support_info)
    TextView tvTechSupportInfo;
    @Bind(R.id.rl_check_result_root_container)
    LinearLayout llCheckResultRootContainer;
    @Bind(R.id.iv_print_icon)
    ImageView ivPrintIcon;
    @Bind(R.id.tv_print)
    TextView tvPrint;
    @Bind(R.id.tv_call_teacher)
    TextView tvCallTeacher;
    @Bind(R.id.tv_call_guard)
    TextView tvCallGuard;
    @Bind(R.id.ll_call_guard)
    LinearLayout llCallGuard;
    @Bind(R.id.tv_call_lead)
    TextView tvCallLead;
    @Bind(R.id.ll_call_lead)
    LinearLayout llCallLead;
    @Bind(R.id.ll_black_list_container)
    LinearLayout llBlackListContainer;
    @Bind(R.id.ll_call_and_print_container)
    LinearLayout llCallAndPrintContainer;
    @Bind(R.id.ll_info_container)
    LinearLayout llInfoContainer;
    @Bind(R.id.tv_allow_state_content)
    TextView tvAllowStateContent;
    @Bind(R.id.tv_unregister_allow)
    TextView tvUnregisterAllow;
    @Bind(R.id.tv_unregister_refuse)
    TextView tvUnregisterRefuse;
    @Bind(R.id.rl_unregister_container1)
    RelativeLayout rlUnregisterContainer1;
    @Bind(R.id.rb_setting_parent)
    RadioButton rbSettingParent;
    @Bind(R.id.rb_setting_teacher)
    RadioButton rbSettingTeacher;
    @Bind(R.id.rb_setting_stu)
    RadioButton rbSettingStu;
    @Bind(R.id.rb_setting_blacklist)
    RadioButton rbSettingBlacklist;
    @Bind(R.id.radioGroup)
    RadioGroup radioGroup;
    @Bind(R.id.ll_unregister_container2)
    LinearLayout llUnregisterContainer2;
    @Bind(R.id.tv_unlogin_visitor_name)
    TextView tvUnloginVisitorName;

    private Context mContext;
    private int visitorResultState = -1;
    private String matchScore;
    private Bitmap mSceneImg;
    private Bitmap idCardImg;
    private CustomerInfo getmCustomerInfo;
    private TextView tvInfoTitleContent;
    private String sceneImgPath;
    private boolean isBlackList = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ButterKnife.bind(this);

        mContext = this;
        mSceneImg = App.getInstance().getmSceneImg();
        //判断用户类型
        Intent intent = getIntent();
        matchScore = intent.getStringExtra("match_score");
        sceneImgPath = intent.getStringExtra("scene_img_path");
        int user_type = intent.getIntExtra("user_type", 0);
        if (user_type != Constants.USER_TYPE_UNKNOWN) {
            getmCustomerInfo = App.getInstance().getmCustomerInfo();
            idCardImg = BitmapFactory.decodeFile(getmCustomerInfo.getFeature_img());
        }

        if (user_type != Constants.USER_TYPE_PARENT) {
            //如果不是家长，就不要显示学生的照片
            flImg3Container.setVisibility(View.INVISIBLE);
        }

        switch (user_type) {
            case Constants.USER_TYPE_PARENT:
                //判断预约状态,只有家长才有这个状态判断
                if (App.getInstance().getmCustomerInfo().getBespoke().equals("yes")) {
                    //1.访客登记成功
                    visitorResultState = Constants.RESULT_STATE_LOGIN_OK;
                } else {
                    //2.访客未预约
                    visitorResultState = Constants.RESULT_STATE_UNBOOK;
                }
                break;
            case Constants.USER_TYPE_STU:

                visitorResultState = Constants.RESULT_STATE_STU_IO;
                break;
            case Constants.USER_TYPE_TEACHER:

                visitorResultState = Constants.RESULT_STATE_TEACHER_IO;
                break;
            case USER_TYPE_BLACKLIST:
                visitorResultState = Constants.RESULT_STATE_BLACK_LIST;

                break;
            case Constants.USER_TYPE_UNKNOWN:
                visitorResultState = Constants.USER_TYPE_UNKNOWN;
                break;
        }

        //根据访客类型选择界面显示
        initView(user_type);
        //根据 visitorResultState
        //如果用户已知
        if (user_type != Constants.USER_TYPE_UNKNOWN) {
            if (user_type != USER_TYPE_BLACKLIST) {
                ivSceneImg.setImageBitmap(mSceneImg);
                //这个显示的证件照片
                switch (user_type) {
                    case Constants.USER_TYPE_PARENT:
                        tvStuInfo.setText("学生信息： " + App.getInstance().getmCustomerInfo().getVisitor_stu() + "," + App.getInstance().getmCustomerInfo().getSt_class());
                        tvStuTeacherInfo.setText("教师信息： " + App.getInstance().getmCustomerInfo().getStu_teacher() + "," + App.getInstance().getmCustomerInfo().getPosition() + "," + App.getInstance().getmCustomerInfo().getPhone());
                        tvStuParentInfo.setText("家长姓名： " + App.getInstance().getmCustomerInfo().getName());
                        tvInfoTitleContent.setText("您的登记信息");
                        break;
                    case Constants.USER_TYPE_STU:
                        tvStuInfo.setText("学生信息： " + App.getInstance().getmCustomerInfo().getVisitor_stu() + "," + App.getInstance().getmCustomerInfo().getSt_class());
                        tvStuTeacherInfo.setText("班主任信息： " + App.getInstance().getmCustomerInfo().getStu_teacher() + "," + App.getInstance().getmCustomerInfo().getPosition() + "," + App.getInstance().getmCustomerInfo().getPhone());
                        tvStuParentInfo.setVisibility(View.INVISIBLE);
                        tvInfoTitleContent.setText("学生信息");
                        //隐藏打印通行证
                        llPrintPassword.setVisibility(View.INVISIBLE);
                        tvResultState.setBackgroundResource(R.drawable.success_inside2);
                        break;
                    case Constants.USER_TYPE_TEACHER:
                        tvStuInfo.setVisibility(View.INVISIBLE);
                        tvStuTeacherInfo.setText("教工信息： " + App.getInstance().getmCustomerInfo().getStu_teacher() + "," + App.getInstance().getmCustomerInfo().getPosition() + "," + App.getInstance().getmCustomerInfo().getPhone());
                        tvStuParentInfo.setVisibility(View.GONE);
                        tvInfoTitleContent.setText("教工信息");
                        //隐藏通知教师和打印通行证
                        llCallAndPrintContainer.setVisibility(View.INVISIBLE);
                        tvResultState.setBackgroundResource(R.drawable.success_inside2);
                        break;
                }
                ivRegisterImg.setImageBitmap(App.getInstance().getmIdCardInfo().getIdCardImg());

            } else {
                //如果是黑名单的人
                tvInfoTitleContent.setText("黑名单信息");
                tvStuParentInfo.setText("姓名： " + App.getInstance().getmCustomerInfo().getName());
                tvStuInfo.setText("备注： " + App.getInstance().getmCustomerInfo().getRemark());
                tvStuTeacherInfo.setText("相关负责人： " + App.getInstance().getmCustomerInfo().getStu_teacher());
            }

            addVisitorLog(getmCustomerInfo);
        } else {
            //否则

            tvUnloginVisitorName.setText("姓名： " + App.getInstance().getmIdCardInfo().getName());
        }

        ivSceneImg.setImageBitmap(mSceneImg);
        ivRegisterImg.setImageBitmap(App.getInstance().getmIdCardInfo().getIdCardImg());
    }

    private void initView(int userType) {
        tvInfoTitleContent = (TextView) findViewById(R.id.tv_info_title_content);
        switch (userType) {
            case Constants.USER_TYPE_PARENT:
                switch (visitorResultState) {
                    case Constants.RESULT_STATE_UNBOOK:
                        //如果是未预约
                        llCheckResultRootContainer.setBackground(getDrawable(R.drawable.shape_rectangle_grey5));
                        tvResultState.setBackground(getDrawable(R.drawable.notbooked_inside));
                        llCallTeacher.setBackground(getDrawable(R.drawable.down_grey3_selector));
                        tvAllow.setBackground(getDrawable(R.drawable.down_grey3_selector));
                        tvRefuse.setBackground(getDrawable(R.drawable.down_grey3_selector));
                        tvTechSupportInfo.setTextColor(0x6C6C6C);
                        ivPrintIcon.setVisibility(View.GONE);
                        tvPrint.setVisibility(View.GONE);
                        tvAllow.setVisibility(View.VISIBLE);
                        tvRefuse.setVisibility(View.VISIBLE);
                        llUnregisterContainer2.setVisibility(View.VISIBLE);
                        flImg3Container.setVisibility(View.GONE);
                        llBlackListContainer.setVisibility(View.VISIBLE);
                        rlUnregisterContainer1.setVisibility(View.VISIBLE);
                        llCallGuard.setVisibility(View.GONE);
                        llCallLead.setVisibility(View.GONE);
                        break;
                }
                break;
            case Constants.USER_TYPE_TEACHER:
            case Constants.USER_TYPE_STU:

                break;
            case Constants.USER_TYPE_UNKNOWN:
                //如果是未登记，隐藏通知保卫和通知负责人,隐藏 “您的等级信息”
                Bitmap bitmap = null;
                llCheckResultRootContainer.setBackground(getDrawable(R.drawable.shape_rectangle_grey5));
                rbSettingParent.setButtonDrawable(new BitmapDrawable(bitmap));
                rbSettingTeacher.setButtonDrawable(new BitmapDrawable(bitmap));
                rbSettingStu.setButtonDrawable(new BitmapDrawable(bitmap));
                rbSettingBlacklist.setButtonDrawable(new BitmapDrawable(bitmap));
                llCallAndPrintContainer.setVisibility(View.GONE);
                llInfoContainer.setVisibility(View.GONE);
                llCallGuard.setVisibility(View.GONE);
                llCallLead.setVisibility(View.GONE);
                tvResultState.setBackground(getDrawable(R.drawable.notregistered_inside));
                llUnregisterContainer2.setVisibility(View.VISIBLE);
                flImg3Container.setVisibility(View.GONE);
                llBlackListContainer.setVisibility(View.VISIBLE);
                rlUnregisterContainer1.setVisibility(View.VISIBLE);

                break;
            case USER_TYPE_BLACKLIST:
                //如果是黑名单。1.学生照片布局隐藏;2.显示呼叫保卫，通知负责人;3.同时通知外屏显示黑名单界面

                llCheckResultRootContainer.setBackground(getDrawable(R.drawable.shape_rectangle_grey5));
                tvResultState.setBackground(getDrawable(R.drawable.blacklist_inside));
                flImg3Container.setVisibility(View.GONE);
                llCallAndPrintContainer.setVisibility(View.GONE);
                rlUnregisterContainer1.setVisibility(View.GONE);
                llCallGuard.setVisibility(View.VISIBLE);
                llCallLead.setVisibility(View.VISIBLE);
                llInfoContainer.setVisibility(View.VISIBLE);
                llBlackListContainer.setVisibility(View.VISIBLE);

                try {
                    App.getInstance().getClientService().showResult(null, userType);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
        }


    }

    @Override
    protected void onStop() {
        super.onStop();
        //然后添加一条到访记录

        App.getInstance().setmCustomerInfo(null);
    }

    /**
     * 添加一条到访记录
     */
    private void addVisitorLog(CustomerInfo customerInfo) {
        String visitorName = customerInfo.getName();
        String visitorPhone = customerInfo.getPhone();
        String visitorType = customerInfo.getUser_type();
        String visitorClass = customerInfo.getSt_class();
        String mathcTime = TimeUtil.getDayOfYear() + "   " + TimeUtil.getTimeOfDay();
        String matchScore = this.matchScore;
        String matchImg = sceneImgPath;
        String featureImg = customerInfo.getFeature_img();
        String stuTeacher = customerInfo.getStu_teacher();
        String vistorStu = customerInfo.getVisitor_stu();
        String idCardNum = customerInfo.getCard_id();
        DataBaseUtil.addVisitor_LogRecord(DataBaseUtil.getVisitorLogTable(), new VisitorLog(null, "2017_" + TimeUtil.getDayOfYear() + "   " + TimeUtil.getTimeOfDay()
                , visitorName, visitorPhone, visitorType, visitorClass, mathcTime, matchScore, matchImg, featureImg, stuTeacher, vistorStu, idCardNum));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        /*finish();
        App.mCustomerInfo = null;
        App.mIdCardInfo = null;
        App.currentIdCard = null;
        startActivity(new Intent(mContext, CheckFaceActivity.class));*/
    }

    @OnClick({R.id.tv_result_state, R.id.ll_call_teacher, R.id.tv_allow, R.id.tv_refuse
            , R.id.ll_print_password, R.id.bt_checkResult_back, R.id.tv_unregister_allow,
            R.id.tv_unregister_refuse, R.id.rb_setting_parent, R.id.rb_setting_teacher,
            R.id.rb_setting_stu, R.id.rb_setting_blacklist, R.id.ll_call_guard, R.id.ll_call_lead})
    public void onClick(View view) {
        CustomerInfo customerInfo = App.getInstance().getmCustomerInfo();
        getmCustomerInfo = customerInfo;
        switch (view.getId()) {
            case R.id.bt_checkResult_back:
                //通知外屏，回到 监测人脸界面
                try {
                    App.getInstance().getClientService().changeServiceUI(1);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                finish();
//                startActivity(new Intent(mContext, CheckFaceActivity.class));
                break;
            case R.id.ll_call_teacher:
                ToastUtil.showToast(mContext, "敬请期待！");
                break;
            case R.id.ll_call_guard:
                ToastUtil.showToast(mContext, "敬请期待！");
                break;
            case R.id.ll_call_lead:
                ToastUtil.showToast(mContext, "敬请期待！");
                break;
            case R.id.tv_allow:
                //点击允许，就变为登记成功
                llCheckResultRootContainer.setBackground(getDrawable(R.drawable.shape_rectangle_green));
                tvResultState.setBackground(getDrawable(R.drawable.success_inside));
                llCallTeacher.setBackground(getDrawable(R.drawable.shape_rectangle_green_dark));
                llPrintPassword.setBackground(getDrawable(R.drawable.shape_rectangle_green_dark));
                tvAllow.setBackground(getDrawable(R.drawable.down_grey3_selector));
                tvRefuse.setBackground(getDrawable(R.drawable.down_grey3_selector));
                tvTechSupportInfo.setTextColor(0x76dbab);
                ivPrintIcon.setVisibility(View.VISIBLE);
                tvPrint.setVisibility(View.VISIBLE);
                tvAllow.setVisibility(View.GONE);
                tvRefuse.setVisibility(View.GONE);
                break;
            case R.id.tv_refuse:
                //点击拒绝，就变为教师拒绝
                tvResultState.setBackground(getDrawable(R.drawable.refuse_inside));

                break;
            case R.id.ll_print_password:                                                            //打印通行证
                PrinterUtil printInstance = PrinterUtil.getInstance().init();
                printInstance.OpenComPort();
                printInstance.printTest("黄冈中学接待处", 1001, getmCustomerInfo.getName(), getmCustomerInfo.getStu_teacher(), getmCustomerInfo.getPhone(), App.getInstance().getmIdCardInfo().getGender().equals("男") ? 0 : 1);
                printInstance.CloseComPort();
                break;
            case R.id.tv_unregister_allow:
                //未登记时点击允许
                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.rb_setting_parent:

                        loginUser(Constants.USER_TYPE_PARENT);
                        break;
                    case R.id.rb_setting_stu:
                        //显示 学生登记成功界面
                        loginUser(Constants.USER_TYPE_STU);
                        break;
                    case R.id.rb_setting_teacher:
                        //显示 教师登记成功界面
                        //显示 家长登记成功界面
                        loginUser(Constants.USER_TYPE_TEACHER);
                        break;

                    case R.id.rb_setting_blacklist:
                        ToastUtil.showToast(this, "黑名单不能通过！");
                        return;
                    default:
                        ToastUtil.showToast(this, "请选择登记类型！");
                        return;
                }

                break;
            case R.id.tv_unregister_refuse:
                if (isBlackList) {
                    visitorResultState = Constants.USER_TYPE_BLACKLIST;
                } else {

                    visitorResultState = Constants.USER_TYPE_REFUSE;
                }
                refuse(visitorResultState);
                break;
            case R.id.rb_setting_parent:

                isBlackList = false;
                break;
            case R.id.rb_setting_teacher:

                isBlackList = false;
                break;
            case R.id.rb_setting_stu:

                isBlackList = false;
                break;
            case R.id.rb_setting_blacklist:
                isBlackList = true;
                break;
        }
    }

    /**
     * 解决分为，拒绝和黑名单拒绝
     *
     * @param settingUserType
     */
    private void refuse(int settingUserType) {
        IDCardInfo idCardInfo = App.getInstance().getmIdCardInfo();
        String count = session.getCustomerInfoDao().count() + "";
        //如果拒绝，就只在来访登记表中添加。
        CustomerInfo customerInfo = new CustomerInfo(
                null, "refuse_visitor_" + TimeUtil.getDayOfYear() + "   " + TimeUtil.getTimeOfDay(), idCardInfo.getName(), "no_phone", settingUserType + "", "no_class", "no_position", "no_bespoke", "no_feature_value"
                , "/sdcard/Pictures/idCard/" + idCardInfo.getIdcardNumber() + ".jpg", "no_teacher", "no_student", idCardInfo.getGender(), "no_stu_img", idCardInfo.getIdcardNumber(), "no_remark"
        );

        llInfoContainer.setVisibility(View.VISIBLE);
        flImg3Container.setVisibility(View.INVISIBLE);
        llUnregisterContainer2.setVisibility(View.GONE);
        llCallAndPrintContainer.setVisibility(View.GONE);
        rlUnregisterContainer1.setVisibility(View.GONE);
        llCheckResultRootContainer.setBackground(getDrawable(R.drawable.shape_rectangle_grey5));
        if (settingUserType == USER_TYPE_BLACKLIST) {

            visitorResultState = USER_TYPE_BLACKLIST;

            llCallGuard.setVisibility(View.VISIBLE);
            llCallLead.setVisibility(View.VISIBLE);
            llBlackListContainer.setVisibility(View.VISIBLE);
            tvResultState.setBackground(getDrawable(R.drawable.blacklist_inside));
            tvInfoTitleContent.setText("黑名单信息：");
            tvStuInfo.setText("备注：" + customerInfo.getRemark());
            tvStuTeacherInfo.setText("相关负责人：" + customerInfo.getStu_teacher() + "," + customerInfo.getPosition() + "," + customerInfo.getPhone());
            DataBaseUtil.addCustomer_InfoRecord(session.getCustomerInfoDao(), "2017_" + count, customerInfo);

        } else {
            visitorResultState = USER_TYPE_REFUSE;

            llCallGuard.setVisibility(View.GONE);
            llCallLead.setVisibility(View.GONE);
            llBlackListContainer.setVisibility(View.GONE);
            tvResultState.setBackground(getDrawable(R.drawable.refuse_inside));

        }

        //并且存取用户类型

        customerInfo.setUser_type(settingUserType + "");
        addVisitorLog(customerInfo);

        //给 App.mCustomerInfo 赋值
        getmCustomerInfo = null;

        ivSceneImg.setImageBitmap(mSceneImg);
        ivRegisterImg.setImageBitmap(BitmapFactory.decodeFile(customerInfo.getFeature_img()));


        tvStuParentInfo.setText("姓名： " + customerInfo.getName());


        try {
            App.getInstance().getClientService().showResult(null, settingUserType);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    /**
     * 给用户现场登记
     *
     * @param settingUserType
     */
    private void loginUser(int settingUserType) {
        visitorResultState = Constants.RESULT_STATE_LOGIN_OK;
        IDCardInfo idCardInfo = App.getInstance().getmIdCardInfo();
        llInfoContainer.setVisibility(View.VISIBLE);
        if (settingUserType == Constants.USER_TYPE_PARENT) {
            flImg3Container.setVisibility(View.VISIBLE);
            llCallAndPrintContainer.setVisibility(View.VISIBLE);
        } else {
            if (settingUserType == Constants.USER_TYPE_STU) {
                tvStuParentInfo.setVisibility(View.INVISIBLE);
                tvStuTeacherInfo.setVisibility(View.VISIBLE);
                llCallAndPrintContainer.setVisibility(View.VISIBLE);
                llCallTeacher.setVisibility(View.VISIBLE);
                llPrintPassword.setVisibility(View.INVISIBLE);
            } else {
                tvStuParentInfo.setVisibility(View.INVISIBLE);
                tvStuInfo.setVisibility(View.GONE);
                llCallAndPrintContainer.setVisibility(View.INVISIBLE);
            }
            flImg3Container.setVisibility(View.INVISIBLE);
        }
        llUnregisterContainer2.setVisibility(View.GONE);
        llInfoContainer.setVisibility(View.VISIBLE);
        llCallGuard.setVisibility(View.GONE);
        llCallLead.setVisibility(View.GONE);
        llBlackListContainer.setVisibility(View.GONE);
        tvResultState.setBackground(getDrawable(R.drawable.success_inside));
        llCheckResultRootContainer.setBackground(getDrawable(R.drawable.shape_rectangle_green));
        //并且往模拟库中添加一条用户信息
        String count = session.getCustomerInfoDao().count() + "";
        CustomerInfo customerInfo = null;
        switch (settingUserType) {
            case Constants.USER_TYPE_PARENT:
                tvStuParentInfo.setText("家长姓名： " + idCardInfo.getName());
                customerInfo = new CustomerInfo(null, "2017_" + count, idCardInfo.getName(), "133xxxxxxxx"
                        , settingUserType + "", "无", "党员", "yes", "特征值暂时没有", "/sdcard/Pictures/idCard/" + idCardInfo.getIdcardNumber() + ".jpg"
                        , "无", "无", "男", "无", idCardInfo.getIdcardNumber(), "测试用");
                break;
            case Constants.USER_TYPE_STU:
                tvStuInfo.setText("学生信息： " + idCardInfo.getName());
                customerInfo = new CustomerInfo(null, "2017_" + count, idCardInfo.getName() + "_家长", "133xxxxxxxx"
                        , settingUserType + "", "无", "党员", "yes", "特征值暂时没有", "/sdcard/Pictures/idCard/" + idCardInfo.getIdcardNumber() + ".jpg"
                        , idCardInfo.getName() + "_老师", idCardInfo.getName(), "男", "无", idCardInfo.getIdcardNumber(), "测试用");
                break;
            case Constants.USER_TYPE_TEACHER:
                tvStuTeacherInfo.setText("教师信息： " + idCardInfo.getName());
                customerInfo = new CustomerInfo(null, "2017_" + count, idCardInfo.getName() + "_学生家长", "133xxxxxxxx"
                        , settingUserType + "", "无", "党员", "yes", "特征值暂时没有", "/sdcard/Pictures/idCard/" + idCardInfo.getIdcardNumber() + ".jpg"
                        , idCardInfo.getName(), "无", "男", "无", idCardInfo.getIdcardNumber(), "测试用");
                break;
        }


        DataBaseUtil.addCustomer_InfoRecord(session.getCustomerInfoDao(), "2017_" + count, customerInfo);

        //给 App.mCustomerInfo 赋值
        getmCustomerInfo = customerInfo;
        getmCustomerInfo.setUser_type(settingUserType + "");
        App.getInstance().setmCustomerInfo(getmCustomerInfo);
        addVisitorLog(getmCustomerInfo);
        //通知外屏应用显示信息

        try {
            App.getInstance().getClientService().showResult(getmCustomerInfo, settingUserType);
        } catch (RemoteException e) {
            e.printStackTrace();
        }


    }
}
