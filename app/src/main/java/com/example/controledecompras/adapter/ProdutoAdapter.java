package com.example.controledecompras.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.controledecompras.R;
import com.example.controledecompras.bean.ProdutoBEAN;

import java.text.DecimalFormat;
import java.util.List;

public class ProdutoAdapter extends BaseAdapter {

    private List<ProdutoBEAN> produtoBEAN;
    private Activity activity;
    private int cor = 0;
    private DecimalFormat dinheiro = new DecimalFormat("R$ ###,##0.00");
    private DecimalFormat qtd = new DecimalFormat("###,##0.00");

    public ProdutoAdapter(Activity activity, List<ProdutoBEAN> produtoBEAN){
        this.activity = activity;
        this.produtoBEAN = produtoBEAN;
    }

    @Override
    public int getCount() {
        return produtoBEAN.size();
    }

    @Override
    public Object getItem(int i) {
        return produtoBEAN.get(i);
    }

    @Override
    public long getItemId(int i) {
        return produtoBEAN.get(i).getId();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View v = activity.getLayoutInflater().inflate(R.layout.item_produto, parent,false);
        TextView codigobarra    = v.findViewById(R.id.txtCodigobarra);
        TextView nome           = v.findViewById(R.id.txtNome);
        TextView quantidade     = v.findViewById(R.id.txtQuantidade);
        ImageView foto          = v.findViewById(R.id.imgFoto2);
        TextView localcompra    = v.findViewById(R.id.txtLocalcompra);
        TextView comprar        = v.findViewById(R.id.txtComprar);
        TextView valor          = v.findViewById(R.id.txtValor);
        TextView datacompra     = v.findViewById(R.id.txtDatavalidade);


        ProdutoBEAN a = produtoBEAN.get(i);
        codigobarra.setText(a.getCodigobarra());
        nome.setText(a.getNomeproduto());
        quantidade.setText(qtd.format(a.getQuantidade()));

        byte[] im = a.getFoto();
        Bitmap bitmap = BitmapFactory.decodeByteArray(im, 0, im.length);
        foto.setImageBitmap(bitmap);

        localcompra.setText(a.getLocalcompra());

        if(a.getComprar().equals("sim")){
            comprar.setTextColor(comprar.getResources().getColor(R.color.vermelho));
            comprar.setBackgroundColor(Color.parseColor("#F3E6A4")); // fundo amarelo
            comprar.setText(a.getComprar());
        }else{
            comprar.setTextColor(comprar.getResources().getColor(R.color.purple_700));
            comprar.setText(a.getComprar());
        }

        valor.setText(dinheiro.format(a.getValor()));
        datacompra.setText(a.getDatacompra());

        return v;
    }
}
