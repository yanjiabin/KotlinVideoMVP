package com.hot.kotlinvideomvp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.hot.kotlinvideomvp.R
import com.hot.kotlinvideomvp.base.BaseFragment
import com.hot.kotlinvideomvp.mvp.contract.HotTabContract
import com.hot.kotlinvideomvp.mvp.model.bean.TabInfoBean
import com.hot.kotlinvideomvp.mvp.presenter.HotTabPresenter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_hot.*
import kotlinx.android.synthetic.main.fragment_hot.view.*

/**
 *  author : anderson
 *  date   : 2020/9/1
 *  desc   :
 */
class HotFragment : BaseFragment(), HotTabContract.View {
    private var title: String? = null
    private val mTitles = ArrayList<String>()
    private val mFragmentList = ArrayList<Fragment>()
    private val mPresenter by lazy { HotTabPresenter() }

    init {
        mPresenter.attachView(this)
    }

    companion object {
        fun getInstance(title: String): HotFragment {
            val fragment = HotFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.title = title
            return fragment
        }
    }

    override fun initView() {

    }

    override fun getLayout(): Int {
        return R.layout.fragment_hot
    }

    override fun lazyLoad() {
        mPresenter.getTabInfo()
    }

    override fun setTabInfo(tabInfoBean: TabInfoBean) {
        multipleStatusView.showContent()
        tabInfoBean.tabInfo.tabList.mapTo(mTitles) { it.name }
        tabInfoBean.tabInfo.tabList.mapTo(mFragmentList) { RankFragment.getInstance(it.apiUrl) }
        mViewPager.adapter = BaseViewPagerAdapter(childFragmentManager, mFragmentList, mTitles)
        mTabLayout.setupWithViewPager(mViewPager)
    }

    override fun showError(msg: String, code: Int) {
    }

    override fun showLoading() {
    }

    override fun dismissLoading() {
    }
}