package com.toeii.extensionreadjetpack.entity


class Status {
    var isRetweet: Boolean = false
    var text: String? = null
    var userName: String? = null
    var userAvatar: String? = null
    var createdAt: String? = null

    override fun toString(): String {
        return "Status{" +
                "isRetweet=" + isRetweet +
                ", text='" + text + '\'' +
                ", userName='" + userName + '\'' +
                ", userAvatar='" + userAvatar + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}'
    }
}