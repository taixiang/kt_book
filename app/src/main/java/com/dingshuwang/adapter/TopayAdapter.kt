package com.dingshuwang.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import com.dingshuwang.DetailActivity
import com.dingshuwang.PayActivity
import com.dingshuwang.R
import com.dingshuwang.base.BaseActivity
import com.dingshuwang.model.WaitPayItem
import com.dingshuwang.util.GlideImgManager
import com.dingshuwang.util.UIUtil

/**
 * Created by Administrator on 2016/7/27.
 */
class TopayAdapter : BaseAdapter {

    lateinit var list: List<WaitPayItem.ShopItem>
    lateinit var  activity: BaseActivity
    lateinit var  inflater: LayoutInflater
    var type: Int = 0

    constructor() {}

    constructor(list: List<WaitPayItem.ShopItem>, activity: BaseActivity, type: Int) {
        this.list = list
        this.activity = activity
        inflater = LayoutInflater.from(activity)
        this.type = type
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
        var viewHolder: ViewHolder? = null
        if (convertView == null) {
            viewHolder = ViewHolder()
            convertView = inflater.inflate(R.layout.adapter_to_pay, null)
            convertView!!.tag = viewHolder
        } else {
            viewHolder = convertView.tag as ViewHolder
        }
        viewHolder.container = convertView.findViewById<View>(R.id.container) as LinearLayout
        viewHolder.container!!.removeAllViews()
        val items = list!![position]
        for (i in items.Order!!.indices) {
            if (items.Order != null) {
                val view = inflater.inflate(R.layout.to_pay_item_view, null)
                //                TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
                //                TextView tv_money = (TextView) view.findViewById(R.id.tv_money);
                //                TextView tv_status = (TextView) view.findViewById(R.id.tv_status);
                val tv_id = view.findViewById<View>(R.id.tv_id) as TextView
                val order_detail = view.findViewById<View>(R.id.order_detail) as TextView
                val container = view.findViewById<View>(R.id.container) as LinearLayout
                //                tv_name.setText(items.Order.get(i).supplier_name);
                val good = items.Order!![i].order_goods
                container.removeAllViews()
                for (j in good!!.indices) {
                    val view2 = inflater.inflate(R.layout.item_good_view, null)
                    val iv_icon = view2.findViewById(R.id.iv_icon) as ImageView
                    val tv_boook = view2.findViewById(R.id.tv_book) as TextView
                    val tv_price = view2.findViewById(R.id.price) as TextView
                    val tv_count = view2.findViewById(R.id.tv_count) as TextView
                    GlideImgManager.loadImage(activity, good[j].img_url!!, iv_icon)
                    tv_boook.text = good[j].goods_title
                    tv_price.text = "￥" + good[j].price_sell!!
                    tv_count.text = "x " + good[j].quantity!!
                    container.addView(view2)
                    view2.setOnClickListener(View.OnClickListener {
                        if (UIUtil.isfastdoubleClick()) {
                            return@OnClickListener
                        }

                        DetailActivity.actionDetail(activity, good[j].goods_id)
                    })
                }

                tv_id.text = "订单号：" + items.Order!![i].id!!
                order_detail.setOnClickListener(View.OnClickListener {
                    if (UIUtil.isfastdoubleClick()) {
                        return@OnClickListener
                    }
                    PayActivity.actConfirm(activity, items.Order!![i].id)
                })
                if (type == 1) {


                } else if (type == 2) {

                } else if (type == 3) {

                } else if (type == 4) {

                }

                viewHolder.container!!.addView(view)
            }
        }
        return convertView
    }

    private inner class ViewHolder {
        lateinit var container: LinearLayout
    }

}
