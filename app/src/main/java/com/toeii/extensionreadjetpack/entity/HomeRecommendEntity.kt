package com.toeii.extensionreadjetpack.entity

data class HomeRecommendEntity(
    val adExist: Boolean,
    val count: Int,
    val itemList: List<RecommendItem>?,
    val nextPageUrl: String,
    val total: Int
)

data class RecommendItem(
    val `data`: RecommendData,
    val adIndex: Int,
    val id: Int,
    val tag: Any,
    val type: String
)

data class RecommendData(
    val actionUrl: String,
    val adTrack: Any,
    val autoPlay: Boolean,
    val dataType: String,
    val description: String,
    val header: RecommendHeader,
    val id: Int,
    val image: String,
    val label: RecommendLabel,
    val labelList: List<Any>,
    val shade: Boolean,
    val title: String
)

data class RecommendLabel(
    val card: String,
    val detail: Any,
    val text: String
)

data class RecommendHeader(
    val actionUrl: Any,
    val cover: Any,
    val description: Any,
    val font: Any,
    val icon: Any,
    val id: Int,
    val label: Any,
    val labelList: Any,
    val rightText: Any,
    val subTitle: Any,
    val subTitleFont: Any,
    val textAlign: String,
    val title: Any
)