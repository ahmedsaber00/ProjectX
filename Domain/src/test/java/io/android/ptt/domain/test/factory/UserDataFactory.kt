package io.android.ptt.domain.test.factory

import io.android.ptt.domain.features.usermanagement.model.User

object UserDataFactory {

    fun makeUser(): User {
        return User(
            DataFactory.uniqueId(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomBoolean()
        )
    }

}