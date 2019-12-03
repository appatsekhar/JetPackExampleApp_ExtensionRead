package com.toeii.extensionreadjetpack.ui.home.recommend

import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.toeii.extensionreadjetpack.common.CoroutineBus
import com.toeii.extensionreadjetpack.entity.RecommendBannerItem
import com.toeii.extensionreadjetpack.network.RetrofitManager
import com.toeii.extensionreadjetpack.common.safeLaunch
import com.toeii.extensionreadjetpack.config.ERAppConfig
import com.toeii.extensionreadjetpack.entity.EventMessage
import com.toeii.extensionreadjetpack.entity.HomeRecommendItemListBean
import kotlinx.coroutines.*

class RecommendRepository {

    suspend fun getHomeRecommendList(page: Int): List<HomeRecommendItemListBean>? = withContext(Dispatchers.IO) {
        val result = RetrofitManager.apiService.getHomeRecommendList(page.toString()).itemList
        val bannerData =  getHomeRecommendBannerList()
        if(bannerData!!.isNotEmpty()){
            val filterData = bannerData.filter {
                it?.data?.author != null
            }
            result[0].bannerData = filterData
        }
        result
    }

    suspend fun getHomeRecommendBannerList(): List<RecommendBannerItem>? = withContext(Dispatchers.IO) {
        val issueList = RetrofitManager.apiService.getHomeRecommendBannerList().issueList
        val result = ArrayList<RecommendBannerItem>()
        if(issueList.isNotEmpty()){
            issueList[0].itemList.forEach {
                result.add(it)
            }
        }
        result
    }

}

class RecommendDataSource(private val repository: RecommendRepository) :PageKeyedDataSource<Int, HomeRecommendItemListBean>(), CoroutineScope by MainScope(){

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, HomeRecommendItemListBean>) {
        safeLaunch {
            val data = repository.getHomeRecommendList(1)
            data?.let {
                callback.onResult(it, 1,2)
                CoroutineBus.post(
                    EventMessage(
                        RecommendFragment::class.java.name
                                + ERAppConfig.PAGE_DATA_INIT,
                        null)
                )
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, HomeRecommendItemListBean>) {
        safeLaunch {
            val data = repository.getHomeRecommendList(params.key)
            var isLoadStart = false
            if(!data.isNullOrEmpty()){
                if(data.size > ERAppConfig.PAGE_SIZE){
                    CoroutineBus.post(
                        EventMessage(
                            RecommendFragment::class.java.name
                                    + ERAppConfig.PAGE_DATA_LOAD_START,
                            null)
                    )
                    isLoadStart = true
                }
                data.let {
                    callback.onResult(it, params.key + 1)
                }
            }
            if(!isLoadStart){
                CoroutineBus.post(
                    EventMessage(
                        RecommendFragment::class.java.name
                                + ERAppConfig.PAGE_DATA_LOAD_END,
                        null)
                )
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, HomeRecommendItemListBean>) {

    }

    override fun invalidate() {
        super.invalidate()
        cancel()
    }

}

class RecommendDataSourceFactory(private val repository: RecommendRepository) :
    DataSource.Factory<Int, HomeRecommendItemListBean>() {
    override fun create(): DataSource<Int, HomeRecommendItemListBean> = RecommendDataSource(repository)
}

