package com.dingshuwang.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView

import com.dingshuwang.adapter.FindAdapter
import com.dingshuwang.base.BaseFragment
import com.dingshuwang.model.FindItem
import com.dingshuwang.util.GsonUtils
import com.dingshuwang.util.RequestUtils
import com.dingshuwang.util.UIUtil
import com.dingshuwang.view.XListView

import java.util.ArrayList
import java.util.LinkedList

import butterknife.BindView
import butterknife.OnClick
import com.dingshuwang.*

/**
 * Created by tx on 2017/6/6.
 */

class FindFragment : BaseFragment(), DataView {

    @BindView(R.id.xListView)
    lateinit var xListView: XListView

    @BindView(R.id.et_mail)
    lateinit var et_mail: EditText
    @BindView(R.id.search)
    lateinit var search: ImageView

    private var findAdapter: FindAdapter? = null
    private val mList = LinkedList<FindItem.Find>()
    private var currentPage = 0
    private val isEnd = false
    private var count = 0

    override val fragmentTitle: String
        get() = ""


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_find, null)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        xListView!!.goneHeader()
        xListView!!.setPullRefreshEnable(false)
        mList.clear()
        findAdapter = null
        currentPage = 0
        count = 0
        getFindData()

        et_mail!!.setOnClickListener(View.OnClickListener {
            if (UIUtil.isfastdoubleClick()) {
                return@OnClickListener
            }
            SearchFindActivity.actionSearchFind(mActivity)
        })

        search!!.setOnClickListener(View.OnClickListener {
            if (UIUtil.isfastdoubleClick()) {
                return@OnClickListener
            }
            SearchFindActivity.actionSearchFind(mActivity)
        })

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
                    //                    if(isEnd){
                    //                        xListView.goneFooter();
                    //                        return;
                    //                    }

                    xListView!!.showFooter()
                    getFindData()
                    //                    if (mList.size() % 15 ==0) {
                    //                        xListView.showFooter();
                    //                        getFindData();
                    //                    }else {
                    //                        xListView.goneFooter();
                    //                    }

                }
            }
        })

    }

    private fun getFindData() {
        currentPage++
        Log.i("》》》》   ", "  currentpage ===  $currentPage")
        val url = String.format(APIURL.FIND, "", currentPage, Math.random().toString() + "")
        RequestUtils.getDataFromUrl(mActivity, url, this, FIND_TAG, false, false)
    }

    override fun onGetDataFailured(msg: String, requestTag: String) {
        currentPage--
        count = 0
        onLoad()
    }

    override fun onGetDataSuccess(result: String, requestTag: String) {
        if (result != null) {
            if (FIND_TAG == requestTag) {
                val findItem = GsonUtils.jsonToClass(result, FindItem::class.java)
                if (findItem != null && "true" == findItem.result && findItem.pros != null && findItem.pros!!.size > 0) {
                    mList.addAll(findItem.pros!!)
                    if (!mList.isEmpty()) {
                        if (findAdapter == null) {
                            findAdapter = FindAdapter(mList, mActivity)
                            xListView!!.setAdapter(findAdapter!!)
                        } else {
                            findAdapter!!.notifyDataSetChanged()
                        }
                        count = 0
                    }

                    Log.i("》》》》 list success == ", " $currentPage")
                } else {
                    Log.i("》》》》 list fail page== ", " $currentPage")
                    //                    isEnd = true;
                }
            }
        }
        onLoad()
    }

    private fun onLoad() {
        xListView!!.stopLoadMore()
    }

    companion object {

        private val FIND_TAG = "find_tag"
    }

}
