package com.hot.kotlinvideomvp.ui.activity

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.hot.kotlinvideomvp.MyApplication
import com.hot.kotlinvideomvp.R
import com.hot.kotlinvideomvp.base.BaseActivity
import com.hot.kotlinvideomvp.mvp.contract.VideoDetailContact
import com.hot.kotlinvideomvp.mvp.model.bean.HomeBean
import com.hot.kotlinvideomvp.mvp.presenter.VideoDetailPresenter
import com.hot.kotlinvideomvp.showToast
import com.hot.kotlinvideomvp.ui.adapter.VideoDetailAdapter
import com.hot.kotlinvideomvp.utils.Constants
import com.hot.kotlinvideomvp.utils.StatusBarUtil
import com.hot.kotlinvideomvp.utils.WatchHistoryUtils
import com.hot.kotlinvideomvp.views.VideoListener
import com.orhanobut.logger.Logger
import com.scwang.smartrefresh.header.MaterialHeader
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.shuyu.gsyvideoplayer.listener.LockClickListener
import com.shuyu.gsyvideoplayer.render.view.GSYSurfaceView
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer
import kotlinx.android.synthetic.main.activity_video_detail.*
import kotlinx.android.synthetic.main.activity_video_detail.mRecyclerView
import kotlinx.android.synthetic.main.activity_video_detail.mRefreshLayout
import kotlinx.android.synthetic.main.fragment_home.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.SimpleFormatter
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

    private var mMaterialHeader: MaterialHeader? = null
    private lateinit var mData: HomeBean.Issue.Item
    private var itemList = ArrayList<HomeBean.Issue.Item>()

    private val adapter by lazy { VideoDetailAdapter(this, itemList) }
    private var isTransition: Boolean = false
    private val mPresenter: VideoDetailPresenter by lazy { VideoDetailPresenter() }

    private var isPause = false;
    private var isPlay = false
    private var orientationUtils: OrientationUtils? = null

    private val mFormat by lazy { SimpleDateFormat("yyyyMMddHHmmss") }
    override fun start() {

    }

    override fun initView() {
        mPresenter.attachView(this)

        initVideoViewConfig()

        loadVideoInfo()
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = adapter


        adapter.setOnItemDetailClick { mPresenter.loadVideoInfo(it) }

        StatusBarUtil.immersive(this)
        StatusBarUtil.setPaddingSmart(this, mVideoView)
        //下拉刷新
        mRefreshLayout.setEnableHeaderTranslationContent(true)
        mRefreshLayout.setOnRefreshListener {
            loadVideoInfo()
        }

        mMaterialHeader = mRefreshLayout.refreshHeader as MaterialHeader?
        //打开下拉刷新区域块背景:
        mMaterialHeader?.setShowBezierWave(true)
        //设置下拉刷新主题颜色
        mRefreshLayout.setPrimaryColorsId(R.color.color_light_black, R.color.color_title_bg)
    }


    /**
     *  初始化videoView的配置
     */
    private fun initVideoViewConfig() {
        orientationUtils = OrientationUtils(this, mVideoView)
        //设置是否可以自动旋转
        mVideoView.isRotateViewAuto = false
        //设置是否可以滑动调整
        mVideoView.setIsTouchWiget(true)
        val imageView = ImageView(this)

        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        Glide.with(this)
            .load(mData.data?.cover?.feed)
            .into(imageView)
        mVideoView.thumbImageView = imageView
        mVideoView.setStandardVideoAllCallBack(object : VideoListener {
            override fun onPrepared(url: String, vararg objects: Any) {
                super.onPrepared(url, *objects)
                //开始播放了才能旋转和全屏
                orientationUtils?.isEnable = true
                isPlay = true
            }

            override fun onAutoComplete(url: String, vararg objects: Any) {
                super.onAutoComplete(url, *objects)
                Logger.d("***** onAutoPlayComplete **** ")
            }

            override fun onPlayError(url: String, vararg objects: Any) {
                super.onPlayError(url, *objects)
                showToast("播放失败")
            }

            override fun onEnterFullscreen(url: String, vararg objects: Any) {
                super.onEnterFullscreen(url, *objects)
                Logger.d("***** onEnterFullscreen **** ")
            }

            override fun onQuitFullscreen(url: String, vararg objects: Any) {
                super.onQuitFullscreen(url, *objects)
                Logger.d("***** onQuitFullscreen **** ")
                //列表返回的样式判断
                orientationUtils?.backToProtVideo()
            }
        })

        //设置视频左上角的返回键点击效果
        mVideoView.backButton.setOnClickListener {
            onBackPressed()
        }

        //全屏点击
        mVideoView.fullscreenButton.setOnClickListener {
            //直接横屏
            orientationUtils?.resolveByClick()
            //第一个true表示是否需要隐藏actionBar,第二个true表示是否需要隐藏statusbar
            mVideoView.startWindowFullscreen(this, true, true)
        }

        //锁屏事件
        mVideoView.setLockClickListener(object : LockClickListener {
            override fun onClick(view: View?, lock: Boolean) {
                orientationUtils?.isEnable = !lock
            }
        })
    }

    /**
     *加载视频信息
     */
    fun loadVideoInfo() {
        mPresenter.loadVideoInfo(mData)
    }

    override fun initData() {
        mData = intent.getSerializableExtra(Constants.BUNDLE_VIDEO_DATA) as HomeBean.Issue.Item
        isTransition = intent.getBooleanExtra(TRANSITION, false)
        saveWatchVideoHistoryInfo(mData)
    }


    /**
     * 保存观看记录
     */
    private fun saveWatchVideoHistoryInfo(watchItem: HomeBean.Issue.Item) {
        //保存之前要先查询sp中是否有该value的记录，有则删除.这样保证搜索历史记录不会有重复条目
        val historyMap = WatchHistoryUtils.getAll(Constants.FILE_WATCH_HISTORY_NAME,MyApplication.context) as Map<*, *>
        for ((key, _) in historyMap) {
            if (watchItem == WatchHistoryUtils.getObject(Constants.FILE_WATCH_HISTORY_NAME,MyApplication.context, key as String)) {
                WatchHistoryUtils.remove(Constants.FILE_WATCH_HISTORY_NAME,MyApplication.context, key)
            }
        }
        WatchHistoryUtils.putObject(Constants.FILE_WATCH_HISTORY_NAME,MyApplication.context, watchItem,"" + mFormat.format(Date()))
    }


    override fun getLayout(): Int = R.layout.activity_video_detail


    override fun setVideo(url: String) {
        Logger.d("playUrl:$url")
        mRefreshLayout.finishRefresh()
        mVideoView.setUp(url, false, "")
        //开始自动播放
        mVideoView.startPlayLogic()

    }

    override fun setVideoInfo(itemInfo: HomeBean.Issue.Item) {
        adapter.addData(itemInfo)
    }

    override fun setBackground(url: String) {

    }

    override fun setRecentRelatedVideo(itemList: ArrayList<HomeBean.Issue.Item>) {
        adapter.addData(itemList)
        this.itemList = itemList
    }

    override fun setErrorMsg(errorMsg: String) {

    }

    override fun showLoading() {

    }

    override fun dismissLoading() {
        mRefreshLayout.finishRefresh()
    }

    /**
     * 按下返回键
     */
    override fun onBackPressed() {
        super.onBackPressed()
        orientationUtils?.backToProtVideo()
        if (StandardGSYVideoPlayer.backFromWindowFull(this)) {
            return
        }
        //释放所有资源
        mVideoView.setStandardVideoAllCallBack(null)
        GSYVideoPlayer.releaseAllVideos()
        finish()
        overridePendingTransition(R.anim.anim_out, R.anim.anim_in)
    }


    override fun onResume() {
        super.onResume()
        getCurPlay().onVideoResume()
        isPause = false
    }

    override fun onPause() {
        super.onPause()
        getCurPlay().onVideoPause()
        isPause = true
    }

    private fun getCurPlay(): GSYVideoPlayer {
        return if (mVideoView.fullWindowPlayer != null) {
            return mVideoView.fullWindowPlayer
        } else {
            return mVideoView
        }
    }
}