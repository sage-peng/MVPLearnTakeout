package com.xjd.mvplearntakeout.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat.getExtras
import android.os.Bundle
import android.os.Message
import android.text.TextUtils
import android.util.Log
import cn.jpush.android.api.JPushInterface
import com.heima.takeout.utils.OrderObservable


/**
 * Created by Administrator on 2018-11-09.
 */

class MyReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent != null) {
            val bundle = intent.extras
            if (bundle != null) {
                val message: String? = bundle.getString(JPushInterface.EXTRA_MESSAGE)
                if (!TextUtils.isEmpty(message)) {
                    Log.e("MyReceiver:message", message)
                }

                val extras: String? = bundle.getString(JPushInterface.EXTRA_EXTRA)
                if (!TextUtils.isEmpty(extras)) {
                    Log.e("MyReceiver:extras", extras)
                    OrderObservable.instance.newInstance(extras.toString())
                }

            }


        }
    }

}
