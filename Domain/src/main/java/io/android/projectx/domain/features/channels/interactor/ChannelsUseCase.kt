package io.android.projectx.domain.features.channels.interactor

import io.android.projectx.domain.base.executor.PostExecutionThread
import io.android.projectx.domain.base.interactor.ObservableUseCase
import io.android.projectx.domain.features.channels.model.ChannelModel
import io.android.projectx.domain.features.channels.repository.ChannelsRepository
import io.reactivex.Observable
import java.lang.IllegalArgumentException
import javax.inject.Inject

open class ChannelsUseCase @Inject constructor(
    private val channelsRepository: ChannelsRepository,
    postExecutionThread: PostExecutionThread
) : ObservableUseCase<List<ChannelModel>, ChannelsUseCase.Params?>(postExecutionThread) {

    override fun buildUseCaseObservable(params: Params?): Observable<List<ChannelModel>> {
        if (params == null) throw IllegalArgumentException("Params can't be null !!")
        return channelsRepository.getChannels(params.authorization,params.page)
    }

    data class Params constructor(val authorization: String,val page: Int) {
        companion object {
            fun forChannels(authorization: String,page: Int): Params {
                return Params(authorization,page)
            }
        }
    }

}