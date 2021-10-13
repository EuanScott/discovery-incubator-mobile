package com.example.discoveryincubator.models

data class Issue(
    val id: Long,
    val title: String,
    val description: String,
    val seriesNumber: Long,
    val publicationDate: String,
    //val publisherId: Long,
    val publisher: String,
    val creators: List<Creators>,
    val stock: List<Stock>,
    val thumbnail: Thumbnail,
    val images: List<Image>
)
