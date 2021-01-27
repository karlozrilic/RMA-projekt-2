package zrilic.rma_projekt_2.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zrilic.rma_projekt_2.R;
import zrilic.rma_projekt_2.model.Vjezba;
import zrilic.rma_projekt_2.view.adapter.VjezbaAdapter;
import zrilic.rma_projekt_2.view.adapter.VjezbaClickListener;
import zrilic.rma_projekt_2.viewmodel.VjezbaViewModel;

public class ReadFragment extends Fragment {

    @BindView(R.id.lista)
    RecyclerView recyclerView;

    VjezbaViewModel model;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_read, container, false);
        ButterKnife.bind(this, view);

        model = ((MainActivity) getActivity()).getModel();

        definirajListu();

        osvjeziPodatke();

        return view;
    }

    public void osvjeziPodatke(){
        model.dohvatiVjezbe().observe(getViewLifecycleOwner(), new Observer<List<Vjezba>>() {
            @Override
            public void onChanged(List<Vjezba> vjezbe) {
                ((VjezbaAdapter)recyclerView.getAdapter()).setPodaci(vjezbe);
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        });
    }

    private void definirajListu() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new VjezbaAdapter(new VjezbaClickListener() {
            @Override
            public void onItemClick(Vjezba vjezba) {
                model.setVjezba(vjezba);
                ((MainActivity)getActivity()).cud();
            }
        }));
    }

    @OnClick(R.id.fab)
    public void novaOsoba(){
        model.setVjezba(new Vjezba());
        ((MainActivity)getActivity()).cud();
    }

}
