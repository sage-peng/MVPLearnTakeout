package com.xjd.mvplearntakeout.ui.iui

import com.xjd.mvplearntakeout.model.bean.Order

/**
 * Created by Administrator on 2018-11-07.
 */

interface IOrderFragment {

    fun onOrderFailed()
    fun onOrderSuccess(orders: List<Order>)
}