package com.toeii.extensionreadjetpack.entity

data class HomeDailyEntity(val itemList: List<HomeDailyItemListBean>) {

}

data class HomeDailyItemListBean(val type: String,
                        var data: HomeDailyDataBean,
                        var tag: Any?,
                        val id: Int,
                        val adIndex: Int)


data class HomeDailyDataBean(val dataType: String,
                    var header: HomeDailyHeaderBean,
                    val content: HomeDailyContentBean,
                    var adTrack: Any?)


data class HomeDailyHeaderBean(val id: Int,
                      val actionUrl: String,
                      var labelList: Any?,
                      var icon: String,
                      var iconType: String?,
                      val time: Long?,
                      val showHateVideo: Boolean,
                      val followType: String,
                      val tagId: Int,
                      var tagName: Any?,
                      var issuerName: String?,
                      val topShow: Boolean){

}

data class HomeDailyContentBean(val type: String,
                       val data: HomeDailyContentDataBean,
                       var tag: Any?,
                       val id: Int,
                       val adIndex: Int) {

}


data class HomeDailyContentDataBean(val dataType: String,
                    val id: Int,
                    val title: String,
                    val description: String,
                    val library: String,
                    val tags: List<HomeDailyTagsBean>,
                    val consumption: HomeDailyConsumptionBean,
                    val resourceType: String,
                    val uid: Int,
                    val createTime: Long,
                    val updateTime: Long,
                    val collected: Boolean,
                    val owner: HomeDailyOwnerBean,
                    val cover: HomeDailyCoverBean,
                    val author: HomeDailyAuthorBean,
                    val selectedTime: Long,
                    val checkStatus: String,
                    val area: String,
                    val city: String,
                    val longitude: Double,
                    val latitude: Double,
                    val ifMock: Boolean,
                    val validateStatus: String,
                    val validateResult: String,
                    val width: Int,
                    val height: Int,
                    val addWatermark: Boolean,
                    var recentOnceReply: Any?,
                    var privateMessageActionUrl: Any?,
                    val url: String,
                    val urls: List<String>,
                    val status: String,
                    val releaseTime: Long,
                    val urlsWithWatermark: List<String>) {

}


data class HomeDailyTagsBean(val id: Int,
                    val name: String,
                    val actionUrl: String,
                    var adTrack: Any?,
                    val desc: String,
                    val bgPicture: String,
                    val headerImage: String,
                    val tagRecType: String,
                    var childTagList: Any?,
                    var childTagIdList: Any?,
                    val communityIndex: Int)

data class HomeDailyConsumptionBean(val collectionCount: Int,
                           val shareCount: Int,
                           val replyCount: Int)

data class HomeDailyOwnerBean(val uid: Int,
                     val nickname: String,
                     val avatar: String,
                     val userType: String,
                     val ifPgc: Boolean,
                     val description: String,
                     var area: Any?,
                     val gender: String,
                     val registDate: Long,
                     val releaseDate: Long?,
                     var cover: Any?,
                     val actionUrl: String,
                     val followed: Boolean,
                     val limitVideoOpen: Boolean,
                     val library: String,
                     val birthday: Long,
                     var country: Any?,
                     var city: Any?,
                     var university: Any?,
                     var job: Any?,
                     val expert: Boolean)

data class HomeDailyAuthorBean(val id: Int,
                      val icon: String,
                      val name: String,
                      val description: String,
                      val link: String,
                      val latestReleaseTime: Long,
                      val videoNum: Int,
                      var adTrack: Any?,
                      val follow: Any?,
                      val shield: Any?,
                      val approvedNotReadyVideoCount: Int,
                      val ifPgc: Boolean,
                      val recSort: Int,
                      val expert: Boolean) {

}


data class HomeDailyCoverBean(val feed: String,
                     val detail: String,
                     var blurred: Any?,
                     var sharing: Any?,
                     var homepage: Any?)