package com.dingshuwang.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText

import com.dingshuwang.APIURL
import com.dingshuwang.Constants
import com.dingshuwang.DataView
import com.dingshuwang.PREF
import com.dingshuwang.R
import com.dingshuwang.base.BaseFragment
import com.dingshuwang.base.MMApplication
import com.dingshuwang.model.RegisterItem
import com.dingshuwang.util.GsonUtils
import com.dingshuwang.util.RequestUtils
import com.dingshuwang.util.StringUtils
import com.dingshuwang.util.UIUtil

import butterknife.BindView
import butterknife.OnClick

/**
 * Created by tx on 2017/6/8.
 */

class RegisterFragment : BaseFragment(), DataView {
    @BindView(R.id.et_name)
    var et_name: EditText? = null
    @BindView(R.id.et_password)
    var et_password: EditText? = null
    @BindView(R.id.et_code)
    var et_code: EditText? = null
    @BindView(R.id.et_confirm_password)
    var et_confirm_password: EditText? = null

    override val fragmentTitle: String
        get() = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register, null)
    }

    @OnClick(R.id.tv_register)
    internal fun register() {
        if (UIUtil.isfastdoubleClick()) {
            return
        }

        val name = et_name!!.text.toString().trim { it <= ' ' }
        val password = et_password!!.text.toString().trim { it <= ' ' }
        val confirm_password = et_confirm_password!!.text.toString().trim { it <= ' ' }
        if (StringUtils.isEmpty(name)) {
            mActivity.showToast("请填写账户")
            return
        }
        if (StringUtils.isEmpty(password)) {
            mActivity.showToast("请填写密码")
            return
        }
        if (password != confirm_password) {
            mActivity.showToast("两次输入密码不同")
            return
        }
        registerUser(name, password)
    }

    @OnClick(R.id.tv_login)
    internal fun login() {
        if (UIUtil.isfastdoubleClick()) {
            return
        }
        mActivity.finish()
    }

    private fun registerUser(name: String, password: String) {
        val url = String.format(APIURL.REGISTER_URL, name, password, Constants.token)
        RequestUtils.getDataFromUrl(mActivity, url, this, REGISTER_URL, false, false)
    }

    override fun onGetDataFailured(msg: String, requestTag: String) {
        if (REGISTER_URL == requestTag) {
            mActivity.showToast("注册失败，稍后再试")
        }
    }

    override fun onGetDataSuccess(result: String, requestTag: String) {
        if (result != null) {
            val registerItem = GsonUtils.jsonToClass(result, RegisterItem::class.java)
            if (registerItem != null) {
                if (REGISTER_URL == requestTag) {
                    mActivity.showToast(registerItem.info!!)
                    if ("y" == registerItem.status) {
                        //                        MMApplication.mIsLogin = true;
                        //                        mActivity.getPreferences().edit().putString(PREF.PREF_USER_ID,registerItem.user_id).apply();
                        //                        Constants.USER_ID = registerItem.user_id;
                        //                        Intent intent = new Intent(Constants.LOGIN_FLAG_CLEAR);
                        //                        mActivity.sendBroadcast(intent);
                        mActivity.finish()
                    }
                }
            }
        }
    }

    companion object {

        private val REGISTER_URL = "register_url"

        fun newInstance(): RegisterFragment {
            return RegisterFragment()
        }
    }
}