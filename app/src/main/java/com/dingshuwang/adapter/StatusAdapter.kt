package com.dingshuwang.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

import com.dingshuwang.R
import com.dingshuwang.base.BaseActivity

/**
 * Created by Administrator on 2016/7/24.
 */
class StatusAdapter : BaseAdapter {

    lateinit var strs: Array<String>
    lateinit var  activity: BaseActivity
    lateinit var inflater: LayoutInflater

    constructor() {}

    constructor(strs: Array<String>, activity: BaseActivity) {
        this.strs = strs
        this.activity = activity
        inflater = LayoutInflater.from(activity)
    }

    override fun getCount(): Int {
        return strs.size
    }

    override fun getItem(position: Int): Any {
        return strs[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        var textItem: TextItem? = null
        if (convertView == null) {
            textItem = TextItem()
            convertView = inflater.inflate(R.layout.one_text_item, null)
            convertView!!.tag = textItem
        } else {
            textItem = convertView.tag as TextItem
        }
        textItem.textView = convertView.findViewById<View>(R.id.text) as TextView

        textItem.textView!!.text = strs[position]
        return convertView
    }

    private inner class TextItem {
        internal var textView: TextView? = null
    }

}
