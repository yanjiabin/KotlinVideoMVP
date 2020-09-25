package com.hot.kotlinvideomvp.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.lang.RuntimeException

/**
 *  author : anderson
 *  date   : 2020/9/1
 *  desc   :
 */
open class BasePresenter<T : IBaseView> : IPresenter<T> {

    private var compositeDisposable = CompositeDisposable()
    //v 的实现类 例如HomeFragment...
    var mRootView: T? = null
        private set

    private val isViewAttached: Boolean
        get() = mRootView != null

    override fun attachView(mRootView: T) {
        this.mRootView = mRootView
        //保证activity结束时取消所有正在执行的订阅
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.clear()
        }
    }

    override fun detachView() {
        mRootView = null
        //todo  取消订阅
    }

    fun checkViewAttached() {
        if (!isViewAttached) throw MvpViewNotAttachedException()
    }
    fun addSubscription(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    private class MvpViewNotAttachedException internal constructor():RuntimeException("Please call IPresenter.attachView(IBaseView) before" + " requesting data to the IPresenter")

}