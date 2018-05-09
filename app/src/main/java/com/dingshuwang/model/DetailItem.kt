package com.dingshuwang.model

import java.io.Serializable

/**
 * Created by tx on 2017/6/15.
 * message: "成功",
 * result: "true",
 * id: 534627062959,
 * ISBN: "9787303115112",
 * name: "二手正版 经济管理应用型系列教材:消费者行为学 高孟立 978730",
 * price_market: 11.3,
 * price_sell: 11.3,
 * img_url: "http://images.iisbn.com/images_id_1_160629/21f52815fa7fb7cfa5651d98a2fb4aa8.jpg",
 * infor: "经济管理应用型系列教材:消费者行为学,9787303115112",
 * sale_nums: 4,
 * goods_nums: 307,
 * stuff_status: "二手品-好",
 * description: "
 *
 *<fon></fon>
 */

class DetailItem : Serializable {
    var message: String? = null
    var result: Boolean = false
    var id: String? = null
    var ISBN: String? = null
    var name: String? = null
    var price_market: String? = null
    var price_sell: String? = null
    var img_url: String? = null
    var infor: String? = null
    var sale_nums: String? = null
    var goods_nums: String? = null
    var stuff_status: String? = null
    var description: String? = null
}
