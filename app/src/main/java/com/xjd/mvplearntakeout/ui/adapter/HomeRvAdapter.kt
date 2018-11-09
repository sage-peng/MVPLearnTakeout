package com.xjd.mvplearntakeout.ui.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.daimajia.slider.library.SliderLayout
import com.daimajia.slider.library.SliderTypes.TextSliderView
import com.squareup.picasso.Picasso
import com.xjd.mvplearntakeout.R
import com.xjd.mvplearntakeout.model.bean.Seller
import com.xjd.mvplearntakeout.ui.activity.BusinessActivity
import org.jetbrains.anko.find

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
        if (mDatas.size > 0) {
            return mDatas.size + 1
        } else {
            return 0
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            TITLE -> return TitleHolder(View.inflate(context, R.layout.item_title, null))
            else -> return SellerHolder(View.inflate(context, R.layout.item_seller, null))
//            else->return SellerHolder(View.inflate(context, R.layout.item_home_common, null))
//         else->TitleHolder(View.inflate(context, R.layout.item_home_common, null))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val itemViewType = getItemViewType(position)
        when (itemViewType) {
            TITLE -> (holder as TitleHolder).bindData("头部来了")
            SELLER -> (holder as SellerHolder).bindData(mDatas[position - 1])//注意位置
        }

    }


    var url_maps: HashMap<String, String> = HashMap()

    inner class TitleHolder(item: View) : RecyclerView.ViewHolder(item) {
        val slideLayout: SliderLayout

        init {
            slideLayout = item.findViewById<SliderLayout>(R.id.slider)
        }

        fun bindData(data: String) {

            if (url_maps.size == 0) {
                url_maps.put("Hannibal", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
                url_maps.put("Big Bang Theory", "https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=2378317207,3360913767&fm=173&app=49&f=JPEG?w=584&h=375&s=560025E3DB6A9B5118F9D40F030030C1");
                url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
                url_maps.put("Game of Thrones", "https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=3835797144,1240647964&fm=173&app=49&f=JPEG?w=594&h=375&s=D8F121C6CC1210DC20558C8A0300B092");


                for ((key, value) in url_maps) {
                    val textSliderView = TextSliderView(context)
                    textSliderView.description(key).image(value)
                    slideLayout.addSlider(textSliderView)

                }
            }
        }
    }

    inner class SellerHolder(item: View) : RecyclerView.ViewHolder(item) {
        val tvTitle: TextView
        val ivLogo: ImageView
        val rbScore: RatingBar
        val tvSale: TextView
        val tvSendPrice: TextView
        val tvDistance: TextView
        lateinit var mSeller: Seller

        init {
            tvTitle = item.find(R.id.tv_title)
            ivLogo = item.find(R.id.seller_logo)
            rbScore = item.find(R.id.ratingBar)

            tvSale = item.find(R.id.tv_home_sale)
            tvSendPrice = item.find(R.id.tv_home_send_price)
            tvDistance = item.find(R.id.tv_home_distance)

            item.setOnClickListener {
                val intent = Intent(context, BusinessActivity::class.java)
                context.startActivity(intent)
            }
        }

        fun bindData(seller: Seller) {
            this.mSeller = seller
            tvTitle.text = seller.name
            //TODO:赋值其他字段
            Picasso.with(context).load(seller.icon).into(ivLogo)
            rbScore.rating = seller.score.toFloat()
            tvSale.text = "月售${seller.sale}单"
            tvSendPrice.text = "￥${seller.sendPrice}起送/配送费￥${seller.deliveryFee}"
            tvDistance.text = seller.distance
        }
    }

}


