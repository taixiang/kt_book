package com.dingshuwang.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import butterknife.BindView

import com.dingshuwang.APIURL
import com.dingshuwang.Constants
import com.dingshuwang.DataView
import com.dingshuwang.R
import com.dingshuwang.adapter.TopayAdapter
import com.dingshuwang.base.BaseFragment
import com.dingshuwang.model.WaitPayItem
import com.dingshuwang.util.GsonUtils
import com.dingshuwang.util.RequestUtils


/**
 * Created by Administrator on 2016/7/28.
 */
class ForExpFragment : BaseFragment(), DataView {

    @BindView(R.id.listview)
    lateinit var listView: ListView

    override val fragmentTitle: String
        get() = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_to_pay, null)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        doData()
    }

    private fun doData() {
        val url = String.format(APIURL.FOR_EXP, Constants.USER_ID)
        RequestUtils.getDataFromUrl(mActivity, url, this, "", false, false)
    }

    override fun onGetDataFailured(msg: String, requestTag: String) {

    }

    override fun onGetDataSuccess(result: String, requestTag: String) {
        if (null != result) {
            val item = GsonUtils.jsonToClass(result, WaitPayItem::class.java)
            if (null != item && null != item.shops) {
                listView!!.adapter = TopayAdapter(item.shops!!, mActivity, 3)
            }
        }
    }

    companion object {

        fun newInstance(): ForExpFragment {
            return ForExpFragment()
        }
    }
}
