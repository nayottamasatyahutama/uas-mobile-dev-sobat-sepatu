package com.nayottama.sobatsepatu.model

data class Order(
    val merk : String = "",
    val catatan : String = "",
    val userid: String = "",
    val jenis: String = "",
    val biaya: Int = 0
)
