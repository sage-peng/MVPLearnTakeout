package com.xjd.mvplearntakeout.ui.activity

import android.app.AlertDialog
import android.app.Fragment
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.heima.takeout.utils.PriceFormater

import com.xjd.mvplearntakeout.R
import com.xjd.mvplearntakeout.model.bean.CacheSelectedInfo
import com.xjd.mvplearntakeout.model.bean.GoodsInfo
import com.xjd.mvplearntakeout.ui.adapter.CartRvAdapter
import com.xjd.mvplearntakeout.ui.adapter.GoodsVpAdapter
import com.xjd.mvplearntakeout.ui.fragment.CommentsFragmet
import com.xjd.mvplearntakeout.ui.fragment.GoodsFragment
import com.xjd.mvplearntakeout.ui.fragment.SellerFragment
import kotlinx.android.synthetic.main.activity_business.*
import org.jetbrains.anko.find

/**
 * Created by Administrator on 2018-11-09.
 */
class BusinessActivity : AppCompatActivity(), View.OnClickListener {

    var bottomSheetView: View? = null

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.bottom -> showOrHideCart()
            R.id.tvSubmit -> {

            }
        }

    }


     fun showOrHideCart() {


        val rv = bottomSheetView!!.find<RecyclerView>(R.id.rvCart)
        rv.layoutManager = LinearLayoutManager(this)
        val cartRvAdapter = CartRvAdapter(this)
        rv.adapter = cartRvAdapter


        //判断BottomSheetLayout内容是否显示
        if (bottomSheetLayout.isSheetShowing) {
            //关闭内容显示
            bottomSheetLayout.dismissSheet()
        } else {
            //显示BottomSheetLayout里面的内容
            var tmpGoodsinfo: ArrayList<GoodsInfo> = arrayListOf()
            for (goodsInfo in (fragments[0] as GoodsFragment).stickyAdapter.goodsList) {
                if (goodsInfo.count > 0) {
                    tmpGoodsinfo.add(goodsInfo)
                }
            }

            if (!tmpGoodsinfo.isEmpty()) {
                cartRvAdapter.setData(tmpGoodsinfo)
                bottomSheetLayout.showWithSheetView(bottomSheetView)
            }


        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_business)
        initView()
        initListener()
        processIntent()
    }

    /**
     *      intent.putExtra("hasSelectInfo",hasSelectInfo)
    intent.putExtra("seller",mSeller)
     */

    private fun processIntent() {
        if (intent.hasExtra("hasSelectInfo")){
//            val hasSelectInfo = intent.getBooleanExtra("hasSelectInfo",false)
//            intent.getSerializableExtra()
        }
    }

    private fun initListener() {
        bottom.setOnClickListener(this)
        bottomSheetView = LayoutInflater.from(this).inflate(R.layout.cart_list, window.decorView as ViewGroup, false)

        val tvClear: TextView = bottomSheetView!!.find<TextView>(R.id.tvClear)
        tvClear.setOnClickListener {
            var builder = AlertDialog.Builder(this)
            builder.setTitle("确认都不吃了么？")
            builder.setPositiveButton("是，我要减肥", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    //开始清空购物车,把购物车中商品的数量重置为0
                    val goodsFragment: GoodsFragment = fragments.get(0) as GoodsFragment
                    goodsFragment.goodsFragmentPresenter.clearAll()

                }


            })
            builder.setNegativeButton("不，我还要吃", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {

                }
            })
            builder.show()
        }
    }


    val titles: List<String> = listOf("商品", "商家", "评论")
    val fragments: List<Fragment> = listOf(GoodsFragment(), SellerFragment(), CommentsFragmet())


    private fun initView() {
        val goodsVpAdapter = GoodsVpAdapter(fragments, fragmentManager, titles)
        vp.adapter = goodsVpAdapter
        tabs.setupWithViewPager(vp)
    }

    fun addImagebutton(ib: ImageButton, width: Int, height: Int) {
        fl_Container.addView(ib, width, height)
    }

    fun getDestinationCartLocation(): IntArray {
        var desLoc = IntArray(2)
        imgCart.getLocationInWindow(desLoc)
        return desLoc
    }

    fun removeImagebutton(ib: ImageButton) {
        fl_Container.removeView(ib)
    }

    fun upDateCart() {
        var count = 0
        var totalprice = 0f
        for (goodsInfo in (fragments[0] as GoodsFragment).stickyAdapter.goodsList) {
            if (goodsInfo.count > 0) {
                count += goodsInfo.count
                totalprice += goodsInfo.newPrice!!.toFloat() * goodsInfo.count.toFloat()
            }
        }

        if (count > 0) {
            tvSelectNum.visibility = View.VISIBLE
        } else {
            tvSelectNum.visibility = View.GONE
        }
        tvSelectNum.text = count.toString()
        tvCountPrice.text = PriceFormater.format(totalprice)
    }

    override fun onDestroy() {
        super.onDestroy()
        for (goodsInfo in (fragments[0] as GoodsFragment).stickyAdapter.goodsList) {
            if (goodsInfo.count > 0) {
                CacheSelectedInfo()
            }
        }

    }
}