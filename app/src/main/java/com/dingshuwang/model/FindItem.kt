package com.dingshuwang.model

import java.io.Serializable

/**
 * Created by tx on 2017/6/11.
 * user_name: "soul臧",
 * university: "",
 * pro_id: 170516150208134,
 * ISBN: null,
 * pro_name: "正版 药理学——第七版/本科学 朱依谆等 人民卫生出版社 97871171",
 * price_sell: 19,
 * image_url: "https://img.ali
 */

class FindItem : Serializable {
    var result: String? = null
    var pros: List<Find>? = null

    inner class Find : Serializable {
        var user_name: String? = null
        var university: String? = null
        var pro_id: String? = null
        var ISBN: String? = null
        var pro_name: String? = null
        var price_sell: String? = null
        var image_url: String? = null
    }
}
