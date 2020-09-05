package com.dev.ta_etyy.utils.apihelper;

public class UtilsApi {
    public static final String BASE_URL = "http://192.168.43.7:9203/TugasAkhir/HCGS/";

    public static ApiInterface getApiService(){
        return ApiClient.getClient(BASE_URL).create(ApiInterface.class);
    }
}
