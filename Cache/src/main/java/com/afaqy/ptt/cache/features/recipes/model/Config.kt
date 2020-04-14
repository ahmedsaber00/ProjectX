package com.afaqy.ptt.cache.features.recipes.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.afaqy.ptt.cache.features.recipes.db.ConfigConstants

@Entity(tableName = ConfigConstants.TABLE_NAME)
data class Config(
    @PrimaryKey
    var id: Int = 17,
    var lastCacheTime: Long)
