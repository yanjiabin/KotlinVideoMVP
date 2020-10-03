package com.hot.kotlinvideomvp.mvp.presenter

import com.hot.kotlinvideomvp.base.BasePresenter
import com.hot.kotlinvideomvp.mvp.contract.HotTabContract
import com.hot.kotlinvideomvp.mvp.model.HotTabModel
import com.hot.kotlinvideomvp.net.ExceptionHandle

class HotTabPresenter :BasePresenter<HotTabContract.View>(),HotTabContract.Presenter {


    private val hotTabModel by lazy { HotTabModel() }
    override fun getTabInfo() {

        checkViewAttached()
        val disposable  = hotTabModel.getTabInfo()
            .subscribe({
                data->
                mRootView?.apply {
                    dismissLoading()
                    setTabInfo(data)
                }
            },{throwable->
                mRootView?.apply {
                    dismissLoading()
                    showError(ExceptionHandle.handleException(throwable),ExceptionHandle.errorCode)
                }
            })
        disposable?.let { addSubscription(it) }
    }


}