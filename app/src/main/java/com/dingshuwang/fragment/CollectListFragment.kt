package com.dingshuwang.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import butterknife.BindView

import com.dingshuwang.APIURL
import com.dingshuwang.Constants
import com.dingshuwang.DataView
import com.dingshuwang.R
import com.dingshuwang.adapter.CollectListAdapter
import com.dingshuwang.base.BaseFragment
import com.dingshuwang.model.CollectListItem
import com.dingshuwang.util.GsonUtils
import com.dingshuwang.util.RequestUtils


/**
 * Created by tx on 2017/6/24.
 */

class CollectListFragment : BaseFragment(), DataView {

    @BindView(R.id.listview)
    lateinit var listView: ListView

    override val fragmentTitle: String
        get() = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_collect_list, null)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        collectList()
    }

    override fun onResume() {
        super.onResume()
    }

    private fun collectList() {
        val url = String.format(APIURL.COLLECT_LIST_URL, Constants.USER_ID)
        RequestUtils.getDataFromUrl(mActivity, url, this, COLLECT_LIST, false, false)

    }

    override fun onGetDataFailured(msg: String, requestTag: String) {

    }

    override fun onGetDataSuccess(result: String, requestTag: String) {
        if (result != null) {
            if (COLLECT_LIST == requestTag) {
                val item = GsonUtils.jsonToClass(result, CollectListItem::class.java)
                if (item != null && item.result && item.favorites != null) {
                    listView!!.adapter = CollectListAdapter(mActivity, item.favorites)
                }
            }
        }
    }

    companion object {

        private val COLLECT_LIST = "collect_list_url"

        fun newInstance(): CollectListFragment {
            return CollectListFragment()
        }
    }
}
