package com.dingshuwang.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.TextView
import butterknife.BindView

import com.dingshuwang.APIURL
import com.dingshuwang.CompleteActivity
import com.dingshuwang.Constants
import com.dingshuwang.DataView
import com.dingshuwang.ExpOrderActivity
import com.dingshuwang.ForExpActivity
import com.dingshuwang.PREF
import com.dingshuwang.R
import com.dingshuwang.ToPayActivity
import com.dingshuwang.adapter.UserAdapter
import com.dingshuwang.base.BaseFragment
import com.dingshuwang.base.MMApplication
import com.dingshuwang.model.CouponsItem
import com.dingshuwang.model.PointItem
import com.dingshuwang.model.SignItem
import com.dingshuwang.model.UserItem
import com.dingshuwang.util.GsonUtils
import com.dingshuwang.util.RequestUtils
import com.dingshuwang.util.StringUtils
import com.dingshuwang.util.UIUtil
import com.dingshuwang.view.AlertDialogUI

import butterknife.OnClick

/**
 * Created by tx on 2017/6/6.
 */

class UserCenterFragment : BaseFragment(), DataView {

    @BindView(R.id.tv_name)
    internal var tv_name: TextView? = null
    @BindView(R.id.tv_id)
    internal var tv_id: TextView? = null
    @BindView(R.id.tv_qiandao)
    internal var tv_qiandao: TextView? = null

    @BindView(R.id.gridView)
    internal var gridView: GridView? = null
    private val iv_ids = intArrayOf(R.mipmap.menu1, R.mipmap.menu3, R.mipmap.menu4, R.mipmap.menu5, R.mipmap.menu6, R.mipmap.menu2, R.mipmap.menu7, R.mipmap.menu8, R.mipmap.dfh)
    private val names = arrayOf("我的订单", "优惠券", "我的积分", "购物车", "我的收藏", "我的发布", "收货地址", "图书回收", "联系我们")
    private var userAdapter: UserAdapter? = null

    override val fragmentTitle: String
        get() = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_me, null)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.i("》》》》》  ", " usercenter create ")
        userAdapter = UserAdapter(iv_ids, names, mActivity)
        gridView!!.adapter = userAdapter


    }

    override fun onResume() {
        super.onResume()
        doGetMsg()

        qiandao()
        coupons_url()
        point_url()
    }

    @OnClick(R.id.tv_qiandao)
    internal fun sign_in() {
        if (UIUtil.isfastdoubleClick()) {
            return
        }
        qiandao()
    }

    //待付款
    @OnClick(R.id.ll_dfk)
    internal fun topay() {
        if (UIUtil.isfastdoubleClick()) {
            return
        }
        ToPayActivity.actToPay(mActivity)
    }

    //待发货
    @OnClick(R.id.ll_dfh)
    internal fun waitExp() {
        if (UIUtil.isfastdoubleClick()) {
            return
        }
        ExpOrderActivity.actExpOrder(mActivity)
    }

    //待收货
    @OnClick(R.id.ll_dsh)
    internal fun for_exp() {
        if (UIUtil.isfastdoubleClick()) {
            return
        }
        ForExpActivity.actForExp(mActivity)
    }

    //已完成
    @OnClick(R.id.ll_dpj)
    internal fun complete() {
        if (UIUtil.isfastdoubleClick()) {
            return
        }
        CompleteActivity.actComplete(mActivity)
    }

    @OnClick(R.id.btn_out)
    internal fun btnOut() {
        val dialogUI = AlertDialogUI(mActivity)
        dialogUI.setMessage("是否确定退出")
        dialogUI.setNegativeButton("取消", View.OnClickListener { dialogUI?.dismiss() })
        dialogUI.setPositiveButton("确定", View.OnClickListener {
            mActivity.preferences!!.edit().remove(PREF.PREF_USER_ID).apply()
            Constants.USER_ID = ""
            MMApplication.mIsLogin = false
            mActivity.finish()
        })
    }

    //个人信息
    private fun doGetMsg() {
        val url = String.format(APIURL.USER_INFO_URL, Constants.USER_ID)
        RequestUtils.getDataFromUrlByPost(mActivity, url, "", this, USER_INFO, false, false)
    }

    //签到
    private fun qiandao() {
        val url = String.format(APIURL.sign_in_url, Constants.USER_ID)
        RequestUtils.getDataFromUrl(mActivity, url, this, QIANDAO, false, false)
    }

    //优惠券
    private fun coupons_url() {
        val url = String.format(APIURL.coupons_url, Constants.USER_ID)
        RequestUtils.getDataFromUrl(mActivity, url, this, coupons_url, false, false)
    }

    //积分
    private fun point_url() {
        val url = String.format(APIURL.point, Constants.USER_ID)
        RequestUtils.getDataFromUrl(mActivity, url, this, point_url, false, false)
    }

    override fun onGetDataFailured(msg: String, requestTag: String) {

    }

    override fun onGetDataSuccess(result: String, requestTag: String) {
        if (result != null) {
            if (USER_INFO == requestTag) {
                val item = GsonUtils.jsonToClass(result, UserItem::class.java)
                if (item != null) {
                    tv_name!!.text = item.user_name
                    tv_id!!.text = "ID：" + item.id!!
                }
            } else if (QIANDAO == requestTag) {
                val item = GsonUtils.jsonToClass(result, SignItem::class.java)
                if (item != null) {
                    if (item.result) {
                        tv_qiandao!!.text = "签到"
                    } else {
                        tv_qiandao!!.text = "已签到"
                    }
                }
            } else if (coupons_url == requestTag) {
                val item = GsonUtils.jsonToClass(result, CouponsItem::class.java)
                if (item != null && item.coupons != null && item.coupons!!.size > 0) {
                    names[1] = "优惠券(" + item.coupons!!.size + ")"
                    userAdapter!!.notifyDataSetChanged()
                }
            } else if (point_url == requestTag) {
                val item = GsonUtils.jsonToClass(result, PointItem::class.java)
                if (item != null && item.message != null && item.message!!.size > 0) {
                    var total = 0.0
                    for (point in item.message!!) {
                        total += point.value
                    }
                    val d = StringUtils.doubleFormat(total)
                    names[2] = "我的积分($d)"
                    userAdapter!!.notifyDataSetChanged()
                }
            }
        }
    }

    companion object {

        /**
         * 用户信息
         */
        val USER_INFO = "USER_INFO"
        /**
         * 签到
         */
        val QIANDAO = "qiandao"

        //优惠券
        val coupons_url = "coupons_url"

        //积分
        val point_url = "point_url"
    }
}
