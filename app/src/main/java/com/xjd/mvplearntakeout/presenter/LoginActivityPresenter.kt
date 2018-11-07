package com.xjd.mvplearntakeout.presenter

import com.google.gson.Gson
import com.xjd.mvplearntakeout.model.bean.User
import com.xjd.mvplearntakeout.ui.iui.ILoginActivity
import com.xjd.mvplearntakeout.utils.TakeOutApp

/**
 * Created by Administrator on 2018-11-07.
 */

class LoginActivityPresenter(val loginActivity: ILoginActivity) : BasePresenter() {


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
