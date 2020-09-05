package com.dev.ta_etyy.utils.apihelper;

import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {

    @Headers("Accept: application/json")
    @POST("Login")
    Call<ResponseBody> login(@Body JsonObject body);

    @Headers("Accept: application/json")
    @POST("PengajuanCuti")
    Call<ResponseBody> daftarCuti(@Body JsonObject body);

    @Headers("Accept: application/json")
    @POST("InquirylistCuti")
    Call<ResponseBody> getListCuti();

    @Headers("Accept: application/json")
    @POST("InquiryDetailCuti")
    Call<ResponseBody> getDetailCuti(@Body JsonObject body);
}
