package com.hot.kotlinvideomvp.mvp.contract

import com.hot.kotlinvideomvp.base.IBaseView
import com.hot.kotlinvideomvp.mvp.model.bean.HomeBean

/**
 * create By：anderson
 * on 2020/10/3
 * desc:
 */
interface RankContract {



    interface View:IBaseView{
        fun setRankList(data:HomeBean.Issue)

        fun showError(msg:String,code:Int)
    }

    interface Presenter{

        fun getRankList(url:String)
    }
}