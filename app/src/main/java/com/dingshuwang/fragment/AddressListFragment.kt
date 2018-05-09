package com.dingshuwang.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import butterknife.BindView

import com.dingshuwang.APIURL
import com.dingshuwang.AddressEditActivity
import com.dingshuwang.Constants
import com.dingshuwang.DataView
import com.dingshuwang.R
import com.dingshuwang.adapter.AddressListAdapter
import com.dingshuwang.base.BaseFragment
import com.dingshuwang.model.AddressListItem
import com.dingshuwang.util.GsonUtils
import com.dingshuwang.util.RequestUtils
import com.dingshuwang.util.UIUtil

import butterknife.OnClick

/**
 * Created by tx on 2017/6/19.
 */

class AddressListFragment : BaseFragment(), DataView {

    @BindView(R.id.xListview)
    lateinit var listView: ListView

    private var tag: Int = 0

    override val fragmentTitle: String
        get() = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_addresslist, null)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tag = arguments!!.getInt("tag", 0)

    }

    override fun onResume() {
        super.onResume()
        doGetAddres()
    }

    @OnClick(R.id.ll_add)
    internal fun add_address() {
        if (UIUtil.isfastdoubleClick()) {
            return
        }
        AddressEditActivity.actionAddressEdit(mActivity, "-1")
    }

    private fun doGetAddres() {
        val url = String.format(APIURL.ADDRESS_LIST, Constants.USER_ID)
        RequestUtils.getDataFromUrl(mActivity, url, this, GET_ADDRESS, false, false)
    }

    override fun onGetDataFailured(msg: String, requestTag: String) {

    }

    override fun onGetDataSuccess(result: String, requestTag: String) {
        if (result != null) {
            if (GET_ADDRESS == requestTag) {
                val item = GsonUtils.jsonToClass(result, AddressListItem::class.java)
                if (item != null) {
                    listView!!.adapter = AddressListAdapter(mActivity, item.address, tag)
                }
            }
        }

    }

    companion object {


        /**
         * 获取收获地址列表
         */
        val GET_ADDRESS = "get_address_list"

        fun newInstance(tag: Int): AddressListFragment {
            val fragment = AddressListFragment()
            val bundle = Bundle()
            bundle.putInt("tag", tag)
            fragment.arguments = bundle
            return fragment
        }
    }
}
