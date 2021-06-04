package com.daishupei.cmrdemo.bean

import com.tongji.cmr.bean.SalesVINInfoBean

/*******************************************************************
 *    * * * *   * * * *   *     *       @Author: OCN.Yang
 *    *     *   *         * *   *       @CreateDate: 2020/11/18 4:01 PM.
 *    *     *   *         *   * *       @Email: ocnyang@gmail.com
 *    * * * *   * * * *   *     *.Yang  @GitHub: https://github.com/OCNYang
 *******************************************************************/

data class SalesVinBean(
    var salesVinInfoList: ArrayList<SalesVINInfoBean> = arrayListOf(),
    var vinInfoVOList: Any? = Any()
)