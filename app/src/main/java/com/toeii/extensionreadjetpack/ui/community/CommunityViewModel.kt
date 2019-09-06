package com.toeii.extensionreadjetpack.ui.community


import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.toeii.extensionreadjetpack.config.ERAppConfig
import com.toeii.extensionreadjetpack.common.safeLaunch
import com.toeii.extensionreadjetpack.entity.OpenEyeItemResult
import com.toeii.extensionreadjetpack.entity.OpenEyeResult

class CommunityViewModel(private val repository: CommunityRepository) : ViewModel() {

    var result: LiveData<PagedList<OpenEyeResult>>? = null
    var itemResult = MutableLiveData<List<OpenEyeItemResult>>()

    fun fetchResult(key: String) {
        result = LivePagedListBuilder(
            CommunityDataSourceFactory(repository,key),
            PagedList.Config.Builder()
                .setPageSize(ERAppConfig.PAGE_SIZE)
                .setEnablePlaceholders(ERAppConfig.ENABLE_PLACEHOLDERS)
                .setInitialLoadSizeHint(ERAppConfig.PAGE_SIZE_HINT)
                .setPrefetchDistance((ERAppConfig.PAGE_SIZE/2))
                .build()
        ).build()
    }

    fun fetchItemResult() {
        viewModelScope.safeLaunch {
            itemResult.value = repository.getCommunityItemList()
        }
    }

}

class CommunityModelFactory(private val repository: CommunityRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = CommunityViewModel(repository) as T

}


