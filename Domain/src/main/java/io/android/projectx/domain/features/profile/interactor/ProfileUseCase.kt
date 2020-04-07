package io.android.projectx.domain.features.profile.interactor

import io.android.projectx.domain.base.executor.PostExecutionThread
import io.android.projectx.domain.base.interactor.ObservableUseCase
import io.android.projectx.domain.features.profile.model.ProfileModel
import io.android.projectx.domain.features.profile.repository.ProfileRepository
import io.reactivex.Observable
import java.lang.IllegalArgumentException
import javax.inject.Inject

open class ProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository,
    postExecutionThread: PostExecutionThread
) : ObservableUseCase<ProfileModel, ProfileUseCase.Params?>(postExecutionThread) {

    override fun buildUseCaseObservable(params: Params?): Observable<ProfileModel> {
        if (params == null) throw IllegalArgumentException("Params can't be null !!")
        return profileRepository.getProfile(params.authorization,params.page)
    }

    data class Params constructor(val authorization: String,val page: String) {
        companion object {
            fun forChannels(authorization: String,page: String): Params {
                return Params(authorization,page)
            }
        }
    }

}