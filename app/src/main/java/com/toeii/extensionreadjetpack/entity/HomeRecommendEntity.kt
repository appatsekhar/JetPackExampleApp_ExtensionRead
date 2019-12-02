package com.toeii.extensionreadjetpack.entity

data class HomeRecommendEntity(val itemList: List<HomeRecommendItemListBean>)

data class HomeRecommendItemListBean(val type: String,
                        val data: HomeRecommendDataBean,
                        var tag: Any?,
                        val id: Int,
                        val adIndex: Int,
                         var bannerData: List<RecommendBannerItem> )


data class HomeRecommendDataBean(val dataType: String,
                    val header: HomeRecommendHeaderBean,
                    val content: HomeRecommendContentBean,
                    val adTrack: List<Any?>,
                    val id: Int,
                    val title: String,
                    val text: String,
                    val description: String,
                    val library: String,
                    val tags: List<HomeRecommendTagsBean>,
                    val consumption: HomeRecommendConsumptionBean,
                    val resourceType: String,
                    var slogan: Any?,
                    val provider: HomeRecommendProviderBean,
                    val category: String,
                    val author: HomeRecommendAuthorBean,
                    val cover: HomeRecommendCoverBean,
                    val playUrl: String,
                    var thumbPlayUrl: Any?,
                    val duration: Int,
                    val webUrl: HomeRecommendWebUrlBean,
                    val releaseTime: Long,
                    val playInfo: List<Any?>,
                    var campaign: Any?,
                    var waterMarks: Any?,
                    val ad: Boolean,
                    val type: String,
                    var titlePgc: Any?,
                    var descriptionPgc: Any?,
                    var remark: Any?,
                    val ifLimitVideo: Boolean,
                    val searchWeight: Int,
                    var brandWebsiteInfo: Any?,
                    val idx: Int,
                    var shareAdTrack: Any?,
                    var favoriteAdTrack: Any?,
                    var webAdTrack: Any?,
                    val date: Long,
                    var promotion: Any?,
                    var label: Any?,
                    val labelList: List<Any?>,
                    val descriptionEditor: String,
                    val collected: Boolean,
                    val played: Boolean,
                    val subtitles: List<Any?>,
                    var lastViewTime: Any?,
                    var playlists: Any?,
                    val src: Int)

data class HomeRecommendTagsBean(val id: Int,
                    val name: String,
                    val actionUrl: String,
                    var adTrack: Any?,
                    var desc: Any?,
                    val bgPicture: String,
                    val headerImage: String,
                    val tagRecType: String,
                    var childTagList: Any?,
                    var childTagIdList: Any?,
                    val communityIndex: Int)

data class HomeRecommendConsumptionBean(val collectionCount: Int,
                           val shareCount: Int,
                           val replyCount: Int)

data class HomeRecommendProviderBean(val name: String,
                        val alias: String,
                        val icon: String)

data class HomeRecommendAuthorBean(val id: Int,
                      val icon: String,
                      val name: String,
                      val description: String,
                      val link: String,
                      val latestReleaseTime: Long,
                      val videoNum: Int,
                      var adTrack: Any?,
                      val follow: HomeRecommendFollowBean,
                      val shield: HomeRecommendShieldBean,
                      val approvedNotReadyVideoCount: Int,
                      val ifPgc: Boolean,
                      val recSort: Int,
                      val expert: Boolean)

    data class HomeRecommendFollowBean(val itemType: String,
                          val itemId: Int,
                          val followed: Boolean)

    data class HomeRecommendShieldBean(val itemType: String,
                          val itemId: Int,
                          val shielded: Boolean)


data class HomeRecommendCoverBean(val feed: String,
                     val detail: String,
                     val blurred: String,
                     var sharing: Any?,
                     val homepage: String)

data class HomeRecommendWebUrlBean(val raw: String,
                      val forWeibo: String)


data class HomeRecommendHeaderBean(val id: Int,
                      val title: String,
                      var font: Any?,
                      var subTitle: Any?,
                      var subTitleFont: Any?,
                      val textAlign: String,
                      var cover: Any?,
                      var label: Any?,
                      val actionUrl: String,
                      var labelList: Any?,
                      var rightText: Any?,
                      val icon: String,
                      val iconType: String,
                      val description: String,
                      val time: Long,
                      val showHateVideo: Boolean)

data class HomeRecommendContentBean(val type: String,
                       val data: HomeRecommendDataBean,
                       var tag: Any?,
                       val id: Int,
                       val adIndex: Int)



