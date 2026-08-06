package modelo;

import java.util.*;

// Um jogador tem um nome e as cartas que ele esta segurando na mão.
public class Jogador {
    private String nome;
    private ArrayList<Carta> mao = new ArrayList<>();
    private boolean disseUno = false;

    public Jogador(String nome) {
        this.nome = nome;
    }

    //getters e setters
    public String getNome() {return nome;}
    public ArrayList<Carta> getMao() {return mao;}
    public boolean isDisseUno() { return disseUno; }
    public void setDisseUno(boolean disseUno) { this.disseUno = disseUno; }

    public void adicionarCarta(Carta c) {
        if (c != null) {
            mao.add(c);
            if (mao.size() > 1) {
                this.disseUno = false;
            }
        }
    }

    public void removerCarta(Carta c) {
        mao.remove(c);
    }
}
