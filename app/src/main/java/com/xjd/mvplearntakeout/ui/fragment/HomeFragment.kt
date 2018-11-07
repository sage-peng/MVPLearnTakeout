package com.xjd.mvplearntakeout.ui.fragment

import android.app.Fragment
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xjd.mvplearntakeout.R
import com.xjd.mvplearntakeout.dagger.component.DaggerHomeFragmentComponet
import com.xjd.mvplearntakeout.dagger.module.HomeFragmentMoudle
import com.xjd.mvplearntakeout.model.bean.Seller
import com.xjd.mvplearntakeout.presenter.HomeFragmentPresenter
import com.xjd.mvplearntakeout.ui.adapter.HomeRvAdapter
import com.xjd.mvplearntakeout.ui.iui.IHomeFragment
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

/**
 * Created by Administrator on 2018-11-06.
 */

class HomeFragment : Fragment(), IHomeFragment {



    //声明成员变量的时候可以懒加载

    lateinit var rv_home: RecyclerView
    var mDatas: ArrayList<Seller> = ArrayList()
    lateinit var homeRvAdapter: HomeRvAdapter

    @Inject
    lateinit var homeFragmentPresenter:HomeFragmentPresenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = View.inflate(activity, R.layout.fragment_home, null)
//        homeFragmentPresenter = HomeFragmentPresenter(this)
        DaggerHomeFragmentComponet.builder()
                .homeFragmentMoudle(HomeFragmentMoudle(this)).build()
                .inject(this)

        rv_home = view.findViewById<RecyclerView>(R.id.rv_home)
        rv_home.layoutManager = LinearLayoutManager(activity)
        homeRvAdapter = HomeRvAdapter(activity)
        rv_home.adapter = homeRvAdapter
        initData()
        initListener();
        return view
    }

    var sum: Int = 0
    var alpha: Int = 0x55
    var distance: Int = 300
    /**
     * #553190E8
     */
    private fun initListener() {
        rv_home.setOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                sum += dy
                if (sum > distance) {
                    alpha = 255

                } else {
                    alpha = (255-0x55)* sum / distance + 0x55

                }
                Log.e("HomeRV", "distance is ${sum} alpha is  ${alpha}")
                ll_title_container.setBackgroundColor(Color.argb(alpha, 0x31, 0x90, 0xE8))
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })

    }

    private fun initData() {
        homeFragmentPresenter.getHomeInfo()
    }

    override fun onHomeSuccess(nearbySellers: List<Seller>, otherSellers: List<Seller>) {
        mDatas.addAll(nearbySellers)
        mDatas.addAll(otherSellers)
        homeRvAdapter.setData(mDatas)
    }


    override fun onHomeFailed() {

    }


}
