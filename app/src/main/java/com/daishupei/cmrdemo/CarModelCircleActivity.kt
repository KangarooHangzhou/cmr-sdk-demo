package com.daishupei.cmrdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import com.tongji.cmr.CarModelFragment
import com.tongji.cmr.manage.CarCircledListener

class CarModelCircleActivity : AppCompatActivity(), CarCircledListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_model_circle)

        val carModelFragment = CarModelFragment.newInstance("", "")
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, carModelFragment)
            .commit()

        findViewById<View>(R.id.btn_submit).setOnClickListener { _ ->
            findViewById<EditText>(R.id.etUrl).let {
                it.text.toString().apply {
                    if (TextUtils.isEmpty(this)) {
                        it.error = "网址不能为空"
                    } else {
                        carModelFragment.setNewData(this)
                    }
                }
            }
        }
    }

    override fun onCarCircled(data: String?) {
        data?.let {
            startActivity(
                Intent(
                    this@CarModelCircleActivity,
                    ShowDrawPartResultActivity::class.java
                ).apply {
                    putExtra("json", data)
                })
        }
    }

    override fun onLoadUrlError() {

    }
}