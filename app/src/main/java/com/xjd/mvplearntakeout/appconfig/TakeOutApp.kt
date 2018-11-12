package com.xjd.mvplearntakeout.appconfig

import cn.jpush.android.api.JPushInterface
import com.mob.MobApplication
import com.mob.MobSDK
import com.xjd.mvplearntakeout.model.bean.CacheSelectedInfo
import com.xjd.mvplearntakeout.model.bean.User
import java.util.concurrent.CopyOnWriteArrayList

/**
 * Created by Administrator on 2018-11-07.
 */

class TakeOutApp : MobApplication() {
    var cacheSelectedInfolist: CopyOnWriteArrayList<CacheSelectedInfo> = CopyOnWriteArrayList()

    companion object {
        var user: User = User()
        lateinit var instance: TakeOutApp

    }

    override fun onCreate() {
        super.onCreate()
        user.id = -1
        instance=this
        //短信登录
        MobSDK.init(this)

        //极光推送
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }


}
