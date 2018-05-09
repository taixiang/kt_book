package com.dingshuwang.model

import java.io.Serializable

/**
 * Created by tx on 2017/6/22.
 */

class PointItem : Serializable {
    var result: Boolean = false
    var message: List<Point>? = null

    inner class Point : Serializable {
        var value: Double = 0.toDouble()
        var remark: String? = null
        var add_time: String? = null
    }
}
