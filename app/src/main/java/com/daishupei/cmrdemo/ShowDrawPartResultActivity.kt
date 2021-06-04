package com.daishupei.cmrdemo

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.JsonParser

class ShowDrawPartResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_draw_part_result)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        findViewById<TextView>(R.id.tv_result).setText(
            intent.getStringExtra("json")?.toPrettyFormat() ?: ""
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                false
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

fun String.toPrettyFormat(): String {
    val jsonParser = JsonParser()
    val jsonObject: JsonObject = jsonParser.parse(this).getAsJsonObject()
    val gson = GsonBuilder().setPrettyPrinting().create()
    return gson.toJson(jsonObject)
}