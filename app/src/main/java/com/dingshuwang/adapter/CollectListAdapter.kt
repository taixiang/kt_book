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
import com.dingshuwang.model.AddressListItem
import com.dingshuwang.model.CollectListItem
import com.dingshuwang.util.GlideImgManager
import com.dingshuwang.util.UIUtil

/**
 * Created by tx on 2017/7/7.
 */

class CollectListAdapter(private val activity: BaseActivity, private val list: List<CollectListItem.CollectList>?) : BaseAdapter() {
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
            convertView = inflater.inflate(R.layout.adapter_collect_list, null)
            convertView!!.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }
        holder.iv_logo = convertView.findViewById(R.id.iv_logo) as ImageView
        holder.tv_name = convertView.findViewById(R.id.tv_name) as TextView
        holder.tv_isbn = convertView.findViewById(R.id.tv_isbn) as TextView
        holder.tv_price = convertView.findViewById(R.id.tv_price) as TextView
        val item = list!![position]
        GlideImgManager.loadImage(activity, item.img_url!!, holder.iv_logo!!)
        holder.tv_name!!.text = item.name
        holder.tv_isbn!!.text = item.ISBN
        //        holder.tv_price.setText(item.pro_id);
        convertView.setOnClickListener(View.OnClickListener {
            if (UIUtil.isfastdoubleClick()) {
                return@OnClickListener
            }
            DetailActivity.actionDetail(activity, item.pro_id)
        })


        return convertView
    }

    private inner class ViewHolder {
        var iv_logo: ImageView? = null
        var tv_name: TextView? = null
        var tv_isbn: TextView? = null
        var tv_price: TextView? = null
    }


}
