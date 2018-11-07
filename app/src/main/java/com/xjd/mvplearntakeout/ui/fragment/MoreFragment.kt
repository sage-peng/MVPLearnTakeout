package com.xjd.mvplearntakeout.ui.fragment

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.xjd.mvplearntakeout.R
import org.jetbrains.anko.find

/**
 * Created by Administrator on 2018-11-06.
 */

class MoreFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = View.inflate(activity, R.layout.fragment_, null)
        view.find<TextView>(R.id.text).text="个人4"
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
}