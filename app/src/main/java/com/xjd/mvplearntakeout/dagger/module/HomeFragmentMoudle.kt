package com.xjd.mvplearntakeout.dagger.module


import com.xjd.mvplearntakeout.presenter.HomeFragmentPresenter
import com.xjd.mvplearntakeout.ui.fragment.HomeFragment
import dagger.Module
import dagger.Provides

/**
 * Created by Administrator on 2018-11-07.
 */


@Module
class HomeFragmentMoudle(val homeFragment: HomeFragment) {

    @Provides fun providerHomeFragmentPresenter(): HomeFragmentPresenter {
        return HomeFragmentPresenter(homeFragment)
    }
}
