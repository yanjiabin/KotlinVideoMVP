package com.hot.kotlinvideomvp.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hot.kotlinvideomvp.R
import com.hot.kotlinvideomvp.base.BaseFragment
import com.hot.kotlinvideomvp.mvp.contract.HomeContract
import com.hot.kotlinvideomvp.mvp.model.bean.HomeBean
import com.hot.kotlinvideomvp.mvp.presenter.HomePresenter
import com.hot.kotlinvideomvp.ui.activity.SearchActivity
import com.hot.kotlinvideomvp.ui.adapter.HomeAdapter
import com.hot.kotlinvideomvp.utils.StatusBarUtil
import com.scwang.smartrefresh.header.MaterialHeader
import com.squareup.leakcanary.CanaryLog.d
import kotlinx.android.synthetic.main.fragment_home.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.Logger
import kotlin.collections.ArrayList

/**
 *  author : anderson
 *  date   : 2020/9/1
 *  desc   :
 */
class HomeFragment : BaseFragment(), HomeContract.View {
    companion object {
        fun getInstance(title: String): HomeFragment {
            val fragment = HomeFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment
        }
    }

    private val simpleDateFormat by lazy {
        SimpleDateFormat("- MMM. dd, 'Brunch' -", Locale.ENGLISH)
    }
    private val mPresenter by lazy { HomePresenter() }
    private var mTitle: String? = null
    private var isRefresh = false
    private var loadingMore = false
    private var num: Int = 1
    private var mHomeAdapter: HomeAdapter? = null
    private val TAG = HomeFragment.javaClass.name
    private val linearLayoutManager by lazy {
        LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL,
            false
        )
    }

    private var mMaterialHeader: MaterialHeader? = null

    override fun initView() {
        mPresenter.attachView(this)
        mRefreshLayout.setEnableHeaderTranslationContent(true)
        mRefreshLayout.setOnRefreshListener {
            isRefresh = true
            mPresenter.requestHomeData(num)
        }
        mMaterialHeader = mRefreshLayout.refreshHeader as MaterialHeader?
        //打开下拉刷新区域块背景:
        mMaterialHeader?.setShowBezierWave(true)
        //设置下拉刷新主题颜色
        mRefreshLayout.setPrimaryColorsId(R.color.color_light_black, R.color.color_title_bg)

        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val childCount = mRecyclerView.childCount
                val itemCount = mRecyclerView.layoutManager?.itemCount
                val findFirstVisibleItemPosition =
                    (mRecyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
//                d(TAG, "childCount: "+childCount+"----itemCount"+itemCount+"----findFirstVisibleItemPosition:"+findFirstVisibleItemPosition)
                if (findFirstVisibleItemPosition + childCount == itemCount) {
                    loadingMore = true
                    mPresenter.loadMoreData()
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val currentVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition()
                if (currentVisibleItemPosition == 0) {
                    //背景设置为透明
                    toolbar.setBackgroundColor(getColor(R.color.color_translucent))
                    iv_search.setImageResource(R.mipmap.ic_action_search_white)
                    tv_header_title.text = ""
                } else {
                    if (mHomeAdapter?.mData!!.size > 1) {
                        toolbar.setBackgroundColor(getColor(R.color.color_title_bg))
                        iv_search.setImageResource(R.mipmap.ic_action_search_black)
                        val itemList = mHomeAdapter!!.mData
                        val item =
                            itemList[currentVisibleItemPosition + mHomeAdapter!!.bannerItemSize - 1]
                        if (item.type == "textHeader") {
                            tv_header_title.text = item.data?.text
                        } else {
                            tv_header_title.text = simpleDateFormat.format(item.data?.date)
                        }
                    }
                }
            }
        }
        )


//        iv_search.setOnClickListener { openSearchActivity() }

        mLayoutStatusView = multipleStatusView

        //状态栏透明和间距处理
        activity?.let { StatusBarUtil.darkMode(it) }
        activity?.let { StatusBarUtil.setPaddingSmart(it, toolbar) }
        iv_search.setOnClickListener {
            val intent = Intent(activity, SearchActivity::class.java)
            activity?.startActivity(intent)
        }
    }

    override fun getLayout(): Int {
        return R.layout.fragment_home
    }

    override fun lazyLoad() {
        mPresenter.requestHomeData(num)
    }

    override fun setHomeData(homeBean: HomeBean) {
        mRefreshLayout.finishRefresh()
        mLayoutStatusView?.showContent()
//        Logger.d(homeBean)
        mHomeAdapter = activity?.let { HomeAdapter(it, homeBean.issueList[0].itemList) }
        mHomeAdapter?.setBannerSize(homeBean.issueList[0].count)
        mRecyclerView.adapter = mHomeAdapter
        mRecyclerView.layoutManager = linearLayoutManager
        mRecyclerView.itemAnimator = DefaultItemAnimator()
    }

    override fun setMoreData(itemList: ArrayList<HomeBean.Issue.Item>) {
        loadingMore = false
        mHomeAdapter?.addItemData(itemList)
    }

    override fun showError(msg: String, errorCode: Int) {

    }

    override fun showLoading() {

    }

    override fun dismissLoading() {

    }
    fun getColor(colorId: Int): Int {
        return resources.getColor(colorId)
    }

}