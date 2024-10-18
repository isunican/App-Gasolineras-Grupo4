package es.unican.gasolineras.repository;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import es.unican.gasolineras.model.Pago;

/**
 * Base de datos
 */
@Database(entities = {Pago.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    /**
     * Devuelve el pagos DAO
     * @return la DAO de pagos
     */
    public abstract IPagoDAO pagoDAO();

}
