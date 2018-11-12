package com.xjd.mvplearntakeout.ui.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v13.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationListener
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.LocationSource
import com.amap.api.maps.MapView
import com.amap.api.maps.model.LatLng
import com.amap.api.services.core.PoiItem
import com.amap.api.services.poisearch.PoiResult
import com.xjd.mvplearntakeout.R
import com.xjd.mvplearntakeout.ui.adapter.MapLocationAdapter
import kotlinx.android.synthetic.main.activity_gaode_map.*
import org.jetbrains.anko.toast
import com.amap.api.services.poisearch.PoiSearch
import com.amap.api.services.core.LatLonPoint


/**
 * Created by Administrator on 2018-11-12.
 */
class MapLocationActivity : AppCompatActivity(), PoiSearch.OnPoiSearchListener {
    override fun onPoiItemSearched(p0: PoiItem?, p1: Int) {

    }

    override fun onPoiSearched(poiResult: PoiResult?, rcode: Int) {
        if (rcode == 1000) {
            if (poiResult != null) {
                val poiItems: ArrayList<PoiItem> = poiResult.pois!!
                mapLocationAdapter.setData(poiItems)
            }

        }
    }

    //声明AMapLocationClient类对象

    lateinit var mLocationClient: AMapLocationClient
    lateinit var aMap: AMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gaode_map)
        val mapView = findViewById<View>(R.id.map) as MapView
        mapView.onCreate(savedInstanceState)// 此方法必须重写
        aMap = mapView.getMap()

        initView()
        initMap()
    }


    private fun initMap() {
        mLocationClient = AMapLocationClient(getApplicationContext());

        mLocationClient.setLocationListener(object : AMapLocationListener {
            override fun onLocationChanged(amapLocation: AMapLocation?) {
                if (amapLocation != null && amapLocation.getErrorCode() == 0) {
                    toast(amapLocation.address)
                    //移动地图到当前位置
                    aMap.moveCamera(CameraUpdateFactory.changeLatLng(LatLng(amapLocation.latitude, amapLocation.longitude)))
                    aMap.moveCamera(CameraUpdateFactory.zoomTo(16f))
                    doSearchQuery(amapLocation)
                    mLocationClient.stopLocation()
                }

            }

        });

        checkPermision()

        //异步获取定位结果


    }

    private fun doSearchQuery(amapLocation: AMapLocation) {
        var query = PoiSearch.Query("", "", amapLocation.city)

        query.setPageSize(30)// 设置每页最多返回多少条poiitem
        query.setPageNum(1)//设置查询页码

        var poiSearch = PoiSearch(this, query)
        poiSearch.setOnPoiSearchListener(this)


        poiSearch.bound = PoiSearch.SearchBound(LatLonPoint(amapLocation.latitude,
                amapLocation.longitude), 10000)
        poiSearch.searchPOIAsyn();
    }


    lateinit var mapLocationAdapter: MapLocationAdapter
    private fun initView() {
        rv_map.layoutManager = LinearLayoutManager(this)

        mapLocationAdapter = MapLocationAdapter(this)
        rv_map.adapter = mapLocationAdapter

    }

    private val WRITE_COARSE_LOCATION_REQUEST_CODE: Int = 10
    private fun checkPermision() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                    WRITE_COARSE_LOCATION_REQUEST_CODE);//自定义的code
        } else {
//启动定位
            mLocationClient.startLocation();
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //用户在对话框中点击允许
//启动定位
            mLocationClient.startLocation();
        } else {
            finish()
            toast("需要有定位权限才能成功定位")
        }
    }
}