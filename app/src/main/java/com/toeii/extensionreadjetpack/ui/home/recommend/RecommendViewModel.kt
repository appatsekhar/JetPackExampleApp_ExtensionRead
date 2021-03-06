package com.toeii.extensionreadjetpack.ui.home.recommend

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.toeii.extensionreadjetpack.config.ERAppConfig
import com.toeii.extensionreadjetpack.entity.RecommendBannerItem
import com.toeii.extensionreadjetpack.common.safeLaunch
import com.toeii.extensionreadjetpack.entity.HomeRecommendItemListBean

class RecommendViewModel(private val repository: RecommendRepository) : ViewModel() {

    var result: LiveData<PagedList<HomeRecommendItemListBean>>? = null
    var bannerResult = MutableLiveData<List<RecommendBannerItem>>()

    fun fetchResult() {
        result = LivePagedListBuilder(
            RecommendDataSourceFactory(repository),
            PagedList.Config.Builder()
                .setPageSize(ERAppConfig.PAGE_SIZE)
                .setEnablePlaceholders(ERAppConfig.ENABLE_PLACEHOLDERS)
                .setInitialLoadSizeHint(ERAppConfig.PAGE_SIZE_HINT)
                .setPrefetchDistance((ERAppConfig.PAGE_SIZE/2))
                .build()
        ).build()
    }

    fun fetchBannerResult() {
        viewModelScope.safeLaunch {
            bannerResult.value = repository.getHomeRecommendBannerList()
        }
    }

}

class RecommendModelFactory(private val repository: RecommendRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = RecommendViewModel(repository) as T

}


