package com.hot.kotlinvideomvp.base

/**
 *  author : anderson
 *  date   : 2020/9/1
 *  desc   :
 */
interface IPresenter<in V:IBaseView> {

    fun attachView(mRootView:V)

    fun detachView()

}