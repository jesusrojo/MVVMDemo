package com.jesusrojo.mvvmdemo.data.model

import com.google.gson.annotations.SerializedName

data class RawDataResponse(
    @SerializedName("incomplete_results") val incompleteResults: Boolean?,
    @SerializedName("items") val rawDatas: List<RawData>?,
    @SerializedName("total_count") val totalCount: Int?
   // val nextPage: Int? = null
)

data class RawData(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("forks_count") val forksCount: Int?,
    @SerializedName("stargazers_count") val stargazersCount: Int?,
    @SerializedName("owner") val owner: OwnerRawData?
){
    override fun toString(): String {
        return "ID: $id\nNAME: $name\nDESCRIPTION: $description" +
                "\nFORKS: $forksCount\nSTARS: $stargazersCount" +
                "\nAUTHOR NAME: ${owner?.ownerName}"
    }
}

data class OwnerRawData(
    @SerializedName("login") val ownerName: String?,
    @SerializedName("avatar_url") val avatarUrl: String?,
)