package com.xjd.mvplearntakeout.ui.activity

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.TypedValue
import android.view.View
import android.widget.Toast
import com.heima.takeout.utils.SMSUtil
import com.xjd.mvplearntakeout.R
import com.xjd.mvplearntakeout.model.Dao.AddressDao
import com.xjd.mvplearntakeout.model.bean.AddressBean
import com.xjd.mvplearntakeout.utils.BarUtils
import kotlinx.android.synthetic.main.activity_add_edit_receipt_address.*
import org.jetbrains.anko.toast

/**
 * Created by Administrator on 2018-11-12.
 */
class AddOrEditAddressActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var addressDao: AddressDao
    val titles = arrayOf("无", "家", "学校", "公司")
    val colors = arrayOf("#778899", "#ff3399", "#ff9933", "#33ff99")

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ib_business_back -> finish()
            R.id.ib_add_phone_other -> rl_phone_other.visibility = View.VISIBLE
            R.id.ib_delete_phone -> et_phone_other.setText("")
            R.id.ib_delete_phone_other -> et_phone_other.setText("")
            R.id.ib_select_label -> selectLabel()
            R.id.btn_ok -> {
                if (checkReceiptAddressInfo()) {
                    insertOrUpdateAdderssToDb()
                }
            }
        }
    }

    private fun updateAdderssToDb() {


    }

    private fun insertOrUpdateAdderssToDb() {
        var username = et_name.text.toString().trim()
        var sex = "女士"
        if (rb_man.isChecked) {
            sex = "先生"
        }
        var phone = et_phone.text.toString().trim()
        var phoneOther = et_phone_other.text.toString().trim()
        var address = et_receipt_address.text.toString().trim()
        var detailAddress = et_detail_address.text.toString().trim()
        var label = tv_label.text.toString()
        if (intent.hasExtra("addAddressBean")) {
            addressBean.user = username
            addressBean.sex = sex
            addressBean.phone = phone
            addressBean.phone_other = phoneOther
            addressBean.adress = address
            addressBean.aderss_other = detailAddress
            addressBean.label = label
            addressDao.updateAddressBean(addressBean)
            toast("更新地址成功")
            finish()

        } else {
            addressDao.addAddressBean(AddressBean(999, username, sex, phone, phoneOther, address, detailAddress, label, "38"))
            toast("新增地址成功")

        }
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_receipt_address)
        addressDao = AddressDao(this)
        initNaviBar()
        initListener()
        processIntent()
    }


    lateinit var addressBean: AddressBean
    private fun processIntent() {
        if (intent.hasExtra("addAddressBean")) {
            addressBean = intent.getSerializableExtra("addAddressBean") as AddressBean
            tv_title.text = "修改地址"
            ib_delete.visibility = View.VISIBLE
            ib_delete.setOnClickListener {
                addressDao.deleteAddressBean(addressBean)
                toast("删除此地址成功")
                finish()
            }
            et_name.setText(addressBean.user)
            val sex = addressBean.sex
            if ("先生".equals(sex)) {
                rb_man.isChecked = true
            } else {
                rb_women.isChecked = true
            }
            et_phone.setText(addressBean.phone)
            et_phone_other.setText(addressBean.phone_other)
            et_receipt_address.setText(addressBean.adress)
            et_detail_address.setText(addressBean.aderss_other)
            tv_label.text = addressBean.label
        }
    }


    private fun initNaviBar() {
        if (BarUtils.checkDeviceHasNavigationBar(this)) {
            aaera_container.setPadding(0, 0, 0, 49.dp2px())
        }
    }

    fun Int.dp2px(): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, toFloat(), resources.displayMetrics).toInt()

    }

    private fun initListener() {
        ib_business_back.setOnClickListener(this)
        ib_add_phone_other.setOnClickListener(this)
        ib_delete_phone.setOnClickListener(this)
        ib_delete_phone_other.setOnClickListener(this)
        ib_select_label.setOnClickListener(this)
        btn_ok.setOnClickListener(this)
        btn_location_address.setOnClickListener(this)
        et_phone.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (!TextUtils.isEmpty(s)) {
                    ib_delete_phone.visibility = View.VISIBLE
                } else {
                    ib_delete_phone.visibility = View.GONE
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        et_phone_other.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (!TextUtils.isEmpty(s)) {
                    ib_delete_phone_other.visibility = View.VISIBLE
                } else {
                    ib_delete_phone_other.visibility = View.GONE
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    fun checkReceiptAddressInfo(): Boolean {
        val name = et_name.getText().toString().trim()
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "请填写联系人", Toast.LENGTH_SHORT).show()
            return false
        }
        val phone = et_phone.getText().toString().trim()
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "请填写手机号码", Toast.LENGTH_SHORT).show()
            return false
        }
        if (!SMSUtil.isMobileNO(phone)) {
            Toast.makeText(this, "请填写合法的手机号", Toast.LENGTH_SHORT).show()
            return false
        }
        val receiptAddress = et_receipt_address.getText().toString().trim()
        if (TextUtils.isEmpty(receiptAddress)) {
            Toast.makeText(this, "请填写收获地址", Toast.LENGTH_SHORT).show()
            return false
        }
        val address = et_detail_address.getText().toString().trim()
        if (TextUtils.isEmpty(address)) {
            Toast.makeText(this, "请填写详细地址", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun selectLabel() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("请选择地址标签")
        builder.setItems(titles, object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                tv_label.text = titles[which].toString()
                tv_label.setBackgroundColor(Color.parseColor(colors[which]))
                tv_label.setTextColor(Color.BLACK)
            }
        })
        builder.show()
    }
}