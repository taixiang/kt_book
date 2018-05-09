package com.dingshuwang.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

import com.dingshuwang.APIURL
import com.dingshuwang.AddressEditActivity
import com.dingshuwang.Constants
import com.dingshuwang.DataView
import com.dingshuwang.R
import com.dingshuwang.base.BaseActivity
import com.dingshuwang.model.AddressListItem
import com.dingshuwang.util.RequestUtils
import com.dingshuwang.util.UIUtil

/**
 * Created by Administrator on 2016/7/21.
 */
class AddressListAdapter(private val activity: BaseActivity, private val list: List<AddressListItem.AddressItem>?, private val tag: Int) : BaseAdapter() {
    private val inflater: LayoutInflater

    init {
        inflater = LayoutInflater.from(activity)
    }

    override fun getCount(): Int {
        return list?.size ?: 0
    }

    override fun getItem(position: Int): Any {
        return list!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        var holder: ViewHolder? = null
        if (convertView == null) {
            holder = ViewHolder()
            convertView = inflater.inflate(R.layout.adapter_address_list, null)
            convertView!!.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }
        holder.tv_name = convertView.findViewById<View>(R.id.name) as TextView
        holder.tv_phone = convertView.findViewById<View>(R.id.phone) as TextView
        holder.tv_address = convertView.findViewById<View>(R.id.address) as TextView
        holder.iv_edit = convertView.findViewById<View>(R.id.iv_edit) as ImageView
        holder.tv_name.text = list!![position].accept_name
        holder.tv_phone!!.text = list[position].phone
        holder.tv_address!!.text = list[position].area!! + list[position].address!!
        holder.iv_edit!!.setOnClickListener(View.OnClickListener {
            if (UIUtil.isfastdoubleClick()) {
                return@OnClickListener
            }
            AddressEditActivity.actionAddressEdit(activity, list[position].id)
        })

        convertView.setOnClickListener(View.OnClickListener {
            if (tag == 1) {
                return@OnClickListener
            }
            val dataView = object : DataView {
                override fun onGetDataFailured(msg: String, requestTag: String) {

                }

                override fun onGetDataSuccess(result: String, requestTag: String) {
                    //                        Intent intent = new Intent(Constants.DEFAULT_ADDRESS);
                    //                        activity.sendBroadcast(intent);
                    if (result != null) {
                        activity.finish()
                    }
                }
            }
            val url = String.format(APIURL.DEFAULT_ADDRESS_URL, Constants.USER_ID, list[position].id)
            RequestUtils.getDataFromUrl(activity, url, dataView, "", false, false)
        })
        return convertView
    }

    class ViewHolder {
        lateinit var tv_name: TextView
        lateinit var tv_phone: TextView
        lateinit var tv_address: TextView
        lateinit var iv_edit: ImageView
    }

}
