package com.xjd.mvplearntakeout.model.net


import com.xjd.mvplearntakeout.model.bean.ResponseInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface TakeoutService {
//    @GET("users/{user}/repos")
//    fun listRepos(@Path("user") user: String): Call<List<Repo>>

    @GET("home")
    fun getHomeInfo(): Call<ResponseInfo>




}