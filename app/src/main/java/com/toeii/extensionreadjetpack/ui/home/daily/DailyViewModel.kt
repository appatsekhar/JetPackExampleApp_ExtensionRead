package com.toeii.extensionreadjetpack.ui.home.daily

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.toeii.extensionreadjetpack.config.ERAppConfig
import com.toeii.extensionreadjetpack.entity.HomeDailyItemListBean

class DailyViewModel(private val repository: DailyRepository) : ViewModel() {

    var result: LiveData<PagedList<HomeDailyItemListBean>>? = null

    fun fetchResult() {
        result = LivePagedListBuilder(
            DailyDataSourceFactory(repository),
            PagedList.Config.Builder()
                .setPageSize(ERAppConfig.PAGE_SIZE)
                .setEnablePlaceholders(ERAppConfig.ENABLE_PLACEHOLDERS)
                .setInitialLoadSizeHint(ERAppConfig.PAGE_SIZE_HINT)
                .setPrefetchDistance((ERAppConfig.PAGE_SIZE/2))
                .build()
        ).build()
    }

}


class DailyModelFactory(private val repository: DailyRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = DailyViewModel(repository) as T

}