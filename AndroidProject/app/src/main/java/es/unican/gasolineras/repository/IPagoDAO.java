package es.unican.gasolineras.repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import es.unican.gasolineras.model.Pago;

@Dao
public interface IPagoDAO {
    @Query("SELECT * FROM pago")
    List<Pago> getAll();

    @Query("SELECT * FROM pago WHERE pid IN (:pagoIds)")
    List<Pago> loadAllByIds(int[] pagoIds);

    @Query("SELECT * FROM pago WHERE station_name LIKE :stationName")
    Pago findByName(String stationName);

    @Insert
    void insertAll(Pago... pagos);

    @Delete
    void delete(Pago pago);
    // Cher
}
