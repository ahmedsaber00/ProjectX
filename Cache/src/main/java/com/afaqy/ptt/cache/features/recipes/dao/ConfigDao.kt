package com.afaqy.ptt.cache.features.recipes.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.afaqy.ptt.cache.features.recipes.db.ConfigConstants
import com.afaqy.ptt.cache.features.recipes.model.Config
import io.reactivex.Single

@Dao
abstract class ConfigDao {

    @Query(ConfigConstants.QUERY_CONFIG)
    abstract fun getConfig(): Single<Config>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertConfig(config: Config)

}