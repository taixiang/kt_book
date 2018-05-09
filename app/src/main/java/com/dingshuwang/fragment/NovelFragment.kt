package com.dingshuwang.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.EditText
import android.widget.ImageView
import butterknife.BindView

import com.dingshuwang.APIURL
import com.dingshuwang.DataView
import com.dingshuwang.R
import com.dingshuwang.adapter.KeywordsAdapter
import com.dingshuwang.adapter.NovelAdapter
import com.dingshuwang.base.BaseFragment
import com.dingshuwang.model.NovelItem
import com.dingshuwang.model.SearchItem
import com.dingshuwang.util.GsonUtils
import com.dingshuwang.util.RequestUtils
import com.dingshuwang.view.XListView

import java.util.LinkedList


/**
 * Created by tx on 2017/6/15.
 * 小说专区
 */

class NovelFragment : BaseFragment(), DataView {

    @BindView(R.id.xListView)
    internal var xListView: XListView? = null

    @BindView(R.id.search)
    internal var search: ImageView? = null
    @BindView(R.id.et_mail)
    internal var et_mail: EditText? = null

    private var adapter: NovelAdapter? = null
    private var currentPage = 0
    private var count = 0
    private val mList = LinkedList<NovelItem.Novel>()


    override val fragmentTitle: String
        get() = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_novel, null)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        xListView!!.goneHeader()
        xListView!!.setPullRefreshEnable(false)
        mList.clear()
        adapter = null
        currentPage = 0
        count = 0

        getNovelData()

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
                    getNovelData()
                }
            }
        })

    }

    private fun getNovelData() {
        currentPage++
        val url = String.format(APIURL.NOVEL_URL, currentPage)
        RequestUtils.getDataFromUrl(mActivity, url, this, KEYWORD, false, false)
    }

    override fun onGetDataFailured(msg: String, requestTag: String) {
        currentPage--
        count = 0
        onLoad()
    }

    override fun onGetDataSuccess(result: String, requestTag: String) {
        if (result != null) {
            if (KEYWORD == requestTag) {
                val novel = GsonUtils.jsonToClass(result, NovelItem::class.java)
                if (novel!!.result && novel.pros != null && novel.pros!!.size > 0) {
                    mList.addAll(novel.pros!!)
                    Log.i("》》》》》  ", " mlist size == " + mList.size)
                    if (adapter == null) {
                        adapter = NovelAdapter(mList, mActivity)
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

    companion object {

        private val KEYWORD = "KEYWORD"
        private val SEARCH = "SEARCH"

        fun newInstance(): NovelFragment {
            return NovelFragment()
        }
    }
}
