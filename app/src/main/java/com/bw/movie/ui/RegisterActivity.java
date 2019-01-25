package com.bw.movie.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.bigkoo.pickerview.TimePickerView;
import com.bw.movie.All_Interface.MyInterface;
import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;

import com.bw.movie.bean.MsgAndSta;
import com.bw.movie.presenter.PresenterImpl;

import com.bw.movie.utils.Contact;
import com.bw.movie.utils.EncryptUtil;
import com.xw.repo.XEditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by android_lhf：2019/1/24
 */
public class RegisterActivity extends BaseActivity implements MyInterface.MyView {

    @BindView(R.id.register_nickName)
    XEditText registerNickName;
    @BindView(R.id.register_sex)
    XEditText registerSex;
    @BindView(R.id.register_birthday)
    XEditText registerBirthday;
    @BindView(R.id.register_phone)
    XEditText registerPhone;
    @BindView(R.id.register_email)
    XEditText registerEmail;
    @BindView(R.id.register_pwd)
    XEditText registerPwd;
    @BindView(R.id.register_btn)
    Button registerBtn;
    @BindView(R.id.rel4)
    RelativeLayout rel4;
    @BindView(R.id.rel5)
    RelativeLayout rel5;
    @BindView(R.id.rel6)
    RelativeLayout rel6;
    private PresenterImpl presenter = new PresenterImpl(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @Override
    protected int getLayout() {
        return 0;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void setOnclick() {

    }

    @Override
    protected void logic() {

    }

    @OnClick({R.id.register_birthday, R.id.register_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.register_birthday:
                showDate();
                break;
            case R.id.register_btn:
                int usersex = 1;
                String name = registerNickName.getText().toString().trim();
                String phone = registerPhone.getText().toString().trim();
                String pwd = registerPwd.getText().toString().trim();
                String sex = registerSex.getText().toString().trim();
                String birthday = registerBirthday.getText().toString().trim();
                String email = registerEmail.getText().toString().trim();
                if (sex.equals("男")) {
                    usersex = 1;
                } else if (sex.equals("女")) {
                    usersex = 2;
                } else {
                    Toast.makeText(this, "请输入正确的字符！", Toast.LENGTH_SHORT).show();
                }
                String encryptpwd = EncryptUtil.encrypt(pwd);


                if (phone.isEmpty() || pwd.isEmpty() || name.isEmpty() || sex.isEmpty() || birthday.isEmpty() || email.isEmpty()) {
                    Toast.makeText(this, "请输入完整信息", Toast.LENGTH_SHORT).show();
                } else {
                    boolean phoneLegal = isChinaPhoneLegal(phone);
                    if ((phone.length() == 11) && (phoneLegal == true) && pwd.length() == 6) {
                        HashMap<String, Object> params = new HashMap<>();
                        params.put("nickName", name);
                        params.put("phone", phone);
                        Log.e("zzz", "onViewClicked: " + phone);
                        params.put("pwd", encryptpwd);
                        params.put("pwd2", encryptpwd);
                        params.put("sex", usersex);
                        params.put("birthday", birthday);
                        params.put("imei", 123456);
                        params.put("ua", "小米");
                        params.put("screenSize", "5.0");
                        params.put("os", "android");
                        params.put("email", email);
                        Log.d("params", "onViewClicked: " + name + phone + encryptpwd + usersex + birthday + email);
                        HashMap<String, Object> headmap = new HashMap<>();
                        presenter.postData(Contact.USER_REGISTER, headmap, params, MsgAndSta.class);
                    } else {
                        Toast.makeText(this, "手机号和密码不合格", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }
   /* @OnClick({R.id.register_btn, R.id.register_birthday})
    public void onViewClicked() {

        int usersex = 1;
        String name = registerNickName.getText().toString().trim();
        String phone = registerPhone.getText().toString().trim();
        String pwd = registerPwd.getText().toString().trim();
        String sex = registerSex.getText().toString().trim();
        String birthday = registerBirthday.getText().toString().trim();
        String email = registerEmail.getText().toString().trim();
        if (sex.equals("男")) {
            usersex = 1;
        } else if (sex.equals("女")) {
            usersex = 2;
        } else {
            Toast.makeText(this, "请输入正确的字符！", Toast.LENGTH_SHORT).show();
        }
        String encryptpwd = EncryptUtils.encrypt(pwd);


        if (phone.isEmpty() || pwd.isEmpty() || name.isEmpty() || sex.isEmpty() || birthday.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "请输入完整信息", Toast.LENGTH_SHORT).show();
        } else {
            boolean phoneLegal = isChinaPhoneLegal(phone);
            if ((phone.length() == 11) && (phoneLegal == true) && pwd.length() == 6) {
                HashMap<String, Object> params = new HashMap<>();
                params.put("nickName", name);
                params.put("phone", phone);
                Log.e("zzz", "onViewClicked: " + phone);
                params.put("pwd", encryptpwd);
                params.put("pwd2", encryptpwd);
                params.put("sex", usersex);
                params.put("birthday", birthday);
                params.put("imei", 123456);
                params.put("ua", "小米");
                params.put("screenSize", "5.0");
                params.put("os", "android");
                params.put("email", email);
                Log.d("params", "onViewClicked: " + name + phone + encryptpwd + usersex + birthday + email);
                HashMap<String, Object> headmap = new HashMap<>();
                presenter.postData(Contact.USER_REGISTER, headmap, params, RegisterBean.class);
            } else {
                Toast.makeText(this, "手机号和密码不合格", Toast.LENGTH_SHORT).show();
            }
        }
    }*/

    //验证手机号
    public static boolean isChinaPhoneLegal(String str)
            throws PatternSyntaxException {
        String regExp = "^((13[0-9])|16[0-9]|(15[^4])|(18[0-9])|(17[0-8])|(147,145))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }
    private void showDate() {
        TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                String time = getTime(date);
                registerBirthday.setText(time);
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
//                .setContentSize(18)//滚轮文字大小
//                .setTitleSize(20)//标题文字大小
//                //.setTitleText("Title")//标题文字
//                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
//                .isCyclic(true)//是否循环滚动
//                //.setTitleColor(Color.BLACK)//标题文字颜色
//                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
//                .setCancelColor(Color.BLUE)//取消按钮文字颜色
//                //.setTitleBgColor(0xFF666666)//标题背景颜色 Night mode
//                .setBgColor(0xFF333333)//滚轮背景颜色 Night mode
////                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
////                .setRangDate(startDate,endDate)//起始终止年月日设定
//                //.setLabel("年","月","日","时","分","秒")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                //.isDialog(true)//是否显示为对话框样式
                .build();

        pvTime.show();
    }
    private String getTime(Date date) {//可根据需要自行截取数据显示
        //"YYYY-MM-DD HH:MM:SS"        "yyyy-MM-dd"
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }



    @Override
    public void onRequestOk(Object data) {
        if (data instanceof MsgAndSta) {
            MsgAndSta bean = (MsgAndSta) data;
            if(bean.getStatus().equals("0000"))
            {
                finish();
            }

        }
    }

    @Override
    public void onRequestNo(String error) {

    }
}
