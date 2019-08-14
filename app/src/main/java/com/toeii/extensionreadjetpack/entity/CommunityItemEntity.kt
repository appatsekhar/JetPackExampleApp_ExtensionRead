package com.toeii.extensionreadjetpack.entity

data class CommunityItemEntity(
    val adExist: Boolean,
    val count: Int,
    val itemList: List<Item>,
    val nextPageUrl: String,
    val total: Int
)

data class Item(
    val `data`: Data,
    val adIndex: Int,
    val id: Int,
    val tag: Any,
    val type: String
)

data class Data(
    val adTrack: Any,
    val content: Content,
    val dataType: String,
    val header: Header
)

data class Header(
    val actionUrl: String,
    val followType: String,
    val icon: String,
    val iconType: String,
    val id: Int,
    val issuerName: String,
    val labelList: Any,
    val showHateVideo: Boolean,
    val tagId: Int,
    val tagName: Any,
    val time: Long,
    val topShow: Boolean
)

data class Content(
    val `data`: DataX,
    val adIndex: Int,
    val id: Int,
    val tag: Any,
    val type: String
)

data class DataX(
    val addWatermark: Boolean,
    val area: String,
    val checkStatus: String,
    val city: String,
    val collected: Boolean,
    val consumption: Consumption,
    val cover: Cover,
    val createTime: Long,
    val dataType: String,
    val description: String,
    val height: Int,
    val id: Int,
    val ifMock: Boolean,
    val latitude: Int,
    val library: String,
    val longitude: Int,
    val owner: Owner,
    val privateMessageActionUrl: Any,
    val recentOnceReply: Any,
    val releaseTime: Long,
    val resourceType: String,
    val selectedTime: Any,
    val status: Int,
    val tags: List<Tag>,
    val title: String,
    val uid: Int,
    val updateTime: Long,
    val url: String,
    val urls: List<String>,
    val urlsWithWatermark: List<String>,
    val validateResult: String,
    val validateStatus: String,
    val width: Int
)

data class Owner(
    val actionUrl: String,
    val area: Any,
    val avatar: String,
    val birthday: Long,
    val city: Any,
    val country: String,
    val cover: String,
    val description: String,
    val expert: Boolean,
    val followed: Boolean,
    val gender: String,
    val ifPgc: Boolean,
    val job: String,
    val library: String,
    val limitVideoOpen: Boolean,
    val nickname: String,
    val registDate: Long,
    val releaseDate: Long,
    val uid: Int,
    val university: Any,
    val userType: String
)

data class Consumption(
    val collectionCount: Int,
    val replyCount: Int,
    val shareCount: Int
)

data class Cover(
    val blurred: Any,
    val detail: String,
    val feed: String,
    val homepage: Any,
    val sharing: Any
)

data class Tag(
    val actionUrl: String,
    val adTrack: Any,
    val bgPicture: String,
    val childTagIdList: Any,
    val childTagList: Any,
    val communityIndex: Int,
    val desc: String,
    val headerImage: String,
    val id: Int,
    val name: String,
    val tagRecType: String
)