package com.dingshuwang.fragment

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.FragmentTabHost
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TabHost

import com.dingshuwang.Constants
import com.dingshuwang.IsbnCodeActivity
import com.dingshuwang.LoginActivity
import com.dingshuwang.R
import com.dingshuwang.base.BaseFragment
import com.dingshuwang.base.MMApplication
import com.dingshuwang.interfaceFile.GoHomeListener
import com.dingshuwang.util.PhotoUtils

/**
 * Created by tx on 2017/6/6.
 */

class MainFragment : BaseFragment() {

    private var isPublish: Boolean = false
    private var isShoppingCart: Boolean = false
    private var isUser: Boolean = false


    private var mFragmentTabHost: FragmentTabHost? = null

    override val fragmentTitle: String
        get() = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main, null)
        initView(view)

        val imageView = view.findViewById<View>(R.id.image) as ImageView
        imageView.setImageResource(R.mipmap.welcome)
        Handler().postDelayed({ imageView.visibility = View.GONE }, 3000)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    private fun initView(view: View) {
        mFragmentTabHost = view.findViewById<View>(android.R.id.tabhost) as FragmentTabHost
        setUpFragmentTabHost()
    }

    private fun setUpFragmentTabHost() {
        mFragmentTabHost!!.setup(mActivity, mActivity.supportFragmentManager, R.id.frame_real_tab_container)
        val inflater = LayoutInflater.from(mActivity)
        //=================================================
        val viewSelect = inflater.inflate(R.layout.layout_home_tab_item, null)
        val btnSelect = viewSelect.findViewById(R.id.btn_tab) as Button
        val ivSelect = viewSelect.findViewById(R.id.iv_tab) as ImageView
        ivSelect.setImageResource(R.drawable.activated_home_tab_select_background)
        ivSelect.isActivated = true
        btnSelect.isActivated = true
        btnSelect.text = TAB_HOME

        //发现
        val viewFind = inflater.inflate(R.layout.layout_home_tab_item, null)
        val btnFind = viewFind.findViewById(R.id.btn_tab) as Button
        val ivFind = viewFind.findViewById(R.id.iv_tab) as ImageView
        ivFind.setImageResource(R.drawable.activated_home_tab_find_background)
        btnFind.text = TAB_FIND

        //================================================= 发布
        val viewPublish = inflater.inflate(R.layout.layout_home_tab_item, null)
        val btnPublish = viewPublish.findViewById(R.id.btn_tab) as Button
        val ivPublish = viewPublish.findViewById(R.id.iv_tab) as ImageView
        ivPublish.setImageResource(R.drawable.activated_home_tab_publish_background)
        btnPublish.text = TAB_PUBLISH
        //=================================================
        val viewShop = inflater.inflate(R.layout.layout_home_tab_item, null)
        val btnShoppingCart = viewShop.findViewById(R.id.btn_tab) as Button
        val ivShoppingCart = viewShop.findViewById(R.id.iv_tab) as ImageView
        ivShoppingCart.setImageResource(R.drawable.activated_home_tab_shopping_cart_background)
        btnShoppingCart.text = TAB_SHOPPINGCART
        //=================================================
        val viewUser = inflater.inflate(R.layout.layout_home_tab_item, null)
        val btnUser = viewUser.findViewById(R.id.btn_tab) as Button
        val ivUser = viewUser.findViewById(R.id.iv_tab) as ImageView
        ivUser.setImageResource(R.drawable.activated_home_tab_user_background)
        btnUser.text = TAB_ME
        //=================================================


        mFragmentTabHost!!.addTab(mFragmentTabHost!!.newTabSpec(btnSelect.text.toString()).setIndicator(viewSelect), HomeFragment::class.java, null)
        mFragmentTabHost!!.addTab(mFragmentTabHost!!.newTabSpec(btnFind.text.toString()).setIndicator(viewFind), FindFragment::class.java, null)
        mFragmentTabHost!!.addTab(mFragmentTabHost!!.newTabSpec(btnPublish.text.toString()).setIndicator(viewPublish), PublishFragment::class.java, null)
        mFragmentTabHost!!.addTab(mFragmentTabHost!!.newTabSpec(btnShoppingCart.text.toString()).setIndicator(viewShop), ShoppingCartFragment::class.java, null)
        mFragmentTabHost!!.addTab(mFragmentTabHost!!.newTabSpec(btnUser.text.toString()).setIndicator(viewUser), UserCenterFragment::class.java, null)

        mFragmentTabHost!!.setOnTabChangedListener { tabId ->
            btnSelect.isActivated = tabId == btnSelect.text
            ivSelect.isActivated = tabId == btnSelect.text

            btnFind.isActivated = tabId == btnFind.text
            ivFind.isActivated = tabId == btnFind.text

            isPublish = tabId == btnPublish.text
            btnPublish.isActivated = isPublish
            ivPublish.isActivated = isPublish

            isShoppingCart = tabId == btnShoppingCart.text
            btnShoppingCart.isActivated = isShoppingCart
            ivShoppingCart.isActivated = isShoppingCart

            isUser = tabId == btnUser.text
            btnUser.isActivated = isUser
            ivUser.isActivated = isUser


            //                if (isUser || isGoods||isShoppingCart)
            //                {
            //                    mActivity.hideTitleBar();
            //                } else
            //                {
            //                    mActivity.showTitleBar();
            //                }
            mActivity.hideTitleBar()
            if (isPublish && !MMApplication.mIsLogin) {
                toFirstTab(1)
            }
            if (isShoppingCart && !MMApplication.mIsLogin) {
                toFirstTab(2)
            }
            if (isUser && !MMApplication.mIsLogin) {
                toFirstTab(3)
            }
        }
    }

    fun toTab(index: Int) {
        if (null != mFragmentTabHost) {
            mFragmentTabHost!!.currentTab = index
        }
    }

    private fun toFirstTab(requestCode: Int) {
        val intent = Intent(mActivity, LoginActivity::class.java)
        mActivity.startActivityForResult(intent, requestCode)
        mActivity.overridePendingTransition(R.anim.push_left_in,
                R.anim.push_left_out)
    }


    override fun onDestroy() {
        super.onDestroy()

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.i("》》》》》 main", "requestCode ==== $requestCode")
        Log.i("》》》》》 main", "resultcode ==== $resultCode")

        if (resultCode == Constants.UNLOGIN) {  //未登录，发布、购物车等页面返回到首页
            if (requestCode == 1) {
                toTab(2)
            } else if (requestCode == 2) {
                toTab(3)
            } else if (requestCode == 3) {
                toTab(4)
            }
        } else if (requestCode == Constants.CODE_HOME) { //首页扫描后跳列表页
            if (resultCode == Constants.CAMMER) {
                val bundle = data!!.extras
                val scanResult = bundle!!.getString("result")
                Log.i("》》》》》  ", " scanresult === " + scanResult!!)
                if (scanResult != null) {
                    IsbnCodeActivity.actIsbn(mActivity, scanResult)
                }
            }
        } else if (requestCode == PhotoUtils.REQUEST_FROM_CAMERA || PhotoUtils.REQUEST_FROM_PHOTO == requestCode) { //发布添加封面
            toTab(2)
        } else if (resultCode == Constants.CAMMER_PUBLISH) { //发布扫描后返回
            toTab(2)
        } else {
            toTab(0)
        }
    }

    companion object {

        private val TAB_HOME = "首页"
        private val TAB_FIND = "发现"
        private val TAB_PUBLISH = "发布"
        private val TAB_SHOPPINGCART = "购物车"
        private val TAB_ME = "我的"

        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }

}
