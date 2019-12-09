package com.toeii.extensionreadjetpack.ui.about.history
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.toeii.extensionreadjetpack.ERApplication
import com.toeii.extensionreadjetpack.common.safeLaunch
import com.toeii.extensionreadjetpack.entity.BrowseRecordEntity
import kotlinx.coroutines.*
import java.util.ArrayList

class BrowseRecordRepository {

    suspend fun getBrowseRecordList(): List<BrowseRecordEntity>? = withContext(Dispatchers.IO) {
        val result = ERApplication.db.browseRecordDao().getAll()
        val datas = ArrayList<BrowseRecordEntity>()
        if(!result.isNullOrEmpty()){
            result.forEach {
                val bean = BrowseRecordEntity(it.uid,it.pointId,it.title,it.content,it.url,it.image)
                datas.add(bean)
            }
        }
        datas
    }


}

class BrowseRecordDataSource(private val repository: BrowseRecordRepository) :PageKeyedDataSource<Int, BrowseRecordEntity>(), CoroutineScope by MainScope(){

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, BrowseRecordEntity>) {
        safeLaunch {
            val data = repository.getBrowseRecordList()
            data?.let {
                callback.onResult(it, 0,1)
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, BrowseRecordEntity>) {

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, BrowseRecordEntity>) {

    }

    override fun invalidate() {
        super.invalidate()
        cancel()
    }

}

class BrowseRecordSourceFactory(private val repository: BrowseRecordRepository) :
    DataSource.Factory<Int, BrowseRecordEntity>() {
    override fun create(): DataSource<Int, BrowseRecordEntity> =
        BrowseRecordDataSource(repository)
}

