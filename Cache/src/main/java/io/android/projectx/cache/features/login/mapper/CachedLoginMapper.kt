package io.android.projectx.cache.features.login.mapper

import io.android.projectx.cache.base.mapper.CacheMapper
import io.android.projectx.cache.features.login.model.CachedLogin
import io.android.projectx.data.features.login.model.LoginEntity
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