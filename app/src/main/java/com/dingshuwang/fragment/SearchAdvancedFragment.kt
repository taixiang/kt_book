package com.dingshuwang.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import butterknife.BindView

import com.dingshuwang.APIURL
import com.dingshuwang.AdvanceListActivity
import com.dingshuwang.DataView
import com.dingshuwang.R
import com.dingshuwang.base.BaseFragment
import com.dingshuwang.model.SearchItem
import com.dingshuwang.util.GsonUtils
import com.dingshuwang.util.RequestUtils
import com.dingshuwang.util.StringUtils
import com.dingshuwang.util.UIUtil

import butterknife.OnClick

/**
 * Created by tx on 2017/6/19.
 */

class SearchAdvancedFragment : BaseFragment(), DataView {

    @BindView(R.id.et_name)
    internal var et_name: EditText? = null
    @BindView(R.id.et_author)
    internal var et_author: EditText? = null
    @BindView(R.id.et_isbn)
    internal var et_isbn: EditText? = null
    @BindView(R.id.et_publish)
    internal var et_publish: EditText? = null

    @BindView(R.id.tv_search)
    internal var tv_search: TextView? = null

    override val fragmentTitle: String
        get() = ""

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search_advanced, null)
    }

    @OnClick(R.id.tv_search)
    internal fun search() {
        if (UIUtil.isfastdoubleClick()) {
            return
        }
        val name = et_name!!.text.toString()
        val author = et_author!!.text.toString()
        val isbn = et_isbn!!.text.toString()
        val publish = et_publish!!.text.toString()
        if (StringUtils.isEmpty(name) && StringUtils.isEmpty(author) && StringUtils.isEmpty(isbn) && StringUtils.isEmpty(publish)) {
            mActivity.showToast("请输入搜索项")
            return
        }
        getSearchAdv(name, author, isbn, publish)
    }

    private fun getSearchAdv(name: String, author: String, isbn: String, publish: String) {
        val url = String.format(APIURL.SEARCH_ADVANCE, name, author, isbn, publish)
        RequestUtils.getDataFromUrl(mActivity, url, this, SEARCH_ADVANCE, false, false)
    }

    override fun onGetDataFailured(msg: String, requestTag: String) {

    }

    override fun onGetDataSuccess(result: String, requestTag: String) {
        if (result != null) {
            if (SEARCH_ADVANCE == requestTag) {
                val searchItem = GsonUtils.jsonToClass(result, SearchItem::class.java)
                if ("true" == searchItem!!.result) {
                    AdvanceListActivity.actionAdvance(mActivity, result)
                } else {
                    mActivity.showToast("没有查询到数据")
                }
            }
        }

    }

    companion object {

        private val SEARCH_ADVANCE = "search_advance"


        fun newInstance(): SearchAdvancedFragment {
            return SearchAdvancedFragment()
        }
    }
}
