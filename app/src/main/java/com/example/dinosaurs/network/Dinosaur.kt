package com.example.dinosaurs.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Dinosaur(
    val name: String,
    val length: String,
    val description: String,
    @SerialName("img_src")
    val imgSrc: String
)
