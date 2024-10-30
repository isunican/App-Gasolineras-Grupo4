package es.unican.gasolineras.repository;

import android.content.Context;

import androidx.room.Room;

/**
 * Clase donde se crea la base de datos si no existe y si existe se devuelve la creada
 * Esta base de datos usa singleton para que solamente haya una base de datos y no haya multiples
 */
public final class DataBase {
    private static AppDatabasePayments basededatos;
    private static AppDatabaseDiscount basededatosDiscount;

    /**
     * Devuelve la base de datos si ya existe y si no la crea con el contexto que se le pasa
     * @param context Contexto de la base de datos con la que se crea con la libreria room
     * @return La base de datos de pagos
     */
    public static AppDatabasePayments getAppDatabase(Context context){
        if(basededatos == null){
            basededatos = Room.databaseBuilder(context,
                            AppDatabasePayments.class, "payments")
                    .allowMainThreadQueries()
                    .build();
        }
        return basededatos;
    }

    /**
     * Devuelve la base de datos si ya existe y si no la crea con el contexto que se le pasa
     * @param context Contexto de la base de datos con la que se crea con la libreria room
     * @return La base de datos de descuentos
     */
    public static AppDatabaseDiscount getAppDatabaseDiscount(Context context){
        if(basededatosDiscount == null){
            basededatosDiscount = Room.databaseBuilder(context,
                            AppDatabaseDiscount.class, "discounts")
                    .allowMainThreadQueries()
                    .build();
        }
        return basededatosDiscount;
    }
}