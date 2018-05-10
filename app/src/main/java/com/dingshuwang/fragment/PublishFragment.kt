package com.dingshuwang.fragment

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.PopupWindow
import android.widget.TextView

import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.amap.api.maps2d.AMap
import com.amap.api.maps2d.CameraUpdateFactory
import com.amap.api.maps2d.LocationSource
import com.amap.api.maps2d.MapView
import com.amap.api.maps2d.model.LatLng
import com.amap.api.maps2d.model.MyLocationStyle
import com.dingshuwang.APIURL
import com.dingshuwang.Constants
import com.dingshuwang.DataView
import com.dingshuwang.LoginActivity
import com.dingshuwang.R
import com.dingshuwang.adapter.StatusAdapter
import com.dingshuwang.base.BaseFragment
import com.dingshuwang.base.MMApplication
import com.dingshuwang.model.ReleaseItem
import com.dingshuwang.util.BitmapUtils
import com.dingshuwang.util.GsonUtils
import com.dingshuwang.util.OkHttpUtils
import com.dingshuwang.util.PhotoUtils
import com.dingshuwang.util.StringUtils
import com.dingshuwang.util.UIUtil
import com.xys.libzxing.zxing.activity.CaptureActivity

import java.io.File

import butterknife.BindView
import butterknife.OnClick

/**
 * Created by tx on 2017/6/6.
 */

class PublishFragment : BaseFragment(), DataView, AMapLocationListener, LocationSource {

    @BindView(R.id.et_isbn)
    lateinit var et_isbn: EditText
    @BindView(R.id.et_name)
    lateinit var et_name: EditText
    @BindView(R.id.et_author)
    lateinit var et_author: EditText
    @BindView(R.id.et_publish)
    lateinit var et_publish: EditText
    @BindView(R.id.et_original)
    lateinit var et_original: EditText
    @BindView(R.id.et_price)
    lateinit var et_price: EditText
    @BindView(R.id.et_count)
    lateinit var et_count: EditText
    @BindView(R.id.et_status)
    lateinit var et_status: EditText
    @BindView(R.id.et_remark)
    lateinit var et_remark: EditText

    @BindView(R.id.et_linkname)
    lateinit var et_linkname: EditText
    @BindView(R.id.et_phone)
    lateinit var et_phone: EditText
    @BindView(R.id.et_school)
    lateinit var et_school: EditText

    @BindView(R.id.iv_zxing)
    lateinit var iv_zxing: ImageView
    @BindView(R.id.iv_photo)
    lateinit var iv_photo: ImageView
    @BindView(R.id.container)
    lateinit var container: LinearLayout
    @BindView(R.id.tv_add_photo)
    lateinit var tv_add_photo: TextView

    @BindView(R.id.mapView)
    lateinit var mapView: MapView

    private val type = "0"
    private val strs = arrayOf("全新品", " 二手品—非常好", " 二手品—好", " 二手品—可接受", " 二手品—笔记较多")

    private var imagePath: String? = null
    private var aMap: AMap? = null
    private var location = ""

    private var mListener: LocationSource.OnLocationChangedListener? = null
    private var mlocationClient: AMapLocationClient? = null
    private var mLocationOption: AMapLocationClientOption? = null

