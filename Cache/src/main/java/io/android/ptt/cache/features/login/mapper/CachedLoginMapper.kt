package io.android.ptt.cache.features.login.mapper

import io.android.ptt.cache.base.mapper.CacheMapper
import io.android.ptt.cache.features.login.model.CachedLogin
import io.android.ptt.data.features.login.model.LoginEntity
import javax.inject.Inject

class CachedLoginMapper @Inject constructor() :
    CacheMapper<CachedLogin, LoginEntity> {

    override fun mapFromCached(type: CachedLogin): LoginEntity {
        return LoginEntity(
            type.accessToken
        )
    }

    override fun mapToCached(type: LoginEntity): CachedLogin {
        return CachedLogin(
            type.accessToken
        )
    }

}