package com.xjd.mvplearntakeout.presenter

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.xjd.mvplearntakeout.model.bean.GoodsInfo
import com.xjd.mvplearntakeout.model.bean.GoodsTypeInfo
import com.xjd.mvplearntakeout.ui.fragment.GoodsFragment
import org.json.JSONObject

class GoodsFragmentPresenter(val goodsFragment: GoodsFragment) : BasePresenter() {
    override fun parserJson(json: String) {
        Log.e("GoodsFragmentPresenter:",json)
        var jsonObject = JSONObject(json)
        val list = jsonObject.getString("list")
        val goodsTypeList :List<GoodsTypeInfo> = Gson().fromJson<List<GoodsTypeInfo>>(list, object : TypeToken<List<GoodsTypeInfo>>() {}.type)
        if (goodsTypeList.size>0){

            var goodslist: ArrayList<GoodsInfo> = ArrayList()
            for (goodsType in goodsTypeList){
                val goods = goodsType.list
                goodslist.addAll(goods)
            }
            goodsFragment.ongoodsSuccess(goodsTypeList,goodslist)
        }

    }

    fun getBusinessInfo(sellerId: String) {
        val businessCall = takeoutService.getBusinessInfo(sellerId)
        businessCall.enqueue(callback)
    }

}
