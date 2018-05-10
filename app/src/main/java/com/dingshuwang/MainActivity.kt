package com.dingshuwang

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import com.dingshuwang.base.BaseActivity

import com.dingshuwang.base.BaseFragment
import com.dingshuwang.base.BaseNoTitleActivity
import com.dingshuwang.fragment.MainFragment

class MainActivity : BaseNoTitleActivity() {

    private var mainFragment: MainFragment? = null

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == Constants.GOTOFIRST) {
                Log.i("》》》》  ", " =============== ")
                mainFragment!!.toTab(0)
            }
        }
    }

    override val activityTitle: CharSequence
        get() = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        register()

    }

    override fun fragmentAsView(): BaseFragment? {
        mainFragment = MainFragment.newInstance()
        return mainFragment
    }


    override fun onDestroy() {

        super.onDestroy()
        try {
            BaseActivity.mActivity.unregisterReceiver(receiver)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun register() {
        val filter = IntentFilter(Constants.GOTOFIRST)
        BaseActivity.mActivity.registerReceiver(receiver, filter)
    }

    //    @Override
    //    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    //        super.onActivityResult(requestCode, resultCode, data);
    //
    //        Log.i("","requestCOde ====  "+requestCode);
    //        Log.i("","resultCode ====  "+resultCode);
    //        if(resultCode == 0){
    //            mainFragment.toTab(0);
    //        }
    //    }
}
