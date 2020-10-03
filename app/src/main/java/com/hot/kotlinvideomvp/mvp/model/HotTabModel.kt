package com.hot.kotlinvideomvp.mvp.model

import com.hot.kotlinvideomvp.mvp.model.bean.TabInfoBean
import com.hot.kotlinvideomvp.net.RetrofitManager
import com.hot.kotlinvideomvp.rx.SchedulerUtils
import io.reactivex.Observable

class HotTabModel {

    fun getTabInfo(): Observable<TabInfoBean> {
        return RetrofitManager.service.getRankList().compose(SchedulerUtils.ioToMain())

    }


}