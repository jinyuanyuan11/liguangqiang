package com.bw.movie.ui;



import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.All_Interface.MyInterface;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;

import com.bw.movie.bean.LoginBean;



import com.bw.movie.presenter.PresenterImpl;
import com.bw.movie.utils.Contact;
import com.bw.movie.utils.EncryptUtil;

import com.bw.movie.utils.SpUtil;
import com.xw.repo.XEditText;

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
public class LoginActivity  extends BaseActivity implements MyInterface.MyView {

    @BindView(R.id.login_phone)
    XEditText loginPhone;
    @BindView(R.id.login_pwd)
    XEditText loginPwd;
    @BindView(R.id.login_zc)
    TextView loginZc;
    @BindView(R.id.login_btn)
    Button loginBtn;
    @BindView(R.id.login_weixin)
    ImageView loginWeixin;
    private PresenterImpl presenter;


    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);

    }

    @Override
    protected void setOnclick() {

    }

    @Override
    protected void logic() {
        presenter=new PresenterImpl(this);
    }

    @OnClick({R.id.login_zc, R.id.login_btn, R.id.login_weixin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_zc:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.login_btn:
                String phone = loginPhone.getText().toString().trim();
                String pwd = loginPwd.getText().toString().trim();
                if (phone.isEmpty() || pwd.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "请输入手机号和密码", Toast.LENGTH_SHORT).show();
                } else {
                    boolean phoneLegal = isChinaPhoneLegal(phone);
                    if ((phone.length() == 11) && (phoneLegal == true)) {
                        String encryptpwd = EncryptUtil.encrypt(pwd);
                        HashMap<String, Object> headmap = new HashMap<>();
                        HashMap<String, Object> params=new HashMap<>();
                        params.put("phone", phone);
                        params.put("pwd", encryptpwd);
                        presenter.postData(Contact.USER_LOGIN, headmap, params, LoginBean.class);
                    } else {
                        Toast.makeText(LoginActivity.this, "手机号和密码不合格", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.login_weixin:
                break;
        }
    }

    //验证手机号
    public static boolean isChinaPhoneLegal(String str)
            throws PatternSyntaxException {
        String regExp = "^((13[0-9])|16[0-9]|(15[^4])|(18[0-9])|(17[0-8])|(147,145))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }



    @Override
    public void onRequestOk(Object data) {
        if(data instanceof LoginBean){
            LoginBean loginBean= (LoginBean) data;
            Log.d("loginBeanddd", "onSuccess: "+loginBean.getMessage());
                if(loginBean.getMessage().equals("登陆成功"))
                {
                    Log.d("zzz",loginBean.getResult().getUserId()+"");
                    Log.d("zzz",loginBean.getResult().getSessionId()+"");
                 //   SpUtil.put("userId",loginBean.getResult().getUserId());
                  //  SpUtil.put("sessionId",loginBean.getResult().getSessionId());
                   Intent intent=new Intent(LoginActivity.this,MainPagerActivity.class);
                   startActivity(intent);

                }



        }
    }

    @Override
    public void onRequestNo(String error) {

    }
}
