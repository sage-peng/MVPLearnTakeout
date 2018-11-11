package com.xjd.mvplearntakeout.ui.activity

import android.app.Fragment
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageButton
import com.heima.takeout.utils.PriceFormater

import com.xjd.mvplearntakeout.R
import com.xjd.mvplearntakeout.ui.adapter.GoodsVpAdapter
import com.xjd.mvplearntakeout.ui.fragment.CommentsFragmet
import com.xjd.mvplearntakeout.ui.fragment.GoodsFragment
import com.xjd.mvplearntakeout.ui.fragment.SellerFragment
import kotlinx.android.synthetic.main.activity_business.*

/**
 * Created by Administrator on 2018-11-09.
 */
class BusinessActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_business)
        initView()
    }


    val titles :List<String> = listOf("商品","商家","评论")
    val fragments:List<Fragment> = listOf(GoodsFragment(), SellerFragment(), CommentsFragmet())



    private fun initView() {
        val goodsVpAdapter = GoodsVpAdapter(fragments, fragmentManager,titles)
        vp.adapter=goodsVpAdapter
        tabs.setupWithViewPager(vp)
    }

    fun addImagebutton(ib: ImageButton, width: Int, height: Int) {
        fl_Container.addView(ib,width,height)
    }

    fun getDestinationCartLocation(): IntArray {
        var desLoc=IntArray(2)
        imgCart.getLocationInWindow(desLoc)
        return desLoc
    }

    fun removeImagebutton(ib: ImageButton) {
        fl_Container.removeView(ib)
    }

    fun upDateCart() {
        var count=0
        var totalprice=0f
        for (goodsInfo in (fragments[0] as GoodsFragment).stickyAdapter.goodsList) {
            if (goodsInfo.count>0) {
                count+=goodsInfo.count
                totalprice+=goodsInfo.newPrice!!.toFloat()*goodsInfo.count.toFloat()
            }
        }

        if (count>0){
            tvSelectNum.visibility=View.VISIBLE
        }else{
            tvSelectNum.visibility=View.GONE
        }
        tvSelectNum.text=count.toString()
        tvCountPrice.text=PriceFormater.format(totalprice)
    }
}