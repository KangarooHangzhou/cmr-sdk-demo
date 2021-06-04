package com.daishupei.cmrdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.daishupei.cmrdemo.CarModelCircleActivity
import com.daishupei.cmrdemo.CarModelCircleByWebviewActivity

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        findViewById<View>(R.id.btn1).setOnClickListener {
            startActivity(
                Intent(
                    this,
                    CarModelCircleByWebviewActivity::class.java
                )
            )
        }
        findViewById<View>(R.id.btn2).setOnClickListener {
            startActivity(
                Intent(
                    this,
                    CarModelCircleActivity::class.java
                )
            )
        }
    }
}