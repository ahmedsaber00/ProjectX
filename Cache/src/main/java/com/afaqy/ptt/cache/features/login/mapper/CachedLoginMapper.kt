package com.afaqy.ptt.cache.features.login.mapper

import com.afaqy.ptt.cache.base.mapper.CacheMapper
import com.afaqy.ptt.cache.features.login.model.CachedLogin
import com.afaqy.ptt.data.features.login.model.LoginEntity
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