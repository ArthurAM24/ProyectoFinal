package com.example.proyectofinal.Remote;

import com.example.proyectofinal.Modelo.MyResponse;
import com.example.proyectofinal.Modelo.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAAaUjIgdA:APA91bF5m_C2TF_T6Q_gWE4RRRZxIVYKm7L6xMhVO391FXoEPMNs2BfmUrgoEgOw8gjujklUB4QU7ug4Xz_NmVp_PvmtAf6QTe_BFs9vCw3AJVtk4HYw8tpJmxCAFKwUleKjm8lVYCuL"
    })
    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);

}