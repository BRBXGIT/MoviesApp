package com.example.core.data.models.create_list_models.create_list_request

data class CreateListRequest(
    val name: String,
    val description: String,
    val language: String = "eng",
    val public: Boolean
)