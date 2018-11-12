package com.xjd.mvplearntakeout.utils

import com.xjd.mvplearntakeout.appconfig.Constants
import com.xjd.mvplearntakeout.appconfig.TakeOutApp
import com.xjd.mvplearntakeout.model.bean.CacheSelectedInfo

/**
 * Created by Administrator on 2018-11-11.
 */
object CacheSelectedInfoUtils {

    fun queryCacheSelectedInfoByGoodsId(goodsId: Int): Int {
        var infos = TakeOutApp.instance.cacheSelectedInfolist
        var count = 0
        for (i in 0..infos.size - 1) {
            val (_, _, goodsId1, count1) = infos[i]
            if (goodsId1 == goodsId) {
                count = count1
                break
            }
        }
        return count
    }

    fun queryCacheSelectedInfoByTypeId(typeId: Int): Int {
        var infos = TakeOutApp.instance.cacheSelectedInfolist
        var count = 0
        for (i in 0..infos.size - 1) {
            val (_, typeId1, _, count1) = infos[i]
            if (typeId1 == typeId) {
                count = count + count1
            }
        }
        return count
    }

    fun queryCacheSelectedInfoBySellerId(sellerId: Int): Int {
        var infos = TakeOutApp.instance.cacheSelectedInfolist
        var count = 0
        for (i in 0..infos.size - 1) {
            val (sellerId1, _, _, count1) = infos[i]
            if (sellerId1 == sellerId) {
                count = count + count1
            }
        }
        return count
    }

    fun addCacheSelectedInfo(info: CacheSelectedInfo) {
        var infos = TakeOutApp.instance.cacheSelectedInfolist
        infos.add(info)
    }

    fun clearCacheSelectedInfo(sellerId: Int) {
        var infos = TakeOutApp.instance.cacheSelectedInfolist
        val temp = ArrayList<CacheSelectedInfo>()
        for (i in 0..infos.size - 1) {
            val info = infos[i]
            if (info.sellerId == sellerId) {
//                infos.remove(info)
                temp.add(info)
            }
        }
        infos.removeAll(temp)
    }

    fun deleteCacheSelectedInfo(goodsId: Int) {
        var infos = TakeOutApp.instance.cacheSelectedInfolist
        for (i in 0..infos.size - 1) {
            val info = infos[i]
            if (info.goodsId == goodsId) {
                infos.remove(info)
                break
            }
        }
    }

    fun updateCacheSelectedInfo(goodsId: Int, operation: Int) {
        var infos = TakeOutApp.instance.cacheSelectedInfolist
        for (i in 0..infos.size - 1) {
            var info = infos[i]
            if (info.goodsId == goodsId) {
                when (operation) {
                    Constants.ADD -> info.count = info.count + 1
                    Constants.MINUS -> info.count = info.count - 1
                }

            }
        }
    }
}