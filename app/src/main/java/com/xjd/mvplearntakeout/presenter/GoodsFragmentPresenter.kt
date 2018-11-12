package com.xjd.mvplearntakeout.presenter

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.AbsListView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.xjd.mvplearntakeout.model.bean.GoodsInfo
import com.xjd.mvplearntakeout.model.bean.GoodsTypeInfo
import com.xjd.mvplearntakeout.ui.activity.BusinessActivity
import com.xjd.mvplearntakeout.ui.adapter.RvGoodsTypeAdapter
import com.xjd.mvplearntakeout.ui.adapter.StickyAdapter
import com.xjd.mvplearntakeout.ui.fragment.GoodsFragment
import org.json.JSONObject
import se.emilsjolander.stickylistheaders.StickyListHeadersListView

class GoodsFragmentPresenter(val goodsFragment: GoodsFragment) : BasePresenter() {
    override fun parserJson(json: String) {
        Log.e("GoodsFragmentPresenter:", json)
        var jsonObject = JSONObject(json)
        val list = jsonObject.getString("list")
        val goodsTypeList: List<GoodsTypeInfo> = Gson().fromJson<List<GoodsTypeInfo>>(list, object : TypeToken<List<GoodsTypeInfo>>() {}.type)
        if (goodsTypeList.size > 0) {

            var goodslist: ArrayList<GoodsInfo> = ArrayList()
            for (goodsType in goodsTypeList) {
                val goods = goodsType.list
                for (good in goods) {
                    good.typeId = goodsType.id
                    good.typeName = goodsType.name
                    goodslist.add(good)
                }
            }
            goodsFragment.ongoodsSuccess(goodsTypeList, goodslist)
        }

    }

    fun getBusinessInfo(sellerId: String) {
        val businessCall = takeoutService.getBusinessInfo(sellerId)
        businessCall.enqueue(callback)
    }


    //左边元素
    lateinit var rvGoodsType: RecyclerView
    lateinit var rvGoodsTypeAdapter: RvGoodsTypeAdapter

    //右边元素
    lateinit var slhGoodslist: StickyListHeadersListView
    lateinit var stickyAdapter: StickyAdapter

    //表示是不是客户手滑动
    var isCustomScroll = false

    //处理左右联动的业务逻辑
    fun proceessLink(rvGoodsType: RecyclerView, rvGoodsTypeAdapter: RvGoodsTypeAdapter, slhGoodslist: StickyListHeadersListView, stickyAdapter: StickyAdapter) {
        //元素赋值
        this.rvGoodsType = rvGoodsType
        this.rvGoodsTypeAdapter = rvGoodsTypeAdapter
        this.slhGoodslist = slhGoodslist
        this.stickyAdapter = stickyAdapter

        //为右边设置监听,让右边驱动左边
        slhGoodslist.setOnScrollListener(object : AbsListView.OnScrollListener {
            override fun onScroll(view: AbsListView?, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
                //客户手滑动的才刷新右边
                if (isCustomScroll) {
                    Log.e("onScroll", "onScroll")
                    //找到所属的typeID
                    val typeId = stickyAdapter.goodsList.get(firstVisibleItem).typeId
                    rvGoodsTypeAdapter.setSelectItem(typeId)
                }
            }

            override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {
                //是客户手滑动的，不是左边驱动的
                isCustomScroll = true
                Log.e("onScrollStateChanged", "onScrollStateChanged")
            }
        })


    }

    //左边驱动右边
    fun setRightSelection(typeId: Int) {
        var index = -1
        for (i in 0 until stickyAdapter.goodsList.size) {
            if (stickyAdapter.goodsList.get(i).typeId == typeId) {
                index = i
                break
            }
        }
        if (index != -1) {
            slhGoodslist.setSelection(index)
        }
    }

    //让左边的选中条目展示出来
    fun showTypeSelect(currentSelectItem: Int) {
        val firstVisibleItemPosition = (rvGoodsType.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        val lastVisibleItemPosition = (rvGoodsType.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
        if (currentSelectItem < firstVisibleItemPosition || currentSelectItem > lastVisibleItemPosition) {
            rvGoodsType.scrollToPosition(currentSelectItem)
        }
    }

    //清空购物车
    fun clearAll() {
        //清空所有红点
        for (goodsTypeInfo in rvGoodsTypeAdapter.goodsTypeList) {
            goodsTypeInfo.redDotCount = 0
        }
        rvGoodsTypeAdapter.notifyDataSetChanged()
        //刷新右侧
        for (goodsInfo in stickyAdapter.goodsList) {
            goodsInfo.count=0
        }
        stickyAdapter.notifyDataSetChanged()

       //刷新购物车
        (goodsFragment.activity as BusinessActivity).upDateCart()
        (goodsFragment.activity as BusinessActivity).showOrHideCart()


        //清空缓存
//        TakeoutApp.sInstance.clearCacheSelectedInfo(seller.id.toInt())
    }

}
