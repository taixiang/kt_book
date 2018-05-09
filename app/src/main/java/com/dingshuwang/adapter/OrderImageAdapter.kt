package com.dingshuwang.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView

import com.dingshuwang.R
import com.dingshuwang.base.BaseActivity
import com.dingshuwang.model.ShopCartItem
import com.dingshuwang.util.GlideImgManager

/**
 * Created by tx on 2017/6/19.
 */

class OrderImageAdapter : BaseAdapter {

    lateinit var activity: BaseActivity
    lateinit var mLayoutInflater: LayoutInflater
    lateinit var mList: List<String>

    constructor() {}

    constructor(activity: BaseActivity, mList: List<String>) {
        this.activity = activity
        this.mList = mList
        mLayoutInflater = LayoutInflater.from(activity)
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
            convertView = mLayoutInflater.inflate(R.layout.adapter_order_image, null)
            convertView!!.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }
        holder.imageView = convertView.findViewById<View>(R.id.image) as ImageView
        GlideImgManager.loadImage(activity, mList!![position], holder.imageView!!)

        return convertView
    }

    private inner class ViewHolder {
        lateinit var imageView: ImageView
    }
}
