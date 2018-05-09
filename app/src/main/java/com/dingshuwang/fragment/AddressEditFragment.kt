package com.dingshuwang.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.AdapterView
import android.widget.EditText
import android.widget.TextView

import com.dingshuwang.APIURL
import com.dingshuwang.Constants
import com.dingshuwang.DataView
import com.dingshuwang.R
import com.dingshuwang.adapter.CityAdapter
import com.dingshuwang.base.BaseFragment
import com.dingshuwang.model.AddressDetailItem
import com.dingshuwang.model.AddressSaveItem
import com.dingshuwang.model.CityItem
import com.dingshuwang.util.GsonUtils
import com.dingshuwang.util.RequestUtils
import com.dingshuwang.util.StringUtils
import com.dingshuwang.util.UIUtil
import com.dingshuwang.view.CustomListView
import com.google.gson.reflect.TypeToken

import java.util.ArrayList

import butterknife.BindView
import butterknife.OnClick

/**
 * Created by tx on 2017/6/19.
 */

class AddressEditFragment : BaseFragment(), DataView {

    private var allList: List<CityItem>? = null
    private val proList = ArrayList<String>()
    private var cityList: List<CityItem.CityName>? = null
    private val cityStr = ArrayList<String>()
    private var pro: String? = null
    private var cityName: String? = null
    private var address_id: String? = null
    private var area_id: String? = null

    @BindView(R.id.name_edit)
    lateinit var name_edit: EditText
    @BindView(R.id.phone_edit)
    lateinit var phone_edit: EditText
    @BindView(R.id.city_choose)
    lateinit var city_choose: EditText
    @BindView(R.id.address_edit)
    lateinit var address_edit: EditText


    private var proDialog: AlertDialog? = null
    private var cityDialog: AlertDialog? = null

    override val fragmentTitle: String
        get() = ""

    private fun doGetCity() {
        val url = APIURL.CITY_URL
        RequestUtils.getDataFromUrl(mActivity, url, this, city_name, false, false)
    }

    private fun doGetDetailAddress(address_id: String?) {
        val url = String.format(APIURL.ADDRESS_INFO_URL, Constants.USER_ID, address_id)
        RequestUtils.getDataFromUrl(mActivity, url, this, detail_address, false, false)
    }

    //新增地址
    private fun doSaveAddress(id: String?, name: String, area_id: String?, address: String, phone: String) {
        val url = String.format(APIURL.ADD_ADDRESS, id, name, area_id, address, phone)
        RequestUtils.getDataFromUrl(mActivity, url, this, SAVE_TAG, false, false)
    }

    //编辑地址
    private fun doEditAddress(id: String?, name: String, area_id: String?, address: String, phone: String, address_id: String?) {
        val url = String.format(APIURL.EDIT_ADDRESS, id, address_id, name, area_id, address, phone)
        RequestUtils.getDataFromUrl(mActivity, url, this, SAVE_TAG, false, false)
    }

