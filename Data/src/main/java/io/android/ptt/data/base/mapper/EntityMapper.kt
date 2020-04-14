package io.android.ptt.data.base.mapper

interface EntityMapper<E, D> {

    fun mapFromEntity(entity: E): D

    fun mapToEntity(domain: D): E

}