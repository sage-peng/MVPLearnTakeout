package com.xjd.mvplearntakeout.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils

import cn.smssdk.EventHandler
import cn.smssdk.SMSSDK
import com.xjd.mvplearntakeout.R
import android.util.Log
import com.heima.takeout.utils.SMSUtil
import com.xjd.mvplearntakeout.dagger.component.DaggerLoginActivityComponet
import com.xjd.mvplearntakeout.dagger.module.LoginActivityMoudle
import com.xjd.mvplearntakeout.model.bean.User
import com.xjd.mvplearntakeout.presenter.LoginActivityPresenter
import com.xjd.mvplearntakeout.ui.iui.ILoginActivity

import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast
import javax.inject.Inject


/**
 * Created by Administrator on 2018-11-07.
 */

class LoginActivity : AppCompatActivity(), ILoginActivity {




    @Inject
    lateinit var loginActivityPresenter:LoginActivityPresenter

    var eh: EventHandler = object : EventHandler() {

        override fun afterEvent(event: Int, result: Int, data: Any) {

            if (result == SMSSDK.RESULT_COMPLETE) {
                //回调完成
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {

                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    Log.e("sms11111", "获取验证码成功")
                } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                    //返回支持发送验证码的国家列表
                }
            } else {
                (data as Throwable).printStackTrace()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        DaggerLoginActivityComponet.builder().loginActivityMoudle(LoginActivityMoudle(this)).build().inject(this)
        SMSSDK.registerEventHandler(eh)

        initlister()
    }

    private fun initlister() {
        iv_user_back.setOnClickListener { finish() }
        tv_user_code.setOnClickListener {
            val phone = et_user_phone.text.toString().trim()
            if (SMSUtil.judgePhoneNums(this, phone)) {
//                SMSSDK.getVerificationCode("86",phone)

//                tv_user_code.isEnabled=false
            }
        }
        login.setOnClickListener {
            val phone = et_user_phone.text.toString().trim()
            val code= et_user_phone.text.toString().trim()
            if (SMSUtil.judgePhoneNums(this, phone)&&!TextUtils.isEmpty(code)) {
//                SMSSDK.submitVerificationCode("86", phone,  code)
                loginActivityPresenter.loginByphone(phone)
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        SMSSDK.unregisterEventHandler(eh)
    }

    override fun onLoginSuccess(user: User) {
        finish()
        toast("登录成功")
    }

    override fun onLoginFailed() {
        toast("登录失败")
    }
}
