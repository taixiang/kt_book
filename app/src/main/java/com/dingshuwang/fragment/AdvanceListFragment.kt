package com.dingshuwang.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import butterknife.BindView

import com.dingshuwang.DataView
import com.dingshuwang.R
import com.dingshuwang.adapter.KeywordsAdapter
import com.dingshuwang.base.BaseFragment
import com.dingshuwang.model.SearchItem
import com.dingshuwang.util.GsonUtils


/**
 * Created by tx on 2017/6/20.
 */

class AdvanceListFragment : BaseFragment(), DataView {

    @BindView(R.id.listView)
    lateinit var listView: ListView

    override val fragmentTitle: String
        get() = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_advancelist, null)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val advance = arguments!!.getString("advance")
        val searchItem = GsonUtils.jsonToClass(advance!!, SearchItem::class.java)
        if (null != searchItem && searchItem.pros!!.size > 0) {
            listView!!.adapter = KeywordsAdapter(searchItem.pros!!, mActivity)
        }


    }

    override fun onGetDataFailured(msg: String, requestTag: String) {

    }

    override fun onGetDataSuccess(result: String, requestTag: String) {

    }

    companion object {

        fun newInstance(item: String): AdvanceListFragment {
            val fragment = AdvanceListFragment()
            val bundle = Bundle()
            bundle.putString("advance", item)
            fragment.arguments = bundle
            return fragment
        }
    }
}
