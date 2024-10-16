package es.unican.gasolineras.model;

import androidx.room.*;

import java.util.Date;

@Entity
public class Pago {
    // Cherry
    @PrimaryKey(autoGenerate = true)
    public int pid;

    @ColumnInfo(name = "station_name")
    public String stationName;

    @ColumnInfo(name = "date")
    public Date date;

    @ColumnInfo(name= "fuel_type")
    public String fuelType;

    @ColumnInfo(name= "quantity")
    public Double quantity;

    @ColumnInfo(name= "final_price")
    public Double finalPrice;

    @ColumnInfo(name= "price_per_litre")
    public Double pricePerLitre;

}
