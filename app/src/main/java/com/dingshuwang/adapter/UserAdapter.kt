package com.dingshuwang.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

import com.dingshuwang.AddressListActivity
import com.dingshuwang.AllOrderActivity
import com.dingshuwang.CollectListActivity
import com.dingshuwang.ConnectUsActivity
import com.dingshuwang.CouponsActivity
import com.dingshuwang.MyPublishActivity
import com.dingshuwang.PointActivity
import com.dingshuwang.R
import com.dingshuwang.ShopCartActivity
import com.dingshuwang.WebViewAct
import com.dingshuwang.base.BaseActivity
import com.dingshuwang.util.UIUtil

/**
 * Created by tx on 2017/6/21.
 */

class UserAdapter(private val iv_id: IntArray, private val name: Array<String>, private val activity: BaseActivity) : BaseAdapter() {
    private val inflater: LayoutInflater

    init {
        this.inflater = LayoutInflater.from(activity)
    }

    override fun getCount(): Int {
        return iv_id.size
    }

    override fun getItem(position: Int): Any {
        return iv_id[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView

        var holder: ViewHolder? = null
        if (null == convertView) {
            holder = ViewHolder()
            convertView = inflater.inflate(R.layout.adapter_user, null)
            convertView!!.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }
        holder.iv_logo = convertView.findViewById<View>(R.id.iv_logo) as ImageView
        holder.tv_name = convertView.findViewById<View>(R.id.tv_name) as TextView
        holder.iv_logo!!.setImageResource(iv_id[position])
        holder.tv_name!!.text = name[position]

        convertView.setOnClickListener(View.OnClickListener {
            if (UIUtil.isfastdoubleClick()) {
                return@OnClickListener
            }
            when (position) {
                0//我的订单
                -> AllOrderActivity.actToPay(activity)
                1 //优惠券
                -> CouponsActivity.actionCoupons(activity)
                2 //我的积分
                -> PointActivity.actPoint(activity)
                3//购物车
                -> ShopCartActivity.actShop(activity)
                4 //我的收藏
                -> CollectListActivity.actCollectList(activity)
                5 //我的发布
                -> MyPublishActivity.actMyPublish(activity)
                6//收货地址
                -> AddressListActivity.actionAddresList(activity, 1)
                7//图书回收
                -> WebViewAct.actionWebView(activity, "图书回收", "http://m.iisbn.com/rec.html")
                8 //联系我们
                -> ConnectUsActivity.actConnectUs(activity)
            }
        })



        return convertView
    }

    private inner class ViewHolder {
        lateinit var  iv_logo: ImageView
        lateinit var tv_name: TextView
    }
}
