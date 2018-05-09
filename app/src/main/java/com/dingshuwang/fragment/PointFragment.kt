package com.dingshuwang.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import butterknife.BindView

import com.dingshuwang.APIURL
import com.dingshuwang.Constants
import com.dingshuwang.DataView
import com.dingshuwang.R
import com.dingshuwang.adapter.PointAdapter
import com.dingshuwang.base.BaseFragment
import com.dingshuwang.model.PointItem
import com.dingshuwang.util.GsonUtils
import com.dingshuwang.util.RequestUtils
import com.dingshuwang.util.StringUtils


/**
 * Created by tx on 2017/6/22.
 */
class PointFragment : BaseFragment(), DataView {

    @BindView(R.id.listview)
    internal var listView: ListView? = null

    @BindView(R.id.tv_point)
    internal var tv_point: TextView? = null

    override val fragmentTitle: String
        get() = ""

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        point_url()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_point, null)
    }

    //积分
    private fun point_url() {
        val url = String.format(APIURL.point, Constants.USER_ID)
        RequestUtils.getDataFromUrl(mActivity, url, this, point_url, false, false)
    }

    override fun onGetDataFailured(msg: String, requestTag: String) {

    }

    override fun onGetDataSuccess(result: String, requestTag: String) {
        if (result != null) {
            if (point_url == requestTag) {
                val item = GsonUtils.jsonToClass(result, PointItem::class.java)
                if (item != null && item.message != null && item.message!!.size > 0) {
                    var total = 0.0
                    for (point in item.message!!) {
                        total += point.value
                    }
                    val d = StringUtils.doubleFormat(total)

                    tv_point!!.text = "现有" + d + "积分"
                    listView!!.adapter = PointAdapter(item.message!!, mActivity)
                }
            }
        }
    }

    companion object {

        //积分
        val point_url = "point_url"

        fun newInstance(): PointFragment {
            return PointFragment()
        }
    }
}
