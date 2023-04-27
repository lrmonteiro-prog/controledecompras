package com.example.controledecompras.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.controledecompras.R;
import com.example.controledecompras.bean.CompraBEAN;
import com.example.controledecompras.bean.ProdutoBEAN;
import com.example.controledecompras.bean.ValidadeBEAN;
import com.example.controledecompras.dao.ProdutoDAO;
import com.example.controledecompras.dao.ValidadeDAO;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ValidadeAdapter extends BaseAdapter {

    private List<ValidadeBEAN> validadeBEAN;
    private Activity activity;

    private String dataCorrente;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    public ValidadeAdapter(Activity activity, List<ValidadeBEAN> validadeBEAN){
        this.activity = activity;
        this.validadeBEAN = validadeBEAN;

        dataCorrente = sdf.format(new Date());
    }

    @Override
    public int getCount() {
        return validadeBEAN.size();
    }

    @Override
    public Object getItem(int i) {
        return validadeBEAN.get(i);
    }

    @Override
    public long getItemId(int i) {
        return validadeBEAN.get(i).getId();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {

        View v = activity.getLayoutInflater().inflate(R.layout.item_validade, parent,false);
        TextView codigobarra    = v.findViewById(R.id.txtCodigobarra);
        TextView nomeproduto    = v.findViewById(R.id.txtNome);
        TextView datavalidade     = v.findViewById(R.id.txtDatavalidade);
        TextView txtResultado     = v.findViewById(R.id.txtResultado);

        ValidadeBEAN a = validadeBEAN.get(i);
        codigobarra.setText(a.getCodigobarra());
        nomeproduto.setText(a.getNomeproduto());
        // início: Calcula quantos dias da validade do produto
        Calendar data1 = Calendar.getInstance();
        Calendar data2 = Calendar.getInstance();
        try {
            data1.setTime(sdf.parse(dataCorrente));
            data2.setTime(sdf.parse(String.valueOf(a.getDatavalidade())));
        } catch (java.text.ParseException e ) {}
        int dias = data2.get(Calendar.DAY_OF_YEAR) -
                data1.get(Calendar.DAY_OF_YEAR);
        txtResultado.setText(String.valueOf(dias));
        // fim: Calcula quantos dias da validade do produto
        datavalidade.setText(a.getDatavalidade());
        return v;
    }
}
