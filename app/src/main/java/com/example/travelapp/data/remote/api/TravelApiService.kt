package com.example.travelapp.data.remote.api

import com.example.travelapp.data.remote.dto.auth.LoginRequest
import com.example.travelapp.data.remote.dto.auth.LoginResponse
import com.example.travelapp.data.remote.dto.auth.ResetPasswordRequest
import com.example.travelapp.data.remote.dto.auth.ResetPasswordResponse
import com.example.travelapp.data.remote.dto.auth.SignupRequest
import com.example.travelapp.data.remote.dto.auth.SignupResponse
import com.example.travelapp.data.remote.dto.home.profile.EditProfileResponse
import com.example.travelapp.data.remote.dto.home.profile.FetchProfileResponse
import com.example.travelapp.data.remote.dto.message.ChatListResponse
import com.example.travelapp.data.remote.dto.message.MessageResponse
import com.example.travelapp.data.remote.dto.trips.CreateTripRequest
import com.example.travelapp.data.remote.dto.trips.CreateTripResponse
import com.example.travelapp.data.remote.dto.trips.SearchProfileResponse
import com.example.travelapp.data.remote.dto.weather.WeatherResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface TravelApiService {
    //--------------------------------AUTH API---------------------------

    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>


    @POST("api/auth/signup")
    suspend fun signup(@Body request: SignupRequest): Response<SignupResponse>


    @POST("api/auth/forget-password")
    suspend fun resetPassword(@Body request: ResetPasswordRequest): Response<ResetPasswordResponse>


    //------------------------------Profile Api------------------------

    @GET("api/profile/fetch")
    suspend fun fetchProfile():Response<FetchProfileResponse>

    @Multipart
    @PUT("api/profile/edit")
    suspend fun editProfile(
        @Part profilePic: MultipartBody.Part?,
        @Part("fullname") fullname: RequestBody?,
        @Part("dob") dob: RequestBody?,
        @Part("gender") gender: RequestBody?,
        @Part("phone") phone: RequestBody?,
        @Part("city") city: RequestBody?,
        @Part("state") state: RequestBody?,
        @Part("country") country: RequestBody?
    ): Response<EditProfileResponse>


    @GET("api/profile/search-phone")
    suspend fun searchProfileByPhone(
        @Query("phone") phone: String
    ): Response<SearchProfileResponse>


    //------------------------------Message Api------------------------

    @GET("api/message/{receiverId}")
    suspend fun getMessage(
        @Path("receiverId") receiverId: String
    ): Response<MessageResponse>

    @GET("api/message/conversations")
    suspend fun getConversations(): Response<ChatListResponse>



    //------------------------------Weather Api------------------------

    @GET("api/weather/weather")
    suspend fun getWeather(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
    ): Response<WeatherResponse>


    //------------------------------Trips Api------------------------
    @POST("api/trips/create")
    suspend fun createTrip(
        @Body request: CreateTripRequest
    ): Response<CreateTripResponse>









}