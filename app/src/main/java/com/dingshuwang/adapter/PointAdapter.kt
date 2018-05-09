package com.dingshuwang.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

import com.dingshuwang.R
import com.dingshuwang.base.BaseActivity
import com.dingshuwang.model.PointItem

/**
 * Created by tx on 2017/6/22.
 */

class PointAdapter : BaseAdapter {

    lateinit var mList: List<PointItem.Point>
    lateinit var activity: BaseActivity
    lateinit var inflater: LayoutInflater

    constructor() {}

    constructor(mList: List<PointItem.Point>, activity: BaseActivity) {
        this.mList = mList
        this.activity = activity
        this.inflater = LayoutInflater.from(activity)
    }

    override fun getCount(): Int {
        return mList.size
    }

    override fun getItem(position: Int): Any {
        return mList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        var holder: ViewHolder? = null
        if (null == convertView) {
            holder = ViewHolder()
            convertView = inflater.inflate(R.layout.adapter_point, null)
            convertView!!.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }
        holder.tv_remark = convertView.findViewById(R.id.tv_remark) as TextView
        holder.tv_point = convertView.findViewById(R.id.tv_point) as TextView
        holder.tv_time = convertView.findViewById<View>(R.id.tv_time) as TextView
        val item = mList[position]
        holder.tv_remark!!.text = item.remark
        holder.tv_point!!.text = item.value.toString() + ""
        if (item.add_time!!.contains("T")) {
            val times = item.add_time!!.split("T".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            holder.tv_time!!.text = times[0]

        }
        return convertView
    }

    private inner class ViewHolder {
        lateinit var tv_remark: TextView
        lateinit var tv_point: TextView
        lateinit var  tv_time: TextView
    }


}
