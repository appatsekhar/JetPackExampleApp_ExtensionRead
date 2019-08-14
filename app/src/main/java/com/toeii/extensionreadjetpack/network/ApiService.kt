package com.toeii.extensionreadjetpack.network

import com.toeii.extensionreadjetpack.entity.CommunityContentEntity
import com.toeii.extensionreadjetpack.entity.CommunityItemEntity
import com.toeii.extensionreadjetpack.entity.HomeDailyEntity
import com.toeii.extensionreadjetpack.entity.HomeRecommendEntity
import retrofit2.http.*

interface ApiService {

    // 首页推荐
    @GET("/v5/index/tab/allRec?&isOldUser=true&udid=55b862f0d6714f609bd6e45947f8789f0ff90f48&page={page}")
    suspend fun getHomeRecommendList(@Path("page") page: Int): HomeRecommendEntity

    // 首页日报
    @GET("/v5/index/tab/feed?udid=55b862f0d6714f609bd6e45947f8789f0ff90f48&date={time}&num={page}")
    suspend fun getHomeDailyList(@Path("time") time: String,@Path("page") page: Int): HomeDailyEntity

    // 社区分类
    @GET("/v6/community/tab/rec?udid=55b862f0d6714f609bd6e45947f8789f0ff90f48&vc=461&deviceModel=PBAT00")
    suspend fun getCommunityItemList(): CommunityItemEntity

    // 社区分类下内容
    @GET("/v6/tag/dynamics?id={id}&start={start}&num={page}")
    suspend fun getCommunityContentList(@Path("id") id: String,@Path("start") start: Int,@Path("page") page: Int): CommunityContentEntity


}