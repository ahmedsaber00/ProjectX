package com.afaqy.ptt.presentation.base.mapper

interface Mapper<out V, in D> {

    fun mapToView(type: D): V

}