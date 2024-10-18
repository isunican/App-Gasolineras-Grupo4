package es.unican.gasolineras.repository;

import android.content.Context;

import androidx.room.Room;

/**
 * Clase donde se crea la base de datos si no existe y si existe se devuelve la creada
 * Esta base de datos usa singleton para que solamente haya una base de datos y no haya multiples
 */
public final class DataBase {
    private static AppDatabase basededatos;

    /**
     * Devuelve la base de datos si ya existe y si no la crea con el contexto que se le pasa
     * @param context Contexto de la base de datos con la que se crea con la libreria room
     * @return La base de datos
     */
    public static AppDatabase getAppDatabase(Context context){
        if(basededatos == null){
            basededatos = Room.databaseBuilder(context,
                            AppDatabase.class, "database-name")
                    .allowMainThreadQueries()
                    .build();
        }
        return basededatos;
    }
}
