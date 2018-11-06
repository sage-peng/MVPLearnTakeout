package com.xjd.mvplearntakeout.ui.activity

import android.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.xjd.mvplearntakeout.R
import com.xjd.mvplearntakeout.ui.fragment.HomeFragment
import com.xjd.mvplearntakeout.ui.fragment.MoreFragment
import com.xjd.mvplearntakeout.ui.fragment.OrderFragment
import com.xjd.mvplearntakeout.ui.fragment.UserFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val fragments: List<Fragment> =  listOf<Fragment>(HomeFragment(), OrderFragment(), UserFragment(), MoreFragment())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        changeIndex(0)
        initBottomBar()
    }

    private fun initBottomBar() {
        for (i in 0 until main_common_bar.childCount) {
            main_common_bar.getChildAt(i).setOnClickListener(
                    { view -> changeIndex(i) }
            )
        }
    }

    private fun changeIndex(index: Int) {
        for (i in 0 until main_common_bar.childCount) {
            val child = main_common_bar.getChildAt(i)
            if (i == index) {
                setEnable(child, false)//禁用不能点
            } else {
                setEnable(child, true)
            }
        }
        fragmentManager.beginTransaction().replace(R.id.main_content, fragments[index]).commit()

    }

    private fun setEnable(child: View, isEnable: Boolean) {
        child.isEnabled = isEnable
        if (child is ViewGroup) {
            for (i in 0 until child.childCount) {
                setEnable(child.getChildAt(i), isEnable)
            }
        }
    }


}
