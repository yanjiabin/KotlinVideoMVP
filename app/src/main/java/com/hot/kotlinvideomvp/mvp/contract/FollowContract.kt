package com.hot.kotlinvideomvp.mvp.contract

import com.hot.kotlinvideomvp.base.IBaseView
import com.hot.kotlinvideomvp.base.IPresenter
import com.hot.kotlinvideomvp.mvp.model.bean.HomeBean

/**
 * Created by anderson
 * on 2020/9/24.
 * desc:
 */
interface FollowContract {

    interface View : IBaseView {

        /**
         *设置关注信息
         */
        fun setFollowInfo(issue: HomeBean.Issue)

        /**
         * 显示错误信息
         */
        fun showError(errorMsg: String, errorCode: Int)
    }

    interface Presenter : IPresenter<View> {
        /**
         *  请求关注数据
         */
        fun requestFollowList()

        /**
         * 加载更多数据
         */
        fun loadMoreData()
    }

}