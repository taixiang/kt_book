package com.dingshuwang.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

import com.dingshuwang.R
import com.dingshuwang.base.BaseActivity
import com.dingshuwang.model.CouponsItem

/**
 * Created by tx on 2017/6/22.
 */

class CouponsAdapter(private val mList: List<CouponsItem.Coupon>?, private val activity: BaseActivity) : BaseAdapter() {
    private val inflater: LayoutInflater

    init {
        this.inflater = LayoutInflater.from(activity)
    }

    override fun getCount(): Int {
        return mList?.size ?: 0
    }

    override fun getItem(position: Int): Any {
        return mList!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        var holder: ViewHolder? = null
        if (null == convertView) {
            holder = ViewHolder()
            convertView = inflater.inflate(R.layout.adapter_coupons, null)
            convertView!!.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }
        holder.tv_price = convertView.findViewById<View>(R.id.tv_price) as TextView
        holder.tv_name = convertView.findViewById<View>(R.id.tv_name) as TextView
        holder.tv_time = convertView.findViewById<View>(R.id.tv_time) as TextView
        val item = mList!![position]
        holder.tv_price!!.text = item.special_price
        holder.tv_name!!.text = item.coupons_name
        val start = item.start_date!!.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val end = item.end_date!!.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        holder.tv_time!!.text = start[0] + "~" + end[0]
        return convertView
    }

    private inner class ViewHolder {
        lateinit var tv_price: TextView
        lateinit var tv_name: TextView
        lateinit var tv_time: TextView
    }

}
