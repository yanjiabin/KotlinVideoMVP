package com.hot.kotlinvideomvp.ui.fragment

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hot.kotlinvideomvp.R
import com.hot.kotlinvideomvp.base.BaseFragment
import com.hot.kotlinvideomvp.mvp.contract.FollowContract
import com.hot.kotlinvideomvp.mvp.model.bean.HomeBean
import com.hot.kotlinvideomvp.mvp.presenter.FollowPresenter
import com.hot.kotlinvideomvp.net.ErrorStatus
import com.hot.kotlinvideomvp.showToast
import com.hot.kotlinvideomvp.ui.adapter.FollowAdapter
import kotlinx.android.synthetic.main.layout_recyclerview.*

/**
 * Created by anderson
 * on 2020/9/24.
 * desc:
 */
class FollowFragment : BaseFragment(), FollowContract.View {

    private val mPresenter: FollowPresenter by lazy { FollowPresenter() }
    private var mTitle: String? = null
    private val mFollowAdapter by lazy { activity?.let { FollowAdapter(it, itemList) } }

    private var itemList = ArrayList<HomeBean.Issue.Item>()
    private var mLoadMore = false

    companion object {
        fun getInstance(title: String): FollowFragment {
            val fragment = FollowFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment
        }
    }

    override fun initView() {
        mPresenter.attachView(this)

        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        mRecyclerView.adapter = mFollowAdapter


        mRecyclerView.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val itemCount = recyclerView.layoutManager?.itemCount
                val lastItemPosition =
                    (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                if (!mLoadMore&&lastItemPosition == (itemCount?.minus(1))){
                    mLoadMore = true
                    mPresenter.loadMoreData()
                }
            }
        })
        this.mLayoutStatusView = multipleStatusView
    }

    override fun getLayout(): Int {
        return R.layout.layout_recyclerview
    }

    override fun lazyLoad() {
        mPresenter.requestFollowList()
    }

    override fun setFollowInfo(issue: HomeBean.Issue) {
        mLoadMore = false
        itemList = issue.itemList
        mFollowAdapter?.addData(itemList)
    }

    override fun showError(errorMsg: String, errorCode: Int) {
        showToast(errorMsg)
        if (errorCode == ErrorStatus.NETWORK_ERROR) {
            multipleStatusView.showNoNetwork()
        } else {
            multipleStatusView.showError()
        }
    }

    override fun showLoading() {
        mLayoutStatusView?.showLoading()
    }

    override fun dismissLoading() {
        mLayoutStatusView?.showContent()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }
}