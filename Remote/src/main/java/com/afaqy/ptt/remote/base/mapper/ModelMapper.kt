package com.afaqy.ptt.remote.base.mapper

interface ModelMapper<in M, out E> {

    fun mapFromModel(model: M): E

}