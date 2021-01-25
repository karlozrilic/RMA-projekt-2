package zrilic.rma_projekt_2.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import zrilic.rma_projekt_2.model.Vjezba;
import zrilic.rma_projekt_2.model.dao.VjezbaBaza;
import zrilic.rma_projekt_2.model.dao.VjezbaDAO;

@Getter
@Setter
public class VjezbaViewModel extends AndroidViewModel {

    VjezbaDAO vjezbaDAO;
    private Vjezba vjezba;
    private LiveData<List<Vjezba>> vjezbe;

    public VjezbaViewModel(Application application) {
        super(application);
        vjezbaDAO = VjezbaBaza.getInstance(application.getApplicationContext()).vjezbaDAO();
    }

    public LiveData<List<Vjezba>> dohvatiVjezbe() {
        vjezbe = vjezbaDAO.dohvatiVjezbe();
        return vjezbe;
    }

    public void dodajNovuVjezbu() {
        vjezbaDAO.dodajNovuVjezbu(vjezba);
    }

    public void promjeniVjezbu() {
        vjezbaDAO.promjeniVjezbu(vjezba);
    }

    public void obrisiVjezbu() {
        vjezbaDAO.obrisiVjezbu(vjezba);
    }

    public void obrisiSveVjezbe() {
        vjezbaDAO.obrisiSveVjezbe();
    }

}
