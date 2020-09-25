package com.hot.kotlinvideomvp.ui.fragment

import android.os.Bundle
import com.hot.kotlinvideomvp.R
import com.hot.kotlinvideomvp.base.BaseFragment

/**
 * Created by anderson
 * on 2020/9/24.
 * desc:
 */
class CategoryFragment : BaseFragment() {
    private var mTitle: String? = null

    companion object {
        fun getInstance(title: String): CategoryFragment {
            val fragment = CategoryFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment
        }
    }

    override fun initView() {

    }

    override fun getLayout(): Int {
        return R.layout.fragment_category
    }

    override fun lazyLoad() {

    }
}