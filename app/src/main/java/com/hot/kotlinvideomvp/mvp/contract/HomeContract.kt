package com.hot.kotlinvideomvp.mvp.contract

import com.hot.kotlinvideomvp.base.IBaseView
import com.hot.kotlinvideomvp.mvp.model.bean.HomeBean

/**
 *  author : anderson
 *  date   : 2020/9/1
 *  desc   :
 */
interface HomeContract {

    interface View : IBaseView{

        /**
         *设置第一次请求的数据
         */
        fun setHomeData(homeBean:HomeBean)

        /**
         *设置加载更多的数据
         */
        fun setMoreData(itemList:ArrayList<HomeBean.Issue.Item>)

        /**
         * 显示错误信息
         */
        fun showError(msg:String,errorCode: Int)

    }

    interface Presenter{
        /**
         *  获取首页精选数据
         */
        fun requestHomeData(num:Int)

        /**
         *  加载更多数据
         */
        fun loadMoreData()
    }

}