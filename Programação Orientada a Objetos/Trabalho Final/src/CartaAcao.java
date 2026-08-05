/** Esta classe representa as cartas de ação do UNO . Ela centraliza as regras que alteram o fluxo de turnos da partida.
 */public class CartaAcao extends Carta {

    public CartaAcao(Cor cor, Valor valor, String nomeVisual) {
        super(cor, valor, nomeVisual);
    }

    // A classe Jogo executa o que deve ser feito usando a variavél contexto
    @Override
    public void aplicarEfeito(Jogo contexto) {
        if (this.valor == Valor.INVERTER) {
            // Com 2 jogadores, Inverter age como Pular
            if (contexto.getQuantidadeJogadores() == 2) {
                contexto.pularProximo();
            } else {
                contexto.inverterSentido(); // Com + Jogadores ele inverte o sentido
                contexto.avancarTurno();
            }
        }
        else if (this.valor == Valor.PULAR) {
            contexto.pularProximo();
        }
        else if (this.valor == Valor.MAIS_DOIS) {
            contexto.fazerProximoComprar(2);
            contexto.pularProximo(); // Garante que quem comprou +2 também perca a vez
        }
    }
}