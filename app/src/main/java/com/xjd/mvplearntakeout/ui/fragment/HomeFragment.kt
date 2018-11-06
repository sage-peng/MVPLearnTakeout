package com.xjd.mvplearntakeout.ui.fragment

import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.xjd.mvplearntakeout.R
import com.xjd.mvplearntakeout.model.bean.Seller
import com.xjd.mvplearntakeout.ui.adapter.HomeRvAdapter

/**
 * Created by Administrator on 2018-11-06.
 */

class HomeFragment : Fragment() {




    //声明成员变量的时候可以懒加载
    lateinit var v_home: RecyclerView
    var mDatas: ArrayList<Seller> = ArrayList()
    lateinit var homeRvAdapter:HomeRvAdapter
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = View.inflate(activity, R.layout.fragment_home, null)
        v_home = view.findViewById<RecyclerView>(R.id.rv_home)
        v_home.layoutManager = LinearLayoutManager(activity)
        homeRvAdapter = HomeRvAdapter(activity)
        v_home.adapter=homeRvAdapter
        initData()
        return view
    }

    private fun initData() {
        for (i in 0 until 100) {
            var seller=Seller()
            seller.name="我是商家：" + i
            mDatas.add(seller)
        }

        homeRvAdapter.setData(mDatas)
    }


}
