package com.xjd.mvplearntakeout.presenter

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.xjd.mvplearntakeout.model.bean.ResponseInfo
import com.xjd.mvplearntakeout.model.bean.Seller
import com.xjd.mvplearntakeout.ui.iui.IHomeFragment
import retrofit2.Retrofit
import com.xjd.mvplearntakeout.model.net.TakeoutService
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import java.util.function.ObjDoubleConsumer

/**
 * Created by Administrator on 2018-11-07.
 */

class HomeFragmentPresenter(val ihomeFragment: IHomeFragment) :BasePresenter(){



    fun getHomeInfo() {
        val homeCall = takeoutService.getHomeInfo()
        homeCall.enqueue(callback)
    }

    override fun parserJson(json: String) {
        val gson = Gson()
        val jsonObject = JSONObject(json)
        val nearby = jsonObject.getString("nearbySellerList")
        val nearbySellers: List<Seller> = gson.fromJson(nearby, object : TypeToken<List<Seller>>() {}.type)

        val other = jsonObject.getString("otherSellerList")
        val otherSellers: List<Seller> = gson.fromJson(other, object : TypeToken<List<Seller>>() {}.type)

        if(nearbySellers.isNotEmpty() ||otherSellers.isNotEmpty()){
            ihomeFragment.onHomeSuccess(nearbySellers,otherSellers)
        }else{
            ihomeFragment.onHomeFailed()
        }
    }

}


