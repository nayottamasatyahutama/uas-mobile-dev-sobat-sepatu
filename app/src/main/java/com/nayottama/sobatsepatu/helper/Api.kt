package com.nayottama.sobatsepatu.helper

import com.nayottama.sobatsepatu.model.Order
import com.nayottama.sobatsepatu.model.User
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

const val BASE_URL = "http://192.168.18.7:3000/"

interface Api {

    @POST("customers")
    fun createUser(@Body user: User) : Call<User>

    @POST("pesanan")
    fun createOrder(@Body order: Order) : Call<Order>

    @GET("customers/{uid}")
    fun getDetailUser(@Path("uid") uid: String) : Call<User>

    @GET("pesanan/{userid}")
    fun getListOrder(@Path("userid") userid: String) : Call<List<Order>>

    companion object {
        fun apiCall() : Api {

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Api::class.java)
        }

    }
}