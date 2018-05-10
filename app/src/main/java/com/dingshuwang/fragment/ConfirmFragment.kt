package com.dingshuwang.fragment

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.annotation.IdRes
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.PopupWindow
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import butterknife.BindView

import com.dingshuwang.APIURL
import com.dingshuwang.AddressListActivity
import com.dingshuwang.Constants
import com.dingshuwang.DataView
import com.dingshuwang.PayActivity
import com.dingshuwang.R
import com.dingshuwang.adapter.CouponsAdapter
import com.dingshuwang.adapter.OrderImageAdapter
import com.dingshuwang.base.BaseFragment
import com.dingshuwang.model.AddressListItem
import com.dingshuwang.model.CouponsItem
import com.dingshuwang.model.OrderItem
import com.dingshuwang.model.ShopCartItem
import com.dingshuwang.model.SupplierItem
import com.dingshuwang.model.UserItem
import com.dingshuwang.util.GsonUtils
import com.dingshuwang.util.RequestUtils
import com.dingshuwang.util.StringUtils
import com.dingshuwang.util.UIUtil
import com.dingshuwang.view.CustomGridView

import java.util.ArrayList

import butterknife.OnClick

/**
 * Created by tx on 2017/6/19.
 */

class ConfirmFragment : BaseFragment(), DataView {


    @BindView(R.id.tv_add_address)
    lateinit var tv_add_address: TextView
    @BindView(R.id.container)
    lateinit var container: LinearLayout
    @BindView(R.id.tv_name)
    lateinit var tv_name: TextView
    @BindView(R.id.tv_phone)
    lateinit var tv_phone: TextView
    @BindView(R.id.tv_address)
    lateinit var tv_address: TextView

    @BindView(R.id.gridView)
    lateinit var gridView: CustomGridView

    @BindView(R.id.tv_goods_num)
    lateinit var tv_goods_num: TextView
    @BindView(R.id.tv_price)
    lateinit var tv_price: TextView
    @BindView(R.id.tv_exp_price)
    lateinit var tv_exp_price: TextView

    @BindView(R.id.rg_supplier)
    lateinit var rg: RadioGroup

    @BindView(R.id.exp_shunf)
    lateinit var exp_shunf: RadioButton

    @BindView(R.id.exp_putong)
    lateinit var exp_putong: RadioButton

    @BindView(R.id.exp_youzheng)
    lateinit var exp_youzheng: RadioButton

    @BindView(R.id.tv_point)
    lateinit var tv_point: TextView
    @BindView(R.id.tv_coupons)
    lateinit var tv_coupons: TextView
    @BindView(R.id.ll_coupons)
    lateinit var ll_coupons: LinearLayout
    @BindView(R.id.tv_total_price)
    lateinit var tv_total_price: TextView

    @BindView(R.id.rg_orderType)
    lateinit var rg_orderType: RadioGroup
    @BindView(R.id.order_common)
    lateinit var order_common: RadioButton
    @BindView(R.id.order_share)
    lateinit var order_share: RadioButton
    private var address_id: String? = null

    private var money: String? = null
    private var item: String? = null
    private val cartItem: ShopCartItem? = null
    private var point: Double = 0.toDouble()
    private var coupons: String? = null
    private var innerid: String? = ""
    private var area_id: String? = ""
    private var supplier_id: String? = ""
    private var order_type = "1"

    private var exp = "2"
    private var express = "6"
    private var strs: List<CouponsItem.Coupon>? = ArrayList()

    internal var popupWindow: PopupWindow? = null

