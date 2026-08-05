import java.util.*;

// Um jogador tem um nome e as cartas que ele esta segurando na mão.
public class Jogador {
    private String nome;
    private ArrayList<Carta> mao = new ArrayList<>();

    public Jogador(String nome) {
        this.nome = nome;
    }

    //Métodos GETTERS
    public String getNome() {return nome;}
    public ArrayList<Carta> getMao() {return mao;}

    public void adicionarCarta(Carta c) {
        if (c != null) {
            mao.add(c);
        }
    }

    public void removerCarta(Carta c) {
        mao.remove(c);
    }
}