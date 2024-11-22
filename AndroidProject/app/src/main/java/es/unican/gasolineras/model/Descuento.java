package es.unican.gasolineras.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Descuento {

    @PrimaryKey(autoGenerate = true)
    public int pid;

    @ColumnInfo(name = "discount_name")
    public String discountName;

    @ColumnInfo(name = "company")
    public String company;

    @ColumnInfo(name= "discount_type")
    public String discountType;

    @ColumnInfo(name= "quantity_discount")
    public Double quantityDiscount;

    @ColumnInfo(name= "expirance_date")
    public String expiranceDate;

    @ColumnInfo(name= "discount_active")
    public boolean discountActive;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Descuento)) return false;
        Descuento descuento = (Descuento) o;
        return Objects.equals(discountName, descuento.discountName) &&
                Objects.equals(company, descuento.company) && Objects.equals(discountType, descuento.discountType)
                && Objects.equals(quantityDiscount, descuento.quantityDiscount) && Objects.equals(expiranceDate, descuento.expiranceDate)
                && Objects.equals(discountActive, descuento.discountActive);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(pid);
    }
}
