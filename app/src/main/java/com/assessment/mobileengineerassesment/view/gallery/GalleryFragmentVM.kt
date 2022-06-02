package com.assessment.mobileengineerassesment.view.gallery

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.assessment.base.event.Event
import com.assessment.base.viewmodel.BaseViewModel
import com.assessment.mobileengineerassesment.model.ImageResponse
import com.assessment.mobileengineerassesment.model.ImageSearchQuery
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class GalleryFragmentVM @Inject constructor(
    private val imageListRepository: ImageListRepository,
) : BaseViewModel() {
    private val filterData = MutableStateFlow(ImageSearchQuery())

    @ExperimentalCoroutinesApi
    val imageDataList: Flow<PagingData<ImageResponse>> = filterData.flatMapLatest {
        imageListRepository.getImageFromRepository(it).cachedIn(viewModelScope)
    }

    fun submitOrderList(orderBy: String?) {
        filterData.value = ImageSearchQuery(orderBy)
    }

    fun navigateToImageDetails(imageResponse: ImageResponse?) {
        imageResponse?.let {
            _navigateToDestination.postValue(Event(Pair("", imageResponse)))
        }
    }
}
