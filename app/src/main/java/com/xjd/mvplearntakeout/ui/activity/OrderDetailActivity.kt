package com.xjd.mvplearntakeout.ui.activity

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.amap.api.maps.AMap
import com.amap.api.maps.AMapUtils
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.MapView
import com.amap.api.maps.model.*
import com.heima.takeout.utils.OrderObservable
import com.xjd.mvplearntakeout.R
import kotlinx.android.synthetic.main.activity_gaode_map.view.*
import kotlinx.android.synthetic.main.activity_order_detail.*
import org.json.JSONObject
import java.util.*

/**
 * Created by Administrator on 2018-11-12.
 */
class OrderDetailActivity : AppCompatActivity(), Observer {

    var points :ArrayList<LatLng> = ArrayList()
    lateinit var riderMarker : Marker
    lateinit var aMap: AMap

    override fun update(o: Observable?, data: Any?) {
//更新UI
        val jsonObj: JSONObject = JSONObject(data as String)
        val pushOrderId = jsonObj.getString("orderId")
        val pushType = jsonObj.getString("type")
        if (orderId.equals(pushOrderId)) {
            type = pushType
        }
        val index = getIndex(type)
        (ll_order_detail_type_point_container.getChildAt(index) as ImageView).setImageResource(R.drawable.order_time_node_disabled)
        (ll_order_detail_type_container.getChildAt(index) as TextView).setTextColor(Color.BLUE)

        when (type) {
            OrderObservable.ORDERTYPE_RECEIVEORDER -> {
                //显示地图
                mMapView.visibility = View.VISIBLE
                //移动地图
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(22.5757890000, 113.9232180000), 16f))
                //标注买卖家 22.5788300000,113.9216030000
                val sellerMarker = aMap.addMarker(MarkerOptions()
                        .position(LatLng(22.5788300000, 113.9216030000))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.order_seller_icon))
                        .title("兰州拉面").snippet("一个渺小的卖家"))
                var imageView = ImageView(this)
                imageView.setImageResource(R.drawable.order_buyer_icon)
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(22.5788300000, 113.9216030000), 16f))
                val buyerMarker = aMap.addMarker(MarkerOptions()
                        .position(LatLng(22.5765760000, 113.9237870000))
                        .icon(BitmapDescriptorFactory.fromView(imageView))
                        .title("黑马深圳校区").snippet("一个霸气的买家"))
            }
            OrderObservable.ORDERTYPE_DISTRIBUTION -> {
                points.add(LatLng(22.5764420000, 113.9208900000))
                //骑士登场
                var imageView = ImageView(this)
                imageView.setImageResource(R.drawable.order_rider_icon)
                riderMarker = aMap.addMarker(MarkerOptions()
                        .position(LatLng(22.5764420000, 113.9208900000))
                        .icon(BitmapDescriptorFactory.fromView(imageView))
                        .title("我是黑马骑士"))
//                        .snippet("我是黑马骑士"))
                riderMarker.showInfoWindow()
            }
            OrderObservable.ORDERTYPE_DISTRIBUTION_RIDER_GIVE_MEAL,
            OrderObservable.ORDERTYPE_DISTRIBUTION_RIDER_TAKE_MEAL -> {
                if (jsonObj.has("lat")) {
                    val lat = jsonObj.getString("lat")
                    val lng = jsonObj.getString("lng")

                    //移动骑士  22.5739110000,113.9180200000
                    //更新骑手位置就是用新位置重新标记骑手
                    riderMarker.hideInfoWindow()
                    riderMarker.position = LatLng(lat.toDouble(), lng.toDouble())
                    aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat.toDouble(), lng.toDouble()), 16f))
                    //测距功能
                    val distance = AMapUtils.calculateLineDistance(LatLng(lat.toDouble(), lng.toDouble()),
                            LatLng(22.5765760000, 113.9237870000))
                    riderMarker.title = "距离您还有" + Math.abs(distance) + "米"
                    riderMarker.showInfoWindow()

                    //绘制轨迹
                    points.add(LatLng(lat.toDouble(), lng.toDouble()))
                    val polyline = aMap.addPolyline(PolylineOptions().color(Color.RED).width(3.0f)
                            .add(points.get(points.size - 1), points.get(points.size - 2)))
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)
        processIntent()
        OrderObservable.instance.addObserver(this)

        mMapView = findViewById<View>(R.id.mMapView) as MapView
        mMapView.onCreate(savedInstanceState)// 此方法必须重写
        aMap = mMapView.map
    }

    lateinit var  mMapView: MapView
    lateinit var orderId: String
    lateinit var type: String
    private fun processIntent() {
        if (intent.hasExtra("orderId")) {
            orderId = intent.getStringExtra("orderId")
            type = intent.getStringExtra("type")
            val index = getIndex(type)
            (ll_order_detail_type_point_container.getChildAt(index) as ImageView).setImageResource(R.drawable.order_time_node_disabled)
            (ll_order_detail_type_container.getChildAt(index) as TextView).setTextColor(Color.BLUE)
        }
    }

    private fun getIndex(type: String): Int {
        var index = -1
        //        String typeInfo = "";
        when (type) {
            OrderObservable.ORDERTYPE_UNPAYMENT -> {
            }
            OrderObservable.ORDERTYPE_SUBMIT ->
                //                typeInfo = "已提交订单";
                index = 0
            OrderObservable.ORDERTYPE_RECEIVEORDER ->
                //                typeInfo = "商家接单";
                index = 1
            OrderObservable.ORDERTYPE_DISTRIBUTION,
            OrderObservable.ORDERTYPE_DISTRIBUTION_RIDER_GIVE_MEAL,
            OrderObservable.ORDERTYPE_DISTRIBUTION_RIDER_TAKE_MEAL,
            OrderObservable.ORDERTYPE_DISTRIBUTION_RIDER_RECEIVE ->
                //                typeInfo = "配送中";
                index = 2
            OrderObservable.ORDERTYPE_SERVED ->
                //                typeInfo = "已送达";
                index = 3
            OrderObservable.ORDERTYPE_CANCELLEDORDER -> {
            }
        }//                typeInfo = "未支付";
        //                typeInfo = "取消的订单";
        return index
    }



    override fun onDestroy() {
        super.onDestroy()
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState)
    }
}