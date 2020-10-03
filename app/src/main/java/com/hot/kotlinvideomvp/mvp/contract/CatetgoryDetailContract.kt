package com.hot.kotlinvideomvp.mvp.contract

import com.hot.kotlinvideomvp.base.IBaseView
import com.hot.kotlinvideomvp.mvp.model.bean.HomeBean

/**
 * Created by anderson
 * on 2020/9/29.
 * desc:
 */
interface CatetgoryDetailContract {

    interface View : IBaseView {
        fun setCategoryList(itemList: ArrayList<HomeBean.Issue.Item>)

        fun showError(errorMsg: String)
    }

    interface Presenter {

        fun getCategoryDetailList(id: Long)

        fun loadMoreData()
    }

}