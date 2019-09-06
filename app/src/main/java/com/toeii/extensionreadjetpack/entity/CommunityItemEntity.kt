package com.toeii.extensionreadjetpack.entity

data class CommunityItemEntity(
    val adExist: Boolean,
    val count: Int,
    val itemList: List<CommunityItem>,
    val nextPageUrl: String,
    val total: Int
)

data class CommunityItem(
    val `data`: CommunityItemData,
    val adIndex: Int,
    val id: Int,
    val tag: Any,
    val type: String
)

data class CommunityItemData(
    val adTrack: Any,
    val count: Int,
    val dataType: String,
    val footer: Any,
    val header: CommunityItemHeader,
    val itemList: List<OpenEyeItemResult>
)

data class OpenEyeItemResult(
    val `data`: CommunityItemResult,
    val adIndex: Int,
    val id: Int,
    val tag: Any,
    val type: String
)

data class CommunityItemResult(
    val actionUrl: String,
    val bgPicture: String,
    val dataType: String,
    val description: String,
    val ifTagIndex: Boolean,
    val seenCount: Int,
    val tagFollowCount: Int,
    val tagId: Int,
    val tagName: String
)

data class CommunityItemHeader(
    val actionUrl: String,
    val cover: Any,
    val font: String,
    val id: Int,
    val label: Any,
    val labelList: Any,
    val rightText: String,
    val subTitle: Any,
    val subTitleFont: Any,
    val textAlign: String,
    val title: String
)