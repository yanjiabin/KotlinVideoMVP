package com.hot.kotlinvideomvp.ui.fragment

import android.os.Bundle
import com.hot.kotlinvideomvp.R
import com.hot.kotlinvideomvp.base.BaseFragment

/**
 *  author : anderson
 *  date   : 2020/9/1
 *  desc   :
 */
class MineFragment : BaseFragment() {
    private var title: String? = null

    companion object {
        fun getInstance(title: String): MineFragment {
            val fragment = MineFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.title = title
            return fragment
        }
    }

    override fun initView() {
    }

    override fun getLayout(): Int {
       return R.layout.fragment_mine
    }

    override fun lazyLoad() {
    }
}