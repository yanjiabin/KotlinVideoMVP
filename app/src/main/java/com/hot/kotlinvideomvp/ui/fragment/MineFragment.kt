package com.hot.kotlinvideomvp.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.hot.kotlinvideomvp.R
import com.hot.kotlinvideomvp.base.BaseFragment
import com.hot.kotlinvideomvp.showToast
import com.hot.kotlinvideomvp.utils.StatusBarUtil
import kotlinx.android.synthetic.main.fragment_mine.*

/**
 *  author : anderson
 *  date   : 2020/9/1
 *  desc   :
 */
class MineFragment : BaseFragment(),View.OnClickListener {

    private var mTitle:String? =null


    companion object {
        fun getInstance(title:String): MineFragment {
            val fragment = MineFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment
        }
    }


    override fun getLayout(): Int =R.layout.fragment_mine

    override fun initView() {
        //状态栏透明和间距处理
        activity?.let { StatusBarUtil.darkMode(it) }
        activity?.let { StatusBarUtil.setPaddingSmart(it, toolbar) }

        tv_view_homepage.setOnClickListener(this)
        iv_avatar.setOnClickListener(this)
        iv_about.setOnClickListener(this)

        tv_collection.setOnClickListener(this)
        tv_comment.setOnClickListener(this)

        tv_mine_message.setOnClickListener(this)
        tv_mine_attention.setOnClickListener(this)
        tv_mine_cache.setOnClickListener(this)
        tv_watch_history.setOnClickListener(this)
        tv_feedback.setOnClickListener(this)


    }

    override fun lazyLoad() {

    }



    override fun onClick(v: View?) {
        when{
            v?.id==R.id.iv_avatar|| v?.id==R.id.tv_view_homepage -> {
//                val intent = Intent(activity, ProfileHomePageActivity::class.java)
//                startActivity(intent)
            }
            v?.id==R.id.iv_about ->{
//                val intent = Intent(activity, AboutActivity::class.java)
//                startActivity(intent)
            }
            v?.id==R.id.tv_collection -> showToast("收藏")
            v?.id==R.id.tv_comment -> showToast("评论")
            v?.id==R.id.tv_mine_message -> showToast("我的消息")
            v?.id==R.id.tv_mine_attention -> showToast("我的关注")
            v?.id==R.id.tv_mine_attention -> showToast("我的缓存")
//            v?.id==R.id.tv_watch_history -> startActivity(Intent(activity,WatchHistoryActivity::class.java))
            v?.id==R.id.tv_feedback -> showToast("意见反馈")

        }
    }

}