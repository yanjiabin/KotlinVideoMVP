package com.hot.kotlinvideomvp.mvp.contract

import com.hot.kotlinvideomvp.base.IBaseView
import com.hot.kotlinvideomvp.mvp.model.bean.TabInfoBean

interface HotTabContract {

    interface View:IBaseView{
        fun setTabInfo(tabInfoBean:TabInfoBean)

        fun showError(msg:String,code:Int)
    }


    interface Presenter{
        fun getTabInfo()
    }
}