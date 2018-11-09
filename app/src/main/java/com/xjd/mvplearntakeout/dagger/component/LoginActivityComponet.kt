package com.xjd.mvplearntakeout.dagger.component

import com.xjd.mvplearntakeout.dagger.module.HomeFragmentMoudle
import com.xjd.mvplearntakeout.dagger.module.LoginActivityMoudle
import com.xjd.mvplearntakeout.ui.activity.LoginActivity
import com.xjd.mvplearntakeout.ui.activity.MainActivity
import com.xjd.mvplearntakeout.ui.fragment.HomeFragment
import dagger.Component

/**
 * Created by Administrator on 2018-11-07.
 */
//
@Component(modules = arrayOf(LoginActivityMoudle::class))
interface LoginActivityComponet {
    fun inject(loginActivity: LoginActivity)
}
