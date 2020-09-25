package com.hot.kotlinvideomvp.mvp.presenter

import android.app.Activity
import com.hot.kotlinvideomvp.MyApplication
import com.hot.kotlinvideomvp.base.BasePresenter
import com.hot.kotlinvideomvp.dataFormat
import com.hot.kotlinvideomvp.mvp.contract.VideoDetailContact
import com.hot.kotlinvideomvp.mvp.model.bean.HomeBean
import com.hot.kotlinvideomvp.showToast
import com.hot.kotlinvideomvp.utils.DisplayManager
import com.hot.kotlinvideomvp.utils.NetworkUtil

/**
 * Created by anderson
 * on 2020/9/16.
 * desc:
 */
class VideoDetailPresenter :BasePresenter<VideoDetailContact.View>(),VideoDetailContact.Presenter {

    override fun loadVideoInfo(itemInfo: HomeBean.Issue.Item) {
        val playInfo = itemInfo.data?.playInfo
        val netType = NetworkUtil.isWifi(MyApplication.context)
        checkViewAttached()
        if (playInfo!!.size>1){
            if (netType){
                for (i in playInfo){
                    if (i.type == "high"){
                        var playUrl = i.url
                        mRootView?.setVideo(playUrl)
                        break
                    }
                }
            } else{
                for (i in playInfo){
                    if (i.type == "normal"){
                        var playUrl = i.url;
                        mRootView?.setVideo(playUrl)
                        // TODO: 2020/9/16  待完善
                        (mRootView as Activity).showToast("本次消耗${(mRootView as Activity)
                            .dataFormat(i.urlList[0].size)}流量")
                    }
                }
            }
        } else{
            mRootView?.setVideo(itemInfo.data.playUrl)
        }

        //设置背景
        val backgroundUrl = itemInfo.data.cover.blurred + "/thumbnail/${DisplayManager.getScreenHeight()!! - DisplayManager.dip2px(250f)!!}x${DisplayManager.getScreenWidth()}"
        backgroundUrl.let { mRootView?.setBackground(it) }

        mRootView?.setVideoInfo(itemInfo)

    }

    override fun requestRelatedVideo(id: Long) {

    }
}