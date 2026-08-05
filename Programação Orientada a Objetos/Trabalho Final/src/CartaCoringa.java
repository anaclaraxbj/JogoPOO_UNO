public class CartaCoringa extends Carta {

    public CartaCoringa(Cor cor, Valor valor, String nomeVisual) {
        super(cor, valor, nomeVisual);
    }

    @Override
    public void aplicarEfeito(Jogo contexto) {
        if (this.valor == Valor.CORINGA_MAIS_QUATRO) {
            contexto.fazerProximoComprar(4); // Força o jogador a comprar 4 cartas
            contexto.pularProximo(); // Pula a vez de quem comprou as 4 cartas
        } else {
            contexto.avancarTurno(); // Coringa normal apenas passa a vez
        }
    }
}