package com.example.controledecompras.lista;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.controledecompras.R;
import com.example.controledecompras.adapter.ValidadeAdapter;
import com.example.controledecompras.bean.ValidadeBEAN;
import com.example.controledecompras.dao.ValidadeDAO;

import java.util.ArrayList;
import java.util.List;

public class ListaActivityValidade extends AppCompatActivity {

    private ListView listviewValidade;
    private ValidadeDAO validadeDAO;
    private List<ValidadeBEAN> validadeBEAN;
    private List<ValidadeBEAN> validadeBEANS = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_activity_validade);

        listviewValidade = findViewById(R.id.listviewValidade);
        validadeDAO = new ValidadeDAO(this);
        validadeBEAN = validadeDAO.obterTodos();
        validadeBEANS.addAll(validadeBEAN);

        ValidadeAdapter adaptador = new ValidadeAdapter(this, validadeBEANS);
        listviewValidade.setAdapter(adaptador);
        registerForContextMenu(listviewValidade);

        listviewValidade.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final ValidadeBEAN validadeBEANExclui = validadeBEANS.get(position);
                AlertDialog dialog = new AlertDialog.Builder(ListaActivityValidade.this)
                        .setTitle("Atenção!")
                        .setMessage("Realmente deseja excluir esse dado:")
                        .setNegativeButton("Não",null)
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                validadeBEANS.remove(validadeBEANExclui);
                                validadeBEAN.remove(validadeBEANExclui);
                                validadeDAO.excluir(validadeBEANExclui);
                                listviewValidade.invalidateViews();
                            }
                        }).create();
                dialog.show();
            }
        });
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_contexto_validade,menu);
    }

    @Override
    public void onResume() {
        super.onResume();
        validadeBEAN = validadeDAO.obterTodos();
        validadeBEANS.clear();
        validadeBEANS.addAll(validadeBEAN);
        listviewValidade.invalidateViews();
    }
}