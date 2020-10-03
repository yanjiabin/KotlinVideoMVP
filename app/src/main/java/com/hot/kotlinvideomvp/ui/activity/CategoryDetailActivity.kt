package com.hot.kotlinvideomvp.ui.activity

import android.graphics.Color
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hot.kotlinvideomvp.R
import com.hot.kotlinvideomvp.base.BaseActivity
import com.hot.kotlinvideomvp.mvp.contract.CatetgoryDetailContract
import com.hot.kotlinvideomvp.mvp.model.bean.CategoryBean
import com.hot.kotlinvideomvp.mvp.model.bean.HomeBean
import com.hot.kotlinvideomvp.mvp.presenter.CategoryDetailPresenter
import com.hot.kotlinvideomvp.ui.adapter.CategoryDetailAdapter
import com.hot.kotlinvideomvp.utils.Constants
import com.hot.kotlinvideomvp.utils.DisplayManager.init
import kotlinx.android.synthetic.main.activity_category_detail.*

/**
 * Created by anderson
 * on 2020/9/27.
 * desc:
 */
class CategoryDetailActivity : BaseActivity(), CatetgoryDetailContract.View {

    private val mPresenter by lazy { CategoryDetailPresenter() }
    private val adapter by lazy {
        CategoryDetailAdapter(
            this,
            dataList,
            R.layout.item_category_detail
        )
    }
    private var dataList = ArrayList<HomeBean.Issue.Item>()
    private var categoryBean: CategoryBean? = null

    private var loadMore = false

    init {
        mPresenter.attachView(this)
    }

    override fun start() {
        categoryBean?.id?.let { mPresenter.getCategoryDetailList(it) }
    }

    override fun initView() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        tv_category_desc.setText("#${categoryBean?.description}#")


        collapsing_toolbar_layout.title = categoryBean?.name
        collapsing_toolbar_layout.setExpandedTitleColor(Color.WHITE)
        collapsing_toolbar_layout.setCollapsedTitleTextColor(Color.BLACK)



        Glide.with(this)
            .load(categoryBean?.headerImage)
            .into(imageView)

        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = adapter

        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val itemCount = recyclerView.layoutManager?.itemCount
                val findLastVisibleItemPosition =
                    (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                if (!loadMore && findLastVisibleItemPosition == (itemCount?.minus(1))) {
                    //证明滑动到最后一个item了  需要加载更多
                    loadMore = true
                    mPresenter.loadMoreData()
                }
            }
        })
    }

    override fun initData() {
        categoryBean = intent.getSerializableExtra(Constants.BUNDLE_CATEGORY_DATA) as CategoryBean
    }

    override fun getLayout(): Int {
        return R.layout.activity_category_detail
    }

    override fun setCategoryList(itemList: ArrayList<HomeBean.Issue.Item>) {
        loadMore = false
        adapter.addData(itemList)
    }

    override fun showError(errorMsg: String) {

    }

    override fun showLoading() {

    }

    override fun dismissLoading() {

    }
}