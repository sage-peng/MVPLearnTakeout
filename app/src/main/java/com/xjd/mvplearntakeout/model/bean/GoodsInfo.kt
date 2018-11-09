package com.xjd.mvplearntakeout.model.bean

class GoodsInfo {
    var id: Int = 0//商品id
    var name: String?=null//商品名称
    var icon: String?=null//商品图片
    var form: String?=null//组成
    var monthSaleNum: Int = 0//月销售量
    var isBargainPrice: Boolean = false//特价
    var isNew: Boolean = false//是否是新产品
    var newPrice: String?=null//新价
    var oldPrice: Int = 0//原价
    var sellerId: Int = 0

    constructor() : super() {}

    constructor(sellerId: Int, id: Int, name: String, icon: String, form: String, monthSaleNum: Int, bargainPrice: Boolean,
                isNew: Boolean, newPrice: String, oldPrice: Int) : super() {
        this.id = id
        this.name = name
        this.icon = icon
        this.form = form
        this.monthSaleNum = monthSaleNum
        this.isBargainPrice = bargainPrice
        this.isNew = isNew
        this.newPrice = newPrice
        this.oldPrice = oldPrice
        this.sellerId = sellerId
    }

    override fun toString(): String {
        return ("GoodsInfo [id=" + id + ", name=" + name + ", icon=" + icon + ", form=" + form + ", monthSaleNum="
                + monthSaleNum + ", bargainPrice=" + isBargainPrice + ", isNew=" + isNew + ", newPrice=" + newPrice
                + ", oldPrice=" + oldPrice + "]")
    }

}
