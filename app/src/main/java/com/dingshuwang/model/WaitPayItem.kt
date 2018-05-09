package com.dingshuwang.model

import java.io.Serializable

/**
 * Created by Administrator on 2016/7/25.
 * "result": "true",
 * "shops": [
 * {
 * "order_id_parent": 201607162044418600,
 * "order_parent_amount": 39.6,
 * "status": 1,
 * "Order": [
 * {
 * "id": 201607162044418600,
 * "supplier_id": 1,
 * "supplier_name": "丁书网金牌卖家",
 * "add_time": "2016-07-16T20:44:41",
 * "order_amount": 22.6,
 * "express_fee": 8,
 * "express_no": "",
 * "express_str": "-",
 * "status": 1,
 * "status_str": "待付款",
 * "payment_status": 1,
 * "payment_status_str": "等待付款",
 * "express_status": 1,
 * "express_status_str": "等待发货",
 * "Goods": null
 */
class WaitPayItem : Serializable {

    var shops: List<ShopItem>? = null

    inner class ShopItem {
        var order_id_parent: String? = null
        var order_parent_amount: String? = null
        var Order: List<OrderItem>? = null

        inner class OrderItem {
            var id: String? = null
            var supplier_name: String? = null
            var add_time: String? = null
            var order_amount: String? = null
            var status_str: String? = null
            var order_goods: List<Goods>? = null

            inner class Goods {
                var goods_id: String? = null
                var goods_title: String? = null
                var img_url: String? = null
                var quantity: String? = null
                var price_sell: String? = null

            }
        }
    }
}
