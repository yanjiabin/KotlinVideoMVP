package com.hot.kotlinvideomvp.ui.adapter

import android.content.Context
import android.graphics.Typeface
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.hot.kotlinvideomvp.MyApplication
import com.hot.kotlinvideomvp.R
import com.hot.kotlinvideomvp.durationFormat
import com.hot.kotlinvideomvp.mvp.model.bean.HomeBean
import com.hot.kotlinvideomvp.views.recycleview.MultipleType
import com.hot.kotlinvideomvp.views.recycleview.ViewHolder
import com.hot.kotlinvideomvp.views.recycleview.adapter.CommonAdapter

/**
 * Created by anderson
 * on 2020/9/21.
 * desc:
 */
class VideoDetailAdapter(mContext: Context, data: ArrayList<HomeBean.Issue.Item>) :
    CommonAdapter<HomeBean.Issue.Item>(mContext, data, object : MultipleType<HomeBean.Issue.Item> {

        override fun getLayoutId(item: HomeBean.Issue.Item, position: Int): Int {
            return when {
                position == 0 ->
                    R.layout.item_video_detail_info

                data[position].type == "textCard" ->
                    R.layout.item_video_text_card

                data[position].type == "videoSmallCard" ->
                    R.layout.item_video_small_card
                else ->
                    throw IllegalAccessException("Api 解析出错了，出现其他类型")
            }
        }
    }) {
    override fun bindData(holder: ViewHolder, data: HomeBean.Issue.Item, position: Int) {
        when {
            position == 0 -> {
                setVideoDetailInfo(data, holder)
            }
            data.type == "textCard" -> {
                holder.setText(R.id.tv_text_card, data.data?.text!!)
            }


            data.type == "videoSmallCard" -> {
                with(holder) {
                    setText(R.id.tv_title, data.data?.title!!)
                    setText(
                        R.id.tv_tag,
                        "#${data.data.category}/${durationFormat(data.data?.duration)}"
                    )
                    setImagePath(R.id.iv_video_small_card,
                        object : ViewHolder.HolderImageLoader(data.data.cover.detail) {
                            override fun loadImage(iv: ImageView, path: String) {

                                Glide.with(mContext)
                                    .load(path)
                                    .into(iv)
                            }
                        })
                }
            }
            else -> {
                throw  IllegalAccessException("Api 解析出错了，出现其他类型")
            }

        }
    }

    private var textTypeface:Typeface?=null
    init {
        textTypeface = Typeface.createFromAsset(MyApplication.context.assets, "fonts/FZLanTingHeiS-L-GB-Regular.TTF")
    }

    private fun setVideoDetailInfo(data: HomeBean.Issue.Item, holder: ViewHolder) {
        //标题
        data.data?.title?.let { holder.setText(R.id.tv_title, it) }
        //简介
        data.data?.description?.let { holder.setText(R.id.expandable_text, it) }
        //标签
        holder.setText(
            R.id.tv_tag,
            "#${data.data?.category} /${durationFormat(data.data?.duration)}"
        )
        //喜欢
        holder.setText(R.id.tv_action_favorites, data.data?.consumption?.collectionCount.toString())
        //分享
        holder.setText(R.id.tv_action_share, data.data?.consumption?.shareCount.toString())
        //评论
        holder.setText(R.id.tv_action_reply, data.data?.consumption?.replyCount.toString())
        if (data.data?.author != null) {
            with(holder) {
                setText(R.id.tv_author_name, data.data.author.name)
                setText(R.id.tv_author_desc, data.data.author.description)
                setImagePath(R.id.iv_avatar,
                    object : ViewHolder.HolderImageLoader(data.data.author.icon) {
                        override fun loadImage(iv: ImageView, path: String) {
                            Glide.with(mContext)
                                .load(path)
                                .into(iv)
                        }
                    })
            }
        } else {
            holder.setViewVisibility(R.id.layout_author_view, View.GONE)
        }

        with(holder) {
            getView<TextView>(R.id.tv_action_favorites).setOnClickListener {
                Toast.makeText(MyApplication.context, "喜欢", Toast.LENGTH_SHORT).show()
            }
            getView<TextView>(R.id.tv_action_share).setOnClickListener {
                Toast.makeText(MyApplication.context, "分享", Toast.LENGTH_SHORT).show()
            }
            getView<TextView>(R.id.tv_action_reply).setOnClickListener {
                Toast.makeText(MyApplication.context, "评论", Toast.LENGTH_SHORT).show()
            }
        }
    }


    /**
     *  添加适配详细信息
     */
    fun addData(item:HomeBean.Issue.Item){
        mData.clear()
        notifyDataSetChanged()
        mData.add(item)
        notifyDataSetChanged()
    }

    /**
     *  添加相关推荐数据item
     */
    fun addData(item:ArrayList<HomeBean.Issue.Item>){
        mData.addAll(item)
        notifyItemRangeChanged(1,item.size)
    }

    /**
     * Kotlin的函数可以作为参数，写callback的时候，可以不用interface了
     */

    private var mOnItemClickRelatedVideo: ((item: HomeBean.Issue.Item) -> Unit)? = null


    fun setOnItemDetailClick(mItemRelatedVideo: (item: HomeBean.Issue.Item) -> Unit) {
        this.mOnItemClickRelatedVideo = mItemRelatedVideo
    }

}