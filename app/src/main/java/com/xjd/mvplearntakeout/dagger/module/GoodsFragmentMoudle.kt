package com.xjd.mvplearntakeout.dagger.module

import com.xjd.mvplearntakeout.presenter.GoodsFragmentPresenter
import com.xjd.mvplearntakeout.ui.fragment.GoodsFragment
import dagger.Module
import dagger.Provides

/**
 * Created by Administrator on 2018-11-09.
 */
@Module
class GoodsFragmentMoudle(val goodsFragment: GoodsFragment) {
    @Provides
    fun providerGoodsFragmentPresenter():GoodsFragmentPresenter{
        return GoodsFragmentPresenter(goodsFragment)
    }
}