package com.hot.kotlinvideomvp.mvp.presenter

import com.hot.kotlinvideomvp.base.BasePresenter
import com.hot.kotlinvideomvp.mvp.contract.RankContract
import com.hot.kotlinvideomvp.mvp.model.RankListModel
import com.hot.kotlinvideomvp.net.ExceptionHandle

/**
 * create By：anderson
 * on 2020/10/3
 * desc:
 */
class RankPresenter : BasePresenter<RankContract.View>(), RankContract.Presenter {
    private val rankListModel by lazy { RankListModel() }
    override fun getRankList(url: String) {
        checkViewAttached()
        val disposable = rankListModel.getRankList(url)
            .subscribe({ data ->
                mRootView?.apply {
                    dismissLoading()
                    setRankList(data)
                }
            }, { throwable ->
                mRootView?.apply {
                    showError(ExceptionHandle.handleException(throwable), ExceptionHandle.errorCode)
                }
            })

        disposable?.let { addSubscription(it) }
    }
}