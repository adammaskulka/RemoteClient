package com.clientsample.adammaskulka.clientsample.rest

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface RestInterface {

    @GET("lights")
    fun search(@Query("light") light: Int,
               @Query("level") level: Int,
               @Query("code") code: String): Observable<Result>

}