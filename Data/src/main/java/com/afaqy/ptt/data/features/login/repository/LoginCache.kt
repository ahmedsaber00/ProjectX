package com.afaqy.ptt.data.features.login.repository

import com.afaqy.ptt.data.features.login.model.LoginEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface LoginCache {

    fun login(): Flowable<LoginEntity>

    fun clearLogin(): Completable

    fun saveLogin(recipes: LoginEntity): Completable

    fun areLoginCached(): Single<Boolean>

    fun setLastCacheTime(lastCache: Long): Completable

    fun isLoginCacheExpired(): Single<Boolean>

}