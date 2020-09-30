package com.hot.kotlinvideomvp.mvp.presenter

import com.hot.kotlinvideomvp.base.BasePresenter
import com.hot.kotlinvideomvp.mvp.contract.CategoryContract
import com.hot.kotlinvideomvp.mvp.model.CategoryModel
import com.hot.kotlinvideomvp.net.ExceptionHandle

/**
 * Created by anderson
 * on 2020/9/27.
 * desc:
 */
class CategoryPresenter:BasePresenter<CategoryContract.View>(),CategoryContract.Presenter {

    private val categoryModel by lazy { CategoryModel() }
    override fun getCategoryData() {
        addSubscription(disposable = categoryModel.getCategoryData()
            .subscribe({
                data->
                mRootView?.apply {
                    dismissLoading()
                    showCategoryData(data)
                }
            },{throwable->
                mRootView?.apply {
                    showError(ExceptionHandle.handleException(throwable),ExceptionHandle.errorCode)
                }
            }))
    }


}