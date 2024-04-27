package ru.webant.domain.entities

data class FavoriteEntity(
    val id: String,
    val image: ImageEntity,
    val imageId: String
): BaseEntity