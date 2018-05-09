package com.dingshuwang.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import butterknife.BindView

import com.dingshuwang.APIURL
import com.dingshuwang.DataView
import com.dingshuwang.R
import com.dingshuwang.adapter.KeywordsAdapter
import com.dingshuwang.base.BaseFragment
import com.dingshuwang.model.SearchItem
import com.dingshuwang.util.GsonUtils
import com.dingshuwang.util.RequestUtils


class IsbnCodeFragment : BaseFragment(), DataView {

    @BindView(R.id.listview)
    internal var listview: ListView? = null

    @BindView(R.id.tv_none)
    internal var tv_none: TextView? = null

    private var isbn: String? = null
    private var searchAdapter: KeywordsAdapter? = null

    override val fragmentTitle: String
        get() = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_isbn, null)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isbn = arguments!!.get("isbn") as String
        searchAdapter = null
        doGetIsbn(isbn)
    }

    private fun doGetIsbn(isbn_id: String?) {
        val url = String.format(APIURL.CODE_ISBN, isbn_id)
        RequestUtils.getDataFromUrl(mActivity, url, this, "", false, false)
    }

    override fun onGetDataFailured(msg: String, requestTag: String) {
        mActivity.showToast("查询失败，稍后再试")
    }

    override fun onGetDataSuccess(result: String, requestTag: String) {
        if (null != result) {
            val searchItem = GsonUtils.jsonToClass(result, SearchItem::class.java)
            if (searchItem != null && "true" == searchItem.result) {
                if (null == searchAdapter) {
                    searchAdapter = KeywordsAdapter(searchItem.pros!!, mActivity)
                    listview!!.adapter = searchAdapter
                } else {
                    searchAdapter!!.notifyDataSetChanged()
                }
            } else {
                listview!!.visibility = View.GONE
                tv_none!!.visibility = View.VISIBLE
            }

        }

    }

    companion object {

        fun newInstance(isbn: String): IsbnCodeFragment {
            val fragment = IsbnCodeFragment()
            val bundle = Bundle()
            bundle.putString("isbn", isbn)
            fragment.arguments = bundle
            return fragment
        }
    }
}
