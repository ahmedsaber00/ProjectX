package io.android.ptt.domain.features.usermanagement.interactor

import io.android.ptt.domain.base.executor.PostExecutionThread
import io.android.ptt.domain.base.interactor.ObservableUseCase
import io.android.ptt.domain.features.usermanagement.model.User
import io.android.ptt.domain.features.usermanagement.repository.UserManagementRepository
import io.reactivex.Observable
import javax.inject.Inject

open class GetUser @Inject constructor(
    private val userManagementRepository: UserManagementRepository,
    postExecutionThread: PostExecutionThread
) : ObservableUseCase<User, Nothing?>(postExecutionThread) {

    public override fun buildUseCaseObservable(params: Nothing?): Observable<User> {
        return userManagementRepository.getUser()
    }

}