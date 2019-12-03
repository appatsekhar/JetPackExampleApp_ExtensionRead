package com.toeii.extensionreadjetpack.ui.home.daily

import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.toeii.extensionreadjetpack.common.CoroutineBus
import com.toeii.extensionreadjetpack.common.safeLaunch
import com.toeii.extensionreadjetpack.config.ERAppConfig
import com.toeii.extensionreadjetpack.entity.EventMessage
import com.toeii.extensionreadjetpack.entity.HomeDailyItemListBean
import com.toeii.extensionreadjetpack.network.RetrofitManager
import kotlinx.coroutines.*

class DailyRepository{

    suspend fun getHomeDailyList(page: Int): List<HomeDailyItemListBean>? = withContext(Dispatchers.IO){
        val result = RetrofitManager.apiService.getHomeDailyList(System.currentTimeMillis().toString()).itemList
        result
    }

}

class DailyDataSource(private val repository: DailyRepository) : PageKeyedDataSource<Int, HomeDailyItemListBean>(), CoroutineScope by MainScope(){


    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, HomeDailyItemListBean>) {
        safeLaunch{
            val result = repository.getHomeDailyList(1)
            result?.let {
                callback.onResult(it,1,2)
                CoroutineBus.post(
                    EventMessage(
                        DailyFragment::class.java.name
                                + ERAppConfig.PAGE_DATA_INIT,
                        null)
                )
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, HomeDailyItemListBean>) {
        CoroutineBus.post(
            EventMessage(
                DailyFragment::class.java.name
                        + ERAppConfig.PAGE_DATA_LOAD_END,
                null)
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, HomeDailyItemListBean>) {

    }

    override fun invalidate() {
        super.invalidate()
        cancel()
    }

}

class DailyDataSourceFactory(private val repository: DailyRepository) : DataSource.Factory<Int, HomeDailyItemListBean>() {
    override fun create(): DataSource<Int, HomeDailyItemListBean> = DailyDataSource(repository)
}
