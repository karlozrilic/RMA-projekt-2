package zrilic.rma_projekt_2.view.adapter;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import zrilic.rma_projekt_2.R;
import zrilic.rma_projekt_2.model.Vjezba;

public class VjezbaAdapter extends RecyclerView.Adapter<VjezbaAdapter.Red> {

    private List<Vjezba> podatci;
    private VjezbaClickListener vjezbaClickListener;

    public VjezbaAdapter(VjezbaClickListener vjezbaClickListener) {
        this.vjezbaClickListener = vjezbaClickListener;
    }

    @Override
    public Red onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.red_liste, parent, false);
        return new Red(view);
    }

    @Override
    public void onBindViewHolder(Red holder, int position) {
        Vjezba v = podatci.get(position);
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(v.getDatum());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd. MM. yyyy.");
        String strDate = dateFormat.format(cal.getTime());
        holder.datum.setText(strDate);
        switch (v.getAktivnost()) {
            case 0:
                Picasso.get().load(R.drawable.trcanje_na_otvorenome).into(holder.slika);
                break;
            case 1:
                Picasso.get().load(R.drawable.setanje_na_otvorenom).into(holder.slika);
                break;
            case 2:
                Picasso.get().load(R.drawable.bicikla_na_otvorenom).into(holder.slika);
                break;
            case 3:
                Picasso.get().load(R.drawable.trcanje_u_zatvorenome).into(holder.slika);
                break;
            case 4:
                Picasso.get().load(R.drawable.plivanje_u_bazenu).into(holder.slika);
                break;
        }
        holder.pocetak.setText(v.getVrijeme());
        holder.trajanje.setText(v.getTrajanje());
        holder.udaljenost.setText(v.getUdaljenostKm()+" km");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vjezbaClickListener.onItemClick(v);
            }
        });
    }

    @Override
    public int getItemCount() {
        return podatci==null ? 0 : podatci.size();
    }

    public void setPodaci(List<Vjezba> vjezbe) {
        this.podatci = vjezbe;
    }

    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.setTimeInMillis(time);
        String date = DateFormat.format("dd. MM. yyyy HH:mm", cal).toString();
        return date;
    }

    public class Red extends RecyclerView.ViewHolder {
        private ImageView slika;
        private TextView datum;
        private TextView pocetak;
        private TextView trajanje;
        private TextView udaljenost;
        public Red(View itemView) {
            super(itemView);
            datum = itemView.findViewById(R.id.datum);
            slika = itemView.findViewById(R.id.slika);
            pocetak = itemView.findViewById(R.id.pocetak);
            trajanje = itemView.findViewById(R.id.trajanje);
            udaljenost = itemView.findViewById(R.id.udaljenost);
        }
    }

}
