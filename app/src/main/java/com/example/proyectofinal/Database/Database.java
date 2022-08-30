package com.example.proyectofinal.Database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.proyectofinal.Modelo.Pedido;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    private static final String DB_NAME = "Proyect.db";
    private static final int DB_VER = 1;
    private static final String TABLA_DB="CREATE TABLE OrdenDetalle(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            "UsercelID TEXT," +
            "ProductoID TEXT," +
            "ProductoNomb TEXT," +
            "Cantidad TEXT," +
            "Precio TEXT," +
            "Descuento TEXT)";

    public Database( Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLA_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @SuppressLint("Range")

    public List<Pedido> getCarrito(String Usercel) {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"UsercelID,ProductoID,ProductoNomb", "Cantidad", "Precio", "Descuento"};
        String sqlTable = "OrdenDetalle";

        qb.setTables(sqlTable);
        Cursor c = qb.query(db, sqlSelect, "UsercelID=?",new String[] {Usercel},null, null, null, null, null);

        final List<Pedido> result= new ArrayList<>();

        if (c.moveToFirst()) {
            do {
                result.add(new Pedido(
                        c.getString(c.getColumnIndex("UsercelID")),
                        c.getString(c.getColumnIndex("ProductoID")),
                        c.getString(c.getColumnIndex("ProductoNomb")),
                        c.getString(c.getColumnIndex("Cantidad")),
                        c.getString(c.getColumnIndex("Precio")),
                        c.getString(c.getColumnIndex("Descuento"))
                ));
            } while (c.moveToNext());
        }
        return result;
    }
    public void agregarAlCarrito(Pedido pedido){

        SQLiteDatabase db=getReadableDatabase();
        if (db!=null) {
            String query = String.format("INSERT INTO OrdenDetalle(UsercelID,ProductoID,ProductONomb,Cantidad,Precio,Descuento) VALUES('%s','%s','%s','%s','%s','%s');",
                    pedido.getUsercelID(),
                    pedido.getProductoID(),
                    pedido.getProductoNomb(),
                    pedido.getCantidad(),
                    pedido.getPrecio(),
                    pedido.getDescuento());
            db.execSQL(query);
            db.close();
        }
    }

    public void limpíaCarrito(String Usercel)
    {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM OrdenDetalle WHERE UsercelID='%s'",Usercel);
        db.execSQL(query);
    }

}
