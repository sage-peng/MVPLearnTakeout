package com.xjd.mvplearntakeout.presenter

import android.util.Log
import cn.smssdk.EventHandler
import cn.smssdk.SMSSDK
import com.google.gson.Gson
import com.xjd.mvplearntakeout.model.bean.User
import com.xjd.mvplearntakeout.ui.iui.ILoginActivity
import com.xjd.mvplearntakeout.utils.TakeOutApp
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Created by Administrator on 2018-11-07.
 */

class LoginActivityPresenter(val loginActivity: ILoginActivity) : BasePresenter() {

    var eh: EventHandler = object : EventHandler() {

        override fun afterEvent(event: Int, result: Int, data: Any) {

            if (result == SMSSDK.RESULT_COMPLETE) {
                //回调完成
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    Log.e("sms11111", "验证码验证成功")
                    loginByphone(loginActivity.getPhone())
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
    override fun parserJson(json: String) {
        val gson = Gson()
        val user = gson.fromJson(json, User::class.java)
        if (user != null) {
            loginActivity.onLoginSuccess(user)
            TakeOutApp.user=user
        }
    }

    fun loginByphone(phone: String) {
        val loginCall = takeoutService.loginByphone(phone)
        loginCall.enqueue(callback)
    }

}
