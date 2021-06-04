package com.daishupei.cmrdemo.bean

/*******************************************************************
 *    * * * *   * * * *   *     *       @Author: OCN.Yang
 *    *     *   *         * *   *       @CreateDate: 2020/11/18 4:03 PM.
 *    *     *   *         *   * *       @Email: ocnyang@gmail.com
 *    * * * *   * * * *   *     *.Yang  @GitHub: https://github.com/OCNYang
 *******************************************************************/

data class BaseBean<T>(
    var data: T? = null,
    var errCode: Int = 0,
    var msg: Any? = Any()
)