package com.example.proyectofinal.Common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.proyectofinal.Modelo.Usuario;
import com.example.proyectofinal.Remote.APIService;
import com.example.proyectofinal.Remote.RetrofitClient;

public class Common {
    public static Usuario currentUser;
    //de notificaciones
    public static final String BASE_URL2="https://fcm.googleapis.com/fcm/send";
    public static final String BASE_URL="https://fcm.googleapis.com";
    public static final String SERVER_KEY="key=AAAAaUjIgdA:APA91bF5m_C2TF_T6Q_gWE4RRRZxIVYKm7L6xMhVO391FXoEPMNs2BfmUrgoEgOw8gjujklUB4QU7ug4Xz_NmVp_PvmtAf6QTe_BFs9vCw3AJVtk4HYw8tpJmxCAFKwUleKjm8lVYCuL";

    public static APIService getFCMService(){

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }

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

}


