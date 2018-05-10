package com.dingshuwang.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import com.dingshuwang.APIURL
import com.dingshuwang.Constants
import com.dingshuwang.DataView
import com.dingshuwang.DetailActivity
import com.dingshuwang.LoginActivity
import com.dingshuwang.R
import com.dingshuwang.base.BaseActivity
import com.dingshuwang.base.MMApplication
import com.dingshuwang.model.AddCartItem
import com.dingshuwang.model.HomeFreeItem
import com.dingshuwang.model.SearchItem
import com.dingshuwang.util.GlideImgManager
import com.dingshuwang.util.GsonUtils
import com.dingshuwang.util.RequestUtils
import com.dingshuwang.util.UIUtil

/**
 * Created by tx on 2017/6/9.
 */

class KeywordsAdapter(private val mList: List<SearchItem.Search>, private val mActivity: BaseActivity) : BaseAdapter(), DataView {
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
            convertView = mLayoutInflater.inflate(R.layout.adapter_keywords, null)
            convertView!!.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }
        holder.iv_logo = convertView.findViewById<View>(R.id.iv_logo) as ImageView
        holder.tv_name = convertView.findViewById<View>(R.id.tv_name) as TextView
        holder.tv_isbn = convertView.findViewById<View>(R.id.tv_isbn) as TextView
        holder.tv_price = convertView.findViewById<View>(R.id.tv_price) as TextView
        holder.tv_count = convertView.findViewById(R.id.tv_count) as TextView
        holder.tv_level = convertView.findViewById(R.id.tv_level) as TextView
        holder.tv_discount = convertView.findViewById(R.id.tv_discount) as TextView
        holder.ll_cart = convertView.findViewById(R.id.ll_cart) as LinearLayout

        val item = mList[position]
        if (item != null) {
            GlideImgManager.loadImage(mActivity, item.img_url!!, holder.iv_logo!!)
            holder.tv_name!!.text = item.name
            if (item.ISBN!!.contains("销售")) {
                val isbn = item.ISBN!!.split("销售".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                holder.tv_isbn!!.text = isbn[0]
            } else {
                holder.tv_isbn!!.text = item.ISBN
            }
            holder.tv_price!!.text = "￥" + item.price_sell!!
            holder.tv_count!!.text = item.goods_nums
            holder.tv_level!!.text = "好"
            holder.tv_discount!!.text = "旧书代发/批发1.9折起"
            holder.ll_cart!!.visibility = View.VISIBLE
            convertView.setOnClickListener(View.OnClickListener {
                if (UIUtil.isfastdoubleClick()) {
                    return@OnClickListener
                }
                DetailActivity.actionDetail(mActivity, item.id!!)
            })
            holder.ll_cart!!.setOnClickListener(View.OnClickListener {
                if (UIUtil.isfastdoubleClick()) {
                    return@OnClickListener
                }
                if (MMApplication.mIsLogin) {
                    doAddCart(item.id)
                } else {
                    LoginActivity.actionLogin(mActivity, Constants.LOGIN)
                }
            })
        }

        return convertView
    }

    //加入购物车
    private fun doAddCart(id: String?) {
        val url = String.format(APIURL.CART_ADD_URL, Constants.USER_ID, id, 1)
        RequestUtils.getDataFromUrlByPost(mActivity, url, "", this, ADD_CART_TAG, false, false)
    }

    override fun onGetDataFailured(msg: String, requestTag: String) {

    }

    override fun onGetDataSuccess(result: String, requestTag: String) {

        if (result != null) {
            val item = GsonUtils.jsonToClass(result, AddCartItem::class.java)
            if ("true" == item!!.result) {
                mActivity.showToast("加入购物车成功")
            } else {
                mActivity.showToast(item.msg!!)
            }
        }
    }

    private inner class ViewHolder {
        var iv_logo: ImageView? = null
        var tv_name: TextView? = null
        var tv_isbn: TextView? = null
        var tv_price: TextView? = null
        var tv_count: TextView? = null
        var tv_level: TextView? = null
        var tv_discount: TextView? = null
        var ll_cart: LinearLayout? = null

    }

    companion object {

        private val ADD_CART_TAG = "ADD_CART_TAG"
    }

}
