package com.xjd.mvplearntakeout.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.heima.takeout.utils.PriceFormater
import com.xjd.mvplearntakeout.R
import com.xjd.mvplearntakeout.model.bean.GoodsInfo
import com.xjd.mvplearntakeout.ui.activity.BusinessActivity
import com.xjd.mvplearntakeout.ui.fragment.GoodsFragment
import com.xjd.mvplearntakeout.utils.MyAnimationUtils
import kotlinx.android.synthetic.main.activity_business.*
import org.jetbrains.anko.find

/**
 * Created by Administrator on 2018-11-11.
 */
class CartRvAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        val ADD: Int = 1
        val MINUS: Int = -1
    }

    val goodsFragment: GoodsFragment

    init {
        goodsFragment = (context as BusinessActivity).fragments.get(0) as GoodsFragment
    }

    var goodsinfo: ArrayList<GoodsInfo> = arrayListOf()

    fun setData(goodsinfo: ArrayList<GoodsInfo>) {
        this.goodsinfo = goodsinfo
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return goodsinfo.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {

        return CartRvViewHolder(LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as CartRvViewHolder).bindData(goodsinfo.get(position))
    }


    inner class CartRvViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView), View.OnClickListener {


        override fun onClick(v: View?) {
            when (v?.id) {
                R.id.ib_add -> doAdd()
                R.id.ib_minus -> doMinus()
            }

        }

        fun modifyTypeSum(count: Int) {
            val goodsTypeList = goodsFragment.goodsFragmentPresenter.rvGoodsTypeAdapter.goodsTypeList
            for (i in 0 until goodsTypeList.size) {
                if (goodsTypeList.get(i).id == goodInfo.typeId) {
                    goodsTypeList.get(i).redDotCount = goodsTypeList.get(i).redDotCount + count
                    goodsFragment.goodsFragmentPresenter.rvGoodsTypeAdapter.notifyItemChanged(i)
                }
            }
        }

        private fun doAdd() {
            var count = goodInfo.count
            count++
            goodInfo.count = count
            notifyDataSetChanged()
            goodsFragment.stickyAdapter.notifyDataSetChanged()
            modifyTypeSum(ADD)
            (context as BusinessActivity).upDateCart()
        }


        private fun doMinus() {
            var count = goodInfo.count
            if (count == 1) {
                goodsinfo.remove(goodInfo)
                if (goodsinfo.isEmpty()){
                    (context as BusinessActivity). bottomSheetLayout.dismissSheet()
                }
            }
            if (count > 0) {
                count--
                goodInfo.count = count

                notifyDataSetChanged()
                goodsFragment.stickyAdapter.notifyDataSetChanged()
                modifyTypeSum(MINUS)
                (context as BusinessActivity).upDateCart()
            }
        }


        val tvName: TextView
        val tvAllPrice: TextView
        val tvCount: TextView
        val ibAdd: ImageButton
        val ibMinus: ImageButton
        lateinit var goodInfo: GoodsInfo

        init {
            tvName = itemView!!.find<TextView>(R.id.tv_name) as TextView
            tvAllPrice = itemView!!.find<TextView>(R.id.tv_type_all_price) as TextView
            tvCount = itemView!!.find<TextView>(R.id.tv_count) as TextView
            ibAdd = itemView!!.find<ImageButton>(R.id.ib_add) as ImageButton
            ibMinus = itemView!!.find<ImageButton>(R.id.ib_minus) as ImageButton

            ibAdd.setOnClickListener(this)
            ibMinus.setOnClickListener(this)
        }

        fun bindData(goodInfo: GoodsInfo) {
            this.goodInfo = goodInfo
            tvName.text = goodInfo.name
            tvAllPrice.text = PriceFormater.format(goodInfo.newPrice!!.toFloat() * goodInfo.count)
            tvCount.text = goodInfo.count.toString()

        }
    }


}



