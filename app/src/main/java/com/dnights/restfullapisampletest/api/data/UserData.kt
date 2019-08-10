package com.dnights.restfullapisampletest.api.data

data class UserData(
    val id: String,
    val updated_at: String,
    val username: String,
    val name: String,
    val first_name: String,
    val last_name: String,
    val twitter_username: String,
    val portfolio_url: String,
    val bio: String,
    val location: String,
    val links: LinksData,
    val profile_image: ProfileImageData,
    val instagram_username: String,
    val total_collections: Int,
    val total_likes: Int,
    val total_photos: Int,
    val accepted_tos: Boolean
)




