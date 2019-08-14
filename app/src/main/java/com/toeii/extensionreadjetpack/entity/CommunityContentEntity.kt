package com.toeii.extensionreadjetpack.entity

data class CommunityContentEntity(
    val adExist: Boolean,
    val count: Int,
    val itemList: List<CommunityItem>?,
    val nextPageUrl: String,
    val total: Int
)

data class CommunityItem(
    val `data`: CommunityData,
    val adIndex: Int,
    val id: Int,
    val tag: Any,
    val type: String
)

data class CommunityData(
    val adTrack: Any,
    val content: CommunityContent,
    val dataType: String,
    val header: CommunityHeader
)

data class CommunityContent(
    val `data`: CommunityDataX,
    val adIndex: Int,
    val id: Int,
    val tag: Any,
    val type: String
)

data class CommunityDataX(
    val addWatermark: Boolean,
    val area: String,
    val checkStatus: String,
    val city: String,
    val collected: Boolean,
    val consumption: CommunityConsumption,
    val cover: CommunityCover,
    val createTime: Long,
    val dataType: String,
    val description: String,
    val height: Int,
    val id: Int,
    val ifMock: Boolean,
    val latitude: Int,
    val library: String,
    val longitude: Int,
    val owner: CommunityOwner,
    val privateMessageActionUrl: Any,
    val recentOnceReply: Any,
    val releaseTime: Long,
    val resourceType: String,
    val selectedTime: Any,
    val status: Int,
    val tags: List<CommunityTag>,
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

data class CommunityTag(
    val actionUrl: String,
    val adTrack: Any,
    val alias: Any,
    val bgPicture: String,
    val childTagIdList: Any,
    val childTagList: Any,
    val communityIndex: Int,
    val desc: String,
    val headerImage: String,
    val id: Int,
    val ifShow: Boolean,
    val level: Int,
    val name: String,
    val parentId: Int,
    val recWeight: Int,
    val relationType: Int,
    val tagRecType: String,
    val tagStatus: String,
    val top: Int,
    val type: String
)

data class CommunityCover(
    val blurred: Any,
    val detail: String,
    val feed: String,
    val homepage: Any,
    val sharing: Any
)

data class CommunityConsumption(
    val collectionCount: Int,
    val playCount: Int,
    val replyCount: Int,
    val shareCount: Int
)

data class CommunityOwner(
    val actionUrl: String,
    val area: Any,
    val avatar: String,
    val bannedDate: Any,
    val bannedDays: Any,
    val birthday: Any,
    val city: Any,
    val country: Any,
    val cover: Any,
    val description: Any,
    val expert: Boolean,
    val followed: Boolean,
    val gender: Any,
    val ifPgc: Boolean,
    val job: Any,
    val library: String,
    val limitVideoOpen: Boolean,
    val nickname: String,
    val registDate: Long,
    val releaseDate: Long,
    val tagIds: Any,
    val uid: Int,
    val university: Any,
    val uploadStatus: String,
    val userMedalBeanList: Any,
    val userType: String,
    val username: String
)

data class CommunityHeader(
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