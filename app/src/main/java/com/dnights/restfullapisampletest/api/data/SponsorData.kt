package com.dnights.restfullapisampletest.api.data

data class SponsorData(
    val id : String,
    val updated_at : String,
    val username : String,
    val name : String,
    val first_name : String,
    val last_name : String,
    val twitter_username : String,
    val portfolio_url : String,
    val bio : String,
    val location : String,
    val links : LinksData
)