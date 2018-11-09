package com.xjd.mvplearntakeout.ui.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.heima.takeout.utils.PriceFormater
import com.squareup.picasso.Picasso
import com.xjd.mvplearntakeout.R
import com.xjd.mvplearntakeout.model.bean.GoodsInfo
import com.xjd.mvplearntakeout.model.bean.GoodsTypeInfo
import org.jetbrains.anko.find
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter

/**
 * Created by Administrator on 2018-11-09.
 */
open class StickyAdapter(val context: Context) : BaseAdapter(), StickyListHeadersAdapter {


    var goodsList: List<GoodsInfo> = listOf()

    fun setData(goodsList: List<GoodsInfo>) {
        this.goodsList = goodsList
        notifyDataSetChanged()
    }


    override fun getItem(position: Int): Any {
        return goodsList.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return goodsList.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val itemView: View
        var itemHolder: ItemHolder
        if (convertView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.item_goods, parent, false)

            itemHolder = ItemHolder(itemView)
            itemView.setTag(itemHolder)
        } else {
            itemHolder = convertView.getTag() as ItemHolder
            itemView = convertView
        }


        itemHolder.BindData(goodsList.get(position))


        return itemView
    }


    override fun getHeaderId(position: Int): Long {
        val typeId = goodsList.get(position).typeId
        return typeId.toLong()
    }

    override fun getHeaderView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val headView = LayoutInflater.from(context).inflate(R.layout.item_type_header, parent, false)
        (headView as TextView).text = goodsList.get(position).typeName
        headView.setTextColor(Color.BLACK)
        return headView
    }

    inner class ItemHolder(itemView: View) {
        val ivIcon: ImageView
        val tvName: TextView
        val tvForm: TextView
        val tvMonthSale: TextView
        val tvNewPrice: TextView
        val tvOldPrice: TextView
        val btnAdd: ImageButton
        val btnMinus: ImageButton
        val tvCount: TextView
        lateinit var goodsInfo: GoodsInfo

        init {
            ivIcon = itemView.find(R.id.iv_icon)
            tvName = itemView.find(R.id.tv_name)
            tvForm = itemView.find(R.id.tv_form)
            tvMonthSale = itemView.find(R.id.tv_month_sale)
            tvNewPrice = itemView.find(R.id.tv_newprice)
            tvOldPrice = itemView.find(R.id.tv_oldprice)
            tvCount = itemView.find(R.id.tv_count)
            btnAdd = itemView.find(R.id.ib_add)
            btnMinus = itemView.find(R.id.ib_minus)

        }

        fun BindData(goodsInfo: GoodsInfo) {
            this.goodsInfo = goodsInfo
            Picasso.with(context).load(goodsInfo.icon).into(ivIcon)
            tvName.text = goodsInfo.name
            tvForm.text = goodsInfo.form
            tvMonthSale.text = "月售${goodsInfo.monthSaleNum}份"
            tvNewPrice.text = PriceFormater.format(goodsInfo.newPrice!!.toFloat())
//            tvNewPrice.text = "$${goodsInfo.newPrice}"
            tvOldPrice.text = "￥${goodsInfo.oldPrice}"
            tvOldPrice.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
            if (goodsInfo.oldPrice > 0) {
                tvOldPrice.visibility = View.VISIBLE
            } else {
                tvOldPrice.visibility = View.GONE
            }
            tvCount.text = goodsInfo.count.toString()
            if (goodsInfo.count > 0) {
                tvCount.visibility = View.VISIBLE
                btnMinus.visibility = View.VISIBLE
            } else {
                tvCount.visibility = View.INVISIBLE
                btnMinus.visibility = View.INVISIBLE
            }

        }
    }

}