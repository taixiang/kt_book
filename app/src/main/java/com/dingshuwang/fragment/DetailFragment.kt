package com.dingshuwang.fragment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import butterknife.BindView

import com.dingshuwang.APIURL
import com.dingshuwang.Constants
import com.dingshuwang.DataView
import com.dingshuwang.LoginActivity
import com.dingshuwang.MainActivity
import com.dingshuwang.R
import com.dingshuwang.ShopCartActivity
import com.dingshuwang.adapter.CommentAdapter
import com.dingshuwang.base.BaseFragment
import com.dingshuwang.base.MMApplication
import com.dingshuwang.model.AddCartItem
import com.dingshuwang.model.CollectItem
import com.dingshuwang.model.CommentItem
import com.dingshuwang.model.DetailItem
import com.dingshuwang.model.GroupInfoItem
import com.dingshuwang.util.GlideImgManager
import com.dingshuwang.util.GsonUtils
import com.dingshuwang.util.RequestUtils
import com.dingshuwang.util.UIUtil
import com.dingshuwang.view.CustomListView
import com.dingshuwang.view.XListView

import butterknife.OnClick

/**
 * Created by tx on 2017/6/14.
 */

class DetailFragment : BaseFragment(), DataView, View.OnClickListener {


    @BindView(R.id.iv_logo)
    internal var iv_logo: ImageView? = null
    @BindView(R.id.tv_name)
    internal var tv_name: TextView? = null

    @BindView(R.id.iv_minus)
    internal var iv_minus: ImageView? = null
    @BindView(R.id.tv_count)
    internal var tv_count: TextView? = null
    @BindView(R.id.iv_add)
    internal var iv_add: ImageView? = null

    @BindView(R.id.tv_isbn)
    internal var tv_isbn: TextView? = null
    @BindView(R.id.tv_price_sell)
    internal var tv_price_sell: TextView? = null
    @BindView(R.id.goods_nums) //库存
    internal var goods_nums: TextView? = null
    @BindView(R.id.tv_sale_nums)
    internal var tv_sale_nums: TextView? = null

    @BindView(R.id.webView)
    internal var webView: WebView? = null
    @BindView(R.id.listview)
    internal var listView: CustomListView? = null

    @BindView(R.id.tv_info)
    internal var tv_info: TextView? = null

    @BindView(R.id.rg)
    internal var radioGroup: RadioGroup? = null
    @BindView(R.id.rb_detail)
    internal var rb_detail: RadioButton? = null
    @BindView(R.id.rb_comment)
    internal var rb_comment: RadioButton? = null

    @BindView(R.id.com_container)
    internal var com_container: LinearLayout? = null

    @BindView(R.id.iv_collect)
    internal var iv_collect: ImageView? = null
    @BindView(R.id.tv_collect)
    internal var tv_collect: TextView? = null
    private var count = 0
    private var sell_count: Int = 0
    private var id: String? = null

    private var isCollect: Boolean = false //是否收藏

    override val fragmentTitle: String
        get() = ""

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        id = arguments!!.getString("id")
        getDetailData(id)
        showCollect()
        iv_minus!!.setOnClickListener(this)
        iv_add!!.setOnClickListener(this)

