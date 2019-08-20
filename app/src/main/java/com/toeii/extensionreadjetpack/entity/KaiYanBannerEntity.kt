package com.toeii.extensionreadjetpack.entity

import com.stx.xhb.androidx.entity.SimpleBannerInfo

data class HomeRecommendBannerEntity(
    val dialog: Any,
    val issueList: List<RecommendBannerIssue>,
    val newestIssueType: String,
    val nextPageUrl: String,
    val nextPublishTime: Long
)

data class RecommendBannerIssue(
    val count: Int,
    val date: Long,
    val itemList: List<RecommendBannerItem>,
    val publishTime: Long,
    val releaseTime: Long,
    val type: String
)

data class RecommendBannerItem(
    val `data`: RecommendBannerData,
    val adIndex: Int,
    val id: Int,
    val tag: Any,
    val type: String
): SimpleBannerInfo(){
    override fun getXBannerUrl(): Any {
        return `data`
    }
}

data class RecommendBannerData(
    val ad: Boolean,
    val adTrack: Any,
    val author: RecommendBannerAuthor,
    val campaign: Any,
    val category: String,
    val collected: Boolean,
    val consumption: Consumption,
    val cover: RecommendBannerCover,
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
    val playInfo: List<RecommendBannerPlayInfo>,
    val playUrl: String,
    val played: Boolean,
    val playlists: Any,
    val promotion: Any,
    val provider: RecommendBannerProvider,
    val releaseTime: Long,
    val remark: String,
    val resourceType: String,
    val searchWeight: Int,
    val shareAdTrack: Any,
    val slogan: Any,
    val src: Any,
    val subtitles: List<Any>,
    val tags: List<Tag>,
    val thumbPlayUrl: Any,
    val title: String,
    val titlePgc: Any,
    val type: String,
    val waterMarks: Any,
    val webAdTrack: Any,
    val webUrl: RecommendBannerWebUrl
)

data class RecommendBannerCover(
    val blurred: String,
    val detail: String,
    val feed: String,
    val homepage: String,
    val sharing: Any
)

data class RecommendBannerAuthor(
    val adTrack: Any,
    val approvedNotReadyVideoCount: Int,
    val description: String,
    val expert: Boolean,
    val follow: RecommendBannerFollow,
    val icon: String,
    val id: Int,
    val ifPgc: Boolean,
    val latestReleaseTime: Long,
    val link: String,
    val name: String,
    val recSort: Int,
    val shield: RecommendBannerShield,
    val videoNum: Int
)

data class RecommendBannerFollow(
    val followed: Boolean,
    val itemId: Int,
    val itemType: String
)

data class RecommendBannerShield(
    val itemId: Int,
    val itemType: String,
    val shielded: Boolean
)

data class RecommendBannerProvider(
    val alias: String,
    val icon: String,
    val name: String
)

data class RecommendBannerWebUrl(
    val forWeibo: String,
    val raw: String
)

data class RecommendBannerTag(
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

data class RecommendBannerPlayInfo(
    val height: Int,
    val name: String,
    val type: String,
    val url: String,
    val urlList: List<RecommendBannerUrl>,
    val width: Int
)

data class RecommendBannerUrl(
    val name: String,
    val size: Int,
    val url: String
)

data class RecommendBannerConsumption(
    val collectionCount: Int,
    val replyCount: Int,
    val shareCount: Int
)