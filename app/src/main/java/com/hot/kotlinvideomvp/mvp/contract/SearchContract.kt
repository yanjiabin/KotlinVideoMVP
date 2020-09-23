package com.hot.kotlinvideomvp.mvp.contract

import com.hot.kotlinvideomvp.base.IBaseView
import com.hot.kotlinvideomvp.base.IPresenter
import com.hot.kotlinvideomvp.mvp.model.bean.HomeBean

/**
 * Created by anderson
 * on 2020/9/23.
 * desc:
 */
interface SearchContract {

    interface View:IBaseView{
        /**
         * 设置热门关键词数据
         */
        fun setHotWordData(string: ArrayList<String>)

        /**
         * 设置搜索关键词返回的结果
         */
        fun setSearchResult(issue: HomeBean.Issue)
        /**
         * 关闭软件盘
         */
        fun closeSoftKeyboard()

        /**
         * 设置空 View
         */
        fun setEmptyView()


        fun showError(errorMsg: String,errorCode:Int)
    }


    interface Presenter:IPresenter<View>{

        /**
         * 获取热门信息
         */
        fun requestHotWordData()

        /**
         *查询搜索
         */
        fun querySearchData(words:String)

        /**
         * 加载更多
         */
        fun loadMoreData()
    }

}