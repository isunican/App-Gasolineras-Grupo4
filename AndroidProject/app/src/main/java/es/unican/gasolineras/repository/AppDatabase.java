package es.unican.gasolineras.repository;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import es.unican.gasolineras.model.Pago;

@Database(entities = {Pago.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract IPagoDAO pagoDAO();
    // Cher
}
