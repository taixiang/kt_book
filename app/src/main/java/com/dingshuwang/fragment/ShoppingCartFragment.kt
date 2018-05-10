package com.dingshuwang.fragment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import butterknife.BindView

import com.dingshuwang.APIURL
import com.dingshuwang.ConfirmActivity
import com.dingshuwang.Constants
import com.dingshuwang.DataView
import com.dingshuwang.LoginActivity
import com.dingshuwang.R
import com.dingshuwang.adapter.ShopcartAdapter
import com.dingshuwang.base.BaseFragment
import com.dingshuwang.base.MMApplication
import com.dingshuwang.model.ShopCartItem
import com.dingshuwang.util.GsonUtils
import com.dingshuwang.util.RequestUtils
import com.dingshuwang.util.UIUtil

import java.text.DecimalFormat

import butterknife.OnClick

/**
 * Created by tx on 2017/6/6.
 */

class ShoppingCartFragment : BaseFragment(), DataView {

    @BindView(R.id.listview)
    lateinit var listView: ListView

    @BindView(R.id.tv_money)
    lateinit var tv_money: TextView

    @BindView(R.id.tv_empty)
    lateinit var tv_empty: TextView

    private var adapter: ShopcartAdapter? = null
    private var cartItem: ShopCartItem? = null
    private var allMoney: String? = null

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == Constants.GOOD_NUM) {
                doCount(intent.getStringExtra("book_id"), Integer.parseInt(intent.getStringExtra("count")))
            } else if (intent.action == Constants.DELETE_GOOD) {
                doGetShop()
            } else if (intent.action == Constants.LOGIN_SUCCESS) {
                //                doGetShop();
            }
        }
    }

    override val fragmentTitle: String
        get() = ""

    private fun doGetShop() {
        val url = String.format(APIURL.GOODS_LIST, Constants.USER_ID)
        RequestUtils.getDataFromUrl(mActivity, url, this, GOODS_LIST, false, false)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_shopcart, null)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        register()
        adapter = null
    }

    override fun onResume() {
        super.onResume()
        doGetShop()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (receiver != null) {
            mActivity.unregisterReceiver(receiver)

        }
    }

    @OnClick(R.id.confirm)
    fun confirm() {
        if (UIUtil.isfastdoubleClick()) {
            return
        }
        if (!MMApplication.mIsLogin) {
            LoginActivity.actionLogin(mActivity, Constants.LOGIN)
        } else {
            if (cartItem != null && cartItem!!.shops != null && cartItem!!.shops!!.size > 0) {
                ConfirmActivity.actConfirm(mActivity, GsonUtils.toJson(cartItem!!), tv_money!!.text.toString().trim { it <= ' ' })

            }
        }
    }

    private fun doCount(pro_id: String, num: Int) {
        if (cartItem != null && cartItem!!.shops != null) {
            for (item in cartItem!!.shops!!) {
                for (shopItem in item.Shop!!) {
                    if (pro_id == shopItem.pro_id) {
                        shopItem.order_number = num.toString() + ""
                    }
                }
            }
            var allPrice = 0.0
            var all = 0.0
            var price_cell: Double
            var count: Int
            for (item in cartItem!!.shops!!) {
                for (shopItem in item.Shop!!) {
                    price_cell = java.lang.Double.parseDouble(shopItem.price_sell)
                    count = Integer.parseInt(shopItem.order_number)
                    allPrice = price_cell * count
                    all += allPrice
                }
            }
            val money = DecimalFormat("#0.00").format(all)
            tv_money!!.text = money
        }

    }

    private fun register() {

        val filter_count = IntentFilter(Constants.GOOD_NUM)
        mActivity.registerReceiver(receiver, filter_count)

        val filter_delete = IntentFilter(Constants.DELETE_GOOD)
        mActivity.registerReceiver(receiver, filter_delete)

        val filter_login = IntentFilter(Constants.LOGIN_SUCCESS)
        mActivity.registerReceiver(receiver, filter_login)
    }

    override fun onGetDataFailured(msg: String, requestTag: String) {

    }

    override fun onGetDataSuccess(result: String, requestTag: String) {
        if (result != null) {
            if (GOODS_LIST == requestTag) {
                cartItem = GsonUtils.jsonToClass(result, ShopCartItem::class.java)

                if (null != cartItem && null != cartItem!!.shops) {
                    //                    if(null == adapter){
                    Log.i("》》》》   ", " cartItem  " + cartItem!!.toString())
                    tv_empty!!.visibility = View.GONE
                    adapter = ShopcartAdapter(mActivity, cartItem!!.shops!!)
                    listView!!.adapter = adapter
                    //                    }else {
                    //                        adapter.notifyDataSetChanged();
                    //                    }
                    var allPrice = 0.0
                    var all = 0.0
                    var price_cell: Double
                    var count: Int
                    for (item in cartItem!!.shops!!) {
                        for (shopItem in item.Shop!!) {
                            price_cell = java.lang.Double.parseDouble(shopItem.price_sell)
                            count = Integer.parseInt(shopItem.order_number)
                            allPrice = price_cell * count
                            all += allPrice
                        }
                    }
                    allMoney = DecimalFormat("#0.00").format(all)
                    tv_money!!.text = allMoney
                    tv_money!!.visibility = View.VISIBLE
                    listView!!.visibility = View.VISIBLE
                } else {
                    tv_empty!!.visibility = View.VISIBLE
                    listView!!.visibility = View.GONE
                    tv_money!!.visibility = View.INVISIBLE
                    Log.i("》》》》  ", " 222222 ")
                    if (adapter != null && cartItem != null && cartItem!!.shops != null) {
                        val q = cartItem!!.shops as ArrayList<ShopCartItem.ShopsItem>
                        q.clear()
                        Log.i("》》》》  ", " 33333 ")
                        adapter!!.notifyDataSetChanged()
                    }
                }
            }
        }

    }

    companion object {

        val GOODS_LIST = "GOODS_LIST"

        fun newInstance(): ShoppingCartFragment {
            return ShoppingCartFragment()
        }
    }
}
