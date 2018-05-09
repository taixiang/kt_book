package com.dingshuwang.model

import java.io.Serializable

/**
 * Created by tx on 2017/6/15.
 * id: 1100877128,
 * name: "爱他，就去追 (韩)徐亨周 ,李小华 9787221067029",
 * ISBN: "9787221067029",
 * price_market: 19,
 * price_sell: 12,
 * img_url: "https://img.alicdn.com/bao/uploaded/i1/T1bPdnXpRhXXXXXXXX_!!0-item_pic.jpg",
 * sale_nums: "5",
 * goods_nums: "21"
 */

class NovelItem : Serializable {
    var result: Boolean = false
    var pros: List<Novel>? = null

    inner class Novel : Serializable {
        var id: String? = null
        var name: String? = null
        var ISBN: String? = null
        var price_market: String? = null
        var price_sell: String? = null
        var img_url: String? = null
        var sale_nums: String? = null
        var goods_nums: String? = null
    }
}
