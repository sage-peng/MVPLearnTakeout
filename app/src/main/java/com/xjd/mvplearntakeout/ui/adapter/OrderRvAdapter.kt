package com.xjd.mvplearntakeout.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.heima.takeout.utils.OrderObservable
import com.mob.wrappers.PaySDKWrapper
import com.xjd.mvplearntakeout.R
import com.xjd.mvplearntakeout.R.string.order
import com.xjd.mvplearntakeout.model.bean.Order
import org.jetbrains.anko.find
import org.json.JSONObject
import java.util.*

/**
 * Created by Administrator on 2018-11-09.
 */

class OrderRvAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Observer {
    init {
        OrderObservable.instance.addObserver(this)
    }
    override fun update(o: Observable?, arg: Any?) {
        val json = arg as String
        val jsonObject = JSONObject(json)
        val id = jsonObject.getString("id")

        val pushType = jsonObject.getString("type")
        for (order in orders){
            if (order.id!!.equals(id)){
                order.type=pushType
                notifyItemChanged(orders.indexOf(order))
                break
            }
        }

    }

    private var orders: List<Order> = ArrayList<Order>()

    fun setData(orders: List<Order>) {
        this.orders = orders
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return orders.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        //list类型在填充的时候，如果是matchParent的方式填充，下面的方法没有指定parent，
        // 那么是安装unspeciay的模式，那么按最小的填充会填充不满
        //val item = View.inflate(context, R.layout.item_order_item, null)
        //应该这样填充
        val item = LayoutInflater.from(context).inflate(R.layout.item_order_item, parent, false)
        return OrderViewHolder(item)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as OrderViewHolder).bindView(orders.get(position))
    }


    inner class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvSellerName: TextView
        val tvOrderType: TextView

        init {
            tvSellerName = itemView.find(R.id.tv_order_item_seller_name)
            tvOrderType = itemView.find(R.id.tv_order_item_type) //订单状态
        }

        fun bindView(order: Order) {
            tvSellerName.text = order.seller!!.name
            tvOrderType.text = getOrderTypeInfo(order.type)
        }
    }


    private fun getOrderTypeInfo(type: String): String {
        /**
         * 订单状态
         * 1 未支付 2 已提交订单 3 商家接单  4 配送中,等待送达 5已送达 6 取消的订单
         */
        //            public static final String ORDERTYPE_UNPAYMENT = "10";
        //            public static final String ORDERTYPE_SUBMIT = "20";
        //            public static final String ORDERTYPE_RECEIVEORDER = "30";
        //            public static final String ORDERTYPE_DISTRIBUTION = "40";
        //            public static final String ORDERTYPE_SERVED = "50";
        //            public static final String ORDERTYPE_CANCELLEDORDER = "60";

        var typeInfo = ""
        when (type) {
            OrderObservable.ORDERTYPE_UNPAYMENT -> typeInfo = "未支付"
            OrderObservable.ORDERTYPE_SUBMIT -> typeInfo = "已提交订单"
            OrderObservable.ORDERTYPE_RECEIVEORDER -> typeInfo = "商家接单"
            OrderObservable.ORDERTYPE_DISTRIBUTION -> typeInfo = "配送中"
            OrderObservable.ORDERTYPE_SERVED -> typeInfo = "已送达"
            OrderObservable.ORDERTYPE_CANCELLEDORDER -> typeInfo = "取消的订单"
        }
        return typeInfo
    }


}


