package es.unican.gasolineras.repository;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import es.unican.gasolineras.model.Descuento;

/**
 * Base de datos
 */
@Database(entities = {Descuento.class}, version = 1)
public abstract class AppDatabaseDiscount extends RoomDatabase {

    /**
     * Devuelve el descuentos dao
     * @return la DAO de descuentos
     */
    public abstract IDescuentoDAO descuentosDAO();

}