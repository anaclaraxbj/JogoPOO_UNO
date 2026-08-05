import java.util.ArrayList;

public class Jogo {
    private ArrayList<Jogador> jogadores = new ArrayList<>();
    private Baralho baralho;
    private ArrayList<Carta> descarte = new ArrayList<>();

    private int indiceAtual = 0;
    private boolean sentidoHorario = true;
    private Cor corAtual;

    private boolean gritouUno = false;
    private int deficitCartas = 0; // Controla o acúmulo de +2 e +4

    public Jogo(Baralho baralho) {
        this.baralho = baralho;
    }

    public void adicionarJogador(Jogador j) {
        jogadores.add(j);
    }

    public void iniciarJogo() {
        for (Jogador j : jogadores) {
            for (int i = 0; i < 7; i++) {
                j.adicionarCarta(baralho.comprarCarta());
            }
        }

        Carta primeira = baralho.comprarCarta();
        while (primeira.getCor() == Cor.ESPECIAL) {
            descarte.add(primeira);
            primeira = baralho.comprarCarta();
        }

        descarte.add(primeira);
        corAtual = primeira.getCor();
    }

    public boolean validarJogada(Carta c) {
        // REGRA DE EMPILHAR: Se o jogador está sofrendo um ataque (+2 ou +4)
        if (deficitCartas > 0) {
            // Só pode rebater com outro +4, ou com um +2 que seja da mesma cor exigida
            if (c.getValor() == Valor.CORINGA_MAIS_QUATRO) return true;
            if (c.getValor() == Valor.MAIS_DOIS && c.getCor() == corAtual) return true;
            return false;
        }

        // Regra Normal
        Carta topo = descarte.get(descarte.size() - 1);
        return c.getCor() == Cor.ESPECIAL || c.getCor() == corAtual || c.getValor() == topo.getValor();
    }

    public void jogarCarta(Jogador j, Carta c) {
        if (validarJogada(c)) {
            j.removerCarta(c);
            descarte.add(c);
            corAtual = c.getCor();
            c.aplicarEfeito(this);
        }
    }

    public Carta comprarCartaAtual() {
        Carta comprada = baralho.comprarCarta();

        if (comprada != null) {
            jogadores.get(indiceAtual).adicionarCarta(comprada);

            if (deficitCartas > 0) {
                deficitCartas--;
                if (deficitCartas == 0) {
                    avancarTurno();
                }
            } else {
                avancarTurno();
            }
        }

        return comprada; // Retorna a carta para a interface gráfica
    }

    // --- MÉTODOS DE CONTROLE ---

    public void avancarTurno() {
        gritouUno = false;

        if (sentidoHorario) {
            indiceAtual++;
            if (indiceAtual >= jogadores.size()) {
                indiceAtual = 0;
            }
        } else {
            indiceAtual--;
            if (indiceAtual < 0) {
                indiceAtual = jogadores.size() - 1;
            }
        }
    }

    public void pularProximo() {
        avancarTurno();
        avancarTurno();
    }

    public void inverterSentido() {
        sentidoHorario = !sentidoHorario;
    }

    public void adicionarDeficit(int quantidade) {
        this.deficitCartas += quantidade;
    }

    public void punirFaltaDeUno(Jogador j) {
        j.adicionarCarta(baralho.comprarCarta());
        j.adicionarCarta(baralho.comprarCarta());
    }

    public void escolherNovaCor() { }
    public void setCorAtual(Cor c) { this.corAtual = c; }

    // --- GETTERS E SETTERS ---
    public Jogador getJogadorAtual() { return jogadores.get(indiceAtual); }
    public Carta getTopoDescarte() { return descarte.get(descarte.size() - 1); }
    public Cor getCorAtual() { return corAtual; }
    public boolean verificarVitoria() { return getJogadorAtual().getMao().isEmpty(); }
    public int getQuantidadeJogadores() { return jogadores.size(); }
    public boolean isGritouUno() { return gritouUno; }
    public void setGritouUno(boolean gritouUno) { this.gritouUno = gritouUno; }
    public int getDeficitCartas() { return deficitCartas; }
}