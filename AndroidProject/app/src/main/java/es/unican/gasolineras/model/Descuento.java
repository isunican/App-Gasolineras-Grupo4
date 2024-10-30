package es.unican.gasolineras.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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
}
