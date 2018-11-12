package com.xjd.mvplearntakeout.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.util.TypedValue
import android.widget.Toast
import com.heima.takeout.utils.SMSUtil.isMobileNO
import com.xjd.mvplearntakeout.R
import com.xjd.mvplearntakeout.model.Dao.AddressDao
import com.xjd.mvplearntakeout.ui.adapter.RecepitAddressRvAdapter
import com.xjd.mvplearntakeout.utils.BarUtils
import kotlinx.android.synthetic.main.activity_add_edit_receipt_address.*
import kotlinx.android.synthetic.main.activity_address_list.*
import kotlinx.android.synthetic.main.activity_business.*

/**
 * Created by Administrator on 2018-11-12.
 */
class RecepitAddressActivity : AppCompatActivity() {

    lateinit var addressDao:AddressDao
    lateinit var recepitAddressRvAdapter: RecepitAddressRvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address_list)
        initNaviBar()
        initView()
        initDataBase()
        tv_add_address.setOnClickListener {
            startActivity(Intent(this,AddOrEditAddressActivity::class.java))
        }
    }

    private fun initDataBase() {
        addressDao = AddressDao(this)
    }

    private fun initView() {
        rv_receipt_address.layoutManager=LinearLayoutManager(this)

        recepitAddressRvAdapter = RecepitAddressRvAdapter(this)
        rv_receipt_address.adapter= recepitAddressRvAdapter
    }

    private fun initNaviBar() {
        if (BarUtils.checkDeviceHasNavigationBar(this)) {
            aal_container.setPadding(0, 0, 0, 49.dp2px())
        }
    }
    fun Int.dp2px(): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, toFloat(), resources.displayMetrics).toInt()

    }

    override fun onStart() {
        super.onStart()

        val allAddressBeans = addressDao.queryAllAddressBean()
        if (!allAddressBeans.isEmpty()){
            recepitAddressRvAdapter.setData(allAddressBeans)
        }
    }





}