package com.practice.headlines.model

import com.google.gson.annotations.SerializedName


data class NewsApiResponse (

  @SerializedName("status"       ) var status       : String?        = null,
  @SerializedName("totalResults" ) var totalResults : Int?           = null,
  @SerializedName("articles"     ) var articles     : List<Articles> = arrayListOf()

)