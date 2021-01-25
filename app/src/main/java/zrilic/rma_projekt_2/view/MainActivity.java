package zrilic.rma_projekt_2.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

import zrilic.rma_projekt_2.R;
import zrilic.rma_projekt_2.viewmodel.VjezbaViewModel;

public class MainActivity extends AppCompatActivity {

    private VjezbaViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        model = ViewModelProviders.of(this).get(VjezbaViewModel.class);
        read();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                model.obrisiSveVjezbe();
                Toast.makeText(this, getString(R.string.obrisiSve), Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

        return true;
    }

    public VjezbaViewModel getModel() {
        return model;
    }

    public void read(){
        setFragment( new ReadFragment());
    };

    public void cud(){
        setFragment( new CUDFragment());
    };

    private void setFragment(Fragment fragment){
        //fragment.setEnterTransition(new Slide(Gravity.RIGHT));
        //fragment.setExitTransition(new Slide(Gravity.LEFT));
        FragmentManager fragmentManager =getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,fragment);
        fragmentTransaction.commit();
    }

}