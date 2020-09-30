package com.hot.kotlinvideomvp.mvp.contract

import com.hot.kotlinvideomvp.base.BasePresenter
import com.hot.kotlinvideomvp.base.IBaseView
import com.hot.kotlinvideomvp.base.IPresenter
import com.hot.kotlinvideomvp.mvp.model.bean.CategoryBean

/**
 * Created by anderson
 * on 2020/9/27.
 * desc:
 */
interface CategoryContract {

    interface View :IBaseView{
        /**
         *  显示分类信息
         */
        fun  showCategoryData(data:ArrayList<CategoryBean>)

        /**
         * 显示错误信息
         */
        fun showError(errorMsg:String,errorCode:Int)
    }


    interface Presenter{
        fun getCategoryData()

    }

}