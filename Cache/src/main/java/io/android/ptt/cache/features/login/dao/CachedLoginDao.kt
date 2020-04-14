package io.android.ptt.cache.features.login.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.android.ptt.cache.features.login.db.LoginConstants.DELETE_LOGIN
import io.android.ptt.cache.features.login.db.LoginConstants.QUERY_LOGIN
import io.android.ptt.cache.features.login.model.CachedLogin
import io.reactivex.Flowable

@Dao
abstract class CachedLoginDao {

    @Query(QUERY_LOGIN)
    @JvmSuppressWildcards
    abstract fun getLogin(): Flowable<CachedLogin>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    abstract fun insertLogin(logins: CachedLogin)

    @Query(DELETE_LOGIN)
    abstract fun deleteLogin()

}