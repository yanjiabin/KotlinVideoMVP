package com.hot.kotlinvideomvp.api

import com.hot.kotlinvideomvp.mvp.model.bean.HomeBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * Created by anderson
 * on 2020/9/3.
 * desc:
 */
interface ApiService {

    /**
     *首页精选
     */
    @GET("v2/feed?")
    fun getFirstHomeData(@Query("num") num: Int): Observable<HomeBean>

    /**
     * 根据 nextPageUrl 请求数据下一页数据
     */
    @GET
    fun getMoreHomeData(@Url url: String): Observable<HomeBean>


    /**
     * 热门搜索词
     */
    @GET("v3/queries/hot")
    fun getHotWord(): Observable<ArrayList<String>>

    @GET("v1/search?&num=10&start=10")
    fun getSearchData(@Query("query") query: String): Observable<HomeBean.Issue>

    /**
     * 获取更多的 Issue
     */
    @GET
    fun getIssueData(@Url url: String): Observable<HomeBean.Issue>

}