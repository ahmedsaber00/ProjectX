package com.afaqy.ptt.cache

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.afaqy.ptt.cache.features.login.dao.CachedLoginDao
import com.afaqy.ptt.cache.features.login.model.CachedLogin
import com.afaqy.ptt.cache.features.recipes.dao.CachedRecipesDao
import com.afaqy.ptt.cache.features.recipes.dao.ConfigDao
import com.afaqy.ptt.cache.features.recipes.model.CachedRecipe
import com.afaqy.ptt.cache.features.recipes.model.Config
import com.afaqy.ptt.cache.features.restaurants.dao.CachedRestaurantDao
import com.afaqy.ptt.cache.features.restaurants.model.CachedRestaurant
import javax.inject.Inject

@Database(
    entities = [CachedLogin::class,CachedRecipe::class,
        Config::class,
        CachedRestaurant::class],
    version = 1
)
abstract class AppDatabase @Inject constructor() : RoomDatabase() {

    abstract fun cachedLoginDao(): CachedLoginDao
    abstract fun cachedRecipesDao(): CachedRecipesDao
    abstract fun configDao(): ConfigDao
    abstract fun cachedRestaurantDao(): CachedRestaurantDao

    companion object {

        private var INSTANCE: AppDatabase? = null
        private val lock = Any()

        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(lock) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java, "app.db"
                        )
                            .build()
                    }
                    return INSTANCE as AppDatabase
                }
            }
            return INSTANCE as AppDatabase
        }
    }

}