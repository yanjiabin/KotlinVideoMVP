package com.hot.kotlinvideomvp.mvp.presenter

import com.hot.kotlinvideomvp.base.BasePresenter
import com.hot.kotlinvideomvp.mvp.contract.SearchContract
import com.hot.kotlinvideomvp.mvp.model.SearchModel
import com.hot.kotlinvideomvp.net.ExceptionHandle

/**
 * Created by anderson
 * on 2020/9/23.
 * desc:
 */
class SearchPresenter : BasePresenter<SearchContract.View>(), SearchContract.Presenter {


    private val searchModel by lazy { SearchModel() }
    private var nextPageUrl: String? = null

    override fun requestHotWordData() {
        checkViewAttached()
        mRootView?.apply {
            closeSoftKeyboard()
            showLoading()
        }

        addSubscription(
            disposable = searchModel.requestHotWordData()
                .subscribe({ dataList ->
                    mRootView?.apply {
                        setHotWordData(dataList)
                    }
                }, { throwable ->

                    mRootView?.apply {
                        showError(
                            ExceptionHandle.handleException(throwable),
                            ExceptionHandle.errorCode
                        )
                    }

                })
        )
    }

    override fun querySearchData(words: String) {
        checkViewAttached()
        mRootView?.apply {
            closeSoftKeyboard()
            showLoading()
        }
        addSubscription(
            disposable = searchModel.getSearchResult(words)
                .subscribe({ issue ->
                    mRootView?.apply {
                        dismissLoading()
                        if (issue.count > 0 && issue.itemList.size > 0) {
                            nextPageUrl = issue.nextPageUrl
                            setSearchResult(issue)
                        } else {
                            setEmptyView()
                        }
                    }
                }, { throwable ->
                    mRootView?.apply {
                        dismissLoading()
                        showError(
                            ExceptionHandle.handleException(throwable),
                            ExceptionHandle.errorCode
                        )
                    }

                })
        )
    }

    override fun loadMoreData() {
        checkViewAttached()
        nextPageUrl?.let {
            addSubscription(
                disposable = searchModel.loadMoreData(it)
                    .subscribe({ issue ->
                        mRootView?.apply {
                            nextPageUrl = issue.nextPageUrl
                            setSearchResult(issue)
                        }
                    }, { throwable ->
                        mRootView?.apply {
                            showError(
                                ExceptionHandle.handleException(throwable),
                                ExceptionHandle.errorCode
                            )
                        }
                    })
            )
        }
    }


}