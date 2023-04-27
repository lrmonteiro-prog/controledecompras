package com.example.controledecompras.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.controledecompras.R;
import com.example.controledecompras.bean.CompraBEAN;
import com.example.controledecompras.bean.ProdutoBEAN;

import java.text.DecimalFormat;
import java.util.List;

public class ComparaPrecoAdapter extends BaseAdapter {

    private List<ProdutoBEAN> produtoBEAN;
    private List<CompraBEAN> compraBEAN;
    private Activity activity;
    private int cor = 0;
    private DecimalFormat dinheiro = new DecimalFormat("R$ ###,##0.00");
    private DecimalFormat qtd = new DecimalFormat("###,##0.00");

    public ComparaPrecoAdapter(Activity activity, List<CompraBEAN> compraBEAN){
        this.activity = activity;
        this.compraBEAN = compraBEAN;
    }

    @Override
    public int getCount() {
        return compraBEAN.size();
    }

    @Override
    public Object getItem(int i) {
        return compraBEAN.get(i);
    }

    @Override
    public long getItemId(int i) {
        return compraBEAN.get(i).getId();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View v = activity.getLayoutInflater().inflate(R.layout.item_comparapreco, parent,false);
        TextView codigobarra    = v.findViewById(R.id.txtCodigobarra);
        TextView localcompra    = v.findViewById(R.id.txtLocalcompra);
        TextView datacompra     = v.findViewById(R.id.txtDatavalidade);
        TextView valor          = v.findViewById(R.id.txtValor);


        CompraBEAN a = compraBEAN.get(i);
      //  codigobarra.setText(a.getCodigobarra());

      //  byte[] im = a.getFoto();
      //  Bitmap bitmap = BitmapFactory.decodeByteArray(im, 0, im.length);
      //  foto.setImageBitmap(bitmap);

        localcompra.setText(a.getLocalcompra());
        valor.setText(dinheiro.format(a.getValor()));
        datacompra.setText(a.getDatacompra());

        return v;
    }
}
