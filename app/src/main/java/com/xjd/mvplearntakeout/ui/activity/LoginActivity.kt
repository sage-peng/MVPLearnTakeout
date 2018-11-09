package com.xjd.mvplearntakeout.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.os.SystemClock
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
import com.xjd.mvplearntakeout.R.id.tv_user_code


/**
 * Created by Administrator on 2018-11-07.
 */

class LoginActivity : AppCompatActivity(), ILoginActivity {


    @Inject
    lateinit var loginActivityPresenter: LoginActivityPresenter
    var timeTask: TimeTask? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
//        loginActivityPresenter = LoginActivityPresenter(this)
        DaggerLoginActivityComponet.builder().loginActivityMoudle(LoginActivityMoudle(this)).build().inject(this)
        SMSSDK.registerEventHandler(loginActivityPresenter.eh)

        initlister()
    }



    private fun initlister() {
        iv_user_back.setOnClickListener { finish() }
        tv_user_code.setOnClickListener {
            val phone = et_user_phone.text.toString().trim()
            if (SMSUtil.judgePhoneNums(this, phone)) {
//                SMSSDK.getVerificationCode("86", phone)

                tv_user_code.isEnabled = false
                timeTask = TimeTask()
                Thread(timeTask).start()
            }
        }
        login.setOnClickListener {
            val phone = et_user_phone.text.toString().trim()
            val code = et_user_code.text.toString().trim()
            if (SMSUtil.judgePhoneNums(this, phone) && !TextUtils.isEmpty(code)) {
//                SMSSDK.submitVerificationCode("86", phone, code)
//                loginActivityPresenter.loginByphone(phone)
            }
            loginActivityPresenter.loginByphone(phone)
        }
    }


    companion object {
        val TIME_MINUT = -1
        val TIME_OUT = 0
    }

    var handle = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message?) {
            when (msg!!.what) {
                TIME_MINUT -> tv_user_code.text = "剩余时间(${time})秒"
                TIME_OUT -> {
                    tv_user_code.isEnabled = true
                    time = 60
                    tv_user_code.text = "点击重发"
                }
            }
        }
    }

    var time = 60

    inner class TimeTask : Runnable {
        override fun run() {
            while (time > 0) {
                handle.sendEmptyMessage(TIME_MINUT)
                SystemClock.sleep(1000)
                time--
            }
            handle.sendEmptyMessage(TIME_OUT)
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        if (timeTask!=null){
            handle.removeCallbacks(timeTask)
        }

        SMSSDK.unregisterEventHandler(loginActivityPresenter.eh)
    }

    override fun onLoginSuccess(user: User) {
        finish()
        toast("登录成功")
    }

    override fun onLoginFailed() {
        toast("登录失败")
    }

    override fun getPhone(): String {
        return et_user_phone.text.toString().trim()
    }
}

