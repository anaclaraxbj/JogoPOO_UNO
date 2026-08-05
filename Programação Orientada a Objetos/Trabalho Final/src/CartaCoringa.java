public class CartaCoringa extends Carta {

    public CartaCoringa(Cor cor, Valor valor, String nomeVisual) {
        super(cor, valor, nomeVisual);
    }

    @Override
    public void aplicarEfeito(Jogo contexto) {
        if (this.valor == Valor.CORINGA_MAIS_QUATRO) {
            contexto.adicionarDeficit(4);
            contexto.avancarTurno(); // Passa a bomba para o próximo
        } else {
            contexto.avancarTurno();
        }
    }
}