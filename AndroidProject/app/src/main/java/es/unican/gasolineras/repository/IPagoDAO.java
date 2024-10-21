package es.unican.gasolineras.repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import es.unican.gasolineras.model.Pago;

/**
 * Interfaz que implementa pagos DAO
 */
@Dao
public interface IPagoDAO {
    @Query("SELECT * FROM pago ORDER BY pid DESC")
    List<Pago> getAll();

    /**
     * Hace una consulta a la base de datos para retornar todos los pagos con unas IDS
         * @param pagoIds Array que contiene los ids de los pagos
     * @return Lista de pagos con los IDS pasados
     */
    @Query("SELECT * FROM pago WHERE pid IN (:pagoIds)")
    List<Pago> loadAllByIds(int[] pagoIds);

    /**
     * Hace una consulta sobre una gasolinera con un nombre pasado
     * @param stationName Nombre de la gasolinera a buscar
     * @return Lista de pagos que se han hecho en la gasolinera pasada como argumento
     */
    @Query("SELECT * FROM pago WHERE station_name LIKE :stationName")
    Pago findByName(String stationName);

    /**
     * Inserta un objeto de la clase Pago en la base de datos
     * @param pagos Pago a insertar en la base de datos
     */
    @Insert
    void insertAll(Pago... pagos);

    /**
     * Elimina un objeto Pago de la base de datos
     * @param pago Pago a eliminar de la base de datos
     */
    @Delete
    void delete(Pago pago);
    // Cher
}
