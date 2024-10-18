package es.unican.gasolineras.repository;

import android.content.Context;

import androidx.room.Room;

public final class DataBase {
    private static AppDatabase basededatos;
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
