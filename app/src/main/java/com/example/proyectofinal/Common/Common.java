package com.example.proyectofinal.Common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.proyectofinal.Modelo.Usuario;
import com.example.proyectofinal.Remote.APIService;
import com.example.proyectofinal.Remote.RetrofitClient;

public class Common {
    //Elimina pedido
    public static final String DELETE = "Eliminar";
    //Obtiene usuario
    public static Usuario currentUser;
    //de notificaciones
    public static final String BASE_URL="https://fcm.googleapis.com";

    public static APIService getFCMService(){

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
    //

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


}


