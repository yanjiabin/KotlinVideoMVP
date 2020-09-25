package com.hot.kotlinvideomvp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.hot.kotlinvideomvp.R
import com.hot.kotlinvideomvp.base.BaseFragment
import com.hot.kotlinvideomvp.mvp.contract.FollowContract
import com.hot.kotlinvideomvp.mvp.model.bean.HomeBean
import com.hot.kotlinvideomvp.utils.StatusBarUtil
import com.hot.kotlinvideomvp.views.TabLayoutHelper
import kotlinx.android.synthetic.main.fragment_hot.*

/**
 *  author : anderson
 *  date   : 2020/9/1
 *  desc   :
 */
class DiscoveryFragment : BaseFragment(){


    private var mTitle: String? = null
    private val tabList = ArrayList<String>()
    private val fragmentList = ArrayList<Fragment>()
    companion object {
        fun getInstance(title: String): DiscoveryFragment {
            val fragment = DiscoveryFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment

        }
    }

    override fun initView() {
        //状态栏透明和间距处理
        activity?.let { StatusBarUtil.darkMode(it)  }
        activity?.let { StatusBarUtil.setPaddingSmart(it,toolbar) }
        tv_header_title?.text = mTitle
        tabList.add("关注")
        tabList.add("分类")
        fragmentList.add(FollowFragment.getInstance("关注"))
        fragmentList.add(CategoryFragment.getInstance("分类"))

        mViewPager.adapter = BaseViewPagerAdapter(childFragmentManager,fragmentList,tabList)
        mTabLayout.setupWithViewPager(mViewPager)
        TabLayoutHelper.setUpIndicatorWidth(tabLayout = mTabLayout)
    }

    override fun getLayout(): Int {
        return R.layout.fragment_hot
    }

    override fun lazyLoad() {
    }

}