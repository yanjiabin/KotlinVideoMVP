package com.hot.kotlinvideomvp.ui.activity

import com.hot.kotlinvideomvp.R
import com.hot.kotlinvideomvp.base.BaseActivity
import com.hot.kotlinvideomvp.mvp.contract.VideoDetailContact
import com.hot.kotlinvideomvp.mvp.model.bean.HomeBean
import com.hot.kotlinvideomvp.mvp.presenter.VideoDetailPresenter
import com.hot.kotlinvideomvp.utils.Constants
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_video_detail.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by anderson
 * on 2020/9/16.
 * desc:
 */
class VideoDetailActivity : BaseActivity(), VideoDetailContact.View {

    companion object {
        const val IMG_TRANSITION = "IMG_TRANSITION"
        const val TRANSITION = "TRANSITION"
    }

    private lateinit var mData: HomeBean.Issue.Item

    private var isTransition: Boolean = false
    private val mPresenter: VideoDetailPresenter by lazy { VideoDetailPresenter() }

    override fun start() {

    }

    override fun initView() {
        mPresenter.attachView(this)

        mPresenter.loadVideoInfo(mData)
    }

    override fun initData() {
        mData = intent.getSerializableExtra(Constants.BUNDLE_VIDEO_DATA) as HomeBean.Issue.Item
        isTransition = intent.getBooleanExtra(TRANSITION,false)
//        saveWatchVideoHistoryInfo(mData) todo
    }
    /**
     * 保存观看记录
     */
//    private fun saveWatchVideoHistoryInfo(watchItem: HomeBean.Issue.Item) {
//        //保存之前要先查询sp中是否有该value的记录，有则删除.这样保证搜索历史记录不会有重复条目
//        val historyMap = WatchHistoryUtils.getAll(Constants.FILE_WATCH_HISTORY_NAME,MyApplication.context) as Map<*, *>
//        for ((key, _) in historyMap) {
//            if (watchItem == WatchHistoryUtils.getObject(Constants.FILE_WATCH_HISTORY_NAME,MyApplication.context, key as String)) {
//                WatchHistoryUtils.remove(Constants.FILE_WATCH_HISTORY_NAME,MyApplication.context, key)
//            }
//        }
//        WatchHistoryUtils.putObject(Constants.FILE_WATCH_HISTORY_NAME,MyApplication.context, watchItem,"" + mFormat.format(
//            Date()
//        ))
//    }
    override fun getLayout(): Int = R.layout.activity_video_detail


    override fun setVideo(url: String) {
        Logger.d("playUrl:$url")
        mVideoView.setUp(url,false,"")
        //开始自动播放
        mVideoView.startPlayLogic()

    }

    override fun setVideoInfo(itemInfo: HomeBean.Issue.Item) {

    }

    override fun setBackground(url: String) {

    }

    override fun setRecentRelatedVideo(itemList: ArrayList<HomeBean.Issue.Item>) {

    }

    override fun setErrorMsg(errorMsg: String) {

    }

    override fun showLoading() {

    }

    override fun dismissLoading() {

    }
}