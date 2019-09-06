package com.toeii.extensionreadjetpack.ui.community


import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.toeii.extensionreadjetpack.common.CoroutineBus
import com.toeii.extensionreadjetpack.common.safeLaunch
import com.toeii.extensionreadjetpack.config.ERAppConfig
import com.toeii.extensionreadjetpack.entity.*
import com.toeii.extensionreadjetpack.network.RetrofitManager
import kotlinx.coroutines.*

class CommunityRepository{

    suspend fun getCommunityItemList(): List<OpenEyeItemResult>? = withContext(Dispatchers.IO){
        val result = RetrofitManager.apiService.getCommunityItemList().itemList[0].data.itemList
        result
    }

    suspend fun getCommunityContentList(key: String,start: Int): List<OpenEyeResult>? = withContext(Dispatchers.IO){
        val result = RetrofitManager.apiService.getCommunityContentList(key,start,ERAppConfig.PAGE_SIZE).itemList
        result
    }

}

class  CommunityDataSource(private val repository: CommunityRepository,private val key: String) : PageKeyedDataSource<Int, OpenEyeResult>(), CoroutineScope by MainScope(){


    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, OpenEyeResult>) {
        safeLaunch{
            val result = repository.getCommunityContentList(key,0)
            result?.let {
                callback.onResult(it,0,1)
                CoroutineBus.post(EventMessage(ERAppConfig.HOME_DAILY_PAGE_DATA_INIT,null))
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, OpenEyeResult>) {
        safeLaunch{
            val result = repository.getCommunityContentList(key,params.key)
            result?.let {
                callback.onResult(it,params.key + 1)
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, OpenEyeResult>) {

    }

    override fun invalidate() {
        super.invalidate()
        cancel()
    }

}

class  CommunityDataSourceFactory(private val repository:  CommunityRepository,private val key: String) : DataSource.Factory<Int, OpenEyeResult>() {
    override fun create(): DataSource<Int, OpenEyeResult> =  CommunityDataSource(repository,key)
}
