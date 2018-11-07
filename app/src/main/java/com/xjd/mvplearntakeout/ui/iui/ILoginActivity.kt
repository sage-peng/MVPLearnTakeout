package com.xjd.mvplearntakeout.ui.iui

import com.xjd.mvplearntakeout.model.bean.User

/**
 * Created by Administrator on 2018-11-07.
 */

interface ILoginActivity{
    fun onLoginFailed()
    fun onLoginSuccess(user: User)
    fun getPhone():String
}
