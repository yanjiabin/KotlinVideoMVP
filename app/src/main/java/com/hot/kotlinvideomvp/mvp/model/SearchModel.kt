package com.hot.kotlinvideomvp.mvp.model

import com.hot.kotlinvideomvp.mvp.model.bean.HomeBean
import com.hot.kotlinvideomvp.net.RetrofitManager
import com.hot.kotlinvideomvp.rx.SchedulerUtils
import io.reactivex.Observable

/**
 * Created by anderson
 * on 2020/9/23.
 * desc:
 */
class SearchModel {

    /**
     *  请求热门词语
     */
    fun requestHotWordData(): Observable<ArrayList<String>> {
        return RetrofitManager.service.getHotWord().compose(SchedulerUtils.ioToMain())
    }


    /**
     *  根据搜索关键词查询搜索结果
     */
    fun  getSearchResult(words:String ):Observable<HomeBean.Issue>{
        return RetrofitManager.service.getSearchData(words).compose(SchedulerUtils.ioToMain())
    }


    /**
     * 获取更多的issue
     */
    fun loadMoreData(url:String):Observable<HomeBean.Issue>{
        return RetrofitManager.service.getSearchData(url).compose(SchedulerUtils.ioToMain())
    }


}