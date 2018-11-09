package com.xjd.mvplearntakeout.presenter

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.xjd.mvplearntakeout.model.bean.Order
import com.xjd.mvplearntakeout.model.bean.Seller
import com.xjd.mvplearntakeout.ui.fragment.OrderFragment
import com.xjd.mvplearntakeout.ui.iui.IHomeFragment
import org.json.JSONObject

/**
 * Created by Administrator on 2018-11-07.
 */

class OrderFragmentPresenter(val orderFragment: OrderFragment) : BasePresenter(){

    fun getListInfo(userId:String){
        val orderListCall = takeoutService.getListInfo(userId)
        orderListCall.enqueue(callback)
    }
    override fun parserJson(json: String) {
       Log.e("OrderFragmentPresenter",json)

        //解析数据
        val gson = Gson()
        val orders:List<Order> = gson.fromJson(json, object: TypeToken<List<Order>>(){}.type)
        orderFragment.onOrderSuccess(orders)
    }


}