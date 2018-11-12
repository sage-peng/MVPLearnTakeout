package com.xjd.mvplearntakeout.model.bean

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import java.io.Serializable

/**
 * Created by Administrator on 2018-11-12.
 */
@DatabaseTable(tableName = "t_address")
class AddressBean() : Serializable{
    constructor(id: Int, user: String, sex: String, phone: String, phone_other: String, adress: String, aderss_other: String, label: String, userId: String):this() {
        this.id = id
        this.user = user
        this.sex = sex
        this.phone = phone
        this.phone_other = phone_other
        this.adress = adress
        this.aderss_other = aderss_other
        this.label = label
        this.userId = userId
    }


    @DatabaseField(generatedId = true)
    var id: Int = 0
    @DatabaseField(columnName = "user")
    var user: String = ""
    @DatabaseField(columnName = "sex")
    var sex: String = ""
    @DatabaseField(columnName = "phone")
    var phone : String= ""
    @DatabaseField(columnName = "phone_other")
    var phone_other: String = ""
    @DatabaseField(columnName = "adress")
    var adress: String = ""
    @DatabaseField(columnName = "aderss_other")
    var aderss_other : String= ""
    @DatabaseField(columnName = "label")
    var label : String= ""
    @DatabaseField(columnName = "userId")
    var userId: String = "38"

}