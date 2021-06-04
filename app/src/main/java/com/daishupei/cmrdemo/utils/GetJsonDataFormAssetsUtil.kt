package com.daishupei.cmrdemo.utils

import android.content.Context
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.StringBuilder

/*******************************************************************
 *    * * * *   * * * *   *     *       @Author: OCN.Yang
 *    *     *   *         * *   *       @CreateDate: 2020/11/18 3:51 PM.
 *    *     *   *         *   * *       @Email: ocnyang@gmail.com
 *    * * * *   * * * *   *     *.Yang  @GitHub: https://github.com/OCNYang
 *******************************************************************/

class GetJsonDataFormAssetsUtil {

    companion object {
        @JvmStatic
        fun getJson(context: Context, fileName: String): String {
            val stringBuilder = StringBuilder()
            try {
                val bufferedReader =
                    BufferedReader(InputStreamReader(context.assets.open(fileName)))
                var line: String? = bufferedReader.readLine()
                while (line != null) {
                    stringBuilder.append(line)
                    line = bufferedReader.readLine()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return stringBuilder.toString()
        }
    }
}