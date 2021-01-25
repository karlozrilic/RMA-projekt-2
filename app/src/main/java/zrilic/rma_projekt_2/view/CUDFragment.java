package zrilic.rma_projekt_2.view;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zrilic.rma_projekt_2.R;
import zrilic.rma_projekt_2.model.Vjezba;
import zrilic.rma_projekt_2.viewmodel.VjezbaViewModel;

public class CUDFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    @BindView(R.id.slika)
    ImageView slika;
    @BindView(R.id.aktivnost)
    Spinner aktivnost;
    @BindView(R.id.trajanje)
    TextView trajanje;
    @BindView(R.id.udaljenost)
    TextView udaljenost;
    @BindView(R.id.datum)
    TextView datum;
    @BindView(R.id.vrijeme)
    TextView vrijeme;

    @BindView(R.id.aktivnostLayout)
    LinearLayout aktivnostLayout;
    @BindView(R.id.trajanjeLayout)
    LinearLayout trajanjeLayout;
    @BindView(R.id.udaljenostLayout)
    LinearLayout udaljenostLayout;
    @BindView(R.id.datumLayout)
    LinearLayout datumLayout;
    @BindView(R.id.vrijemeLayout)
    LinearLayout vrijemeLayout;

    @BindView(R.id.novaVjezba)
    Button novaVjezba;
    @BindView(R.id.izmjeniVjezbu)
    Button izmjeniVjezbu;
    @BindView(R.id.izbrisiVjezbu)
    Button izbrisiVjezbu;

    VjezbaViewModel model;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cud, container, false);
        ButterKnife.bind(this, view);
        model = ((MainActivity) getActivity()).getModel();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.aktivnosti, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        aktivnost.setAdapter(adapter);
        aktivnost.setOnItemSelectedListener(this);

        aktivnostLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aktivnost.performClick();
            }
        });

        trajanjeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trajanjePopup();
            }
        });
        udaljenostLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                udaljenostPopup();
            }
        });
        datumLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datumPopup();
            }
        });
        vrijemeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vrijemePopup();
            }
        });

        if (model.getVjezba().getId() == 0) {
            definirajNovaVjezba();
            return view;
        }

        definirajIzmjenaBrisanjeVjezbe();
        return view;
    }

    private void definirajNovaVjezba() {
        izmjeniVjezbu.setVisibility(View.GONE);
        izbrisiVjezbu.setVisibility(View.GONE);
        novaVjezba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                novaVjezba();
            }
        });
    }

    private void novaVjezba() {
        if (checkAll()) {
            model.getVjezba().setAktivnost(aktivnost.getSelectedItemPosition());
            model.getVjezba().setTrajanje(trajanje.getText().toString());
            model.getVjezba().setUdaljenostKm(udaljenost.getText().toString());

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd. MM. yyyy.");
            Date dat = null;
            try {
                dat = dateFormat.parse(datum.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            model.getVjezba().setDatum(dat == null ? new Date().getTime() : dat.getTime());
            model.getVjezba().setVrijeme(vrijeme.getText().toString());

            model.dodajNovuVjezbu();
            nazad();
        } else {
            Toast.makeText(this.getActivity(), getString(R.string.ispunitePodatke), Toast.LENGTH_SHORT).show();
        }

    }

    private void definirajIzmjenaBrisanjeVjezbe() {
        Vjezba v = model.getVjezba();
        novaVjezba.setVisibility(View.GONE);
        aktivnost.setSelection(v.getAktivnost());
        trajanje.setText(v.getTrajanje());
        udaljenost.setText(v.getUdaljenostKm());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd. MM. yyyy.");
        String dateTime = dateFormat.format(v.getDatum());
        datum.setText(dateTime);
        vrijeme.setText(v.getVrijeme());

        izmjeniVjezbu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                izmjeniVjezbu();
            }
        });
        izbrisiVjezbu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                izbrisiVjezbu();
            }
        });
    }

    private void izmjeniVjezbu() {
        if (checkAll()) {
            model.getVjezba().setAktivnost(aktivnost.getSelectedItemPosition());
            model.getVjezba().setTrajanje(trajanje.getText().toString());
            model.getVjezba().setUdaljenostKm(udaljenost.getText().toString());

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd. MM. yyyy.");
            Date dat = null;
            try {
                dat = dateFormat.parse(datum.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            model.getVjezba().setDatum(dat == null ? new Date().getTime() : dat.getTime());
            model.getVjezba().setVrijeme(vrijeme.getText().toString());
            model.promjeniVjezbu();
            nazad();
        } else {
            Toast.makeText(this.getActivity(), getString(R.string.ispunitePodatke), Toast.LENGTH_SHORT).show();
        }
    }

    private void izbrisiVjezbu() {
        model.obrisiVjezbu();
        nazad();
    }

    private void trajanjePopup() {
        int hour = 00;
        int minute = 00;
        if (model.getVjezba().getTrajanje() != null && model.getVjezba().getTrajanje() != "00:00") {
            String[] time = model.getVjezba().getTrajanje().split(":");
            hour = Integer.parseInt(time[0]);
            minute = Integer.parseInt(time[1]);
        }
        TimePickerDialog mTimePicker = new TimePickerDialog(this.getActivity(), TimePickerDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar cal = Calendar.getInstance();
                cal.setTimeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
                cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                cal.set(Calendar.MINUTE, minute);
                DateFormat dateFormat = new SimpleDateFormat("HH:mm");
                String strDate = dateFormat.format(cal.getTime());
                Log.wtf("DATE", cal.getTime().toString());
                trajanje.setText(strDate);
            }
        }, hour, minute, true);
        mTimePicker.setTitle(getString(R.string.trajanjePopup));
        mTimePicker.show();
    }

    private void udaljenostPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setTitle(getString(R.string.udaljenostPopup));

        final EditText input = new EditText(this.getActivity());
        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        input.setText(model.getVjezba().getUdaljenostKm());
        builder.setView(input);

        builder.setPositiveButton(getString(R.string.udaljenostPopupUredu), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                udaljenost.setText(input.getText().toString());
            }
        });
        builder.setNegativeButton(getString(R.string.udaljenostPopupOdustani), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void datumPopup() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        if (model.getVjezba().getDatum() != 0) {
            cal.setTimeInMillis(model.getVjezba().getDatum());
            year = cal.get(Calendar.YEAR);
            month = cal.get(Calendar.MONTH);
            day = cal.get(Calendar.DAY_OF_MONTH);
        }
        DatePickerDialog datePicker = new DatePickerDialog(this.getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar cal = Calendar.getInstance();
                cal.set(year, month, dayOfMonth);
                DateFormat dateFormat = new SimpleDateFormat("dd. MM. yyyy.");
                String strDate = dateFormat.format(cal.getTime());
                datum.setText(strDate);
            }
        }, year, month, day);
        datePicker.setTitle(getString(R.string.datumPopup));
        datePicker.show();
    }

    private void vrijemePopup() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minutes = cal.get(Calendar.MINUTE);
        if (model.getVjezba().getVrijeme() != null && model.getVjezba().getVrijeme() != "00:00") {
            String[] vri = model.getVjezba().getVrijeme().split(":");
            hour = Integer.parseInt(vri[0]);
            minutes = Integer.parseInt(vri[1]);
        }
        TimePickerDialog timePicker = new TimePickerDialog(this.getActivity(), TimePickerDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar cal = Calendar.getInstance();
                cal.setTimeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
                cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                cal.set(Calendar.MINUTE, minute);
                DateFormat dateFormat = new SimpleDateFormat("HH:mm");
                String strDate = dateFormat.format(cal.getTime());
                vrijeme.setText(strDate);
            }
        }, hour, minutes, true);
        timePicker.setTitle(getString(R.string.vrijemePopup));
        timePicker.show();
    }

    private boolean checkAll() {
        boolean trajanjeB = false;
        boolean udaljenostB = false;
        boolean datumB = false;
        boolean vrijemeB = false;
        if (!trajanje.getText().equals("")) {
            trajanjeB = true;
        }
        if (!udaljenost.getText().equals("")) {
            udaljenostB = true;
        }
        if (!datum.getText().equals("")) {
            datumB = true;
        }
        if (!vrijeme.getText().equals("")) {
            vrijemeB = true;
        }
        return trajanjeB && udaljenostB && datumB && vrijemeB;
    }

    @OnClick(R.id.nazad)
    public void nazad(){
        ((MainActivity)getActivity()).read();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                Picasso.get().load(R.drawable.trcanje_na_otvorenome).into(slika);
                break;
            case 1:
                Picasso.get().load(R.drawable.setanje_na_otvorenom).into(slika);
                break;
            case 2:
                Picasso.get().load(R.drawable.bicikla_na_otvorenom).into(slika);
                break;
            case 3:
                Picasso.get().load(R.drawable.trcanje_u_zatvorenome).into(slika);
                break;
            case 4:
                Picasso.get().load(R.drawable.plivanje_u_bazenu).into(slika);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
