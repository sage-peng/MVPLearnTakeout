package com.xjd.mvplearntakeout.ui.adapter

import android.app.Fragment
import android.app.FragmentManager
import android.support.v13.app.FragmentPagerAdapter


/**
 * Created by Administrator on 2018-11-09.
 */

class GoodsVpAdapter(val fragments:List<Fragment>, fm : FragmentManager ,val titles :List<String> ): FragmentPagerAdapter(fm){
    override fun getItem(position: Int): Fragment {
        return fragments.get(position)
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return titles.get(position)
    }

}
