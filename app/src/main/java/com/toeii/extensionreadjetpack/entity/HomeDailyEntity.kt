package com.toeii.extensionreadjetpack.entity

data class HomeDailyEntity(
    val adExist: Boolean,
    val count: Int,
    val itemList: List<DailyItem>?,
    val nextPageUrl: String,
    val total: Int
)

data class DailyItem(
    val `data`: DailyData,
    val adIndex: Int,
    val id: Int,
    val tag: Any,
    val type: String
)

data class DailyData(
    val adTrack: Any,
    val content: DailyContent,
    val dataType: String,
    val header: DailyHeader
)

data class DailyHeader(
    val actionUrl: String,
    val cover: Any,
    val description: String,
    val font: Any,
    val icon: String,
    val iconType: String,
    val id: Int,
    val label: Any,
    val labelList: Any,
    val rightText: Any,
    val showHateVideo: Boolean,
    val subTitle: Any,
    val subTitleFont: Any,
    val textAlign: String,
    val time: Long,
    val title: String
)

data class DailyContent(
    val `data`: DailyDataX,
    val adIndex: Int,
    val id: Int,
    val tag: Any,
    val type: String
)

data class DailyDataX(
    val ad: Boolean,
    val adTrack: Any,
    val author: DailyAuthor,
    val campaign: Any,
    val category: String,
    val collected: Boolean,
    val consumption: DailyConsumption,
    val cover: DailyCover,
    val dataType: String,
    val date: Long,
    val description: String,
    val descriptionEditor: String,
    val descriptionPgc: Any,
    val duration: Int,
    val favoriteAdTrack: Any,
    val id: Int,
    val idx: Int,
    val ifLimitVideo: Boolean,
    val label: Any,
    val labelList: List<Any>,
    val lastViewTime: Any,
    val library: String,
    val playInfo: List<DailyPlayInfo>,
    val playUrl: String,
    val played: Boolean,
    val playlists: Any,
    val promotion: Any,
    val provider: DailyProvider,
    val releaseTime: Long,
    val remark: String,
    val resourceType: String,
    val searchWeight: Int,
    val shareAdTrack: Any,
    val slogan: Any,
    val src: Any,
    val subtitles: List<Any>,
    val tags: List<DailyTag>,
    val thumbPlayUrl: Any,
    val title: String,
    val titlePgc: Any,
    val type: String,
    val waterMarks: Any,
    val webAdTrack: Any,
    val webUrl: DailyWebUrl
)

data class DailyConsumption(
    val collectionCount: Int,
    val replyCount: Int,
    val shareCount: Int
)

data class DailyProvider(
    val alias: String,
    val icon: String,
    val name: String
)

data class DailyCover(
    val blurred: String,
    val detail: String,
    val feed: String,
    val homepage: String,
    val sharing: Any
)

data class DailyAuthor(
    val adTrack: Any,
    val approvedNotReadyVideoCount: Int,
    val description: String,
    val expert: Boolean,
    val follow: DailyFollow,
    val icon: String,
    val id: Int,
    val ifPgc: Boolean,
    val latestReleaseTime: Long,
    val link: String,
    val name: String,
    val recSort: Int,
    val shield: DailyShield,
    val videoNum: Int
)

data class DailyFollow(
    val followed: Boolean,
    val itemId: Int,
    val itemType: String
)

data class DailyShield(
    val itemId: Int,
    val itemType: String,
    val shielded: Boolean
)

data class DailyWebUrl(
    val forWeibo: String,
    val raw: String
)

data class DailyTag(
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

data class DailyPlayInfo(
    val height: Int,
    val name: String,
    val type: String,
    val url: String,
    val urlList: List<DailyUrl>,
    val width: Int
)

data class DailyUrl(
    val name: String,
    val size: Int,
    val url: String
)