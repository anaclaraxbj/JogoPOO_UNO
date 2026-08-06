package modelo;

/** Esse é o modelo.Baralho Convencional feito com o molde da classe modelo.Baralho **/
public class BaralhoConvencional extends Baralho {
    @Override
    protected void criarCartas() {


        Cor[] cores = {Cor.VERMELHO, Cor.AMARELO, Cor.VERDE, Cor.AZUL}; // ♥=Copas/Vermelho, ♦=Ouros/Amarelo, ♣=Paus/Verde, ♠=Espadas/Azul).

        // Uso de símbolos reais de naipes para o visual do baralho (usamos ALT+4)
        String[] naipes = {"♥", "♦", "♣", "♠"};

        // São criadas 4 cartas para cada um dos 4 naipes, uma de cada cor e naipe, representando a equivalência de ♦=Amarelo.
        for (int i = 0; i < 4; i++) {
            Cor c = cores[i];
            String n = naipes[i];

            cartas.add(new CartaNormal(c, Valor.UM, n + " A"));
            cartas.add(new CartaNormal(c, Valor.DOIS, n + " 2"));
            cartas.add(new CartaNormal(c, Valor.TRES, n + " 3"));
            cartas.add(new CartaNormal(c, Valor.QUATRO, n + " 4"));
            cartas.add(new CartaNormal(c, Valor.CINCO, n + " 5"));
            cartas.add(new CartaNormal(c, Valor.SEIS, n + " 6"));
            cartas.add(new CartaNormal(c, Valor.SETE, n + " 7"));
            cartas.add(new CartaNormal(c, Valor.OITO, n + " 8"));
            cartas.add(new CartaNormal(c, Valor.NOVE, n + " 9"));
            cartas.add(new CartaNormal(c, Valor.ZERO, n + " 10"));

            //Figuras (J, Q, K) equivalentes às Ações (Pular, Inverter, +2).
            cartas.add(new CartaAcao(c, Valor.PULAR, n + " J"));
            cartas.add(new CartaAcao(c, Valor.INVERTER, n + " Q"));
            cartas.add(new CartaAcao(c, Valor.MAIS_DOIS, n + " K"));
        }
        // As cartas especiais estão fora do for porque são apenas 2 por valor especial
        cartas.add(new CartaCoringa(Cor.ESPECIAL, Valor.CORINGA, "Joker"));
        cartas.add(new CartaCoringa(Cor.ESPECIAL, Valor.CORINGA, "Joker"));
        cartas.add(new CartaCoringa(Cor.ESPECIAL, Valor.CORINGA_MAIS_QUATRO, "Joker +4"));
        cartas.add(new CartaCoringa(Cor.ESPECIAL, Valor.CORINGA_MAIS_QUATRO, "Joker +4"));
    }
}