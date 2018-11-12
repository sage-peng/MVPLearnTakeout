package com.xjd.mvplearntakeout.model.Dao

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils
import com.xjd.mvplearntakeout.model.bean.AddressBean
import com.xjd.mvplearntakeout.model.bean.User

/**
 * Created by Administrator on 2018-11-08.
 */
class TakeOutOpenHelper(val context:Context): OrmLiteSqliteOpenHelper(context,"kotlin.db",null,2) {
    override fun onCreate(database: SQLiteDatabase?, connectionSource: ConnectionSource?) {
        TableUtils.createTable(connectionSource, User::class.java)
        TableUtils.createTable(connectionSource, AddressBean::class.java)
    }

    override fun onUpgrade(database: SQLiteDatabase?, connectionSource: ConnectionSource?, oldVersion: Int, newVersion: Int) {
        TableUtils.createTable(connectionSource, AddressBean::class.java)
    }
}