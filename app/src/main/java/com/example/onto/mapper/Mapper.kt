package com.example.onto.mapper

interface Mapper<T, E, D> {
    fun mapFromEntity(data: E): T
    fun mapToEntity(data: T): E
    fun mapFromDomain(data: D): T
    fun mapToDomain(data: T): D
}