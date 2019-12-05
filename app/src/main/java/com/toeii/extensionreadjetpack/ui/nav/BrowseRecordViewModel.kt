package com.toeii.extensionreadjetpack.ui.nav
import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.toeii.extensionreadjetpack.entity.BrowseRecordEntity

class BrowseRecordViewModel(private val repository: BrowseRecordRepository) : ViewModel() {

    var result: LiveData<PagedList<BrowseRecordEntity>>? = null

    fun fetchBrowseRecordResult() {
        result = LivePagedListBuilder(
            BrowseRecordSourceFactory(repository),
            PagedList.Config.Builder().build()
        ).build()
    }

}

class BrowseRecordModelFactory(private val repository: BrowseRecordRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = BrowseRecordViewModel(repository) as T

}


