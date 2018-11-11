package com.xjd.mvplearntakeout.dagger.module

import com.xjd.mvplearntakeout.presenter.OrderFragmentPresenter
import com.xjd.mvplearntakeout.ui.fragment.OrderFragment
import dagger.Module
import dagger.Provides

/**
 * Created by Administrator on 2018-11-07.
 */


@Module
class OrderFragmentMoudle(val orderFragment: OrderFragment) {

    @Provides
    fun providerOrderFragmentPresenter(): OrderFragmentPresenter {
        return  OrderFragmentPresenter(orderFragment)
    }
}