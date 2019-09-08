package com.eyecool.visitor.sysinner;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.eyecool.visitor.sysinner.interfaces.Constants;
import com.eyecool.visitor.sysinner.utils.SPUtil;
import com.eyecool.visitor.sysinner.utils.ToastUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created date: 2017/6/15
 * Author:  Leslie
 * 这个是设置界面
 */

public class SettingActivity extends Activity {
    private static final String TAG = "SettingActivity";
    @Bind(R.id.et_server_ip_address)
    EditText etServerIpAddress;
    @Bind(R.id.tv_software_version)
    TextView tvSoftwareVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        reshow();
    }

    private void reshow() {
        String serverIPAddress = SPUtil.getDataFromSP(getApplicationContext(), Constants.SETTING_DATA_SP_FILE, Constants.SP_FILE_IP_ADDRESS_KEY_NAME);
        etServerIpAddress.setText(serverIPAddress);
    }

    @OnClick({R.id.bt_setting_save, R.id.bt_setting_cancle})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_setting_save:
                //1.保存数据;2.关闭界面，回到待机界面
                //1.待处理
                String inputServerIPAddress = etServerIpAddress.getText().toString().trim();
                if (TextUtils.isEmpty(etServerIpAddress.getText().toString())) {
                    ToastUtil.showToast(getApplicationContext(),getString(R.string.setting_cue_ipaddress_null));
                    return;
                } else {
                    if (inputServerIPAddress.equals("")) {
                    ToastUtil.showToast(getApplicationContext(),getString(R.string.setting_cue_ipaddress_null));
                    }
                }
                Pattern pattern = Pattern.compile(Constants.REGEX_IP_ADDRESS);
                Matcher matcher = pattern.matcher(inputServerIPAddress);
                if (!matcher.matches()) {
                    ToastUtil.showToast(getApplicationContext(),getString(R.string.setting_cue_ipaddress_error));
                    return;
                }
                SPUtil.saveDataAsSP(getApplicationContext(),Constants.SETTING_DATA_SP_FILE
                        ,Constants.SP_FILE_IP_ADDRESS_KEY_NAME,inputServerIPAddress);
                //2.
                finish();
                break;
            case R.id.bt_setting_cancle:
                //1.最新数据不记录;2.关闭界面，回到待机界面
                //1.待处理

                //2.
                finish();
                break;
        }
    }
}
