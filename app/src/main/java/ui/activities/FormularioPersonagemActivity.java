package ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobileagenda.R;

import dao.PersonagemDAO;
import model.Personagem; //tudo que estou importando.

import static ui.activities.ConstatesActivities.CHAVE_PERSONAGEM;

public class FormularioPersonagemActivity extends AppCompatActivity {
    private static final String TITULO_APPBAR_EDITA_PERSONAGEM = "Editar o Personagem";
    private static final String TITULO_APPBAR_NOVO_PERSONAGEM = "Novo personagem";
    private EditText campoNome;//fazer referencia do xml que será direcionado para o DAO
    private EditText campoNascimento;
    private EditText campoAltura;
    private  final PersonagemDAO dao = new PersonagemDAO();
    private Personagem personagem; //Declarando variaveis

    @Override//subeescreve super classe
    public boolean onCreateOptionsMenu(Menu menu){//onCreate referencia do meu formulario para aparecer em cima da tela
        getMenuInflater().inflate(R.menu.activity_formulario_personagem_menu_salvar, menu);//(inflate) inflando pra chamar referencia para o click que vou dá
        return super.onCreateOptionsMenu(menu);//retorna
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){//boolean(aciona ou nao aciona o item)
        int itemId = item.getItemId();//pega o Id do item
        if (itemId == R.id.activity_formulario_personagem_menu_salvar)//meu item id tem como referencia o id.activity_formulario_personagem_menu_salvar
        {
            finalizarFormulario();//chama o item
        }
        return super.onOptionsItemSelected(item);//retorna
    }
    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);//OnCreate abre o xml que preciso
        setContentView(R.layout.activity_formulario_personagem);//chama o activity formulario personagem
        inicializacaoCampos();//inicialização dos campos
        carregaPersonagem();//carrega personagem
        /*checaPermissoes();//checa permissao nas cameras*/
    }
    private void carregaPersonagem(){
        Intent dados = getIntent();
        if (dados.hasExtra(CHAVE_PERSONAGEM))//hasExtra retorna alguma informaçao que tenha no dado, dentro da chave
        {
            setTitle(TITULO_APPBAR_EDITA_PERSONAGEM);//cabeçalho
            personagem = (Personagem) dados.getSerializableExtra(CHAVE_PERSONAGEM);
            preencheCampos();
        }
        else {
            setTitle(TITULO_APPBAR_NOVO_PERSONAGEM);//se ele nao é um editar personagem, ele é um titulo novo personagem
            personagem = new Personagem();
        }
    }
    private void preencheCampos()//preenchimento do campo
    {
        campoNome.setText(personagem.getNome());
        campoAltura.setText(personagem.getAltura());
        campoNascimento.setText(personagem.getNascimento());//Informações do personagem: nome, altura, nascimento
    }
   private void finalizarFormulario()//usado na finalização do formulario
   {
       preencherPersonagem();
       if (personagem.IdValido()){//identificação do ID
           dao.edita(personagem);
           finish();//quebra o formulario e manda pra lista
       }
       else {//cria um novo personagem
           dao.salva(personagem);
       }
       finish();
   }
   private void inicializacaoCampos() {//metodo inicialização de campos, procura referencias dentro do xml para criar os dados abaixo
       campoNome = findViewById(R.id.editText_nome);
       campoNascimento = findViewById(R.id.editText_nascimento);
       campoAltura = findViewById(R.id.editText_altura);

   }

        private void preencherPersonagem(){

           String nome = campoNome.getText().toString();//gettext pra buscar informação
           String nascimento = campoNascimento.getText().toString();
           String altura = campoAltura.getText().toString();

           personagem.setNome(nome);
           personagem.setAltura(altura);
           personagem.setNascimento(nascimento);
       }
   }

