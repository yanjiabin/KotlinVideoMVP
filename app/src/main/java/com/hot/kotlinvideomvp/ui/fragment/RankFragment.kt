package com.hot.kotlinvideomvp.ui.fragment

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.hot.kotlinvideomvp.R
import com.hot.kotlinvideomvp.base.BaseFragment
import com.hot.kotlinvideomvp.mvp.contract.RankContract
import com.hot.kotlinvideomvp.mvp.model.bean.HomeBean
import com.hot.kotlinvideomvp.mvp.presenter.RankPresenter
import com.hot.kotlinvideomvp.ui.adapter.CategoryDetailAdapter
import kotlinx.android.synthetic.main.fragment_rank.*

/**
 * create By：anderson
 * on 2020/10/3
 * desc:
 */
class RankFragment : BaseFragment(), RankContract.View {
    private var url: String? = null
    private val mPresenter by lazy { RankPresenter() }
    private var dataList = ArrayList<HomeBean.Issue.Item>()
    private val adapter by lazy {
        activity?.let {
            CategoryDetailAdapter(
                it,
                dataList,
                R.layout.item_category_detail
            )
        }
    }

    init {
        mPresenter.attachView(this)
    }

    companion object {
        fun getInstance(url: String): RankFragment {
            val rankFragment = RankFragment()
            rankFragment.url = url
            val bundle = Bundle()
            rankFragment.arguments = bundle
            return rankFragment
        }
    }


    override fun initView() {
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        mRecyclerView.adapter = adapter

    }

    override fun getLayout(): Int {
        return R.layout.fragment_rank
    }

    override fun lazyLoad() {
        if (!url.isNullOrEmpty()){
            mPresenter.getRankList(url!!)
        }
    }

    override fun setRankList(data: HomeBean.Issue) {
        dataList = data.itemList
        adapter?.addData(dataList)
    }

    override fun showError(msg: String, code: Int) {
    }

    override fun showLoading() {
    }

    override fun dismissLoading() {

    }
}