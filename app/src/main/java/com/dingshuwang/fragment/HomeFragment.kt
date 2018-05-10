package com.dingshuwang.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import butterknife.BindView

import com.dingshuwang.APIURL
import com.dingshuwang.Constants
import com.dingshuwang.DataView
import com.dingshuwang.R
import com.dingshuwang.SearchAdvancedActivity
import com.dingshuwang.SearchCommonActivity
import com.dingshuwang.TestAct
import com.dingshuwang.WebViewAct
import com.dingshuwang.adapter.ColumnAdapter
import com.dingshuwang.adapter.FreeAdapter
import com.dingshuwang.base.BaseFragment
import com.dingshuwang.model.HomeFreeItem
import com.dingshuwang.model.HomeMiddleItem
import com.dingshuwang.model.TestItem
import com.dingshuwang.util.GlideImgManager
import com.dingshuwang.util.GsonUtils
import com.dingshuwang.util.RequestUtils
import com.dingshuwang.util.UIUtil
import com.dingshuwang.view.CustomGridView
import com.xys.libzxing.zxing.activity.CaptureActivity

import butterknife.OnClick

/**
 * Created by tx on 2017/6/6.
 */

class HomeFragment : BaseFragment(), DataView {

    @BindView(R.id.grid_column)
    lateinit var grid_column: CustomGridView

    @BindView(R.id.grid_free)
    lateinit var grid_free: CustomGridView

    @BindView(R.id.iv_share)
    lateinit var iv_share: ImageView

    @BindView(R.id.iv_bottom)
    lateinit var iv_bottom: ImageView

    @BindView(R.id.et_mail)
    lateinit var et_mail: EditText
    @BindView(R.id.iv_search)
    lateinit var iv_search: ImageView

    private var columnAdapter: ColumnAdapter? = null
    private var freeAdapter: FreeAdapter? = null
    private var homeMiddleItem: HomeMiddleItem? = null
    private var homeBottomItem: HomeMiddleItem? = null

    override val fragmentTitle: String
        get() = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, null)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        columnAdapter = null
        freeAdapter = null
        getMiddle()
        getShare()
        getFree()
        getBottom()
        et_mail!!.setOnClickListener(View.OnClickListener {
            if (UIUtil.isfastdoubleClick()) {
                return@OnClickListener
            }
            SearchCommonActivity.actionSearchCommon(mActivity)
        })
        iv_search!!.setOnClickListener(View.OnClickListener {
            if (UIUtil.isfastdoubleClick()) {
                return@OnClickListener
            }
            SearchCommonActivity.actionSearchCommon(mActivity)
        })

    }

    private fun getMiddle2() {
        val url = "http://www.mocky.io/v2/598ab678110000c700515cc0"
        RequestUtils.getDataFromUrl(mActivity, url, this, "test", false, false)
    }

    //中间小说区域
    private fun getMiddle() {
        val url = APIURL.Home_Middle
        RequestUtils.getDataFromUrl(mActivity, url, this, HOME_MIDDLE, false, false)
    }

    //享图书
    private fun getShare() {
        val url = APIURL.HOME_SHARE
        RequestUtils.getDataFromUrl(mActivity, url, this, HOME_SHARE, false, false)
    }

    //免费领取
    private fun getFree() {
        val url = APIURL.HOME_FREE
        RequestUtils.getDataFromUrl(mActivity, url, this, HOME_FREE, false, false)
    }

    //底部图片
    private fun getBottom() {
        val url = APIURL.HOME_BOTTOM
        RequestUtils.getDataFromUrl(mActivity, url, this, HOME_BOTTOM, false, false)
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

    @OnClick(R.id.highs)
    internal fun searchAdvanced() {
        if (UIUtil.isfastdoubleClick()) {
            return
        }
        SearchAdvancedActivity.actionSearchAdvaced(mActivity)
    }

    @OnClick(R.id.iv_share)
    internal fun share() {
        if (UIUtil.isfastdoubleClick()) {
            return
        }
        val url = homeMiddleItem!!.data!![0].url
        val title = homeMiddleItem!!.data!![0].keywords
        if (homeMiddleItem != null && null != url && title != null) {
            WebViewAct.actionWebView(mActivity, title, url)
        }
    }

    @OnClick(R.id.iv_bottom)
    internal fun bottom() {
        if (UIUtil.isfastdoubleClick()) {
            return
        }
        val url = homeBottomItem!!.data!![0].url
        val title = homeBottomItem!!.data!![0].keywords
        if (homeBottomItem != null && null != url && title != null) {
            WebViewAct.actionWebView(mActivity, "", url)
        }

    }

    override fun onGetDataFailured(msg: String, requestTag: String) {}

    override fun onGetDataSuccess(result: String, requestTag: String) {
        if (result != null) {

            if (HOME_MIDDLE == requestTag) {
                val homeMiddleItem = GsonUtils.jsonToClass(result, HomeMiddleItem::class.java)
                if (homeMiddleItem!!.result && homeMiddleItem.data!!.size > 0) {
                    if (columnAdapter == null) {
                        columnAdapter = ColumnAdapter(homeMiddleItem.data, mActivity)
                        grid_column!!.adapter = columnAdapter
                    } else {
                        columnAdapter!!.notifyDataSetChanged()
                    }
                }
            } else if (HOME_SHARE == requestTag) {
                homeMiddleItem = GsonUtils.jsonToClass(result, HomeMiddleItem::class.java)
                if (homeMiddleItem!!.result && homeMiddleItem!!.data!!.size > 0) {
                    GlideImgManager.loadImage(mActivity, homeMiddleItem!!.data!![0].image_url!!, iv_share!!)
                }
            } else if (HOME_FREE == requestTag) {
                val freeItem = GsonUtils.jsonToClass(result, HomeFreeItem::class.java)
                if (freeItem!!.result && freeItem.data!!.size > 0) {
                    if (null == freeAdapter) {
                        freeAdapter = FreeAdapter(freeItem.data!!, mActivity)
                        grid_free!!.adapter = freeAdapter
                    } else {
                        freeAdapter!!.notifyDataSetChanged()
                    }
                }
            } else if (HOME_BOTTOM == requestTag) {
                homeBottomItem = GsonUtils.jsonToClass(result, HomeMiddleItem::class.java)
                if (homeBottomItem!!.result && homeBottomItem!!.data!!.size > 0) {
                    GlideImgManager.loadImage(mActivity, homeBottomItem!!.data!![0].image_url!!, iv_bottom!!)
                }
            } else {
                Log.i("  testItem  result ", result)

                val item = GsonUtils.jsonToClass(result, TestItem::class.java)
                if (item != null) {
                    Log.i("  testItem  ", item.toString())
                }


            }

        }
    }

    companion object {


        private val HOME_MIDDLE = "home_middle"
        private val HOME_SHARE = "home_share"
        private val HOME_FREE = "home_free"
        private val HOME_BOTTOM = "home_bottom"
    }
}
