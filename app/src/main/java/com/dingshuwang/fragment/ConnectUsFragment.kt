package com.dingshuwang.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.dingshuwang.R
import com.dingshuwang.base.BaseFragment

/**
 * Created by tx on 2017/6/28.
 */

class ConnectUsFragment : BaseFragment() {

    override val fragmentTitle: String
        get() = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_connect_us, null)
    }

    companion object {

        fun newInstance(): ConnectUsFragment {
            return ConnectUsFragment()
        }
    }
}
