package com.hot.kotlinvideomvp.mvp.model

import com.hot.kotlinvideomvp.mvp.model.bean.CategoryBean
import com.hot.kotlinvideomvp.net.RetrofitManager
import com.hot.kotlinvideomvp.rx.SchedulerUtils
import io.reactivex.Observable
import io.reactivex.Scheduler

/**
 * Created by anderson
 * on 2020/9/27.
 * desc:
 */
class CategoryModel {

    fun getCategoryData():Observable<ArrayList<CategoryBean>>{
        return RetrofitManager.service.getCategoryData().compose(SchedulerUtils.ioToMain())
    }


}