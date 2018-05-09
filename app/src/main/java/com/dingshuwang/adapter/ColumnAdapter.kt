package com.dingshuwang.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

import com.dingshuwang.KeywordsListActivity
import com.dingshuwang.NovelActivity
import com.dingshuwang.R
import com.dingshuwang.base.BaseActivity
import com.dingshuwang.model.HomeMiddleItem
import com.dingshuwang.util.GlideImgManager
import com.dingshuwang.util.UIUtil

/**
 * Created by tx on 2017/6/7.
 */

class ColumnAdapter(private val mList: List<HomeMiddleItem.HomeMiddle>?, private val mActivity: BaseActivity) : BaseAdapter() {
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
            convertView = mLayoutInflater.inflate(R.layout.adapter_column, null)
            holder = ViewHolder()
            convertView!!.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }

        holder.iv_logo = convertView.findViewById<View>(R.id.iv_logo) as ImageView
        holder.tv_name = convertView.findViewById<View>(R.id.tv_name) as TextView
        val item = mList!![position]
        if (item != null && item.keywords != null && item.image_url != null) {

            holder.tv_name!!.text = item.keywords
            GlideImgManager.loadImage(mActivity, item.image_url!!, holder.iv_logo!!)
        }
        convertView.setOnClickListener(View.OnClickListener {
            if (UIUtil.isfastdoubleClick()) {
                return@OnClickListener
            }
            if ("小说专区" == item.keywords) {
                NovelActivity.actionNovel(mActivity)
            } else {
                KeywordsListActivity.actionKeywordsList(mActivity, item.keywords)
            }
        })


        return convertView
    }


    class ViewHolder {
        lateinit var tv_name: TextView
        lateinit var iv_logo: ImageView
    }


}
