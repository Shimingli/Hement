package com.shiming.hement.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.shiming.hement.R
import com.shiming.hement.ui.base.BaseActivity
import com.shiming.hement.ui.db.DBNetWorkDemoActivity
import com.shiming.hement.ui.fragmentdemo.FragmentDemoActivity
import com.shiming.hement.ui.iamgeloader.ImageLoaderActivity
import com.shiming.hement.ui.network.NetWorkActivity
import com.shiming.hement.ui.permission.RxPermissionsActivity
import com.shiming.hement.ui.rxbusdemo.RxEventBusActivity
import com.shiming.hement.ui.sp.SPreferencesActivity



public class MainActivity : BaseActivity() {
    /**
     * cannot be provided without an @Inject constructor or from an @Provides-annotated method.
     * 在kotlin中使用还有问题
     */
//    @field:Named
//     var mDataManager: DataManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        var msg = mDataManager!!.preferencesHelper!!.getString(PreferenceFileNames.TEXT, PreferenceKeys.TEXT)
//        Timber.tag(className).i("mPreferencesHelper=%s", msg)
        findViewById<View>(R.id.btn_net_work).setOnClickListener {
            startActivity(Intent(this, NetWorkActivity::class.java))
        }
        findViewById<View>(R.id.btn_sp_demo).setOnClickListener {
            startActivity(Intent(this, SPreferencesActivity::class.java))
        }
        findViewById<View>(R.id.btn_db_demo).setOnClickListener {
            startActivity(Intent(this, DBNetWorkDemoActivity::class.java))
        }
        findViewById<View>(R.id.btn_rx_bus_demo).setOnClickListener {
            startActivity(Intent(this, RxEventBusActivity::class.java))
        }
        findViewById<View>(R.id.btn_rx_permission).setOnClickListener {
            startActivity(Intent(this, RxPermissionsActivity::class.java))
        }
        findViewById<View>(R.id.btn_imageloader).setOnClickListener {
            startActivity(Intent(this, ImageLoaderActivity::class.java))
        }

        findViewById<View>(R.id.btn_fragment).setOnClickListener {
            startActivity(Intent(this, FragmentDemoActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_still)
        }
    }


}
