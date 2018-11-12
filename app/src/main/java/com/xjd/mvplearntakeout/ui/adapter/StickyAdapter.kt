package com.xjd.mvplearntakeout.ui.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.*
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.daimajia.slider.library.Animations.DescriptionAnimation
import com.heima.takeout.utils.PriceFormater
import com.squareup.picasso.Picasso
import com.xjd.mvplearntakeout.R
import com.xjd.mvplearntakeout.model.bean.GoodsInfo
import com.xjd.mvplearntakeout.model.bean.GoodsTypeInfo
import com.xjd.mvplearntakeout.presenter.GoodsFragmentPresenter
import com.xjd.mvplearntakeout.ui.activity.BusinessActivity
import com.xjd.mvplearntakeout.ui.fragment.GoodsFragment
import com.xjd.mvplearntakeout.utils.MyAnimationUtils
import kotlinx.android.synthetic.main.activity_business.*
import org.jetbrains.anko.find
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter

/**
 * Created by Administrator on 2018-11-09.
 */
open class StickyAdapter(val context: Context) : BaseAdapter(), StickyListHeadersAdapter {


    companion object {
        val ADD: Int = 1
        val MINUS: Int = -1
    }

    var goodsList: List<GoodsInfo> = listOf()
    var goodsFragmentPresenter: GoodsFragmentPresenter



    init {
        val goodsfragment: GoodsFragment = (context as BusinessActivity).fragments.get(0) as GoodsFragment
        goodsFragmentPresenter = goodsfragment.goodsFragmentPresenter
    }

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

    inner class ItemHolder(itemView: View) : View.OnClickListener {
        override fun onClick(v: View?) {
            when (v?.id) {
                R.id.ib_add -> doAdd()
                R.id.ib_minus -> dominus()
            }
            //通知更新购物车
            (context as BusinessActivity).upDateCart()

        }

        fun modifyTypeSum(count: Int) {
            val goodsTypeList = goodsFragmentPresenter.rvGoodsTypeAdapter.goodsTypeList
            for (i in 0 until goodsTypeList.size) {
                if (goodsTypeList.get(i).id == goodsInfo.typeId) {
                    goodsTypeList.get(i).redDotCount = goodsTypeList.get(i).redDotCount + count
                    goodsFragmentPresenter.rvGoodsTypeAdapter.notifyItemChanged(i)
                }
            }
        }

        private fun dominus() {
            var count = goodsInfo.count
            if (count == 1) {
                val showAnimation = MyAnimationUtils.hideAnimation()
                btnMinus.startAnimation(showAnimation)
                tvCount.startAnimation(showAnimation)
            }
            if (count > 0) {
                count--
                goodsInfo.count = count
                modifyTypeSum(MINUS)
                notifyDataSetChanged()
            }

        }

        private fun doAdd() {
            var count = goodsInfo.count
            if (count == 0) {
                val showAnimation = MyAnimationUtils.showAnimation()
                btnMinus.startAnimation(showAnimation)
                tvCount.startAnimation(showAnimation)
            }

            count++
            goodsInfo.count = count
            modifyTypeSum(ADD)

            notifyDataSetChanged()

            //抛物线部分
            //最上层增加一个新的控件
            val ib = ImageButton(context)

            ib.setBackgroundResource(R.drawable.button_add)


            var srcLocation: IntArray = IntArray(2)
            btnAdd.getLocationInWindow(srcLocation)
            ib.x = srcLocation[0].toFloat()
            ib.y = srcLocation[1].toFloat()

            (context as BusinessActivity).addImagebutton(ib, btnAdd.width, btnAdd.height)

            //抛物线动画
            var desLocation: IntArray = (context as BusinessActivity).getDestinationCartLocation()
            val parabolaAnimation = MyAnimationUtils.parabolaAnimation(srcLocation, desLocation)
            ib.startAnimation(parabolaAnimation)

            //动画监听
            //继承原始的接口
//            parabolaAnimation.setAnimationListener(object : Animation.AnimationListener {
//                override fun onAnimationRepeat(animation: Animation?) {}
//
//                override fun onAnimationEnd(animation: Animation?) {
//                    (context as BusinessActivity).removeImagebutton(ib)
//                }
//
//                override fun onAnimationStart(animation: Animation?) {}
//
//            })
            //继承实现好接口的对象
            parabolaAnimation.setAnimationListener(object : MyAnimationUtils.animationlistner() {
                override fun onAnimationEnd(animation: Animation?) {
                    (context as BusinessActivity).removeImagebutton(ib)
                }
            })
        }


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

            btnAdd.setOnClickListener(this)
            btnMinus.setOnClickListener(this)

        }

        fun BindData(goodsInfo: GoodsInfo) {
            this.goodsInfo = goodsInfo

            //处理页面数据绑定
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