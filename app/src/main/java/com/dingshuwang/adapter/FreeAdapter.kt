package com.dingshuwang.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

import com.dingshuwang.DetailActivity
import com.dingshuwang.R
import com.dingshuwang.base.BaseActivity
import com.dingshuwang.model.HomeFreeItem
import com.dingshuwang.util.GlideImgManager
import com.dingshuwang.util.UIUtil

/**
 * Created by tx on 2017/6/7.
 */

class FreeAdapter(private val mList: List<HomeFreeItem.HomeFree>, private val mActivity: BaseActivity) : BaseAdapter() {
    private val mLayoutInflater: LayoutInflater

    init {
        this.mLayoutInflater = LayoutInflater.from(mActivity)
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
            convertView = mLayoutInflater.inflate(R.layout.adapter_free, null)
            convertView!!.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }

        holder.iv_logo = convertView.findViewById(R.id.ic_logo) as ImageView
        holder.tv_name = convertView.findViewById<View>(R.id.tv_name) as TextView
        holder.tv_price = convertView.findViewById<View>(R.id.tv_price) as TextView

        val item = mList[position]
        if (item != null) {
            GlideImgManager.loadImage(mActivity, item.img_url!!, holder.iv_logo!!)
            holder.tv_name!!.text = item.name
            holder.tv_price!!.text = "原价:" + item.price_market!!
            holder.tv_price!!.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
            convertView.setOnClickListener(View.OnClickListener {
                if (UIUtil.isfastdoubleClick()) {
                    return@OnClickListener
                }
                DetailActivity.actionDetail(mActivity, item.id)
            })
        }
        return convertView
    }

    private class ViewHolder {
        lateinit var iv_logo: ImageView
        lateinit var tv_name: TextView
        lateinit var tv_price: TextView


    }


}
