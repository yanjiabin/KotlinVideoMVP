package com.hot.kotlinvideomvp.mvp.presenter

import com.hot.kotlinvideomvp.HomeModel
import com.hot.kotlinvideomvp.base.BasePresenter
import com.hot.kotlinvideomvp.mvp.contract.HomeContract
import com.hot.kotlinvideomvp.mvp.model.bean.HomeBean
import com.hot.kotlinvideomvp.net.ExceptionHandle

/**
 *  author : anderson
 *  date   : 2020/9/1
 *  desc   :
 */
class HomePresenter : BasePresenter<HomeContract.View>(), HomeContract.Presenter {

    private var nextPageUrl: String? = null
    private var bannerHomeBean: HomeBean? = null
    private val homeModel: HomeModel by lazy {
        HomeModel()
    }

    override fun requestHomeData(num: Int) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = homeModel.requestHomeData(num)
            .flatMap { homeBean ->

                //过滤掉 Banner2(包含广告,等不需要的 Type), 具体查看接口分析
                val bannerItemList = homeBean.issueList[0].itemList

                bannerItemList.filter { item ->
                    item.type == "banner2" || item.type == "horizontalScrollCard"
                }.forEach { item ->
                    //移除 item
                    bannerItemList.remove(item)
                }

                bannerHomeBean = homeBean //记录第一页是当做 banner 数据


                //根据 nextPageUrl 请求下一页数据
                homeModel.loadMoreData(homeBean.nextPageUrl)
            }.subscribe({ homeBean ->
                mRootView?.apply {
                    dismissLoading()
                    nextPageUrl = homeBean.nextPageUrl
//过滤掉 Banner2(包含广告,等不需要的 Type), 具体查看接口分析
                    val newBannerItemList = homeBean.issueList[0].itemList

                    newBannerItemList.filter { item ->
                        item.type == "banner2" || item.type == "horizontalScrollCard"
                    }.forEach { item ->
                        //移除 item
                        newBannerItemList.remove(item)
                    }
                    // 重新赋值 Banner 长度
                    bannerHomeBean!!.issueList[0].count =
                        bannerHomeBean!!.issueList[0].itemList.size

                    //赋值过滤后的数据 + banner 数据
                    bannerHomeBean?.issueList!![0].itemList.addAll(newBannerItemList)

                    setHomeData(bannerHomeBean!!)
                }
            }, { t ->
                {
                    mRootView?.apply {
                        dismissLoading()
                        showError(ExceptionHandle.handleException(t), ExceptionHandle.errorCode)
                    }
                }
            })

        addSubscription(disposable)


    }

    override fun loadMoreData() {
        nextPageUrl?.let {
            homeModel.loadMoreData(it)
                .subscribe({ homeBean ->
                    mRootView?.apply {
                        val newItemList = homeBean.issueList[0].itemList
                        newItemList.filter { item ->
                            item.type == "banner2" || item.type == "horizontalScrollCard"
                        }.forEach { item ->
                            newItemList.remove(item)
                        }
                        nextPageUrl = homeBean.nextPageUrl
                        setMoreData(newItemList)
                    }
                }, { t ->
                    mRootView?.apply {
                        showError(ExceptionHandle.handleException(t), ExceptionHandle.errorCode)
                    }
                })
        }
    }
}