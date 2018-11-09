package com.xjd.mvplearntakeout.presenter

import android.content.Context
import android.util.Log
import cn.smssdk.EventHandler
import cn.smssdk.SMSSDK
import com.google.gson.Gson
import com.j256.ormlite.android.AndroidDatabaseConnection
import com.j256.ormlite.dao.Dao
import com.xjd.mvplearntakeout.model.Dao.TakeOutOpenHelper
import com.xjd.mvplearntakeout.model.bean.User
import com.xjd.mvplearntakeout.ui.iui.ILoginActivity
import com.xjd.mvplearntakeout.utils.TakeOutApp
import kotlinx.android.synthetic.main.activity_login.*
import java.lang.Exception
import java.sql.Savepoint

/**
 * Created by Administrator on 2018-11-07.
 */

class LoginActivityPresenter(val loginActivity: ILoginActivity) : BasePresenter() {

    var eh: EventHandler = object : EventHandler() {

        override fun afterEvent(event: Int, result: Int, data: Any) {

            if (result == SMSSDK.RESULT_COMPLETE) {
                //回调完成
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    Log.e("sms11111", "验证码验证成功")
                    loginByphone(loginActivity.getPhone())
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    Log.e("sms11111", "获取验证码成功")
                } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                    //返回支持发送验证码的国家列表
                }
            } else {
                (data as Throwable).printStackTrace()
            }
        }
    }

    override fun parserJson(json: String) {
        val gson = Gson()
        val user = gson.fromJson(json, User::class.java)

        if (user != null) {
            TakeOutApp.user = user
            var connection: AndroidDatabaseConnection? = null
            var savePoint: Savepoint? = null

            try {
                val takeoutOpenHelper = TakeOutOpenHelper(loginActivity as Context)

                //事务处理
                connection = AndroidDatabaseConnection(takeoutOpenHelper.writableDatabase, true)
                savePoint = connection.setSavePoint("start")

                val userDao: Dao<User, Int> = takeoutOpenHelper.getDao(User::class.java)
                //保证如果已有此用户，就不需再创建了
                //  userDao.create(user)
//            userDao.createOrUpdate(user)


                val userList: List<User> = userDao.queryForAll()
                var isOlderUser = false
                for (i in 0 until userList.size) {
                    val u = userList.get(i)
                    if (u.id.equals(user.id)) {
                        isOlderUser = true
                    }
                }

                if (isOlderUser) {
                    //是老用户
                    userDao.update(user)
                    Log.e("LoginActivityPresenter", "老用户登录更新信息")
                } else {
                    userDao.create(user)
                    Log.e("LoginActivityPresenter", "新用户登录")
                }
                connection.commit(savePoint)
                Log.e("LoginActivityPresenter", "事务正常")
                loginActivity.onLoginSuccess(user)
            } catch (e: Exception) {
                Log.e("LoginActivityPresenter", "事务异常")
                if (connection != null)
                    connection.rollback(savePoint)
            }
        }
    }

    fun loginByphone(phone: String) {
        val loginCall = takeoutService.loginByphone(phone)
        loginCall.enqueue(callback)
    }

}