        radioGroup!!.setOnCheckedChangeListener { group, checkedId ->
            if (rb_detail!!.id == checkedId) {
                webView!!.visibility = View.VISIBLE
                com_container!!.visibility = View.GONE
            } else {
                webView!!.visibility = View.GONE
                com_container!!.visibility = View.VISIBLE
            }
        }

    }

    //详情
    private fun getDetailData(id: String?) {
        val url = String.format(APIURL.DETAIL_URL, id)
        RequestUtils.getDataFromUrl(mActivity, url, this, DETAIL_TAG, false, false)
    }

    //团购批发价
    private fun getGroupInfoData() {
        val url = APIURL.DETAIL_GROUPINFO
        RequestUtils.getDataFromUrl(mActivity, url, this, GROUP_INFO_TAG, false, false)
    }

    //评价
    private fun getComment() {
        val url = String.format(APIURL.Comment_url, id)
        RequestUtils.getDataFromUrl(mActivity, url, this, COMMENT_TAG, false, false)
    }

    //加入购物车
    private fun doAddCart() {
        val url = String.format(APIURL.CART_ADD_URL, Constants.USER_ID, id, tv_count!!.text.toString())
        RequestUtils.getDataFromUrlByPost(mActivity, url, "", this, ADD_CART_TAG, false, false)
    }

    //收藏
    private fun collectGoods() {
        val url = String.format(APIURL.COLLECT_URL, Constants.USER_ID, id)
        RequestUtils.getDataFromUrlByPost(mActivity, url, "", this, COLLECT_TAG, false, false)
    }

    //展示收藏
    private fun showCollect() {
        val url = String.format(APIURL.COLLECT_URL, Constants.USER_ID, id)
        RequestUtils.getDataFromUrlByPost(mActivity, url, "", this, SHOW_COLLECT, false, false)
    }

    //取消收藏
    private fun collectCancelGoods() {
        val url = String.format(APIURL.COLLECT_CANCEL_URL, Constants.USER_ID, id)
        RequestUtils.getDataFromUrlByPost(mActivity, url, "", this, COLLECT_CANCEL_TAG, false, false)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail, null)
    }

    override fun onDestroy() {
        super.onDestroy()

    }

    //加入购物车
    @OnClick(R.id.add_cart)
    internal fun addCart() {
        if (UIUtil.isfastdoubleClick()) {
            return
        }
        if (MMApplication.mIsLogin) {
            doAddCart()
        } else {
            LoginActivity.actionLogin(mActivity, Constants.LOGIN)
        }
    }

    //收藏
    @OnClick(R.id.collect)
    internal fun collect() {
        if (UIUtil.isfastdoubleClick()) {
            return
        }
        if (MMApplication.mIsLogin) {
            if (!isCollect) {
                collectGoods()
            } else {
                collectCancelGoods()
            }
        } else {
            LoginActivity.actionLogin(mActivity, Constants.LOGIN)
        }
    }

    //购物车界面
    @OnClick(R.id.shopcart)
    internal fun shopcart() {
        if (UIUtil.isfastdoubleClick()) {
            return
        }
        if (MMApplication.mIsLogin) {
            ShopCartActivity.actShop(mActivity)
        } else {
            LoginActivity.actionLogin(mActivity, Constants.LOGIN)
        }

    }


    override fun onGetDataFailured(msg: String, requestTag: String) {

    }

    override fun onGetDataSuccess(result: String, requestTag: String) {
        if (result != null) {
            if (DETAIL_TAG == requestTag) {
                val detailItem = GsonUtils.jsonToClass(result, DetailItem::class.java)
                if (detailItem != null && detailItem.result) {
                    getGroupInfoData()
                    getComment()
                    GlideImgManager.loadImage(mActivity, detailItem.img_url!!, iv_logo!!)
                    tv_name!!.text = detailItem.name
                    sell_count = Integer.parseInt(detailItem.goods_nums)
                    if (Integer.parseInt(detailItem.goods_nums) == 0) {
                        iv_minus!!.isEnabled = false
                        iv_add!!.isEnabled = false
                        tv_count!!.text = "0"
                    }

                    tv_isbn!!.text = detailItem.ISBN
                    tv_price_sell!!.text = "￥" + detailItem.price_sell!!
                    goods_nums!!.text = detailItem.goods_nums
                    tv_sale_nums!!.text = detailItem.sale_nums

                    var htmlData = detailItem.description
                    if (null != htmlData) {
                        htmlData = htmlData.replace("&".toRegex(), "")
                        htmlData = htmlData.replace("quot;".toRegex(), "\"")
                        htmlData = htmlData.replace("lt;".toRegex(), "<")
                        htmlData = htmlData.replace("gt;".toRegex(), ">")
                        htmlData = htmlData.replace("nbsp".toRegex(), " ")
                        webView!!.loadDataWithBaseURL(null, htmlData, "text/html", "utf-8", null)
                    }
                }
            } else if (GROUP_INFO_TAG == requestTag) {
                val item = GsonUtils.jsonToClass(result, GroupInfoItem::class.java)
                if (null != item!!.msg) {
                    tv_info!!.text = item.msg
                }
            } else if (COMMENT_TAG == requestTag) {
                val commentItem = GsonUtils.jsonToClass(result, CommentItem::class.java)
                if (commentItem != null && commentItem.result) {
                    listView!!.adapter = CommentAdapter(commentItem.Comment, mActivity)
                    rb_comment!!.text = "评价(" + commentItem.Comment!!.size + ")"
                }
            } else if (ADD_CART_TAG == requestTag) {
                val item = GsonUtils.jsonToClass(result, AddCartItem::class.java)
                if ("true" == item!!.result) {
                    mActivity.showToast("加入购物车成功")
                } else {
                    mActivity.showToast(item.msg!!)
                }
            } else if (COLLECT_TAG == requestTag) {
                val collectItem = GsonUtils.jsonToClass(result, CollectItem::class.java)
                if (collectItem != null) {
                    isCollect = true
                    iv_collect!!.setImageResource(R.mipmap.collect_confirm)
                    tv_collect!!.text = "已收藏"
                    if (collectItem.result) {
                        mActivity.showToast("收藏成功")
                    } else {
                        mActivity.showToast(collectItem.msg!!)
                    }
                }

            } else if (COLLECT_CANCEL_TAG == requestTag) {
                val collectItem = GsonUtils.jsonToClass(result, CollectItem::class.java)
                if (collectItem != null) {
                    isCollect = false
                    iv_collect!!.setImageResource(R.mipmap.collect_cancel)
                    tv_collect!!.text = "收藏"
                    if (collectItem.result) {
                        mActivity.showToast("取消收藏")
                    } else {
                        mActivity.showToast(collectItem.msg!!)
                    }
                }
            } else if (SHOW_COLLECT == requestTag) {
                val collectItem = GsonUtils.jsonToClass(result, CollectItem::class.java)
                if (collectItem != null) {
                    isCollect = true
                    iv_collect!!.setImageResource(R.mipmap.collect_confirm)
                    tv_collect!!.text = "已收藏"
                }
            }

        }

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_minus -> if (count > 1) {
                count -= 1
                tv_count!!.text = count.toString() + ""
            }
            R.id.iv_add -> {
                count = Integer.parseInt(tv_count!!.text.toString())
                if (count < sell_count) {
                    count += 1
                    tv_count!!.text = count.toString() + ""
                }
            }
        }
    }

    companion object {

        private val DETAIL_TAG = "detail_tag"
        private val GROUP_INFO_TAG = "group_info_tag"
        private val COMMENT_TAG = "comment_tag"
        private val ADD_CART_TAG = "ADD_CART_TAG"
        private val COLLECT_TAG = "COLLECT_TAG"
        private val COLLECT_CANCEL_TAG = "COLLECT_CANCEL_TAG"
        private val SHOW_COLLECT = "show_collect"

        fun newInstance(id: String): DetailFragment {
            val fragment = DetailFragment()
            val bundle = Bundle()
            bundle.putString("id", id)
            fragment.arguments = bundle
            return fragment
        }
    }
}
