package com.dingshuwang.adapter

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView

import com.dingshuwang.base.BaseActivity

/**
 * Created by Administrator on 2016/7/20.
 */
class CityAdapter(private val list: List<String>, private val activity: BaseActivity) : BaseAdapter() {

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View, parent: ViewGroup): View {

        val textView = TextView(activity)
        textView.text = list[position]
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        params.topMargin = 5
        params.bottomMargin = 5
        textView.layoutParams = params
        textView.gravity = Gravity.CENTER
        return textView
    }
}
