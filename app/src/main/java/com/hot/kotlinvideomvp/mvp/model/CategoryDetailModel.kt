package com.hot.kotlinvideomvp.mvp.model

import com.hot.kotlinvideomvp.mvp.model.bean.HomeBean
import com.hot.kotlinvideomvp.net.RetrofitManager
import com.hot.kotlinvideomvp.rx.SchedulerUtils
import io.reactivex.Observable
import retrofit2.Retrofit

/**
 * Created by anderson
 * on 2020/9/29.
 * desc:
 */
class CategoryDetailModel {

    fun getCategoryDetailList(id:Long):Observable<HomeBean.Issue>{
        return RetrofitManager.service.getCategoryDetailList(id).compose(SchedulerUtils.ioToMain())
    }

    fun loadMoreData(url:String):Observable<HomeBean.Issue>{
        return RetrofitManager.service.getIssueData(url).compose(SchedulerUtils.ioToMain())
    }
}