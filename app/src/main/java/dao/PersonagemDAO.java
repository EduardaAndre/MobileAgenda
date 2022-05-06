package dao;

import java.util.ArrayList;
import java.util.List;

import model.Personagem;

public class PersonagemDAO {
    private final static List<Personagem> personagens = new ArrayList<>();
    private static int contadorDeIds = 1;

    public void salva(Personagem personagemSalvo) { //parametro destinado a ele
        personagemSalvo.setId(contadorDeIds);//todas as vezes que salva entra para a lista de array
        personagens.add(personagemSalvo);
        atualizaId();
    }

    private void atualizaId() {//soma mais um(numero de ids da lista)
        contadorDeIds++;
    }

    public void edita(Personagem personagem) {//Metodo para editar um personagem
        Personagem personagemEncontrado = buscaPersonagemId(personagem);
        if (personagemEncontrado != null) {
            int posicaoDoPersonagem = personagens.indexOf(personagemEncontrado);
            personagens.set(posicaoDoPersonagem, personagem);

        }

    }

    private Personagem buscaPersonagemId(Personagem personagem) {//busca o personagem pelo Id
        for (Personagem p :
                personagens) {
            if (p.getId() == personagem.getId()) {
                return p;
            }

        }
        return null;
    }

    public List<Personagem> todos() {//cria uma nova lista da classe personagem e retorna a classe do personagem deles
        return new ArrayList<>(personagens);
    }

    public void remove(Personagem personagem) {//remove o personagem
        Personagem personagemDevolvido = buscaPersonagemId(personagem);
        if (personagemDevolvido != null) {
            personagens.remove(personagemDevolvido);
        }
    }
}
