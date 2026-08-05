/** Ela estende a classe abstrata Carta com Herança. **/
public class CartaNormal extends Carta {
    public CartaNormal(Cor cor, Valor valor, String nomeVisual) {
        super(cor, valor, nomeVisual);
    }

    // Usa-se Polimorfismo para implementar o método abstrato
    @Override
    public void aplicarEfeito(Jogo contexto) {
        contexto.avancarTurno(); // Como ela é normal seu unico efeito é passar a vez
    }
}