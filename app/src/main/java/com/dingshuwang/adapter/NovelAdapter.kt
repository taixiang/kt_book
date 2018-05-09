package com.dingshuwang.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import com.dingshuwang.DetailActivity
import com.dingshuwang.R
import com.dingshuwang.base.BaseActivity
import com.dingshuwang.model.FindItem
import com.dingshuwang.model.NovelItem
import com.dingshuwang.model.SearchItem
import com.dingshuwang.util.GlideImgManager
import com.dingshuwang.util.UIUtil

/**
 * Created by tx on 2017/6/15.
 * 小说专区
 */

class NovelAdapter : BaseAdapter {
    lateinit var mList: List<NovelItem.Novel>
    lateinit var mActivity: BaseActivity
    lateinit var mLayoutInflater: LayoutInflater

    constructor() {}

    constructor(mList: List<NovelItem.Novel>, mActivity: BaseActivity) {
        this.mList = mList
        this.mActivity = mActivity
        this.mLayoutInflater = LayoutInflater.from(mActivity)
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
            convertView = mLayoutInflater.inflate(R.layout.adapter_keywords, null)
            convertView!!.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }
        holder.iv_logo = convertView.findViewById<View>(R.id.iv_logo) as ImageView
        holder.tv_name = convertView.findViewById<View>(R.id.tv_name) as TextView
        holder.tv_isbn = convertView.findViewById<View>(R.id.tv_isbn) as TextView
        holder.tv_price = convertView.findViewById<View>(R.id.tv_price) as TextView
        holder.tv_count = convertView.findViewById<View>(R.id.tv_count) as TextView
        holder.tv_level = convertView.findViewById<View>(R.id.tv_level) as TextView
        holder.tv_discount = convertView.findViewById<View>(R.id.tv_discount) as TextView

        val item = mList!![position]
        if (item != null) {
            GlideImgManager.loadImage(mActivity, item.img_url!!, holder.iv_logo!!)
            holder.tv_name!!.text = item.name
            holder.tv_isbn!!.text = item.ISBN
            holder.tv_price!!.text = "￥" + item.price_sell!!
            holder.tv_count!!.text = item.goods_nums
            holder.tv_level!!.text = "好"
            holder.tv_discount!!.text = "旧书代发/批发1.9折起"
            convertView.setOnClickListener(View.OnClickListener {
                if (UIUtil.isfastdoubleClick()) {
                    return@OnClickListener
                }
                DetailActivity.actionDetail(mActivity, item.id)
            })
        }
        return convertView
    }

    private inner class ViewHolder {
        var iv_logo: ImageView? = null
        var tv_name: TextView? = null
        var tv_isbn: TextView? = null
        var tv_price: TextView? = null
        var tv_count: TextView? = null
        var tv_level: TextView? = null
        var tv_discount: TextView? = null
    }
}
