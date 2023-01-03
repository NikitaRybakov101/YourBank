package com.example.yourbank.repository

import com.google.gson.annotations.SerializedName

data class ResponseDataYourCard (
    @field:SerializedName("type")    val type: String,
    @field:SerializedName("scheme")  val scheme: String,
    @field:SerializedName("brand")   val brand: String,

    @field:SerializedName("country")   val country: Country?,
    @field:SerializedName("bank")   val bank: Bank,
    @field:SerializedName("prepaid")   val prepaid: String,
    @field:SerializedName("number")   val number: Number
)

data class Country (
    @field:SerializedName("numeric") val numeric: String,
    @field:SerializedName("alpha2") val alpha2: String,
    @field:SerializedName("name") val name: String,
    @field:SerializedName("emoji") val emoji: String,
    @field:SerializedName("currency") val currency: String,
    @field:SerializedName("latitude") val latitude: String,
    @field:SerializedName("longitude") val longitude: String
)

data class Bank (
    @field:SerializedName("name") val name: String?,
    @field:SerializedName("url") val url: String,
    @field:SerializedName("phone") val phone: String,
    @field:SerializedName("city") val city: String
)

data class Number (
    @field:SerializedName("length") val length: String?,
    @field:SerializedName("luhn") val luhn: String?
)