    @OnClick(R.id.city_choose)
    internal fun cityChoose() {
        if (UIUtil.isfastdoubleClick()) {
            return
        }

        if (null != proDialog) {
            proDialog = null
        }
        proDialog = AlertDialog.Builder(mActivity).show()
        val view = LayoutInflater.from(mActivity).inflate(R.layout.pop_up_listview, null)
        proDialog!!.setContentView(view)
        proDialog!!.setCanceledOnTouchOutside(true)
        val window = proDialog!!.window
        window!!.setGravity(Gravity.CENTER)
        val listView = view.findViewById(R.id.listview) as CustomListView
        listView.adapter = CityAdapter(proList, mActivity)
        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, convertView, position, id ->
            cityList = allList!![position].City
            Log.i("》》》》", "cityList  " + cityList!!.toString())
            pro = proList[position]
            cityStr.clear()
            for (cityName in cityList!!) {
                cityStr.add(cityName.city_name!!)
            }
            Log.i("》》》》", "cityStr  " + cityStr.toString())

            if (null != cityDialog) {
                cityDialog = null
            }
            cityDialog = AlertDialog.Builder(mActivity).show()
            val view = LayoutInflater.from(mActivity).inflate(R.layout.pop_up_listview, null)
            cityDialog!!.setContentView(view)
            cityDialog!!.setCanceledOnTouchOutside(true)
            val window = cityDialog!!.window
            window!!.setGravity(Gravity.CENTER)
            val listView = view.findViewById(R.id.listview) as CustomListView
            listView.adapter = CityAdapter(cityStr, mActivity)
            listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
                cityName = cityStr[position]
                area_id = cityList!![position].id
                city_choose!!.setText("$pro $cityName")


                if (proDialog != null) {
                    proDialog!!.dismiss()
                }
                if (cityDialog != null) {
                    cityDialog!!.dismiss()
                }
            }
        }
    }

    @OnClick(R.id.confirm)
    internal fun confirm() {
        if (UIUtil.isfastdoubleClick()) {
            return
        }
        val nameStr = name_edit!!.text.toString().trim { it <= ' ' }
        val mobileStr = phone_edit!!.text.toString().trim { it <= ' ' }
        val streetStr = address_edit!!.text.toString().trim { it <= ' ' }
        val cityStr = city_choose!!.text.toString().trim { it <= ' ' }
        if (StringUtils.isEmpty(nameStr)) {
            mActivity.showToast("请输入姓名")
            return
        }
        if (StringUtils.isEmpty(mobileStr)) {
            mActivity.showToast("请输入电话")
            return
        }
        if (StringUtils.isEmpty(cityStr)) {
            mActivity.showToast("请选择省市")
            return
        }
        if (StringUtils.isEmpty(streetStr)) {
            mActivity.showToast("请输入详细地址")
            return
        }
        if ("-1" != address_id) {
            doEditAddress(Constants.USER_ID, nameStr, area_id, streetStr, mobileStr, address_id)
        } else {
            doSaveAddress(Constants.USER_ID, nameStr, area_id, streetStr, mobileStr)
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        doGetCity()
        address_id = arguments!!.getString("address_id")
        if ("-1" != address_id) {
            doGetDetailAddress(address_id)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_address_edit, null)
    }

    override fun onGetDataFailured(msg: String, requestTag: String) {

    }

    override fun onGetDataSuccess(result: String, requestTag: String) {
        if (result != null) {
            if (detail_address == requestTag) {
                val detailItem = GsonUtils.jsonToClass(result, AddressDetailItem::class.java)
                if (null != detailItem) {
                    val item = detailItem.address
                    if (null != item) {
                        name_edit!!.setText(item.accept_name)
                        phone_edit!!.setText(item.phone)
                        city_choose!!.setText(item.area)
                        address_edit!!.setText(item.address)
                    }
                }
            } else if (city_name == requestTag) {
                allList = GsonUtils.jsonToList(result, object : TypeToken<List<CityItem>>() {

                }.type)
                proList.clear()
                for (cityItem in allList!!) {
                    proList.add(cityItem.province_name!!)
                }
            } else if (SAVE_TAG == requestTag) {
                val item = GsonUtils.jsonToClass(result, AddressSaveItem::class.java)
                mActivity.showToast(item!!.info!!)
                if ("y" == item.status) {
                    mActivity.finish()
                }

            }
        }

    }

    companion object {


        /**
         * json城市数据
         */
        val city_name = "get_city_name"

        val detail_address = "detail_address"

        /**
         * 保存地址
         */
        val SAVE_TAG = "address_save"

        fun newInstance(addres_id: String): AddressEditFragment {
            val fragment = AddressEditFragment()
            val bundle = Bundle()
            bundle.putString("address_id", addres_id)
            fragment.arguments = bundle
            return fragment
        }
    }
}
