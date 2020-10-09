package com.hot.kotlinvideomvp.mvp.model

import com.hot.kotlinvideomvp.mvp.model.bean.HomeBean
import com.hot.kotlinvideomvp.net.RetrofitManager
import com.hot.kotlinvideomvp.rx.SchedulerUtils
import io.reactivex.Observable

/**
 * create By：anderson
 * on 2020/10/3
 * desc:
 */
class RankListModel {

    /**
     *  获取ranklist
     */
    fun getRankList(url:String): Observable<HomeBean.Issue> {
        return RetrofitManager.service.getIssueData(url).compose(SchedulerUtils.ioToMain())
    }
}