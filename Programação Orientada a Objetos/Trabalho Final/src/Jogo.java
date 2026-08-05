import java.util.*;
/** Um jogo tem os jogadores, um baralho e o descarte do baralho. **/
public class Jogo {

    private ArrayList<Jogador> jogadores = new ArrayList<>();
    private Baralho baralho;
    private ArrayList<Carta> descarte = new ArrayList<>();

    // o indice controla qual rodada está. o sentidoHorario controla como está girando o jogo, e a cor atual controla o topo
    private int indiceAtual = 0;
    private boolean sentidoHorario = true;
    private Cor corAtual;

    public Jogo(Baralho baralho) {
        this.baralho = baralho;
    }

    public void adicionarJogador(Jogador j) {
        jogadores.add(j);
    }

    // Valida se a jogada pode acontecer. Regra: Mesma cor, mesmo valor ou coringa.
    public boolean validarJogada(Carta c) {
        Carta topo = descarte.get(descarte.size() - 1); // pega a carta do topo
        return c.getCor() == Cor.ESPECIAL || c.getCor() == corAtual || c.getValor() == topo.getValor(); // retorna TRUE se a cor for igual ao topo ou valor igual ou for um coringa.
    }

    public void jogarCarta(Jogador j, Carta c) {
        if (validarJogada(c)) { // se for uma jogada válida, remove a carta da mão do jogador, e adiciona no descarte, a cor atual será a descartada.
            j.removerCarta(c);
            descarte.add(c);
            corAtual = c.getCor();

            // O POLIMORFISMO ACONTECE AQUI. O jogo não usa if/else para saber a carta, a jogada será feita de acordo com a carta criada.
            c.aplicarEfeito(this);
        }
    }

    public void comprarCartaAtual() {
        jogadores.get(indiceAtual).adicionarCarta(baralho.comprarCarta()); // pega o jogador atual e adiciona uma carta comprada do baralho
        avancarTurno(); // No UNO, se você compra, passa a vez.
    }

    // MÉTODOS DE CONTROLE

    public void avancarTurno() {
        if (sentidoHorario) { // No sentindoHorario a lista de jogadores vai para frente
            indiceAtual++; // Acrescenta 1 até se igualar ao tamanho dos jogadores
            if (indiceAtual >= jogadores.size()) {
                indiceAtual = 0; // Volta pro início da roda
            }
        } else {
            indiceAtual--; // Se for falso, a lista vai para trás
            if (indiceAtual < 0) { // Diminui 1 até ficar menor que 0
                indiceAtual = jogadores.size() - 1; // Vai pro fim da roda
            }
        }
    }

    public void pularProximo() { // Ao ser usado essa ação avança a vez para quem será afetado
        avancarTurno();
        avancarTurno(); // Para que ele não jogue, pula o alvo e passa a vez para o seguinte
    }

    public void inverterSentido() {
        sentidoHorario = !sentidoHorario; // faz o contrário do sentindo que está indo
    }

    public void fazerProximoComprar(int quantidade) {
        // Descobre quem é o próximo para dar as cartas
        int proximo = indiceAtual;
        if (sentidoHorario) {
            if (proximo + 1 >= jogadores.size()) { // Se o próximo for o último jogador
                proximo = 0; // Volta para o 1º jogador
            } else {
                proximo = proximo + 1; // Anda para o próximo
            }

        } else {
            if (proximo - 1 < 0) {
                proximo = jogadores.size() - 1; // Chegou no início da roda, pula para o último
            } else {
                proximo = proximo - 1; // Anda para trás
            }
        }

        // Entrega as cartas para quem for comprar
        for (int i = 0; i < quantidade; i++) {
            Carta c = baralho.comprarCarta(); // Puxa a carta do baralho
            if (c != null) {
                jogadores.get(proximo).adicionarCarta(c); // Adiciona ela na mão do jogador alvo
            }
        }
    }

    // Prepara a mesa e distribui 7 cartas a cada jogador da lista
    public void iniciarJogo() {
        for (Jogador j : jogadores) {
            for (int i = 0; i < 7; i++) {
                j.adicionarCarta(baralho.comprarCarta());
            }
        }

        Carta primeira = baralho.comprarCarta(); // A 1° carta é a que está no topo e não foi jogada por nenhum jogador:
        // Cartas Coringas não podem iniciar o jogo. Compra-se outra.
        while (primeira.getCor() == Cor.ESPECIAL) {
            descarte.add(primeira);
            primeira = baralho.comprarCarta();
        }

        descarte.add(primeira);
        corAtual = primeira.getCor();
    }

    // SET
    public void setCorAtual(Cor c) {this.corAtual = c;}

    // GETTERS
    public Jogador getVencedor() {
        for (Jogador j : jogadores) {
            if (j.getMao().isEmpty()) {
                return j;
            }
        }
        return null;
    }
    public boolean verificarVitoria() {
        for (Jogador j : jogadores) {
            if (j.getMao().isEmpty()) {
                return true; // Encontrou um jogador sem cartas
            }
        }
        return false;
    }

    // MÉTODOS GETTERS
    public Jogador getJogadorAtual() { return jogadores.get(indiceAtual); }
    public Carta getTopoDescarte() { return descarte.get(descarte.size() - 1); }
    public Cor getCorAtual() { return corAtual; }
    public Baralho getBaralho() {return baralho;}
    public int getQuantidadeJogadores() {return jogadores.size();}
}