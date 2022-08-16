package com.example.proyectofinal.Common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.proyectofinal.Modelo.Usuario;

public class Common {
    public static Usuario currentUser;

    public static String convertCodeToStatus(String status) {
        if(status.equals("0"))
            return  "Orden Realizada.";
        else if(status.equals("1"))
            return "Orden Siendo preparada.";
        else
            return "Devilery Listo!";
    }

    public static boolean isConnectedToInternet(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if(connectivityManager != null)
        {
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if(info != null)
            {
                for (NetworkInfo networkInfo : info) {
                    if (networkInfo.getState() == NetworkInfo.State.CONNECTED)
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

