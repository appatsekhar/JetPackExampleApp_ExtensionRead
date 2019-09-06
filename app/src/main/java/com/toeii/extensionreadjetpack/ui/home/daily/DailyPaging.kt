package com.toeii.extensionreadjetpack.ui.home.daily

import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.toeii.extensionreadjetpack.common.CoroutineBus
import com.toeii.extensionreadjetpack.common.safeLaunch
import com.toeii.extensionreadjetpack.config.ERAppConfig
import com.toeii.extensionreadjetpack.entity.EventMessage
import com.toeii.extensionreadjetpack.entity.QdailyResult
import com.toeii.extensionreadjetpack.network.RetrofitManager
import kotlinx.coroutines.*

class DailyRepository{

    suspend fun getHomeDailyList(page: Int): List<QdailyResult>? = withContext(Dispatchers.IO){
        val result = RetrofitManager.apiService.getHomeDailyList(ERAppConfig.PAGE_SIZE.toString(),page.toString()).results
        result
    }

}

class DailyDataSource(private val repository: DailyRepository) : PageKeyedDataSource<Int, QdailyResult>(), CoroutineScope by MainScope(){


    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, QdailyResult>) {
        safeLaunch{
            val result = repository.getHomeDailyList(1)
            result?.let {
                callback.onResult(it,1,2)
                CoroutineBus.post(EventMessage(ERAppConfig.HOME_DAILY_PAGE_DATA_INIT,null))
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, QdailyResult>) {
        safeLaunch{
            val result = repository.getHomeDailyList(params.key)
            result?.let {
                callback.onResult(it,params.key + 1)
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, QdailyResult>) {

    }

    override fun invalidate() {
        super.invalidate()
        cancel()
    }

}

class DailyDataSourceFactory(private val repository: DailyRepository) : DataSource.Factory<Int, QdailyResult>() {
    override fun create(): DataSource<Int, QdailyResult> = DailyDataSource(repository)
}
