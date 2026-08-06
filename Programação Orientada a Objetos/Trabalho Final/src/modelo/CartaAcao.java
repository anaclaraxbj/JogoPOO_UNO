package modelo;

import controle.Jogo;

public class CartaAcao extends Carta {

    public CartaAcao(Cor cor, Valor valor, String nomeVisual) {
        super(cor, valor, nomeVisual);
    }

    @Override
    public void aplicarEfeito(Jogo contexto) {
        if (this.valor == Valor.INVERTER) {
            if (contexto.getQuantidadeJogadores() == 2) {
                contexto.pularProximo();
            } else {
                contexto.inverterSentido();
                contexto.avancarTurno();
            }
        }
        else if (this.valor == Valor.PULAR) {
            contexto.pularProximo();
        }
        else if (this.valor == Valor.MAIS_DOIS) {
            contexto.adicionarDeficit(2);
            contexto.avancarTurno();
        }
    }
}