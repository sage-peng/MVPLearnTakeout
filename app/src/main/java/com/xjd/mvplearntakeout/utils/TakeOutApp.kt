package com.xjd.mvplearntakeout.utils

import android.annotation.SuppressLint
import android.app.Application
import com.mob.MobApplication
import com.mob.MobSDK
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
        MobSDK.init(this)
    }

}
