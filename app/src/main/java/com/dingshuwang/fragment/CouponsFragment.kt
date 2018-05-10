package com.dingshuwang.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.RadioButton
import android.widget.RadioGroup
import butterknife.BindView

import com.dingshuwang.APIURL
import com.dingshuwang.Constants
import com.dingshuwang.DataView
import com.dingshuwang.R
import com.dingshuwang.adapter.CouponsAdapter
import com.dingshuwang.base.BaseFragment
import com.dingshuwang.model.CouponsItem
import com.dingshuwang.util.GsonUtils
import com.dingshuwang.util.RequestUtils


/**
 * Created by tx on 2017/6/21.
 */

class CouponsFragment : BaseFragment(), DataView {

    @BindView(R.id.able_listview)
    lateinit var ableListView: ListView

    @BindView(R.id.unable_listview)
    lateinit var unableListView: ListView

    @BindView(R.id.rg)
    lateinit var radioGroup: RadioGroup
    @BindView(R.id.rb_able)
    lateinit var rb_able: RadioButton
    @BindView(R.id.rb_unable)
    lateinit var rb_unable: RadioButton

    override val fragmentTitle: String
        get() = ""

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        coupons_url()
        radioGroup!!.setOnCheckedChangeListener { group, checkedId ->
            if (rb_able!!.id == checkedId) {
                ableListView!!.visibility = View.VISIBLE
                unableListView!!.visibility = View.GONE
            } else {
                ableListView!!.visibility = View.GONE
                unableListView!!.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_coupons, null)
    }

    //优惠券
    private fun coupons_url() {
        val url = String.format(APIURL.coupons_url, Constants.USER_ID)
        RequestUtils.getDataFromUrl(mActivity, url, this, coupons_url, false, false)
    }


    override fun onGetDataFailured(msg: String, requestTag: String) {

    }

    override fun onGetDataSuccess(result: String, requestTag: String) {
        if (result != null) {
            if (coupons_url == requestTag) {
                val item = GsonUtils.jsonToClass(result, CouponsItem::class.java)
                ableListView!!.adapter = CouponsAdapter(item!!.coupons, mActivity)
            }
        }
    }

    companion object {

        //优惠券
        val coupons_url = "coupons_url"

        fun newInstance(): CouponsFragment {
            return CouponsFragment()
        }
    }
}
