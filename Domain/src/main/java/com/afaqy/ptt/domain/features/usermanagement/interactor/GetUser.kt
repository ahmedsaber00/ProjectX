package com.afaqy.ptt.domain.features.usermanagement.interactor

import com.afaqy.ptt.domain.base.executor.PostExecutionThread
import com.afaqy.ptt.domain.base.interactor.ObservableUseCase
import com.afaqy.ptt.domain.features.usermanagement.model.User
import com.afaqy.ptt.domain.features.usermanagement.repository.UserManagementRepository
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