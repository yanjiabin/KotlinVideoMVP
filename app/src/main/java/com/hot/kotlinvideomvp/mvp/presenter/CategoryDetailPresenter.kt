package com.hot.kotlinvideomvp.mvp.presenter

import com.hot.kotlinvideomvp.base.BasePresenter
import com.hot.kotlinvideomvp.mvp.contract.CategoryContract
import com.hot.kotlinvideomvp.mvp.contract.CatetgoryDetailContract
import com.hot.kotlinvideomvp.mvp.model.CategoryDetailModel
import com.hot.kotlinvideomvp.net.ExceptionHandle

/**
 * Created by anderson
 * on 2020/9/29.
 * desc:
 */
class CategoryDetailPresenter:BasePresenter<CatetgoryDetailContract.View>(),CatetgoryDetailContract.Presenter {


    private val categoryModel by lazy { CategoryDetailModel() }

    private var nextUrl:String?=null

    override fun getCategoryDetailList(id: Long) {
        checkViewAttached()
        addSubscription(disposable =  categoryModel.getCategoryDetailList(id)
            .subscribe({
                data->
                mRootView?.apply {
                    dismissLoading()
                    nextUrl = data.nextPageUrl
                    setCategoryList(data.itemList)
                }
            },{throwable->
                mRootView?.apply {
                    showError(ExceptionHandle.handleException(throwable))
                }
            }))
    }

    override fun loadMoreData() {
        checkViewAttached()
        val disposable =  nextUrl?.let{
            categoryModel.loadMoreData(it).subscribe({
                data->
                mRootView?.apply {
                    dismissLoading()
                    nextUrl = data.nextPageUrl
                    setCategoryList(data.itemList)
                }
            },{throwable->
                mRootView?.apply {
                    showError(ExceptionHandle.handleException(throwable))
                }
            })
        }
        disposable?.let { addSubscription(it) }
    }


}