package com.xjd.mvplearntakeout.dagger.component

import com.xjd.mvplearntakeout.dagger.module.HomeFragmentMoudle
import com.xjd.mvplearntakeout.dagger.module.OrderFragmentMoudle
import com.xjd.mvplearntakeout.ui.fragment.HomeFragment
import com.xjd.mvplearntakeout.ui.fragment.OrderFragment
import dagger.Component

/**
 * Created by Administrator on 2018-11-07.
 */

@Component(modules = arrayOf(OrderFragmentMoudle::class))
interface OrderFragmentComponet {
    fun inject(orderFragment: OrderFragment)
}