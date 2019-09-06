package com.toeii.extensionreadjetpack.network

import com.toeii.extensionreadjetpack.entity.*
import retrofit2.http.*

interface ApiService {

    // 首页推荐
    @GET("/api/xiandu/data/id/vice/count/{number}/page/{page}")
    suspend fun getHomeRecommendList(@Path("number") number: String,@Path("page") page: String): HomeRecommendEntity

    // 首页日报
    @GET("/api/xiandu/data/id/qdaily/count/{number}/page/{page}")
    suspend fun getHomeDailyList(@Path("number") number: String,@Path("page") page: String): HomeDailyEntity

    // 首页banner
    @Headers("urls:baseUrlReport")
    @GET("/api/v2/feed?num=2&udid=26868b32e808498db32fd51fb422d00175e179df&vc=83")
    suspend fun getHomeRecommendBannerList(): HomeRecommendBannerEntity

    // 社区分类
    @Headers("urls:baseUrlReport")
    @GET("/api/v6/community/tab/rec?udid=55b862f0d6714f609bd6e45947f8789f0ff90f48&vc=461&deviceModel=PBAT00")
    suspend fun getCommunityItemList(): CommunityItemEntity

    // 社区分类下内容
    @Headers("urls:baseUrlReport")
    @GET("/api/v6/tag/dynamics?id={id}&start={start}&num={page}")
    suspend fun getCommunityContentList(@Path("id") id: String,@Path("start") start: Int,@Path("page") page: Int): CommunityEntity



}