package com.xjd.mvplearntakeout.model.Dao

import android.content.Context
import android.util.Log
import com.j256.ormlite.dao.Dao
import com.xjd.mvplearntakeout.model.bean.AddressBean

/**
 * Created by Administrator on 2018-11-12.
 */

class AddressDao(val context: Context) {
    lateinit var addressDao: Dao<AddressBean, Int>
    init {
        val takeOutOpenHelper = TakeOutOpenHelper(context)
        addressDao = takeOutOpenHelper.getDao(AddressBean::class.java)

    }

    fun addAddressBean(addressBean: AddressBean){
        try {
            addressDao.create(addressBean)
        }catch (e:Exception){
            Log.e("addAddressBean", e.localizedMessage)
        }
    }

    fun deleteAddressBean(addressBean: AddressBean){
        try {
            addressDao.delete(addressBean)
        }catch (e:Exception){
            Log.e("deleteAddressBean", e.localizedMessage)
        }
    }

    fun updateAddressBean(addressBean: AddressBean){
        try {
            addressDao.update(addressBean)
        }catch (e:Exception){
            Log.e("updateAddressBean", e.localizedMessage)
        }
    }

    fun queryAllAddressBean( ):List<AddressBean>{
        try {
            return addressDao.queryForAll()
        }catch (e:Exception){
            Log.e("updateAddressBean", e.localizedMessage)
            return arrayListOf()
        }
    }
}
