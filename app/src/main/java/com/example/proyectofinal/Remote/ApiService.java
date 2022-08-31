package com.example.proyectofinal.Remote;


import com.example.proyectofinal.Modelo.MyResponse;
import com.example.proyectofinal.Modelo.MySender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public interface ApiService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Autorization:key=BCkkLCYSh6_Z1PKwunz58ZdR36axtfVtWFmqYU8GCYDIkCgkny_i_qp-kRf5tDmgFUksyLl9rA0IgqQ7CHIPZmU"
            }

    )
 @POST("fcm/send")
 Call<MyResponse> sendNotification(@Body MySender body);
}
