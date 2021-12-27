package com.practice.headlines.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.google.gson.annotations.SerializedName
enum class DownloadStatus{
  NDTDOWNLOAD,LOADING,DOWNLOAD
}

data class Articles (
  @SerializedName("source"      ) var source      : Source? = Source(),
  @SerializedName("author"      ) var author      : String? = null,
  @SerializedName("title"       ) var title       : String? = null,
  @SerializedName("description" ) var description : String? = null,
  @SerializedName("url"         ) var url         : String? = null,
  @SerializedName("urlToImage"  ) var urlToImage  : String? = null,
  @SerializedName("publishedAt" ) var publishedAt : String? = null,
  @SerializedName("content"     ) var content     : String? = null,
  var downloaded:DownloadStatus=DownloadStatus.NDTDOWNLOAD,
  var id:Int=-1
)