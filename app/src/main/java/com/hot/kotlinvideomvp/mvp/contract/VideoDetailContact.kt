package com.hot.kotlinvideomvp.mvp.contract

import com.hot.kotlinvideomvp.base.IBaseView
import com.hot.kotlinvideomvp.base.IPresenter
import com.hot.kotlinvideomvp.mvp.model.bean.HomeBean

/**
 * Created by anderson
 * on 2020/9/16.
 * desc:
 */
interface VideoDetailContact {



    interface View:IBaseView{
        /**
         *  设置视频播放源
         */
        fun setVideo(url:String)

        /**
         *  设置视频信息
         */
        fun setVideoInfo(itemInfo:HomeBean.Issue.Item)

        /**
         * 视频背景
         */
        fun setBackground(url:String)

        /**
         * 设置最新的相关视频
         */
        fun setRecentRelatedVideo(itemList:ArrayList<HomeBean.Issue.Item>)

        /**
         *  设置错误信息
         */
        fun setErrorMsg(errorMsg:String)

    }

    interface Presenter:IPresenter<View>{

        /**
         * 加载视频信息
         */
        fun loadVideoInfo(itemInfo: HomeBean.Issue.Item)

        /**
         * 请求相关的视频数据
         */
        fun requestRelatedVideo(id: Long)

    }


}