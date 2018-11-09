package com.xjd.mvplearntakeout.utils

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import cn.jpush.android.api.JPushInterface
import com.mob.MobApplication
import com.mob.MobSDK
import com.xjd.mvplearntakeout.model.Dao.TakeOutOpenHelper
import com.xjd.mvplearntakeout.model.bean.User

/**
 * Created by Administrator on 2018-11-07.
 */

class TakeOutApp : MobApplication() {

    companion object {
        var user:User= User()
    }

    override fun onCreate() {
        super.onCreate()
        user.id=-1
        //短信登录
        MobSDK.init(this)

        //极光推送
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }




}
