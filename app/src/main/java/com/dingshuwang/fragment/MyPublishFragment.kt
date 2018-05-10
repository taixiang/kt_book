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
import com.dingshuwang.adapter.MyPublishAdapter
import com.dingshuwang.base.BaseFragment
import com.dingshuwang.model.PublishListItem
import com.dingshuwang.util.GsonUtils
import com.dingshuwang.util.RequestUtils


/**
 * Created by tx on 2017/7/7.
 */

class MyPublishFragment : BaseFragment(), DataView {


    @BindView(R.id.listview)
    lateinit var listView: ListView

    override val fragmentTitle: String
        get() = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_my_publish, null)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getPublish()
    }

    private fun getPublish() {
        val url = String.format(APIURL.MY_PUBLISH, Constants.USER_ID)
        RequestUtils.getDataFromUrl(mActivity, url, this, MYPUBLISH, false, false)
    }

    override fun onGetDataFailured(msg: String, requestTag: String) {

    }

    override fun onGetDataSuccess(result: String, requestTag: String) {
        if (result != null) {
            val item = GsonUtils.jsonToClass(result, PublishListItem::class.java)
            if (item != null && item.result) {
                listView!!.adapter = MyPublishAdapter(mActivity, item.trade_infor)
            }
        }

    }

    companion object {

        private val MYPUBLISH = "my_publish"

        fun newInstance(): MyPublishFragment {
            return MyPublishFragment()
        }
    }
}
