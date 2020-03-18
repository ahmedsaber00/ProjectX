package io.android.projectx.cache.features.login

import io.android.projectx.cache.AppDatabase
import io.android.projectx.cache.features.login.mapper.CachedLoginMapper
import io.android.projectx.data.features.login.model.LoginEntity
import io.android.projectx.data.features.login.repository.LoginCache
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class LoginCacheImpl @Inject constructor(
    private val appDatabase: AppDatabase,
    private val caschedLoginMapper: CachedLoginMapper,
    private val mapper: CachedLoginMapper
) : LoginCache {
    override fun login(): Flowable<LoginEntity> {
        return appDatabase.cachedLoginDao().getLogin().map {
            caschedLoginMapper.mapFromCached(it)
        }
    }

    override fun setLastCacheTime(lastCache: Long): Completable {
        return Completable.defer {
            appDatabase.cachedLoginDao().insertLogin(
                caschedLoginMapper.mapToCached(LoginEntity("")) )
            Completable.complete()
        }
    }

    override fun clearLogin(): Completable {
        return Completable.defer {
            appDatabase.cachedLoginDao().deleteLogin()
            Completable.complete()
        }
    }

    override fun saveLogin(login: LoginEntity): Completable {
        return Completable.defer {
            appDatabase.cachedLoginDao().insertLogin(
                 caschedLoginMapper.mapToCached(login) )
            Completable.complete()
        }
    }

    override fun areLoginCached(): Single<Boolean> {
        return appDatabase.cachedLoginDao().getLogin().isEmpty
            .map {
                !it
            }
    }

    override fun isLoginCacheExpired(): Single<Boolean> {
        return appDatabase.cachedLoginDao().getLogin().isEmpty
            .map {
                !it
            }
    }
}