    internal var popupWindow: PopupWindow? = null

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == Constants.PHOTO_URL) {
                imagePath = intent.getStringExtra("photo_url")
                val bitmap = BitmapUtils.scaleBitmap(imagePath!!)
                if (null != bitmap) {
                    iv_photo!!.setImageBitmap(bitmap)
                    iv_photo!!.visibility = View.VISIBLE
                    tv_add_photo!!.visibility = View.GONE

                    try {
                        //                        output.close();
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }

            }
        }
    }

    override val fragmentTitle: String
        get() = ""


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mActivity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN or WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        mapView!!.onCreate(savedInstanceState)
        register()
        imagePath = ""
        mListener = null
        mlocationClient = null
        mLocationOption = null
        location = ""

        Log.i("》》》》   ", " map ===  ")
        aMap = mapView!!.getMap()
        val myLocationStyle: MyLocationStyle
        myLocationStyle = MyLocationStyle()//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.strokeColor(Color.BLACK)// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(180, 245, 245, 245))// 设置圆形的填充颜色
        // myLocationStyle.anchor(int,int)//设置小蓝点的锚点
        myLocationStyle.strokeWidth(1.0f)// 设置圆形的边框粗细
        aMap!!.setMyLocationStyle(myLocationStyle)//设置定位蓝点的Style
        aMap!!.setLocationSource(this)// 设置定位监听
        aMap!!.getUiSettings().setMyLocationButtonEnabled(true)//设置默认定位按钮是否显示，非必需设置。
        aMap!!.setMyLocationEnabled(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_publish, null)
    }

    override fun onResume() {
        super.onResume()
        mapView!!.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView!!.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView!!.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (receiver != null) {
            mActivity.unregisterReceiver(receiver)
        }
        mapView!!.onDestroy()
        if (null != mlocationClient) {
            mlocationClient!!.onDestroy()
        }
    }

    private fun doUploadModifyPhoto(imagePath: String?, url: String) {
        mActivity.showProgressDialog()
        OkHttpUtils.TheadUtils.ModifyPhoto(mActivity, url, imagePath!!, false, this, REQUEST_MODIFY_PHOTO_TAG)
    }

    private fun doGetPublish(user_id: String?, isbn: String, name: String, author: String, publish: String, origial: String, price: String, count: String, status: String, remark: String, linkName: String, linkPhone: String, university: String, location: String) {
        val url = String.format(APIURL.publish, user_id, isbn, name, author, publish, origial, price, count, status, remark, linkName, linkPhone, university, location)
        doUploadModifyPhoto(imagePath, url)
    }

    @OnClick(R.id.photo_container)
    internal fun showPhoto() {
        PhotoUtils.showSelectDialog(mActivity)
    }

    @OnClick(R.id.iv_zxing)
    internal fun zxing() {
        val openCameraIntent = Intent(mActivity, CaptureActivity::class.java)
        openCameraIntent.putExtra("tag_code", 2)
        startActivityForResult(openCameraIntent, Constants.CODE_PUBLISH)
    }

    @OnClick(R.id.et_status)
    internal fun choseStatus() {
        showPop()
    }

    private fun showPop() {
        if (null != popupWindow) {
            popupWindow!!.dismiss()
        }
        val view = LayoutInflater.from(mActivity).inflate(R.layout.popup_listview, null)
        popupWindow = PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        //        popupWindow.setContentView(view);
        val listView = view.findViewById<View>(R.id.listview) as ListView
        listView.adapter = StatusAdapter(strs, mActivity)
        popupWindow!!.setBackgroundDrawable(BitmapDrawable())
        popupWindow!!.isOutsideTouchable = true
        popupWindow!!.showAtLocation(container, Gravity.CENTER, 0, 0)
        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            et_status!!.setText(strs[position])
            if (null != popupWindow) {
                popupWindow!!.dismiss()
            }
        }
    }

    @OnClick(R.id.btn_ok)
    internal fun btn_ok() {
        if (UIUtil.isfastdoubleClick()) {
            return
        }
        if (!MMApplication.mIsLogin) {
            mActivity.showToast("请先登录")
            LoginActivity.actionLogin(mActivity, Constants.LOGIN)
            return
        }
        val isbn = et_isbn!!.text.toString().trim { it <= ' ' }
        val name = et_name!!.text.toString().trim { it <= ' ' }
        val author = et_author!!.text.toString().trim { it <= ' ' }
        val publish = et_publish!!.text.toString().trim { it <= ' ' }
        val original = et_original!!.text.toString().trim { it <= ' ' }
        val price = et_price!!.text.toString().trim { it <= ' ' }
        val count = et_count!!.text.toString().trim { it <= ' ' }
        val status = et_status!!.text.toString().trim { it <= ' ' }
        val remark = et_remark!!.text.toString().trim { it <= ' ' }

        val linkName = et_linkname!!.text.toString().trim { it <= ' ' }
        val phone = et_phone!!.text.toString().trim { it <= ' ' }
        val university = et_school!!.text.toString().trim { it <= ' ' }
        if (StringUtils.isEmpty(isbn) || isbn.length != 13) {
            mActivity.showToast("请输入正确的isbn号码")
            return
        }
        if (StringUtils.isEmpty(name)) {
            mActivity.showToast("请输入书名")
            return
        }
        //        if (StringUtils.isEmpty(author)) {
        //            mActivity.showToast("请输入作者");
        //            return;
        //        }
        //        if (StringUtils.isEmpty(publish)) {
        //            mActivity.showToast("请输入出版社");
        //            return;
        //        }
        //        if (StringUtils.isEmpty(original)) {
        //            mActivity.showToast("请输入原价");
        //            return;
        //        }
        //        if (StringUtils.isEmpty(price)) {
        //            mActivity.showToast("请输入出售价");
        //            return;
        //        }
        //        if (StringUtils.isEmpty(count)) {
        //            mActivity.showToast("请输入数量");
        //            return;
        //        }
        //        if (StringUtils.isEmpty(status)) {
        //            mActivity.showToast("请选择新旧程度");
        //            return;
        //        }
        //        if (StringUtils.isEmpty(remark)) {
        //            mActivity.showToast("请输入备注");
        //            return;
        //        }
        if (StringUtils.isEmpty(imagePath) || imagePath!!.length == 0) {
            mActivity.showToast("请添加封面")
            return
        }
        val file = File(imagePath!!)


        if (file.exists()) {
            val filrName = imagePath!!.substring(imagePath!!.lastIndexOf("/") + 1)

            doGetPublish(Constants.USER_ID, isbn, name, author, publish, original, price, count, status, remark, linkName, phone, university, location)
        }
    }

    private fun register() {
        val filter = IntentFilter(Constants.PHOTO_URL)
        mActivity.registerReceiver(receiver, filter)
    }

    override fun onGetDataFailured(msg: String, requestTag: String) {
        mActivity.dismissProgressDialog()
        mActivity.showToast("发布失败，稍后再试")
    }

    override fun onGetDataSuccess(result: String, requestTag: String) {

        if (requestTag == REQUEST_MODIFY_PHOTO_TAG) {
            //            iv_photo.setVisibility(View.VISIBLE);
            mActivity.dismissProgressDialog()
            Log.i("》》》》》》   ", "result  $result")
            if (result != null) {
                val item = GsonUtils.jsonToClass(result, ReleaseItem::class.java)
                if (item != null) {
                    if (item.result == "true") {
                        mActivity.showToast("发布成功")

                        et_isbn!!.setText("")
                        et_name!!.setText("")
                        et_author!!.setText("")
                        et_publish!!.setText("")
                        et_original!!.setText("")
                        et_price!!.setText("")
                        et_count!!.setText("")
                        et_status!!.setText("")
                        et_remark!!.setText("")
                        et_linkname!!.setText("")
                        et_phone!!.setText("")
                        et_school!!.setText("")


                    }
                }
            }
            //            Intent intent= new Intent(Constants.GOTOFIRST);
            //            mActivity.sendBroadcast(intent);
            val intent = Intent(Constants.GOTOFIRST)
            mActivity.sendBroadcast(intent)
        } else if (requestTag == publish_photo) {

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.i("》》》》》  ", " requestCode" + requestCode + "resultCode   " + resultCode)

        if (Constants.CAMMER_PUBLISH == resultCode) {
            val bundle = data!!.extras
            val scanResult = bundle!!.getString("result")
            et_isbn!!.setText(scanResult)
        }

    }

    /**
     * 定位成功后回调函数
     */
    override fun onLocationChanged(amapLocation: AMapLocation?) {
        Log.i("》》》》  ", "  locationchanged ")
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null && amapLocation!!.getErrorCode() === 0) {
                mListener!!.onLocationChanged(amapLocation)// 显示系统小蓝点
                aMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(amapLocation!!.getLatitude(), amapLocation!!.getLongitude()), 19F))
                location = amapLocation!!.getAddress()
                Log.i("  》》》》》  ", "address === $location")
            } else {
                val errText = "定位失败," + amapLocation!!.getErrorCode() + ": " + amapLocation!!.getErrorInfo()
                Log.e("AmapErr", errText)
            }
        }
    }

    /**
     * 激活定位
     */
    override fun activate(onLocationChangedListener: LocationSource.OnLocationChangedListener) {
        Log.i("》》》》  ", "  acyivated ")
        mListener = onLocationChangedListener
        if (mlocationClient == null) {
            mlocationClient = AMapLocationClient(mActivity)
            mLocationOption = AMapLocationClientOption()
            //设置定位监听
            mlocationClient!!.setLocationListener(this)
            mLocationOption!!.setOnceLocation(true)
            //设置为高精度定位模式
            mLocationOption!!.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy)
            mLocationOption!!.setLocationCacheEnable(true)
            //设置定位参数
            mlocationClient!!.setLocationOption(mLocationOption)
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient!!.startLocation()
        }
    }

    override fun deactivate() {
        mListener = null
        if (mlocationClient != null) {
            mlocationClient!!.stopLocation()
            mlocationClient!!.onDestroy()
        }
        mlocationClient = null

    }

    companion object {

        private val REQUEST_MODIFY_PHOTO_TAG = "photo"

        private val publish_photo = "publish"
    }
}
