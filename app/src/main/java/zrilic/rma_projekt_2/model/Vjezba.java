package zrilic.rma_projekt_2.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(tableName = "vjezba")
public class Vjezba {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    @NonNull
    private int aktivnost;
    private String trajanje;
    private String udaljenostKm;
    private long datum;
    private String vrijeme;
}
