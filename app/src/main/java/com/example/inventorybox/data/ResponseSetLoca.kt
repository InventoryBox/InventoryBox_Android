package com.example.inventorybox.data


data class ResponseSetLoca(
    val documents: List<Document>,
    val meta: Meta

)

data class Meta(
    val is_end: Boolean,
    val pageable_count: Int,
    val total_count: Int
)


data class Document(
    val address: Address,
    val address_name: String,
    val road_address: RoadAddress?,
    val x: String,
    val y: String
)
data class RoadAddress(
    val address_name: String,
    val x : Double,
    val y : Double
)

data class Address(
    val address_name: String,
    val x: String,
    val y: String
)