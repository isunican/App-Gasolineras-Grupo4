package es.unican.gasolineras.model;

import androidx.room.*;

import java.time.LocalDate;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Pago {

    @PrimaryKey(autoGenerate = true)
    public int pid;

    @ColumnInfo(name = "station_name")
    public String stationName;

    @ColumnInfo(name = "date")
    public String date;

    @ColumnInfo(name= "fuel_type")
    public String fuelType;

    @ColumnInfo(name= "quantity")
    public Double quantity;

    @ColumnInfo(name= "final_price")
    public Double finalPrice;

    @ColumnInfo(name= "price_per_litre")
    public Double pricePerLitre;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pago)) return false;
        Pago pago = (Pago) o;
        return Objects.equals(stationName, pago.stationName) && Objects.equals(date, pago.date) && Objects.equals(fuelType, pago.fuelType) && Objects.equals(quantity, pago.quantity) && Objects.equals(finalPrice, pago.finalPrice) && Objects.equals(pricePerLitre, pago.pricePerLitre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stationName, date, fuelType, quantity, finalPrice, pricePerLitre);
    }
}
