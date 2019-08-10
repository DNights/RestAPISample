package com.dnights.restfullapisampletest.api.data

data class PhotoData(
    val id: String,
    val created_at: String,
    val updated_at: String,
    val width: String,
    val height: String,
    val color: String,
    val description: String,
    val alt_description: String,
    val urls: UrlsData,
    val links: PhotoLinksData,
    val categories: List<String>,
    val likes: String,
    val liked_by_user: String,
    val current_user_collections: List<String>,
    val user: UserData,
    val sponsorship:SponsorshipData
)

data class PhotoLinksData(
    val self: String,
    val html: String,
    val download: String,
    val download_location: String
)