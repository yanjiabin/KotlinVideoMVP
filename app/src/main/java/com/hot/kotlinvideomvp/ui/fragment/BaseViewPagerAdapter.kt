package com.hot.kotlinvideomvp.ui.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * Created by anderson
 * on 2020/9/24.
 * desc:
 */
class BaseViewPagerAdapter : FragmentPagerAdapter {

    private var fragmentList: List<Fragment>? = ArrayList()

    private var mTitles: List<String>? = ArrayList()

    constructor(fm: FragmentManager, fragmentList: ArrayList<Fragment>) : super(fm) {
        this.fragmentList = fragmentList
    }

    constructor(
        fm: FragmentManager,
        fragmentList: ArrayList<Fragment>,
        mTitles: ArrayList<String>
    ) : super(fm) {
        this.mTitles = mTitles
        setFragments(fm, fragmentList, mTitles)
    }

    private fun setFragments(
        fm: FragmentManager,
        fragmentList: java.util.ArrayList<Fragment>,
        mTitles: java.util.ArrayList<String>
    ) {

        if (this.fragmentList != null) {
            val beginTransaction = fm.beginTransaction()
            this.fragmentList?.forEach {
                beginTransaction.remove(it)
            }
            beginTransaction?.commitAllowingStateLoss()
            fm.executePendingTransactions()

        }
        this.fragmentList = fragmentList
        notifyDataSetChanged()

    }


    override fun getCount(): Int {
        return fragmentList!!.size
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList!![position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return if (null != mTitles)mTitles!![position] else ""
    }

}