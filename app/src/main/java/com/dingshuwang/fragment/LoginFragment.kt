package com.dingshuwang.fragment


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText

import com.dingshuwang.APIURL
import com.dingshuwang.Constants
import com.dingshuwang.DataView
import com.dingshuwang.PREF
import com.dingshuwang.R
import com.dingshuwang.RegisterActivity
import com.dingshuwang.base.BaseFragment
import com.dingshuwang.base.MMApplication
import com.dingshuwang.model.LoginItem
import com.dingshuwang.util.GsonUtils
import com.dingshuwang.util.RequestUtils
import com.dingshuwang.util.StringUtils
import com.dingshuwang.util.UIUtil

import butterknife.BindView
import butterknife.OnClick

/**
 *
 */
class LoginFragment : BaseFragment(), DataView {

    @BindView(R.id.et_name)
    lateinit var et_name: EditText
    @BindView(R.id.et_password)
    lateinit var et_password: EditText
    @BindView(R.id.et_code)
    lateinit var et_code: EditText

    private var tag: Int = 0

    override val fragmentTitle: String
        get() = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_login, null)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (arguments != null) {
            tag = arguments!!.getInt("tag")
        }

    }


    @OnClick(R.id.tv_login)
    internal fun Login() {
        if (UIUtil.isfastdoubleClick()) {
            return
        }

        val name = et_name!!.text.toString().trim { it <= ' ' }
        val password = et_password!!.text.toString().trim { it <= ' ' }
        if (StringUtils.isEmpty(name)) {
            mActivity.showToast("请填写账户")
            return
        }
        if (StringUtils.isEmpty(password)) {
            mActivity.showToast("请填写密码")
            return
        }
        loginUser(name, password)

    }

    @OnClick(R.id.tv_register)
    internal fun register() {
        if (UIUtil.isfastdoubleClick()) {
            return
        }
        loadNext(RegisterActivity::class.java!!)
    }

    private fun loginUser(name: String, password: String) {
        val url = String.format(APIURL.LOGIN_URL, name, password, Constants.token)
        RequestUtils.getDataFromUrl(mActivity, url, this, LOGIN_TAG, false, false)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onGetDataFailured(msg: String, requestTag: String) {
        mActivity.showToast("登录失败，稍后再试")

    }

    override fun onGetDataSuccess(result: String, requestTag: String) {
        if (null != result) {
            val loginItem = GsonUtils.jsonToClass(result, LoginItem::class.java)
            if (loginItem != null && "true" == loginItem.result) {
                mActivity.showToast("登录成功")
                MMApplication.mIsLogin = true
                mActivity.preferences!!.edit().putString(PREF.PREF_USER_ID, loginItem.id).apply()
                Constants.USER_ID = loginItem.id
                val intent = Intent(Constants.LOGIN_SUCCESS)
                mActivity.sendBroadcast(intent)
                if (tag == Constants.LOGIN) {
                    mActivity.finish()
                } else {
                    mActivity.setResult(Constants.UNLOGIN)
                    mActivity.finish()
                }

            } else {
                mActivity.showToast("登录失败，账号或密码有误")
            }
        }
    }

    companion object {

        private val LOGIN_TAG = "login_tag"


        fun newInstance(): LoginFragment {
            return LoginFragment()
        }

        fun newInstance(tag: Int): LoginFragment {
            val fragment = LoginFragment()
            val bundle = Bundle()
            bundle.putInt("tag", tag)
            fragment.arguments = bundle
            return fragment
        }
    }
}
