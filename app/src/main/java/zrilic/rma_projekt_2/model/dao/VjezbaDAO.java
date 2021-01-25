package zrilic.rma_projekt_2.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import zrilic.rma_projekt_2.model.Vjezba;

@Dao
public interface VjezbaDAO {

    @Query("SELECT * FROM vjezba order by datum DESC")
    LiveData<List<Vjezba>> dohvatiVjezbe();

    @Insert
    void dodajNovuVjezbu(Vjezba vjezba);

    @Update
    void promjeniVjezbu(Vjezba vjezba);

    @Delete
    void obrisiVjezbu(Vjezba vjezba);

    @Query("DELETE FROM vjezba")
    void obrisiSveVjezbe();

}
