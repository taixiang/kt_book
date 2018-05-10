package com.dingshuwang

/**
 * @author tx
 * @date 2018/5/7
 */
object Constants{
    var USER_ID: String? = null

    val MONEY_SYMBOL = "¥"
    val PAGE_SIZE = 10
    val PAGE_AC_SIZE = 20
    val CLIENT_ID = "c1eli113-1leo-4fly-ab30-77c1024b"       // App_key
    var CLIENT_SECRET = "d912b203-z1130-43cc-6bc8-9702cd201"  //App_Secret
    val REDIRECT_URI = "native:oauth2:ab:0072"
    var coupon = 0f
    var token: String? = null
    var qrCodeList: List<String> = ArrayList()


    /***
     * 商家Id
     */
    val STORE_ID = "511802"//"559144";//"505236";// "505236";//"505236"; 501766;556040;503815;509011(阿里),560894

    val STORE_PAY_EVN = "prd"//"prd"、dev

    // appid 微信支付
    // 请同时修改 androidmanifest.xml里面，.PayActivityd里的属性<data
    // android:scheme="wx8fa68b04cf0fce85"/>为新设置的appid
    val APP_ID = "wxe20b563509380693"

    // 商户号
    val MCH_ID = "1330115101"//1293942601

    // API密钥，在商户平台设置
    val API_KEY = "7d2kxfg83l1wro2pn6eg9mjzrmyfhpwx"

    /**
     * 支付宝Key
     */
    val PARTNER = "2088811528631908"
    val SELLER = "2260274497@qq.com"
    val RSA_PRIVATE = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBALSQe/gSbGDVZqoF\n" +
            "byaLwryDo1EzMlHknG8Ols5L2sKwL99GO8mAi1m4gOEA72nGpQ1mit8Y7ICTZGQ7\n" +
            "2gwSJX3i2mhyyF22z3WAL32vIdYJGIyNlc3X2nwkxrX/FpAuXkGq7sCdnFNYGZXx\n" +
            "MJpH6cAB3i/AfdXOudjgrRYEJ0YVAgMBAAECgYEAjtwx+Vg6P3MYQzUBeDHj5VsR\n" +
            "gFFNYtXJn2SflKEXeCoF9lWPQCJgHqCH933R7pKoTC3xegoyNJhpOZTRM/O3jMlk\n" +
            "oGILowh9/A2aUvvFEQx8Pz6Gt6AwdBP3McKxF105m++UzT7utpEYhZEjp3kURq0U\n" +
            "XnzfSPQA3TbHwByGYRECQQDlHxJTc2+288wqpOm3eZkqxsj1pdnDa8KWttDsVsJe\n" +
            "MrEzAdONsEl2WlFV34qZT0QIFdXEC49hzjL8yVE6sVB7AkEAyb8oqvkxSDEcT+Gq\n" +
            "godLD1k69adyLfJ8UA4rDXtnmmjv3h7TkXPmpsNIlnPV2tJVk/G6DnfQv2Gen97+\n" +
            "uk0mrwJBAMNiZs9RPnAGoRGwhjnW8R3AXLjWUMhMSakahzkzlxabJe74XL6UGWFb\n" +
            "DccsFNY9+Sbn7935ebEPFP3qc2GYuacCQQCy2Y+A+scQu9DglCsn6i7FnZIiQt57\n" +
            "EzPXeKf8HMF85rh3Dpb6pGf+wLKyBmC4y6xoKmyJ9PgPIciQ7AygNlMrAkBoVuCC\n" +
            "Tc9TLtvR9SH6KRWPGai0SBPuxgf2lZXGvtTXtstVWrrK/acAEMBXKd1L+G9ytiom\n" +
            "NAnQhHAZGMVPyHKl"
    val RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB"
    val SDK_PAY_FLAG = 1
    val SDK_CHECK_FLAG = 2

    val INTENT_PAYMONEY_FORRESULT_CODE = 1200

    val ENVIROMENT_DIR_CACHE = "/sdcard/custom361app/cache/"
    val COUSUM_FLAG = "com.maimeng.mine.receive"
    val COUSUM_FLAG_WXPAY = "com.maimeng.mine.receive.wxpay"
    val COUSUM_FLAG_OK = "com.maimeng.mine.receive.pay.ok"
    val COUSUM_FLAG_CLEAR = "com.maimeng.mine.receive.pay.clear"
    val LOGIN_FLAG_CLEAR = "com.dingshuw.login.finish"
    val LOGIN_SUCCESS = "com.dingshuw.login.success"
    val DELETE_GOOD = "com.dingshuw.delete"
    val GOOD_NUM = "com.dingshuw.good_num"
    val DELETE_ADDRESS = "com.dingshuw.delete.address"
    val DEFAULT_ADDRESS = "com.dingshuw.default.address"
    val EDIT_ADDRESS = "com.dingshuw.edit.address"
    val PHOTO_URL = "com.dingshuwang.photo"
    val NAME_MODIFY = "com.dingshuwang.modifyname"
    val GOTOFIRST = "com.dingshuwang.gofirst"


    val TOSHOPCART = "toshopcart" //购物车 101

    //

    val UNLOGIN = 100 //未登陆返回首页 100

    val LOGIN = 200 //登陆返回当前页面

    @JvmField
    val CAMMER = 300 //首页扫描isbn返回 resultcode

    @JvmField val CAMMER_PUBLISH = 600 //发布扫描isbn返回 resultcode

    val CODE_PUBLISH = 400 //发布扫描返回 request

    val CODE_HOME = 500//首页扫描返回  requestcode
}