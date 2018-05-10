package com.dingshuwang.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckedTextView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView

import com.alipay.sdk.app.PayTask
import com.dingshuwang.APIURL
import com.dingshuwang.Constants
import com.dingshuwang.DataView
import com.dingshuwang.R
import com.dingshuwang.alipayutil.Result
import com.dingshuwang.alipayutil.SignUtils
import com.dingshuwang.base.BaseFragment
import com.dingshuwang.model.PayItem
import com.dingshuwang.util.GsonUtils
import com.dingshuwang.util.RequestUtils
import com.dingshuwang.util.UIUtil

import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Random

import butterknife.OnClick

import com.dingshuwang.Constants.SDK_PAY_FLAG

/**
 * Created by tx on 2017/7/5.
 */

class PayFragment : BaseFragment(), DataView {

    private val notify_url = "http://www.iisbn.com/direct_pay/notify_url.aspx"
    private val return_url = "http://www.iisbn.com/direct_pay/return_url.aspx"

    @BindView(R.id.ctv_alipay)
    lateinit var ctv_alipay: CheckedTextView

    private var order_id: String? = null
    private var money: String? = null
    private var subject: String? = null
    private var body: String? = null

    @BindView(R.id.tv_order_no)
    lateinit var tv_order_no: TextView

    @BindView(R.id.tv_real_amount)
    lateinit var tv_real_amount: TextView
    @BindView(R.id.tv_express_fee)
    lateinit var tv_express_fee: TextView
    @BindView(R.id.tv_point)
    lateinit var tv_point: TextView
    @BindView(R.id.tv_groups_favourable)
    lateinit var tv_groups_favourable: TextView

    @BindView(R.id.tv_pay)
    lateinit var tv_pay: TextView

    @BindView(R.id.tv_address)
    lateinit var tv_address: TextView
    @BindView(R.id.tv_name)
    lateinit var tv_name: TextView
    @BindView(R.id.tv_phone)
    lateinit var tv_phone: TextView
    @BindView(R.id.tv_order)
    lateinit var tv_order: TextView
    @BindView(R.id.tv_way)
    lateinit var tv_way: TextView

    @BindView(R.id.order_status)
    lateinit var order_status: TextView

    @BindView(R.id.btn_ok)
    lateinit var btn_ok: Button
    @BindView(R.id.pay_contain)
    lateinit var pay_contain: LinearLayout

    override val fragmentTitle: String
        get() = ""

    /**
     * get the out_trade_no for an order. 获取外部订单号
     */
    val outTradeNo: String
        get() {
            val format = SimpleDateFormat("MMddHHmmss", Locale.getDefault())
            val date = Date()
            var key = format.format(date)

            val r = Random()
            key = key + r.nextInt()
            key = key.substring(0, 15)
            return key
        }

    /**
     * get the sign type we use. 获取签名方式
     */
    val signType: String
        get() = "sign_type=\"RSA\""

