package com.xjd.mvplearntakeout.ui.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.xjd.mvplearntakeout.R
import com.xjd.mvplearntakeout.model.bean.GoodsTypeInfo
import org.jetbrains.anko.find

/**
 * Created by Administrator on 2018-11-09.
 */
class RvGoodsTypeAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    var goodsTypeList: List<GoodsTypeInfo> = listOf()


    fun setData(goodsTypeList: List<GoodsTypeInfo>) {
        this.goodsTypeList = goodsTypeList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return goodsTypeList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_type, parent, false)
        return GoodsTypeItemHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as GoodsTypeItemHolder).bindData(goodsTypeList.get(position), position)
    }
    var currentSelectItem: Int = 0
    inner class GoodsTypeItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mPosition: Int = 0
        val redDotCount: TextView
        val type: TextView

        init {
            redDotCount = itemView.find<TextView>(R.id.tvRedDotCount)
            type = itemView.find<TextView>(R.id.type)
            itemView.setOnClickListener {
                currentSelectItem = mPosition
                notifyDataSetChanged()
            }
        }

        fun bindData(goodsTypeInfo: GoodsTypeInfo, position: Int) {
            mPosition = position
            Log.e("mPosition:",mPosition.toString())
            type.text = goodsTypeInfo.name
            if (currentSelectItem == position) {
                itemView.setBackgroundColor(Color.WHITE)
                type.setTextColor(Color.BLACK)
                type.setTypeface(Typeface.DEFAULT_BOLD)
            }else{
                itemView.setBackgroundColor(Color.parseColor("#b9dedcdc"))
                type.setTextColor(Color.GRAY)
                type.setTypeface(Typeface.DEFAULT)
            }

        }


    }


}




