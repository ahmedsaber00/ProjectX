package io.android.projectx.data.features.login

import io.android.projectx.data.features.login.mapper.LoginMapper
import io.android.projectx.data.features.login.repository.LoginCache
import io.android.projectx.data.features.login.store.LoginDataStoreFactory
import io.android.projectx.domain.features.login.model.LoginModel
import io.android.projectx.domain.features.login.repository.LoginRepository
import io.reactivex.Observable
import javax.inject.Inject

class LoginDataRepository @Inject constructor(
    private val mapper: LoginMapper,
    private val cache: LoginCache,
    private val factory: LoginDataStoreFactory
) : LoginRepository {

    override fun login(username: String, password: String, imei: String): Observable<LoginModel> {
        return factory.getDataStore(true, true).login(username, password, imei)
            .toObservable().distinctUntilChanged().flatMap { recipes ->
                factory.getCacheDataStore()
                    .saveLogin(recipes)
                    .andThen(Observable.just(recipes))
            }
            .map {
                mapper.mapFromEntity(it)
            }
    }
}