package es.unican.gasolineras.repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import es.unican.gasolineras.model.Descuento;

@Dao
public interface IDescuentoDAO {

    /**
     * Hace una consulta a la base de datos para retornar todos los descuentos almacenados en la
     * base de datos
     * @return Lista de descuentos que hay en la base de datos
     */
    @Query("SELECT * FROM descuento ORDER BY expirance_date ASC")
    List<Descuento> getAll();

    /**
     * Hace una consulta a la base de datos para retornar todos los descuentos con unas IDS
     * @param discountIds Array que contiene los ids de los descuentos
     * @return Lista de descuentos con los IDS pasados
     */
    @Query("SELECT * FROM descuento WHERE pid IN (:discountIds)")
    List<Descuento> loadAllByIds(int[] discountIds);

    /**
     * Hace una consulta sobre un descuento con un nombre pasado
     * @param discountName Nombre del descuento a buscar
     * @return Descuento que se ha registrado con el nombre pasado como parametro
     */
    @Query("SELECT * FROM descuento WHERE discount_name LIKE :discountName")
    Descuento findByName(String discountName);

    /**
     * Inserta un objeto de la clase Descuento en la base de datos
     * @param descuentos Descuento a insertar en la base de datos
     */
    @Insert
    void insertAll(Descuento... descuentos);

    /**
     * Elimina un objeto Descuento de la base de datos
     * @param descuento Descuento a eliminar de la base de datos
     */
    @Delete
    void delete(Descuento descuento);

    /**
     * Actualiza el campo activo a el nuevo campo activo para el nombre pasado
     * @param activo Boolean de si esta activo o no
     * @param nombre Nombre del descuento a actualizar
     */
    @Query("UPDATE Descuento set discount_active = :activo where discount_name = :nombre")
    void update(boolean activo, String nombre);

}
