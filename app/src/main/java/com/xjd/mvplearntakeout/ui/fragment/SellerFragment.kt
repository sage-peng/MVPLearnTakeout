package com.xjd.mvplearntakeout.ui.fragment

import android.annotation.SuppressLint
import android.app.Fragment
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

@SuppressLint("ValidFragment")
/**
 * Created by Administrator on 2018-11-09.
 */

class SellerFragment : Fragment(){
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val textView = TextView(activity)
        textView.text="SellerFragment"
        textView.gravity= Gravity.CENTER
        return textView

    }
}