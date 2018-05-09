package com.dingshuwang.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import android.widget.AbsListView
import android.widget.EditText
import android.widget.ImageView
import butterknife.BindView

import com.dingshuwang.APIURL
import com.dingshuwang.Constants
import com.dingshuwang.DataView
import com.dingshuwang.IsbnCodeActivity
import com.dingshuwang.R
import com.dingshuwang.adapter.KeywordsAdapter
import com.dingshuwang.base.BaseFragment
import com.dingshuwang.model.SearchItem
import com.dingshuwang.util.GsonUtils
import com.dingshuwang.util.PhotoUtils
import com.dingshuwang.util.RequestUtils
import com.dingshuwang.util.UIUtil
import com.dingshuwang.view.XListView
import com.xys.libzxing.zxing.activity.CaptureActivity

import java.util.LinkedList
import java.util.Timer
import java.util.TimerTask

import butterknife.OnClick

/**
 * Created by tx on 2017/6/14.
 */

class SearchCommonFragment : BaseFragment(), DataView {

    @BindView(R.id.xListView)
    internal var xListView: XListView? = null

    @BindView(R.id.search)
    internal var search: ImageView? = null
    @BindView(R.id.et_mail)
    internal var et_mail: EditText? = null
    @BindView(R.id.iv_back)
    internal var iv_back: ImageView? = null

    private var adapter: KeywordsAdapter? = null
    private var currentPage = 0
    private var count = 0
    private val mList = LinkedList<SearchItem.Search>()
    private var key = ""

    override val fragmentTitle: String
        get() = ""

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        xListView!!.goneHeader()
        xListView!!.setPullRefreshEnable(false)
        mList.clear()
        adapter = null
        currentPage = 0
        count = 0

        Handler().postDelayed({
            val imm = mActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(et_mail, 0)
        }, 800)

        search!!.setOnClickListener {
            mActivity.hideSoftKeyBorard()

            key = et_mail!!.text.toString()
            mList.clear()
            currentPage = 0
            count = 0
            adapter = null
            if (key.trim { it <= ' ' }.length <= 0) {
                mActivity.showToast("请输入关键字")
            } else {
                getSearchData(key)
            }
        }

        iv_back!!.setOnClickListener { mActivity.finish() }


        xListView!!.setOnScrollListener(object : AbsListView.OnScrollListener {
            private var lastItemIndex: Int = 0// 当前ListView中最后一个Item的索引
            override fun onScrollStateChanged(view: AbsListView, scrollState: Int) {

            }

            override fun onScroll(view: AbsListView, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
                lastItemIndex = firstVisibleItem + visibleItemCount

                if (lastItemIndex == totalItemCount && totalItemCount > 0) {

                    Log.i("》》》》》   last count === ", count.toString() + "")
                    if (count > 0) {
                        xListView!!.goneFooter()
                        return
                    }
                    count++

                    xListView!!.showFooter()
                    getSearchData(key)
                }
            }
        })

    }

    private fun getSearchData(keyword: String) {
        currentPage++
        val url = String.format(APIURL.SEARCH_URL, keyword, currentPage)
        RequestUtils.getDataFromUrl(mActivity, url, this, SEARCH_KEY, false, false)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_searchcommon, null)
    }

    @OnClick(R.id.iv_sao)
    internal fun cammer() {
        if (UIUtil.isfastdoubleClick()) {
            return
        }
        val openCameraIntent = Intent(mActivity, CaptureActivity::class.java)
        openCameraIntent.putExtra("tag_code", 1)
        mActivity.startActivityForResult(openCameraIntent, Constants.CODE_HOME)
    }

    override fun onGetDataFailured(msg: String, requestTag: String) {

    }

    override fun onGetDataSuccess(result: String, requestTag: String) {
        if (result != null) {
            if (SEARCH_KEY == requestTag) {
                val search = GsonUtils.jsonToClass(result, SearchItem::class.java)
                if (search != null && search.result != null && search.result == "true" && search.pros != null && search.pros!!.size > 0) {
                    mList.addAll(search.pros!!)
                    Log.i("》》》》》  ", " mlist size == " + mList.size)
                    if (adapter == null) {
                        adapter = KeywordsAdapter(mList, mActivity)
                        xListView!!.setAdapter(adapter!!)
                    } else {
                        adapter!!.notifyDataSetChanged()
                    }
                    count = 0
                }
            }
        }
        onLoad()

    }

    private fun onLoad() {
        xListView!!.stopLoadMore()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.i("》》》》》 search common", "requestCode ==== $requestCode")
        Log.i("》》》》》 search common", "resultcode ==== $resultCode")

        if (requestCode == Constants.CODE_HOME) { //首页扫描后跳列表页
            if (resultCode == Constants.CAMMER) {
                val bundle = data!!.extras
                val scanResult = bundle!!.getString("result")
                Log.i("》》》》》  ", " scanresult === " + scanResult!!)
                if (scanResult != null) {
                    IsbnCodeActivity.actIsbn(mActivity, scanResult)
                }
            }
        }
    }

    companion object {

        private val SEARCH_KEY = "search_key"

        fun newInstance(): SearchCommonFragment {

            return SearchCommonFragment()
        }
    }

}
