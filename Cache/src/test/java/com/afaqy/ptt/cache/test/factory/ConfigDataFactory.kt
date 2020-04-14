package com.afaqy.ptt.cache.test.factory

object ConfigDataFactory {

    fun makeCachedConfig(): Config {
        return Config(
            DataFactory.randomInt(),
            DataFactory.randomLong()
        )
    }

}