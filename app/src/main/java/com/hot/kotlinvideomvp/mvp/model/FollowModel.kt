package com.hot.kotlinvideomvp.mvp.model

import com.hot.kotlinvideomvp.mvp.model.bean.HomeBean
import com.hot.kotlinvideomvp.net.RetrofitManager
import com.hot.kotlinvideomvp.rx.SchedulerUtils
import io.reactivex.Observable

/**
 * Created by anderson
 * on 2020/9/24.
 * desc:
 */
class FollowModel {


    /**
     * 获取关注信息
     */
    fun  requestFollowList():Observable<HomeBean.Issue>{
        return RetrofitManager.service.getFollowInfo().compose(SchedulerUtils.ioToMain())
    }


    /**
     * 加载更多
     */
    fun loadMoreData(url:String):Observable<HomeBean.Issue>{
        return RetrofitManager.service.getIssueData(url).compose(SchedulerUtils.ioToMain())
    }


}