package com.toeii.extensionreadjetpack.entity

data class HomeRecommendEntity(
    val error: Boolean,
    val results: List<ViceResult>
)

data class ViceResult(
    val _id: String,
    val content: String,
    val cover: String,
    val crawled: Long,
    val created_at: String,
    val deleted: Boolean,
    val published_at: String,
    val raw: String,
    val site: ViceSite,
    val title: String,
    val uid: String,
    val updated_at: String,
    val url: String,
    var bannerData: List<RecommendBannerItem>
)

data class ViceSite(
    val cat_cn: String,
    val cat_en: String,
    val desc: String,
    val feed_id: String,
    val icon: String,
    val id: String,
    val name: String,
    val subscribers: Int,
    val title: String,
    val type: String,
    val updated_at: String,
    val url: String
)