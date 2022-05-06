package ui.activities;

import static ui.activities.ConstatesActivities.CHAVE_PERSONAGEM;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobileagenda.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import dao.PersonagemDAO;
import model.Personagem;

public class ListaPersonagemActivity extends AppCompatActivity {//extends do appCompatActivity para funcionar
    public  static final String TITULO_APPBAR = "Lista de Personagem";
    private final PersonagemDAO dao = new PersonagemDAO();//ele automaticamente importa meu personagem DAO
    private ArrayAdapter<Personagem> adapter;//o adapter busca personagens

   @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){//faz parte do OnCreate o @Nullable Bundle savedInstanceState
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitity_lista_personagem);
        setTitle(TITULO_APPBAR);
        configuraFabNovoPersonagem();
        configuraLista();
    }
    private void configuraFabNovoPersonagem(){
        FloatingActionButton botaoNovoPersonagem = findViewById(R.id.fab_add);
        botaoNovoPersonagem.setOnClickListener(new View.OnClickListener() {//escuta o botão
            @Override
            public void onClick(View view) {
                abreFormulario();
            }//ao clicar abre o formulario
        });
    }
    private void abreFormulario(){//abre formulario
        startActivity(new Intent(this, FormularioPersonagemActivity.class));
    }
    @Override
    protected void onResume()//atualização do personagem
    {
        super.onResume();
        atualizarPersonagem();
    }
    private void atualizarPersonagem(){
        adapter.clear();
        adapter.addAll(dao.todos());
    }
    private void remove(Personagem personagem)//remoção do personagem
    {
        dao.remove(personagem);
        adapter.remove(personagem);
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)//criação do icone com o inflate(inflando ele)
    {
        super.onCreateContextMenu(menu,v,menuInfo);
        //menu.add("Remover");
        getMenuInflater().inflate(R.menu.activity_lista_personagem_menu, menu);
    }
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item)//sobrepôe o metodo e mostra as opções quando for selecionado
    {
        int itemId= item.getItemId();
        if (itemId== R.id.activity_lista_personagem_menu_remover)
        {
            new AlertDialog.Builder(this)
                    .setTitle("Removendo Personagem")
                    .setMessage("Tem certeza que deseja remover?")
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                            Personagem personagemEscolhido = adapter.getItem(menuInfo.position);
                            remove(personagemEscolhido);//configura o adapter view pra ter a remoção do personagem escolhido
                        }
                    })
                    .setNegativeButton("Não", null)//se ele clicar no setNegative ele vai ter informação do nao.
                    .show();//aparece tela debaixo
        }
        return super.onContextItemSelected(item);//retorno
        }
        private void configuraLista(){//configura lista
            ListView listaDePersonagens = findViewById(R.id.activity_main_lista_personagem);//identificação da lista no xml
            configuraAdapter(listaDePersonagens);
            configuraItemPorClique(listaDePersonagens);//cada item é clicado
            registerForContextMenu(listaDePersonagens);//registro dessas informações
    }
    private void configuraItemPorClique(ListView listaDePersonagens)//item por clique
    {
        listaDePersonagens.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicao, long id) {
                Personagem personagemEscolhido = (Personagem) adapterView.getItemAtPosition(posicao);
                abreFormularioEditar(personagemEscolhido);

            }
        });

    }
    private void configuraAdapter (ListView listaDePersonagens){//Configurar lista com os personagens
            adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
            listaDePersonagens.setAdapter(adapter);
    }

    private void abreFormularioEditar(Personagem personagemEscolhido){//edição do personagem

        Intent vaiParaFormulario = new Intent(ListaPersonagemActivity.this, FormularioPersonagemActivity.class);
        vaiParaFormulario.putExtra(CHAVE_PERSONAGEM, personagemEscolhido);
        startActivity(vaiParaFormulario);
    }
}
