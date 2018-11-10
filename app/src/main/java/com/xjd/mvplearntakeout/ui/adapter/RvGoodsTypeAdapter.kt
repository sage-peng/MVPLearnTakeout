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
import com.xjd.mvplearntakeout.presenter.GoodsFragmentPresenter
import com.xjd.mvplearntakeout.ui.activity.BusinessActivity
import com.xjd.mvplearntakeout.ui.fragment.GoodsFragment
import org.jetbrains.anko.find

/**
 * Created by Administrator on 2018-11-09.
 */
class RvGoodsTypeAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var goodsFragmentPresenter: GoodsFragmentPresenter
    init {
        val goodsfragment: GoodsFragment = (context as BusinessActivity).fragments.get(0) as GoodsFragment
        goodsFragmentPresenter = goodsfragment.goodsFragmentPresenter
    }

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

    //留给右边为左边设置选定item
    fun setSelectItem(typeId: Int) {
        //要变化了才刷，防止不必要的刷新
        if (goodsTypeList.get(currentSelectItem).id == typeId) return
        var select: Int = -1
        for (goodsType in goodsTypeList) {
            if (goodsType.id == typeId) {
                select = goodsTypeList.indexOf(goodsType)
            }
        }
        if (select != -1)
            currentSelectItem = select
        notifyDataSetChanged()

        //让修改的区域可见
        goodsFragmentPresenter.showTypeSelect(currentSelectItem)

    }

    var currentSelectItem: Int = 0

    inner class GoodsTypeItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mPosition: Int = 0
        val redDotCount: TextView
        val type: TextView

        init {
            redDotCount = itemView.find<TextView>(R.id.tvRedDotCount)
            type = itemView.find<TextView>(R.id.type)
            //左边itemView的点击事件
            itemView.setOnClickListener {

                //不是客户点的
                goodsFragmentPresenter.isCustomScroll = false
                currentSelectItem = mPosition
                goodsFragmentPresenter.setRightSelection(goodsTypeList.get(currentSelectItem).id)
                notifyDataSetChanged()
            }
        }

        fun bindData(goodsTypeInfo: GoodsTypeInfo, position: Int) {
            mPosition = position
            Log.e("mPosition:", mPosition.toString())
            type.text = goodsTypeInfo.name
            if (currentSelectItem == position) {
                itemView.setBackgroundColor(Color.WHITE)
                type.setTextColor(Color.BLACK)
                type.setTypeface(Typeface.DEFAULT_BOLD)
            } else {
                itemView.setBackgroundColor(Color.parseColor("#b9dedcdc"))
                type.setTextColor(Color.GRAY)
                type.setTypeface(Typeface.DEFAULT)
            }

            if (goodsTypeInfo.redDotCount==0){
                redDotCount.visibility=View.INVISIBLE
            }else{
                redDotCount.visibility=View.VISIBLE
            }
            redDotCount.text=goodsTypeInfo.redDotCount.toString()

        }


    }


}




