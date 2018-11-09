package com.xjd.mvplearntakeout.ui.fragment

import android.annotation.SuppressLint
import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xjd.mvplearntakeout.R
import com.xjd.mvplearntakeout.dagger.component.DaggerGoodsFragmentComponent
import com.xjd.mvplearntakeout.dagger.module.GoodsFragmentMoudle
import com.xjd.mvplearntakeout.model.bean.GoodsInfo
import com.xjd.mvplearntakeout.model.bean.GoodsTypeInfo
import com.xjd.mvplearntakeout.presenter.GoodsFragmentPresenter
import com.xjd.mvplearntakeout.ui.adapter.RvGoodsTypeAdapter
import com.xjd.mvplearntakeout.ui.adapter.StickyAdapter
import org.jetbrains.anko.find
import se.emilsjolander.stickylistheaders.StickyListHeadersListView
import javax.inject.Inject

@SuppressLint("ValidFragment")
/**
 * Created by Administrator on 2018-11-09.
 */

class GoodsFragment :Fragment(){

    @Inject
    lateinit var goodsFragmentPresenter:GoodsFragmentPresenter

    lateinit var goodsView:View

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        goodsView = LayoutInflater.from(activity).inflate(R.layout.fragment_goods, container, false)

        DaggerGoodsFragmentComponent.builder().goodsFragmentMoudle(GoodsFragmentMoudle(this)).build().inject(this)
        goodsFragmentPresenter.getBusinessInfo("1")

        initView()

        return goodsView
    }


    lateinit var rvGoodsTypeAdapter: RvGoodsTypeAdapter
    lateinit var stickyAdapter: StickyAdapter
    private fun initView() {
        val rvGoodsType = goodsView.find<RecyclerView>(R.id.rv_goods_type)

        rvGoodsType.layoutManager=LinearLayoutManager(activity)
        rvGoodsTypeAdapter = RvGoodsTypeAdapter(activity)
        rvGoodsType.adapter=rvGoodsTypeAdapter


        val slhGoodslist = goodsView.find<StickyListHeadersListView>(R.id.slhlv)
//        stickyAdapter = StickyAdapter(activity)
//        slhGoodslist.adapter=stickyAdapter

    }

    fun ongoodsSuccess(goodsTypeList: List<GoodsTypeInfo>, goodslist: ArrayList<GoodsInfo>) {
        rvGoodsTypeAdapter.setData(goodsTypeList)
//        stickyAdapter.setData(goodslist)
    }


}


