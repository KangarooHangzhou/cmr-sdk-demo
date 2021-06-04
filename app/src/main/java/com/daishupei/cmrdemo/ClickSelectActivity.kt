package com.daishupei.cmrdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.google.gson.Gson
import com.daishupei.cmrdemo.bean.SalesVinBean
import com.daishupei.cmrdemo.utils.GetJsonDataFormAssetsUtil
import com.tongji.cmr.SelectCarModelsActivity
import com.tongji.cmr.bean.*
import org.json.JSONObject

const val REQUEST_CODE = 0x0000

class ClickSelectActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val jsonObject = JSONObject(
            GetJsonDataFormAssetsUtil.getJson(this, "sales_vin_info_data.json")
        ).getJSONObject("data")

        val dataBean = Gson().fromJson(jsonObject.toString(), SalesVinBean::class.java)

        findViewById<View>(R.id.btn_action).setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelableArrayList("data", dataBean?.salesVinInfoList)
            val intent = Intent(this, SelectCarModelsActivity::class.java)
            intent.putExtras(bundle)
            startActivityForResult(intent, REQUEST_CODE)
        }
        findViewById<View>(R.id.btn_ming).apply {
            visibility = View.INVISIBLE
            setOnClickListener {
                startActivity(Intent(this@ClickSelectActivity, CarModelCircleActivity::class.java))
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (REQUEST_CODE == requestCode && SELECT_CAR_RESULT_CODE == resultCode) {

            val heterogeneityBean =
                data?.extras?.get(SELECT_CAR_RESULT_BUNDLE_KEY_OPTIONS) as HeterogeneityBean

            findViewById<TextView>(R.id.tv_car_info).apply {
                text = heterogeneityBean.salesName
                append("\n\n")
                append(heterogeneityBean.getPriorityNameUnion().keys.joinToString("\n") { key ->
                    "${key.getCarOptionChineseName()}：${heterogeneityBean.get(key)}"
                })
            }
            findViewById<View>(R.id.btn_ming).visibility = View.VISIBLE
        }
    }
}