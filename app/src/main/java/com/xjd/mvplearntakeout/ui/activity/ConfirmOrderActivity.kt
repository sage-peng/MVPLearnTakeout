package com.xjd.mvplearntakeout.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.TypedValue
import com.xjd.mvplearntakeout.R
import com.xjd.mvplearntakeout.model.bean.AddressBean
import com.xjd.mvplearntakeout.utils.BarUtils
import kotlinx.android.synthetic.main.activity_confirm_order.*

/**
 * Created by Administrator on 2018-11-12.
 */
class ConfirmOrderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_order)
        initNaviBar()
        rl_location.setOnClickListener {
            val intent = Intent(this, RecepitAddressActivity::class.java)
            startActivityForResult(intent, 1002)
        }
    }

    private fun initNaviBar() {
        if (BarUtils.checkDeviceHasNavigationBar(this)) {
            aco_container.setPadding(0, 0, 0, 49.dp2px())
        }
    }

    fun Int.dp2px(): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, toFloat(), resources.displayMetrics).toInt()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 200) {
            val address: AddressBean = data!!.getSerializableExtra("addAddressBean") as AddressBean
            tv_name.text = address.user

        }
    }
}