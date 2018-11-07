package com.xjd.mvplearntakeout.model.bean

import com.google.gson.FieldNamingPolicy
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

@DatabaseTable class User {

    @DatabaseField(id=true) var id: Int = 0
    @DatabaseField(columnName = "name") var name: String? = null
    @DatabaseField(columnName = "balance") var balance: Float = 0.toFloat()
    @DatabaseField(columnName = "discount") var discount: Int = 0
    @DatabaseField(columnName = "integral") var integral: Int = 0
    @DatabaseField(columnName = "phone") var phone: String? = null

}