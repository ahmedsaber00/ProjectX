package io.android.projectx.cache.features.login.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.android.projectx.cache.features.login.db.LoginConstants

@Entity(tableName = LoginConstants.TABLE_NAME)
data class CachedLogin(
    @PrimaryKey
    @ColumnInfo(name = LoginConstants.COLUMN_LOGIN_ID)
    val accessToken: String
)