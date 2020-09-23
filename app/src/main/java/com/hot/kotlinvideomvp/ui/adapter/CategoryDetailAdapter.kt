package com.hot.kotlinvideomvp.ui.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import com.bumptech.glide.Glide
import com.hot.kotlinvideomvp.R
import com.hot.kotlinvideomvp.durationFormat
import com.hot.kotlinvideomvp.mvp.model.bean.HomeBean
import com.hot.kotlinvideomvp.ui.activity.VideoDetailActivity
import com.hot.kotlinvideomvp.utils.Constants
import com.hot.kotlinvideomvp.views.recycleview.ViewHolder
import com.hot.kotlinvideomvp.views.recycleview.adapter.CommonAdapter

/**
 * Created by anderson
 * on 2020/9/23.
 * desc:
 */
class CategoryDetailAdapter(
    context: Context,
    dataList: ArrayList<HomeBean.Issue.Item>,
    layoutId: Int
) : CommonAdapter<HomeBean.Issue.Item>(context, dataList, layoutId) {

    override fun bindData(holder: ViewHolder, data: HomeBean.Issue.Item, position: Int) {
        setVideoItem(holder,data)
    }

    private fun setVideoItem(holder: ViewHolder, data: HomeBean.Issue.Item) {
        val itemData = data.data
        val cover = itemData?.cover?.feed ?: ""
        Glide.with(mContext)
            .load(cover)
            .into(holder.getView(R.id.iv_image))

        holder.setText(R.id.tv_title,itemData?.title?:"")
        //格式化时间
        val timeFormat = durationFormat(itemData?.date)
        holder.setText(R.id.tv_tag, "#${itemData?.category}/$timeFormat")

        holder.setOnItemClickListener(listener = View.OnClickListener {
            goToVideoPlayer(mContext as Activity, holder.getView(R.id.iv_image), data)
        })
    }
    /**
     * 跳转到视频详情页面播放
     *
     * @param activity
     * @param view
     */
    private fun goToVideoPlayer(activity: Activity, view: View, itemData: HomeBean.Issue.Item) {
        val intent = Intent(activity, VideoDetailActivity::class.java)
        intent.putExtra(Constants.BUNDLE_VIDEO_DATA, itemData)
        intent.putExtra(VideoDetailActivity.Companion.TRANSITION, true)
        activity.startActivity(intent)
        activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out)
    }

    fun addData(itemList: java.util.ArrayList<HomeBean.Issue.Item>) {
        this.mData.addAll(itemList)
        notifyDataSetChanged()

    }
}