package com.xjd.mvplearntakeout.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.daimajia.slider.library.SliderLayout
import com.daimajia.slider.library.SliderTypes.TextSliderView
import com.xjd.mvplearntakeout.R
import com.xjd.mvplearntakeout.model.bean.Seller
import kotlinx.android.synthetic.main.fragment_.view.*

/**
 * Created by Administrator on 2018-11-06.
 */

class HomeRvAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        val TITLE: Int = 0
        val SELLER: Int = 1
    }

    var mDatas: ArrayList<Seller> = ArrayList()


    override fun getItemViewType(position: Int): Int {
        when (position) {
            0 -> return TITLE
            else -> return SELLER
        }
    }


    fun setData(data: ArrayList<Seller>) {
        mDatas = data
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        if(mDatas.size>0){
            return mDatas.size+1
        }else{
            return 0
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        when(viewType){
            TITLE->return TitleHolder(View.inflate(context, R.layout.item_title, null))
//            SELLER->return SellerHolder(View.inflate(context, R.layout.item_seller, null))
            else->return SellerHolder(View.inflate(context, R.layout.item_home_common, null))
        // else->TitleHolder(View.inflate(context, R.layout.item_home_common, null))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val itemViewType = getItemViewType(position)
        when(itemViewType){
            TITLE-> (holder as TitleHolder).bindDate("头部来了")
            SELLER-> (holder as SellerHolder).bindDate(mDatas[position])
        }

    }


    var url_maps: HashMap<String,String> = HashMap()
    inner class TitleHolder(item: View) : RecyclerView.ViewHolder(item) {
        val slideLayout: SliderLayout

        init {
            slideLayout = item.findViewById<SliderLayout>(R.id.slider)
        }

        fun bindDate(data: String) {
            if (url_maps.size==0){
                url_maps.put("Hannibal", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
                url_maps.put("Big Bang Theory", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
                url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
                url_maps.put("Game of Thrones", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");
            }

            for ((key,value) in url_maps){
                val textSliderView = TextSliderView(context)
                textSliderView.description(key).image(value)
                slideLayout.addSlider(textSliderView)

            }
        }
    }

    inner class SellerHolder(item: View) : RecyclerView.ViewHolder(item) {
        val textView: TextView

        init {
            textView = item.findViewById<TextView>(R.id.tv)
        }

        fun bindDate(seller: Seller) {
            textView.text = seller.name
        }
    }

}


