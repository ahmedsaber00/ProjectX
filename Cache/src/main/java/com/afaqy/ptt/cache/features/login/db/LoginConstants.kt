package com.afaqy.ptt.cache.features.login.db

object LoginConstants {

    const val TABLE_NAME = "login"

    const val COLUMN_LOGIN_ID = "login_id"

    const val QUERY_LOGIN= "SELECT * FROM $TABLE_NAME"

    const val DELETE_LOGIN = "DELETE FROM $TABLE_NAME"

}