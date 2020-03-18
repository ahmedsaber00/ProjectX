package io.android.projectx.presentation.features.channels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.android.projectx.domain.features.channels.interactor.ChannelsUseCase
import io.android.projectx.domain.features.channels.model.ChannelModel
import io.android.projectx.presentation.base.mapper.ChannelsViewMapper
import io.android.projectx.presentation.base.model.ChannelsView
import io.android.projectx.presentation.base.state.Resource
import io.android.projectx.presentation.base.state.ResourceState
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

class ChannelsViewModel @Inject constructor(
    private val getChannelsUseCase: ChannelsUseCase?,
    private val mapper: ChannelsViewMapper
) : ViewModel() {

    private val liveData: MutableLiveData<Resource<List<ChannelsView>>> = MutableLiveData()

    override fun onCleared() {
        getChannelsUseCase?.dispose()
        super.onCleared()
    }

    fun getChannels(): LiveData<Resource<List<ChannelsView>>> {
        return liveData
    }

    fun fetchChannels(authorization: String,page:Int) {
        liveData.postValue(Resource(ResourceState.LOADING, null, null))
        getChannelsUseCase?.execute(ChannelsSubscriber(),ChannelsUseCase.Params(authorization,page))
    }

    inner class ChannelsSubscriber : DisposableObserver<List<ChannelModel>>() {

        override fun onNext(t: List<ChannelModel>) {
            liveData.postValue(
                Resource(
                    ResourceState.SUCCESS,
                    t.map { mapper.mapToView(it) },
                    null
                )
            )
        }

        override fun onComplete() {}

        override fun onError(e: Throwable) {
            liveData.postValue(Resource(ResourceState.ERROR, null, e.localizedMessage))
        }

    }

}