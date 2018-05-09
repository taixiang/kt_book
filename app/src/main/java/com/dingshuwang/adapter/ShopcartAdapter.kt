package com.dingshuwang.adapter

import android.content.Intent
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
import com.dingshuwang.R
import com.dingshuwang.base.BaseActivity
import com.dingshuwang.model.DropCartItem
import com.dingshuwang.model.ShopCartItem
import com.dingshuwang.util.GlideImgManager
import com.dingshuwang.util.GsonUtils
import com.dingshuwang.util.RequestUtils
import com.dingshuwang.util.UIUtil
import com.dingshuwang.view.AlertDialogUI

/**
 * Created by Administrator on 2016/7/9.
 */
class ShopcartAdapter : BaseAdapter {

    lateinit var activity: BaseActivity
    lateinit var  mLayoutInflater: LayoutInflater
    lateinit var  mList: List<ShopCartItem.ShopsItem>

    constructor() {}

    constructor(activity: BaseActivity, mList: List<ShopCartItem.ShopsItem>) {
        this.activity = activity
        this.mList = mList
        mLayoutInflater = LayoutInflater.from(activity)
    }

    override fun getCount(): Int {
        return mList.size ?: 0
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
        if (convertView == null) {
            holder = ViewHolder()
            convertView = mLayoutInflater.inflate(R.layout.adapter_shopcart, null)
            convertView!!.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }
        holder.tv_sup_name = convertView.findViewById(R.id.sup_name) as TextView
        holder.container = convertView.findViewById(R.id.container) as LinearLayout
        val shopsItem = mList!![position]
        holder.tv_sup_name!!.text = shopsItem.supplier_name
        for (shopItem in shopsItem.Shop!!) {

            val sell_count: Int
            val view = mLayoutInflater.inflate(R.layout.layout_shop_item, null)
            val iv_logo = view.findViewById(R.id.iv_logo) as ImageView
            val iv_minus = view.findViewById(R.id.iv_minus) as ImageView
            val iv_add = view.findViewById(R.id.iv_add) as ImageView
            val tv_name = view.findViewById(R.id.tv_name) as TextView
            val tv_price = view.findViewById(R.id.tv_price) as TextView
            val tv_count = view.findViewById(R.id.tv_count) as TextView
            val tv_delete = view.findViewById(R.id.tv_delete) as ImageView
            GlideImgManager.loadImage(activity, shopItem.img_url!!, iv_logo)
            tv_name.text = shopItem.pro_name
            tv_price.text = shopItem.price_sell
            tv_count.text = shopItem.order_number
            val count = intArrayOf(Integer.parseInt(shopItem.order_number))
            sell_count = shopItem.available_nums
            iv_minus.setOnClickListener {
                if (count[0] > 1) {
                    count[0] -= 1
                    tv_count.text = count[0].toString() + ""
                    val intent = Intent(Constants.GOOD_NUM)
                    intent.putExtra("book_id", shopItem.pro_id)
                    intent.putExtra("count", tv_count.text.toString())
                    activity.sendBroadcast(intent)
                    val dataView = object : DataView {
                        override fun onGetDataFailured(msg: String, requestTag: String) {

                        }

                        override fun onGetDataSuccess(result: String, requestTag: String) {

                        }
                    }
                    val url = String.format(APIURL.CART_ADD_URL, Constants.USER_ID, shopItem.pro_id, tv_count.text.toString())
                    RequestUtils.getDataFromUrlByPost(activity, url, "", dataView, "", false, false)
                }
            }
            iv_add.setOnClickListener {
                count[0] = Integer.parseInt(tv_count.text.toString())
                if (count[0] < sell_count) {
                    count[0] += 1
                    tv_count.text = count[0].toString() + ""
                    val intent = Intent(Constants.GOOD_NUM)
                    intent.putExtra("book_id", shopItem.pro_id)
                    intent.putExtra("count", tv_count.text.toString())
                    activity.sendBroadcast(intent)

                    val dataView = object : DataView {
                        override fun onGetDataFailured(msg: String, requestTag: String) {

                        }

                        override fun onGetDataSuccess(result: String, requestTag: String) {

                        }
                    }
                    val url = String.format(APIURL.CART_ADD_URL, Constants.USER_ID, shopItem.pro_id, tv_count.text.toString())
                    RequestUtils.getDataFromUrlByPost(activity, url, "", dataView, "", false, false)


                } else {
                    activity.showToast("库存已达最大")
                }
            }

            tv_delete.setOnClickListener(View.OnClickListener {
                if (UIUtil.isfastdoubleClick()) {
                    return@OnClickListener
                }
                val ad = AlertDialogUI(activity)
                ad.setTitle("提示：")
                ad.setMessage("您确定要删除此图书吗？")
                ad.setCanceledOnTouchOutside(true)
                ad.setPositiveButton("确定", View.OnClickListener { doDelete(shopItem.pro_id, ad) })
                ad.setNegativeButton("取消", View.OnClickListener { ad.dismiss() })
                ad.showDialog()
            })
            holder.container!!.addView(view)
        }
        return convertView
    }

    private fun doDelete(pro_id: String?, ad: AlertDialogUI?) {
        val url = String.format(APIURL.DELET_GOOD, Constants.USER_ID, pro_id)
        val dataView = object : DataView {
            override fun onGetDataFailured(msg: String, requestTag: String) {

            }

            override fun onGetDataSuccess(result: String, requestTag: String) {
                if (null != result) {
                    val dropCartItem = GsonUtils.jsonToClass(result, DropCartItem::class.java)
                    if (dropCartItem!!.result) {
                        ad?.dismiss()
                        activity.showToast("删除成功")

                        val intent = Intent(Constants.DELETE_GOOD)
                        activity.sendBroadcast(intent)
                    } else if (dropCartItem.msg != null) {
                        activity.showToast(dropCartItem.msg!!)
                    }

                }
            }
        }
        RequestUtils.getDataFromUrlByPost(activity, url, "", dataView, "", false, false)
    }

    class ViewHolder {
        var tv_sup_name: TextView? = null
        var container: LinearLayout? = null
    }


}
