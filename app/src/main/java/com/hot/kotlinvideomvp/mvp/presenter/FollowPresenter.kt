package com.hot.kotlinvideomvp.mvp.presenter

import com.hot.kotlinvideomvp.base.BasePresenter
import com.hot.kotlinvideomvp.mvp.contract.FollowContract
import com.hot.kotlinvideomvp.mvp.model.FollowModel
import com.hot.kotlinvideomvp.net.ExceptionHandle

/**
 * Created by anderson
 * on 2020/9/24.
 * desc:
 */
class FollowPresenter : FollowContract.Presenter, BasePresenter<FollowContract.View>() {


    private val followModel: FollowModel by lazy { FollowModel() }
    private var nextUrl: String? = null
    override fun requestFollowList() {
        checkViewAttached()

        addSubscription(
            disposable = followModel.requestFollowList()
                .subscribe({ data ->
                    mRootView?.apply {
                        dismissLoading()
                        nextUrl = data.nextPageUrl
                        mRootView?.setFollowInfo(data)
                    }
                }, { throwable ->
                    mRootView?.apply {
                        mRootView?.showError(
                            ExceptionHandle.handleException(throwable),
                            ExceptionHandle.errorCode
                        )
                    }
                })
        )
    }

    override fun loadMoreData() {
        nextUrl?.let {
            followModel.loadMoreData(it).subscribe({
                data ->
                mRootView?.apply {
                    dismissLoading()
                    nextUrl = data.nextPageUrl
                    mRootView?.setFollowInfo(data)
                }
            }, { throwable ->
                mRootView?.apply {
                    mRootView?.showError(
                        ExceptionHandle.handleException(throwable),
                        ExceptionHandle.errorCode
                    )
                }
            })
        }
    }
}