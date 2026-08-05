import javax.swing.*;
import java.awt.*;

/** Esta é a classe que dá o ínicio no programa, pq possui o método main..
 */
public class JanelaConfiguracao {

    public static void main(String[] args) {

        // Cria a janelinha inicial de configuração
        JFrame janela = new JFrame("Configuração do UNO");
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setSize(400, 150);
        janela.setLayout(new FlowLayout());

        // Componentes para receber os nomes dos jogadores
        JLabel textoInfo = new JLabel("Digite os nomes separados por vírgula (2 a 10):");
        JTextField campoNomes = new JTextField("João, Maria, Pedro", 25);

        // Botões para o jogador escolher a regra do baralho
        JButton botaoOficial = new JButton("Jogar UNO Oficial");
        JButton botaoConvencional = new JButton("Jogar Baralho Convencional");

        janela.add(textoInfo);
        janela.add(campoNomes);
        janela.add(botaoOficial);
        janela.add(botaoConvencional);

        //  aqui dá ínicio a partida com o clique
        botaoOficial.addActionListener(evento -> iniciarPartida(new BaralhoUnoOficial(), campoNomes.getText(), janela));
        botaoConvencional.addActionListener(evento -> iniciarPartida(new BaralhoConvencional(), campoNomes.getText(), janela));

        janela.setVisible(true);
    }

    private static void iniciarPartida(Baralho baralhoEscolhido, String nomesTexto, JFrame janelaAtual) {
        String[] nomes = nomesTexto.split(","); // Separa os nomes pela vírgula

        if (nomes.length < 2 || nomes.length > 10) {
            JOptionPane.showMessageDialog(janelaAtual, "Erro: O jogo precisa ter entre 2 e 10 jogadores!");
            return;
        }

        Jogo jogo = new Jogo(baralhoEscolhido);

        for (int i = 0; i < nomes.length; i++) {
            // O .trim() remove espaços em branco sobrando antes e depois do nome
            jogo.adicionarJogador(new Jogador(nomes[i].trim()));
        }

        jogo.iniciarJogo();
        new JanelaMesa(jogo);
        janelaAtual.dispose();// Fecha e destrói essa janelinha de configuração, pois não precisaremos mais dela
    }
}