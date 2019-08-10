package com.dnights.restfullapisampletest.api.data

data class SponsorshipData(
    val impressions_id: String,
    val tagline: String,
    val sponsor: SponsorData,
    val profile_image: ProfileImageData,
    val instagram_username: String,
    val total_collections: Int,
    val total_likes: Int,
    val total_photos: Int,
    val accepted_tos: Boolean
)