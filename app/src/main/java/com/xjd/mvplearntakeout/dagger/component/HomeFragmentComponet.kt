package com.xjd.mvplearntakeout.dagger.component

import com.xjd.mvplearntakeout.dagger.module.HomeFragmentMoudle
import com.xjd.mvplearntakeout.ui.fragment.HomeFragment
import dagger.Component

/**
 * Created by Administrator on 2018-11-07.
 */

@Component(modules = arrayOf(HomeFragmentMoudle::class)) interface HomeFragmentComponet{
    fun inject(homeFragment: HomeFragment)
}