    private val mHandler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                Constants.SDK_PAY_FLAG -> {
                    val resultObj = Result(msg.obj as String)
                    val resultStatus = resultObj.resultStatus
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        UIUtil.showToasts(mActivity, "支付成功")
                        //                        loadNext(OrderFeedbackAct.class, new String[]{"money", cousumTotalMoney + ""}, new String[]{"earn", earn}, new String[]{"businessId", bussnessId});
                        //                        initView();
                        pay_success()
                        mActivity.finish()
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”
                        // 代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            UIUtil.showToasts(mActivity, "支付结果确认中")
                        } else {
                            UIUtil.showToasts(mActivity, "支付失败")
                        }
                    }
                }
                Constants.SDK_CHECK_FLAG -> {
                    UIUtil.showToasts(mActivity, "检查结果为：" + msg.obj)
                }
            }
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_pay, null)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        order_id = arguments!!.get("order_id") as String
        subject = "丁书网商贸[订单流水号：$order_id]"
        body = "丁书网网上书店[订单流水号：$order_id]"

        ctv_alipay!!.isChecked = true
        ctv_alipay!!.setCheckMarkDrawable(R.mipmap.ic_pay_checked)
        doGetDetail(order_id)
    }

    @OnClick(R.id.btn_ok)
    internal fun confirm() {
        if (UIUtil.isfastdoubleClick()) {
            return
        }
        if (order_id != null) {
            pay(subject, body, money, order_id!!, notify_url, return_url)
        }

    }

    private fun doGetDetail(order_id: String?) {
        val url = String.format(APIURL.ORDER_DETAIL, Constants.USER_ID, order_id)
        RequestUtils.getDataFromUrl(mActivity, url, this, order_detail, false, false)
    }

    private fun pay_success() {
        val url = String.format(APIURL.Pay_Success, Constants.USER_ID, order_id, "9000")
        RequestUtils.getDataFromUrl(mActivity, url, this, order_success, false, false)

    }


    override fun onGetDataFailured(msg: String, requestTag: String) {

    }

    override fun onGetDataSuccess(result: String, requestTag: String) {
        if (null != result) {
            if (order_detail == requestTag) {
                val payItem = GsonUtils.jsonToClass(result, PayItem::class.java)
                if (payItem != null && payItem.order != null) {

                    if ("待付款" == payItem.order!!.order_status) {
                        btn_ok!!.visibility = View.VISIBLE
                        pay_contain!!.visibility = View.VISIBLE
                    }

                    money = payItem.order!!.order_amount
                    Log.i("》》》》》", "------money" + money!!)
                    tv_order_no!!.text = payItem.order!!.order_no
                    tv_real_amount!!.text = payItem.order!!.real_amount
                    tv_express_fee!!.text = payItem.order!!.express_fee

                    order_status!!.text = payItem.order!!.order_status

                    tv_address!!.text = payItem.order!!.area!! + payItem.order!!.address!!
                    tv_name!!.text = payItem.order!!.accept_name
                    tv_phone!!.text = payItem.order!!.telphone
                    tv_order!!.text = payItem.order!!.order_no
                    tv_way!!.text = payItem.order!!.express_title

                    tv_point!!.text = "-" + payItem.order!!.point!!
                    tv_groups_favourable!!.text = "-" + payItem.order!!.groups_favourable!!
                    tv_pay!!.text = payItem.order!!.order_amount
                }
            }
        }
    }

    /*** 支付宝  */
    /**
     * call alipay sdk pay. 调用SDK支付
     */
    fun pay(
            subject: String?, body: String?, price: String?, outNo: String, notify_url: String, return_url: String
    ) {
        val orderInfo = getOrderInfo(subject, body, price, outNo, notify_url, return_url)
        var sign = sign(orderInfo)
        try {
            // 仅需对sign 做URL编码
            sign = URLEncoder.encode(sign, "UTF-8")
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }

        val payInfo = "$orderInfo&sign=\"$sign\"&$signType"

        val payRunnable = Runnable {
            // 构造PayTask 对象
            val alipay = PayTask(mActivity)
            // 调用支付接口
            val result = alipay.pay(payInfo)
            val msg = Message()
            msg.what = Constants.SDK_PAY_FLAG
            msg.obj = result
            mHandler.sendMessage(msg)
        }

        val payThread = Thread(payRunnable)
        payThread.start()
    }

    /**
     * create the order info. 创建订单信息
     */
    fun getOrderInfo(
            subject: String?, body: String?, price: String?, outNo: String, notify_url: String, return_url: String
    ): String {
        // 合作者身份ID
        var orderInfo = "partner=" + "\"" + Constants.PARTNER + "\""

        // 卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + Constants.SELLER + "\""

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=\"$outNo\""

        // 商品名称
        orderInfo += "&subject=\"$subject\""

        // 商品详情
        orderInfo += "&body=\"$body\""

        // 商品金额
        orderInfo += "&total_fee=\"$price\""// price

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=\"$notify_url\""

        // 接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\""

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\""

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\""

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\""

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"$return_url\""

        // 调用银行卡支付，需配置此参数，参与签名， 固定值
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    fun sign(content: String): String? {
        return SignUtils.sign(content, Constants.RSA_PRIVATE)
        //        return "wxxp1eaoa60lfppfbtzqbu9o2hjhnmlw";
    }

    companion object {

        private val order_detail = "order_detail"
        private val order_success = "order_success"


        fun newInstance(order_id: String): PayFragment {
            val fragment = PayFragment()
            val bundle = Bundle()
            bundle.putString("order_id", order_id)
            fragment.arguments = bundle
            return fragment
        }
    }


}
