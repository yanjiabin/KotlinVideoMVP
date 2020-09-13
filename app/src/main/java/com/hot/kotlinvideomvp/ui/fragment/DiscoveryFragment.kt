package com.hot.kotlinvideomvp.ui.fragment

import android.os.Bundle
import com.hot.kotlinvideomvp.R
import com.hot.kotlinvideomvp.base.BaseFragment

/**
 *  author : anderson
 *  date   : 2020/9/1
 *  desc   :
 */
class DiscoveryFragment : BaseFragment() {


    private var mTitle: String? = null

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

    }

    override fun getLayout(): Int {
        return R.layout.fragment_discovery
    }

    override fun lazyLoad() {
    }
}