package com.dingshuwang.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

import com.dingshuwang.R
import com.dingshuwang.base.BaseActivity
import com.dingshuwang.model.AddressListItem
import com.dingshuwang.model.PublishListItem
import com.dingshuwang.util.GlideImgManager
import com.github.siyamed.shapeimageview.CircularImageView

/**
 * Created by tx on 2017/7/7.
 */

class MyPublishAdapter(private val activity: BaseActivity, private val list: List<PublishListItem.Publish>?) : BaseAdapter() {
    private val inflater: LayoutInflater

    init {
        inflater = LayoutInflater.from(activity)
    }

    override fun getCount(): Int {
        return list?.size ?: 0
    }

    override fun getItem(position: Int): Any {
        return list!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        var holder: ViewHolder? = null
        if (convertView == null) {
            holder = ViewHolder()
            convertView = inflater.inflate(R.layout.adapter_my_publish, null)
            convertView!!.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }
        holder.tv_time = convertView.findViewById<View>(R.id.tv_time) as TextView
        holder.iv_logo = convertView.findViewById<View>(R.id.iv_logo) as CircularImageView
        holder.tv_name = convertView.findViewById<View>(R.id.tv_name) as TextView
        holder.tv_isbn = convertView.findViewById<View>(R.id.tv_isbn) as TextView
        holder.tv_price = convertView.findViewById<View>(R.id.tv_price) as TextView
        val item = list!![position]
        if (item != null) {
            holder.tv_time!!.text = item.create_date
            GlideImgManager.loadImage(activity, item.image_url!!, holder.iv_logo!!)
            holder.tv_name!!.text = item.pro_name
            holder.tv_isbn!!.text = "ISBN：" + item.pro_isbn!!
            holder.tv_price!!.text = "价格：￥" + item.price_sell!!
        }
        convertView.setOnClickListener { }
        return convertView
    }

    private inner class ViewHolder {
        lateinit var tv_time: TextView
        lateinit var iv_logo: CircularImageView
        lateinit var tv_name: TextView
        lateinit var tv_isbn: TextView
        lateinit var tv_price: TextView
    }

}
