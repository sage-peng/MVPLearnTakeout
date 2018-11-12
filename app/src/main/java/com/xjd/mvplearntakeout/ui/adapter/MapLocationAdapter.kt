package com.xjd.mvplearntakeout.ui.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.amap.api.services.core.PoiItem
import com.xjd.mvplearntakeout.R
import org.jetbrains.anko.find

/**
 * Created by Administrator on 2018-11-12.
 */
class MapLocationAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var poiList = arrayListOf<PoiItem>()


    fun setData(poiList: ArrayList<PoiItem>) {
        this.poiList = poiList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return poiList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return MapLocationViewHolder(LayoutInflater.from(context).inflate(R.layout.item_around_address, parent, false))
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as MapLocationViewHolder).bindData(poiList.get(position))
    }

    inner class MapLocationViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        var tvTitle: TextView
        var tvAddress: TextView

        lateinit var poiItem: PoiItem

        init {
            tvTitle = itemView!!.find<TextView>(R.id.tv_title)
            tvAddress = itemView!!.find<TextView>(R.id.tv_address)
            itemView!!.setOnClickListener {
                val intent = Intent()
                intent.putExtra("title",tvTitle.text)
                intent.putExtra("address",tvAddress.text)
                (context as Activity).setResult(200, intent)
                context.finish()
            }

        }


        fun bindData(poiItem: PoiItem) {
            this.poiItem = poiItem
            tvTitle.text = poiItem.title
            tvAddress.text = poiItem.snippet //摘要
        }

    }
}