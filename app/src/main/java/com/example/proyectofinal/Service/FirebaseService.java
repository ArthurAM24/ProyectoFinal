package com.example.proyectofinal.Service;


import com.example.proyectofinal.Common.Common;
import com.example.proyectofinal.Modelo.Token;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class FirebaseService extends FirebaseInstanceIdService {


    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String tokenrefresh = FirebaseInstanceId.getInstance().getToken();
        if (Common.currentUser != null) {
            actualizaTokenfirebase(tokenrefresh);
        }
    }

    private void actualizaTokenfirebase(String tokenrefresh) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference tokens = db.getReference("Tokens");
        Token token = new Token(tokenrefresh, false);
        tokens.child(Common.currentUser.getCelular()).setValue(token);

    }

}
