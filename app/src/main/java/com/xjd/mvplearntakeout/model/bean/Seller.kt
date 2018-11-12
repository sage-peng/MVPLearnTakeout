package com.xjd.mvplearntakeout.model.bean

import android.content.pm.ActivityInfo
import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

/**
 * Created by Administrator on 2018-11-06.
 */

class Seller() : Serializable {
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

    constructor(parcel: Parcel) : this() {
        id = parcel.readLong()
        pic = parcel.readString()
        name = parcel.readString()
        score = parcel.readString()
        sale = parcel.readString()
        ensure = parcel.readString()
        invoice = parcel.readString()
        sendPrice = parcel.readString()
        deliveryFee = parcel.readString()
        recentVisit = parcel.readString()
        distance = parcel.readString()
        time = parcel.readString()
        icon = parcel.readString()
    }



    companion object CREATOR : Parcelable.Creator<Seller> {
        override fun createFromParcel(parcel: Parcel): Seller {
            return Seller(parcel)
        }

        override fun newArray(size: Int): Array<Seller?> {
            return arrayOfNulls(size)
        }
    }

}
