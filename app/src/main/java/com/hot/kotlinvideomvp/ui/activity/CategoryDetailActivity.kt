package com.hot.kotlinvideomvp.ui.activity

import com.hot.kotlinvideomvp.R
import com.hot.kotlinvideomvp.base.BaseActivity
import com.hot.kotlinvideomvp.mvp.contract.CatetgoryDetailContract
import com.hot.kotlinvideomvp.mvp.model.bean.CategoryBean
import com.hot.kotlinvideomvp.mvp.model.bean.HomeBean
import com.hot.kotlinvideomvp.mvp.presenter.CategoryDetailPresenter
import com.hot.kotlinvideomvp.utils.Constants
import com.hot.kotlinvideomvp.utils.DisplayManager.init

/**
 * Created by anderson
 * on 2020/9/27.
 * desc:
 */
class CategoryDetailActivity : BaseActivity(), CatetgoryDetailContract.View {

    private val mPresenter by lazy { CategoryDetailPresenter() }

    init {
        mPresenter.attachView(this)
    }

    override fun start() {

    }

    override fun initView() {

    }

    override fun initData() {
        var categoryBean = intent.getSerializableExtra(Constants.BUNDLE_CATEGORY_DATA) as CategoryBean
    }

    override fun getLayout(): Int {
        return R.layout.activity_category_detail
    }

    override fun setCategoryList(itemList: ArrayList<HomeBean.Issue.Item>) {

    }

    override fun showError(errorMsg: String) {

    }

    override fun showLoading() {

    }

    override fun dismissLoading() {

    }
}