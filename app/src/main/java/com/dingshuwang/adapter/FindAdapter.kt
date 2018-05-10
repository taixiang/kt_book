package com.dingshuwang.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

import com.dingshuwang.DetailActivity
import com.dingshuwang.R
import com.dingshuwang.base.BaseActivity
import com.dingshuwang.model.FindItem
import com.dingshuwang.model.HomeMiddleItem
import com.dingshuwang.util.GlideImgManager
import com.dingshuwang.util.UIUtil

/**
 * Created by tx on 2017/6/9.
 */

class FindAdapter(private val mList: List<FindItem.Find>?, private val mActivity: BaseActivity) : BaseAdapter() {
    private val mLayoutInflater: LayoutInflater

    init {
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
            convertView = mLayoutInflater.inflate(R.layout.adapter_find, null)
            convertView!!.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }
        holder.iv_logo = convertView.findViewById<View>(R.id.iv_logo) as ImageView
        holder.tv_num = convertView.findViewById(R.id.tv_num) as TextView
        holder.tv_school = convertView.findViewById(R.id.tv_school) as TextView
        holder.tv_name = convertView.findViewById<View>(R.id.tv_name) as TextView
        holder.tv_price = convertView.findViewById<View>(R.id.tv_price) as TextView
        val item = mList!![position]
        if (item != null) {
            GlideImgManager.loadImage(mActivity, item.image_url!!, holder.iv_logo!!)
            holder.tv_num!!.text = item.user_name
            holder.tv_school!!.text = item.university
            holder.tv_name!!.text = item.pro_name
            holder.tv_price!!.text = "￥ " + item.price_sell!!
            convertView.setOnClickListener(View.OnClickListener {
                if (UIUtil.isfastdoubleClick()) {
                    return@OnClickListener
                }
                DetailActivity.actionDetail(mActivity, item.pro_id!!)
            })
        }
        return convertView
    }

    private inner class ViewHolder {
        lateinit var iv_logo: ImageView
        lateinit var  tv_num: TextView
        lateinit var  tv_school: TextView
        lateinit var  tv_name: TextView
        lateinit var  tv_price: TextView
    }

}
