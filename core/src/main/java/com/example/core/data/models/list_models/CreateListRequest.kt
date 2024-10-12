package com.example.core.data.models.list_models

data class CreateListRequest(
    val name: String,
    val description: String,
    val language: String = "eng",
    val public: Boolean
)