    override val fragmentTitle: String
        get() = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_confirm, null)
    }


    override fun onResume() {
        super.onResume()
        doGetAddres()

    }

    @OnClick(R.id.address)
    internal fun addAddress() {
        if (UIUtil.isfastdoubleClick()) {
            return
        }
        AddressListActivity.actionAddresList(mActivity, 0)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        item = arguments!!.get("goods") as String
        money = arguments!!.get("money") as String
        Log.i("》》》》 ", "money 22==" + money!!)
        if (null != item) {
            val item1 = GsonUtils.jsonToClass(item!!, ShopCartItem::class.java)

            supplier_id = item1!!.shops!![0].supplier_id
            val imgs = ArrayList<String>()
            for (shopsItem in item1.shops!!) {
                for (shopItem in shopsItem.Shop!!) {
                    imgs.add(shopItem.img_url!!)
                }
            }
            tv_goods_num!!.text = "共" + imgs.size + "件"
            tv_price!!.text = money
            gridView!!.adapter = OrderImageAdapter(mActivity, imgs)
        }




        rg!!.setOnCheckedChangeListener { group, checkedId ->
            if (exp_shunf!!.id == checkedId) {
                getSupplier("1")
                exp = "1"
                express = "1"
            } else if (exp_putong!!.id == checkedId) {
                getSupplier("2")
                exp = "2"
                express = "6"
            } else {
                getSupplier("3")
                exp = "3"
                express = "11"
            }
        }
        rg_orderType!!.setOnCheckedChangeListener { group, checkedId ->
            if (order_common!!.id == checkedId) {
                order_type = "1"
            } else {
                order_type = "2"
            }
        }

    }

    private fun showPop() {
        if (null != popupWindow) {
            popupWindow!!.dismiss()
        }
        val view = LayoutInflater.from(mActivity).inflate(R.layout.popup_listview, null)
        popupWindow = PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        //        popupWindow.setContentView(view);
        val listView = view.findViewById<View>(R.id.listview) as ListView
        listView.adapter = CouponsAdapter(strs, mActivity)
        popupWindow!!.setBackgroundDrawable(BitmapDrawable())
        popupWindow!!.isOutsideTouchable = true
        popupWindow!!.showAsDropDown(ll_coupons)
        //        popupWindow.showAtLocation(ll_coupons, Gravity.CENTER,0,0);
        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            tv_coupons!!.text = strs!![position].special_price
            innerid = strs!![position].innerid
            if (null != popupWindow) {
                popupWindow!!.dismiss()
            }
        }
    }

    @OnClick(R.id.ll_coupons)
    internal fun showCoupons() {
        if (strs != null && strs!!.size > 0) {
            if (null != popupWindow) {
                popupWindow!!.dismiss()
                popupWindow = null
                return
            }
            showPop()
        }
    }

    @OnClick(R.id.post)
    fun confirm() {
        if (UIUtil.isfastdoubleClick()) {
            return
        }
        try {
            if (exp.length > 0 && express.length > 0) {
                doGetConfirm(exp, express)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun doGetAddres() {
        val url = String.format(APIURL.ADDRESS_LIST, Constants.USER_ID)
        RequestUtils.getDataFromUrl(mActivity, url, this, GET_ADDRESS, false, false)
    }

    //配送费
    private fun getSupplier(type: String) {
        val url = String.format(APIURL.Supplier_URL, supplier_id, type, area_id, Constants.USER_ID)
        RequestUtils.getDataFromUrl(mActivity, url, this, Supplier_URL, false, false)
    }

    //积分
    private fun doGetMsg() {
        val url = String.format(APIURL.USER_INFO_URL, Constants.USER_ID)
        RequestUtils.getDataFromUrlByPost(mActivity, url, "", this, USER_INFO, false, false)
    }

    //优惠券
    private fun doGetCoupons() {
        val url = String.format(APIURL.coupons_url, Constants.USER_ID)
        RequestUtils.getDataFromUrl(mActivity, url, this, "coupons_url", false, false)
    }

    //下单
    private fun doGetConfirm(exp_id: String, express_id: String) {
        val url = String.format(APIURL.ConfirmOrder_url, Constants.USER_ID, address_id, exp_id, express_id, "", tv_point!!.text.toString(), "", "", order_type)
        RequestUtils.getDataFromUrl(mActivity, url, this, PAY_TAG, false, false)
    }


    override fun onGetDataFailured(msg: String, requestTag: String) {

    }

    override fun onGetDataSuccess(result: String, requestTag: String) {
        if (result != null) {
            if (GET_ADDRESS == requestTag) {
                tv_add_address!!.visibility = View.VISIBLE
                container!!.visibility = View.GONE
                val item = GsonUtils.jsonToClass(result, AddressListItem::class.java)
                if (null != item && item.address != null && item.address!!.size > 0) {
                    for (addressItem in item.address!!) {
                        if (addressItem.is_default == "1") {
                            address_id = addressItem.id
                            area_id = addressItem.area_id
                            tv_add_address!!.visibility = View.GONE
                            container!!.visibility = View.VISIBLE
                            tv_name!!.text = addressItem.accept_name
                            tv_phone!!.text = addressItem.phone
                            tv_address!!.text = addressItem.area!! + addressItem.address!!
                            doGetMsg()
                        }
                    }
                }
            } else if (Supplier_URL == requestTag) {
                val item = GsonUtils.jsonToClass(result, SupplierItem::class.java)
                tv_exp_price!!.text = item!!.fright!! + "元"
                Log.i("》》》》》   ", "moneyTotal === " + java.lang.Double.parseDouble(money) + "  fright ==  " + java.lang.Double.parseDouble(item.fright) + "  point== " + point / 100)
                val total = java.lang.Double.parseDouble(money) + java.lang.Double.parseDouble(item.fright) - java.lang.Double.parseDouble(StringUtils.doubleFormat(point / 100))

                tv_total_price!!.text = total.toString() + "元"
            } else if (USER_INFO == requestTag) {
                val userItem = GsonUtils.jsonToClass(result, UserItem::class.java)
                if (null != userItem) {
                    point = userItem.point
                    val point_money = StringUtils.doubleFormat(point / 100)
                    tv_point!!.text = point_money + ""
                    getSupplier("2")
                }
            } else if ("coupons_url" == requestTag) {
                val couponsItem = GsonUtils.jsonToClass(result, CouponsItem::class.java)
                if (couponsItem != null && couponsItem.coupons != null && couponsItem.coupons!!.size > 0) {
                    coupons = couponsItem.coupons!![0].special_price
                    tv_coupons!!.text = coupons
                    strs = couponsItem.coupons
                    innerid = couponsItem.coupons!![0].innerid
                }
            } else if (PAY_TAG == requestTag) {

                val orderItem = GsonUtils.jsonToClass(result, OrderItem::class.java)
                if (null != orderItem) {
                    if (orderItem.result == "true") {
                        PayActivity.actConfirm(mActivity, orderItem.message!!)
                        mActivity.finish()
                    } else {
                        mActivity.showToast(orderItem.message!!)
                    }
                }
            }
        }


    }

    companion object {

        //地址
        val GET_ADDRESS = "get_address_list"

        //配送费
        val Supplier_URL = "supplier_url"

        //积分
        val USER_INFO = "USER_INFO"

        //下单
        val PAY_TAG = "pay_tag"

        fun newInstance(item: String, allMoney: String): ConfirmFragment {
            val fragment = ConfirmFragment()
            val bundle = Bundle()
            bundle.putString("goods", item)
            bundle.putString("money", allMoney)
            fragment.arguments = bundle
            return fragment
        }
    }
}
