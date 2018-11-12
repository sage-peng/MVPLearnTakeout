package com.xjd.mvplearntakeout.ui.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.xjd.mvplearntakeout.R
import com.xjd.mvplearntakeout.model.bean.AddressBean
import com.xjd.mvplearntakeout.ui.activity.AddOrEditAddressActivity
import org.jetbrains.anko.find

/**
 * Created by Administrator on 2018-11-12.
 */
class RecepitAddressRvAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

     var allAddressBeans: List<AddressBean> = arrayListOf()

    fun setData(allAddressBeans: List<AddressBean>) {
        this.allAddressBeans = allAddressBeans
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        return allAddressBeans.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
       return AddressRvViewHolder(LayoutInflater.from(context).inflate(R.layout.item_receipt_address,parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as AddressRvViewHolder).bindData(allAddressBeans.get(position))
    }


    inner class AddressRvViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        override fun onClick(v: View?) {
            when(v?.id){
                R.id.iv_edit->{
                    val intent = Intent(context, AddOrEditAddressActivity::class.java)
                    intent.putExtra("addAddressBean",address)
                    context.startActivity(intent)
                }
                R.id.ira_container->{
                    val intent = Intent()
                    intent.putExtra("addAddressBean",address)
                    (context as Activity).setResult(200,intent)
                    (context as Activity).finish()
                }
            }

        }

        val ivEdit: ImageView
        val tvname: TextView
        val tv_sex:TextView
        val tv_phone:TextView
        val tv_label:TextView
        val tv_address:TextView
        lateinit var address :AddressBean

        init {
            ivEdit = itemView!!.find(R.id.iv_edit)
            tvname = itemView.find(R.id.tv_name)
            tv_sex = itemView.find(R.id.tv_sex)
            tv_phone = itemView.find(R.id.tv_phone)
            tv_label = itemView.find(R.id.tv_label)
            tv_address = itemView.find(R.id.tv_address)

            ivEdit.setOnClickListener(this)
            itemView.setOnClickListener(this)
        }
        fun bindData(address: AddressBean) {
            this.address = address
            tvname.text = address.user
            tv_sex.text = address.sex
            tv_phone.text = address.phone + "," + address.phone_other
            tv_address.text = "${address.adress},${address.aderss_other}"
            tv_label.text = address.label
        }

    }

}


