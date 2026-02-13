package com.example.travelapp.data.remote.api

import com.example.travelapp.data.remote.dto.auth.LoginRequest
import com.example.travelapp.data.remote.dto.auth.LoginResponse
import com.example.travelapp.data.remote.dto.auth.ResetPasswordRequest
import com.example.travelapp.data.remote.dto.auth.ResetPasswordResponse
import com.example.travelapp.data.remote.dto.auth.SignupRequest
import com.example.travelapp.data.remote.dto.auth.SignupResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface TravelApiService {
    //--------------------------------AUTH API---------------------------

    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>


    @POST("api/auth/signup")
    suspend fun signup(@Body request: SignupRequest): Response<SignupResponse>


    @POST("api/auth/forget-password")
    suspend fun resetPassword(@Body request: ResetPasswordRequest): Response<ResetPasswordResponse>


}