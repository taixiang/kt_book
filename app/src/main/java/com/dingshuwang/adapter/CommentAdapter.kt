package com.dingshuwang.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

import com.dingshuwang.R
import com.dingshuwang.base.BaseActivity
import com.dingshuwang.model.CommentItem

/**
 * Created by Administrator on 2016/8/20.
 */
class CommentAdapter(private val list: List<CommentItem.Comment1>?, private val activity: BaseActivity) : BaseAdapter() {
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
            convertView = inflater.inflate(R.layout.comment_item_view, null)
            convertView!!.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }
        holder.tv_mark = convertView.findViewById(R.id.tv_content) as TextView
        holder.tv_point = convertView.findViewById(R.id.tv_time) as TextView
        if (list != null) {
            holder.tv_mark!!.setText(list[position].comment_content)
            holder.tv_point!!.setText(list[position].add_time)
        }
        return convertView
    }

    private inner class ViewHolder {
        lateinit var tv_point: TextView
        lateinit var tv_mark: TextView
    }

}
