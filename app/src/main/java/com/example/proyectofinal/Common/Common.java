package com.example.proyectofinal.Common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.proyectofinal.Modelo.Usuario;
import com.example.proyectofinal.Remote.ApiService;
import com.example.proyectofinal.Remote.RetrofitClient;

public class Common {

    //de notificaciones
    private  static final String BASE_URL="https://fcm.googleapis.com/";
    public static ApiService getFCMService(){
        return RetrofitClient.getClient(BASE_URL).create(ApiService.class);
    }

    public static Usuario currentUser;

    public static String convertCodeToStatus(String status) {
        if(status.equals("0"))
            return  "PEDIDO REALIZADO";
        else if(status.equals("1"))
            return "PEDIDO ATENDIDO!";
        else
            return "PEDIDO LISTO!";
    }

    public static boolean isConnectedToInternet(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if(connectivityManager != null)
        {
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if(info != null)
            {
                for(int i=0;i<info.length;i++)
                {
                    if(info[i].getState() == NetworkInfo.State.CONNECTED)
                        return true;
                }
            }
        }
        return false;
    }

    public static final String DELETE = "Eliminar";

    public static final String USER_KEY = "User";
    public static final String PWD_KEY = "Password";

}

