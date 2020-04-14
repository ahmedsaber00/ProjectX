package io.android.ptt.data.features.login

import io.android.ptt.data.features.login.mapper.LoginMapper
import io.android.ptt.data.features.login.repository.LoginCache
import io.android.ptt.data.features.login.store.LoginDataStoreFactory
import io.android.ptt.domain.features.login.model.LoginModel
import io.android.ptt.domain.features.login.repository.LoginRepository
import io.reactivex.Observable
import javax.inject.Inject

class LoginDataRepository @Inject constructor(
    private val mapper: LoginMapper,
    private val cache: LoginCache,
    private val factory: LoginDataStoreFactory
) : LoginRepository {

    override fun login(username: String, password: String, imei: String, simSerial: String): Observable<LoginModel> {
        return factory.getDataStore(true, true).login(username, password, imei, simSerial)
            .toObservable().distinctUntilChanged().flatMap { user ->
                factory.getCacheDataStore()
                    .saveLogin(user)
                    .andThen(Observable.just(user))
            }
            .map {
                mapper.mapFromEntity(it)
            }
    }
}