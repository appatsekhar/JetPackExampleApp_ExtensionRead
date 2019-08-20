package com.toeii.extensionreadjetpack.ui.home.recommend

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.toeii.extensionreadjetpack.ERApplication
import com.toeii.extensionreadjetpack.entity.RecommendBannerItem
import com.toeii.extensionreadjetpack.entity.ViceResult
import com.toeii.extensionreadjetpack.util.safeLaunch
import org.jetbrains.anko.info

class RecommendViewModel(private val repository: RecommendRepository) : ViewModel() {

    var result: LiveData<PagedList<ViceResult>>? = null
    var bannerResult = MutableLiveData<List<RecommendBannerItem>>()

    fun fetchResult() {
        result = LivePagedListBuilder(
            RecommendDataSourceFactory(repository),
            PagedList.Config.Builder()
                .setPageSize(20)
                .setPrefetchDistance(10)
                .setInitialLoadSizeHint(20)
                .setEnablePlaceholders(false)
                .build()
        ).build()
    }

    fun fetchBannerResult() {
        viewModelScope.safeLaunch {
            bannerResult.value = repository.homeRecommendBannerResult()
        }
    }

}

class RecommendModelFactory(private val repository: RecommendRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RecommendViewModel(repository) as T
    }
}


