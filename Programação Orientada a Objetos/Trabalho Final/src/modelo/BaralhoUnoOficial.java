package modelo;

/** Esse é o Baralho do UNO feito com o molde da classe Baralho **/
public class BaralhoUnoOficial extends Baralho {

    // Esse método cria todos os tipos de cartas que existem nesse baralho
    @Override
    protected void criarCartas() {
        Cor[] cores = {Cor.VERMELHO, Cor.AMARELO, Cor.VERDE, Cor.AZUL};

        //Para cada cor é criada uma carta existente nessa cor
        for (Cor c : cores) {

            // A carta 0 está fora do próximo laço for porque no UNO existe apenas 1 carta com número 0 no modelo.Baralho
            cartas.add(new CartaNormal(c, Valor.ZERO, c + " 0"));

            Valor[] valores = {Valor.UM, Valor.DOIS, Valor.TRES, Valor.QUATRO, Valor.CINCO, Valor.SEIS, Valor.SETE, Valor.OITO, Valor.NOVE};
            String[] algarismos = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};

            //Criamos esse for, porque existem 2 cartas de cada cor para cada número de 1 a 9
            for (int i = 0; i < valores.length; i++) {
                cartas.add(new CartaNormal(c, valores[i], c + " " + algarismos[i]));
                cartas.add(new CartaNormal(c, valores[i], c + " " + algarismos[i]));
            }

            // Da mesma forma são 2 cartas para PULAR, INVERTER E MAIS DOIS.
            cartas.add(new CartaAcao(c, Valor.PULAR, c + " Pular"));
            cartas.add(new CartaAcao(c, Valor.PULAR, c + " Pular"));

            cartas.add(new CartaAcao(c, Valor.INVERTER, c + " Inverter"));
            cartas.add(new CartaAcao(c, Valor.INVERTER, c + " Inverter"));

            cartas.add(new CartaAcao(c, Valor.MAIS_DOIS, c + " +2"));
            cartas.add(new CartaAcao(c, Valor.MAIS_DOIS, c + " +2"));
        }

        // Aqui são criadas as cartas especiais, cria 4 cartas coringa e 4 cartas coringa mais quatro
        for (int i = 0; i < 4; i++) {
            cartas.add(new CartaCoringa(Cor.ESPECIAL, Valor.CORINGA, "Coringa"));
            cartas.add(new CartaCoringa(Cor.ESPECIAL, Valor.CORINGA_MAIS_QUATRO, "Coringa +4"));
        }
    }
}