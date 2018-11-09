package com.xjd.mvplearntakeout.ui.fragment

import android.app.Fragment
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.xjd.mvplearntakeout.R
import com.xjd.mvplearntakeout.dagger.component.DaggerHomeFragmentComponet
import com.xjd.mvplearntakeout.dagger.component.DaggerOrderFragmentComponet
import com.xjd.mvplearntakeout.dagger.module.OrderFragmentMoudle
import com.xjd.mvplearntakeout.model.bean.Order
import com.xjd.mvplearntakeout.presenter.OrderFragmentPresenter
import com.xjd.mvplearntakeout.ui.adapter.OrderRvAdapter
import com.xjd.mvplearntakeout.ui.iui.IOrderFragment
import com.xjd.mvplearntakeout.utils.TakeOutApp
import kotlinx.android.synthetic.main.fragment_order.*
import org.jetbrains.anko.act
import org.jetbrains.anko.find
import org.jetbrains.anko.toast
import javax.inject.Inject

/**
 * Created by Administrator on 2018-11-06.
 */

class OrderFragment : Fragment(), IOrderFragment {


    lateinit var orderRvAdapter:OrderRvAdapter

    @Inject
    lateinit var orderFragmentPresenter: OrderFragmentPresenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = View.inflate(activity, R.layout.fragment_order, null)
//        orderFragmentPresenter = OrderFragmentPresenter(this)
        DaggerOrderFragmentComponet.builder().orderFragmentMoudle(OrderFragmentMoudle(this)).build().inject(this)

        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val id = TakeOutApp.user.id
        if (id==-1){
            toast("请先登录")
        }else{
            orderFragmentPresenter.getListInfo(id.toString())
        }
        initView(view)

    }

    lateinit var swipesRefreshLayout:SwipeRefreshLayout

    private fun initView(view:View ) {
        val rvOrder = view.find<RecyclerView>(R.id.rv_order_list)
        rvOrder.layoutManager=LinearLayoutManager(activity)
        orderRvAdapter = OrderRvAdapter(activity)
        rvOrder.adapter=orderRvAdapter

        swipesRefreshLayout = view.find<SwipeRefreshLayout>(R.id.srl_order)
        swipesRefreshLayout.setOnRefreshListener{
            if (TakeOutApp.user.id==-1){
                toast("请重新登录")
            }else{
                orderFragmentPresenter.getListInfo(TakeOutApp.user.id.toString())
            }
        }

    }

    override fun onOrderFailed() {
        toast("服务器繁忙")
        swipesRefreshLayout.isRefreshing=false
    }

    override fun onOrderSuccess(orders: List<Order>) {
        orderRvAdapter.setData(orders)
        swipesRefreshLayout.isRefreshing=false
    }
}