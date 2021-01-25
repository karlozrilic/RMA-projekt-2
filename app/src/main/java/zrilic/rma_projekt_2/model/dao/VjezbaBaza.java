package zrilic.rma_projekt_2.model.dao;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import zrilic.rma_projekt_2.model.Vjezba;

@Database(entities = {Vjezba.class}, version = 2, exportSchema = false)
public abstract class VjezbaBaza extends RoomDatabase {

    public abstract VjezbaDAO vjezbaDAO();

    private static VjezbaBaza instance;

    public static VjezbaBaza getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    VjezbaBaza.class,
                    "vjezba-baza"
            ).allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return instance;
    }

}
