package com.example.bnetapp.model.retrofit.drugs

data class DrugsItem(
    val categories: Categories,
    val description: String,
    val documentation: String,
    val fields: List<Field>,
    val id: Int,
    val image: String,
    val name: String
)