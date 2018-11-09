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

class CommentsFragmet : Fragment(){
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val textView = TextView(activity)
        textView.text="CommentsFragmet"
        textView.gravity= Gravity.CENTER
        return textView

    }
}