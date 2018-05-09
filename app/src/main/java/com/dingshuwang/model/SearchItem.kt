package com.dingshuwang.model

import java.io.Serializable

/**
 * Created by tx on 2017/6/13.
 * row_number: 1,
 * id: 1606271755125910,
 * ISBN: "9787040360530销售:522;库存:909",
 * name: "经济管理基础 单大明 9787040360530",
 * price_market: 13,
 * price_sell: 7,
 * img_url: "http://imgthird.iisbn.com/img/1213303a30dtb2pgqxx___281611390.jpg",
 * infor: "",
 * sale_nums: 522,
 * goods_nums: 909
 */

class SearchItem : Serializable {
    var result: String? = null
    var pros: List<Search>? = null

    inner class Search : Serializable {
        var id: String? = null
        var ISBN: String? = null
        var name: String? = null
        var price_market: String? = null
        var price_sell: String? = null
        var img_url: String? = null
        var infor: String? = null
        var sale_nums: String? = null
        var goods_nums: String? = null
    }
}
