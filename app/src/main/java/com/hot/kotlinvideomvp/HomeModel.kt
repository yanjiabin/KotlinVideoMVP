package com.hot.kotlinvideomvp

import com.hot.kotlinvideomvp.mvp.model.bean.HomeBean
import com.hot.kotlinvideomvp.net.RetrofitManager
import com.hot.kotlinvideomvp.rx.SchedulerUtils
import io.reactivex.Observable

class HomeModel {

    fun requestHomeData(num:Int): Observable<HomeBean> {
        return RetrofitManager.service.getFirstHomeData(num)
            .compose(SchedulerUtils.ioToMain())
    }

    fun loadMoreData(url: String): Observable<HomeBean> {
        return RetrofitManager.service.getMoreHomeData(url)
            .compose(SchedulerUtils.ioToMain())
    }
}