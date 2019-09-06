package com.toeii.extensionreadjetpack.ui.home.recommend

import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.toeii.extensionreadjetpack.common.CoroutineBus
import com.toeii.extensionreadjetpack.entity.RecommendBannerItem
import com.toeii.extensionreadjetpack.entity.ViceResult
import com.toeii.extensionreadjetpack.network.RetrofitManager
import com.toeii.extensionreadjetpack.common.safeLaunch
import com.toeii.extensionreadjetpack.config.ERAppConfig
import com.toeii.extensionreadjetpack.entity.EventMessage
import kotlinx.coroutines.*

class RecommendRepository {

    suspend fun homeRecommendResult(page: Int): List<ViceResult>? = withContext(Dispatchers.IO) {
        val result = RetrofitManager.apiService.getHomeRecommendList(page.toString()).results
        val bannerData =  homeRecommendBannerResult()
        if(bannerData!!.isNotEmpty()){
            val filterData = bannerData.filter {
                it?.data?.author != null
            }
            result[0].bannerData = filterData
        }
        result
    }

    suspend fun homeRecommendBannerResult(): List<RecommendBannerItem>? = withContext(Dispatchers.IO) {
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

class RecommendDataSource(private val repository: RecommendRepository) :PageKeyedDataSource<Int, ViceResult>(), CoroutineScope by MainScope(){

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, ViceResult>) {
        safeLaunch {
            val data = repository.homeRecommendResult(1)
            data?.let {
                callback.onResult(it, 1,2)
                CoroutineBus.post(EventMessage(ERAppConfig.PAGE_DATA_INIT,null))
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ViceResult>) {
        safeLaunch {
            val data = repository.homeRecommendResult(params.key)
            if(!data.isNullOrEmpty()){
                data.let {
                    callback.onResult(it, params.key + 1)
                }
                if(data.size < ERAppConfig.PAGE_SIZE){
                    CoroutineBus.post(EventMessage(ERAppConfig.PAGE_DATA_LOAD_END,null))
                }else{
                    CoroutineBus.post(EventMessage(ERAppConfig.PAGE_DATA_LOAD_START,null))
                }
            }else{
                CoroutineBus.post(EventMessage(ERAppConfig.PAGE_DATA_LOAD_END,null))
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ViceResult>) {

    }

    override fun invalidate() {
        super.invalidate()
        cancel()
    }

}

class RecommendDataSourceFactory(private val repository: RecommendRepository) :
    DataSource.Factory<Int, ViceResult>() {
    override fun create(): DataSource<Int, ViceResult> = RecommendDataSource(repository)
}

