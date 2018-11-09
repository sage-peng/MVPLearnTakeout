package com.xjd.mvplearntakeout.model.bean

import android.content.pm.ActivityInfo

/**
 * Created by Administrator on 2018-11-06.
 */

class Seller {
    var id: Long = 0
    var pic: String? = null
    var name: String = ""

    var score: String = ""
    var sale: String? = null
    var ensure: String? = null

    var invoice: String? = null
    var sendPrice: String? = null
    var deliveryFee: String? = null

    var recentVisit: String? = null
    var distance: String? = null
    var time: String? = null

    var icon: String? = null

    var activityList: ArrayList<ActivityInfo>? = null

}
