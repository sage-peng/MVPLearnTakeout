package com.xjd.mvplearntakeout.dagger.component

import com.xjd.mvplearntakeout.dagger.module.GoodsFragmentMoudle
import com.xjd.mvplearntakeout.ui.adapter.RvGoodsTypeAdapter
import com.xjd.mvplearntakeout.ui.fragment.GoodsFragment
import dagger.Component

/**
 * Created by Administrator on 2018-11-09.
 */
@Component(modules = arrayOf(GoodsFragmentMoudle::class))
interface GoodsFragmentComponent {
    fun inject(goodsFragment: GoodsFragment)

}