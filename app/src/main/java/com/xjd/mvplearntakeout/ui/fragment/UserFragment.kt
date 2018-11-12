package com.xjd.mvplearntakeout.ui.fragment

import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.xjd.mvplearntakeout.R
import com.xjd.mvplearntakeout.ui.activity.LoginActivity
import com.xjd.mvplearntakeout.appconfig.TakeOutApp
import org.jetbrains.anko.find

/**
 * Created by Administrator on 2018-11-06.
 */

class UserFragment : Fragment() {

    lateinit var ll_userinfo: LinearLayout
    lateinit var username: TextView
    lateinit var phone: TextView
    lateinit var ivLogin:ImageView

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = View.inflate(activity, R.layout.fragment_user, null)
        ll_userinfo = view.find<LinearLayout>(R.id.ll_userinfo)
        username = view.find<TextView>(R.id.username)
        phone = view.find<TextView>(R.id.phone)
        ivLogin = view.find<ImageView>(R.id.login)

        ivLogin.setOnClickListener {
            val intent = Intent(activity, LoginActivity::class.java)
            activity.startActivity(intent)
        }
        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        if (TakeOutApp.user.id != -1) {
            ivLogin.visibility = View.GONE
            ll_userinfo.visibility = View.VISIBLE
            username.text = "欢迎您，${TakeOutApp.user.name}"
            phone.text = TakeOutApp.user.phone
        } else {
            //未登录
            ll_userinfo.visibility = View.GONE
            ivLogin.visibility = View.VISIBLE
        }
    }
}