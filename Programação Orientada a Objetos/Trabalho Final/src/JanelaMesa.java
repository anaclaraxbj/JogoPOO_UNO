/** Onde acontece a interface gráfica, ela apenas repassa os cliques e atualiza a tela **/

import javax.swing.*;
import java.awt.*;

public class JanelaMesa {
    private Jogo jogo;
    private JFrame janela; // A moldura principal da janela do Windows/Mac
    private JPanel painelMao; // Onde as cartas do jogador vão ficar organizadas
    private JLabel textoInfo; // O texto de informações no topo da tela

    public JanelaMesa(Jogo jogo) {
        this.jogo = jogo;

        // Configuração básica da janela principal
        janela = new JFrame("Mesa de UNO");
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setSize(1300, 500);

        // Divide a tela em regiões
        janela.setLayout(new BorderLayout());
        textoInfo = new JLabel("", SwingConstants.CENTER);
        janela.add(textoInfo, BorderLayout.NORTH);

        // Organiza os intens um ao lado do outro
        painelMao = new JPanel(new FlowLayout());
        janela.add(painelMao, BorderLayout.CENTER);

        //Botões de Ação
        JPanel painelAcoes = new JPanel(new FlowLayout());
        JButton botaoComprar = new JButton("Comprar Carta");
        JButton botaoUno = new JButton("Gritar UNO!");
        JButton botaoRender = new JButton("Render-se/Sair");

        // EVENTOS
        botaoComprar.addActionListener(e -> {
            jogo.comprarCartaAtual();
            atualizarTela();
        });

        botaoUno.addActionListener(e -> {
            JOptionPane.showMessageDialog(janela, "ATENÇÃO: O jogador " + jogo.getJogadorAtual().getNome() + " gritou UNO!");
        });

        botaoRender.addActionListener(e -> {
            Jogador atual = jogo.getJogadorAtual();
            // Pergunta aos outros se aceitam
            int resposta = JOptionPane.showConfirmDialog(janela,
                    "O jogador " + atual.getNome() + " pediu rendição. Os adversários aceitam?",
                    "Pedido de Rendição", JOptionPane.YES_NO_OPTION);

            if (resposta == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(janela, "Rendição aceita! Partida encerrada.");
                janela.dispose(); // Fecha o jogo
            } else {
                JOptionPane.showMessageDialog(janela, "Rendição recusada! A partida continua.");
            }
        });

        painelAcoes.add(botaoComprar);
        painelAcoes.add(botaoUno);
        painelAcoes.add(botaoRender);
        janela.add(painelAcoes, BorderLayout.SOUTH);

        atualizarTela();
        janela.setVisible(true);
    }

    private void atualizarTela() {
        // MENSAGEM DE VITÓRIA FICA AQUI: Ocorre assim que as cartas acabam
        if (jogo.verificarVitoria()) {
            JOptionPane.showMessageDialog(janela, "FIM DE JOGO! O jogador " + jogo.getVencedor().getNome() + " VENCEU A PARTIDA!");
            janela.dispose();
            return;
        }

        Carta topo = jogo.getTopoDescarte();
        Jogador atual = jogo.getJogadorAtual();

        textoInfo.setText("Vez de: " + atual.getNome() + "  |  Topo: [" + topo.getNomeVisual() + "]  |  Cor Valida: " + jogo.getCorAtual());

        painelMao.removeAll();

        for (int i = 0; i < atual.getMao().size(); i++) {
            Carta c = atual.getMao().get(i);
            String nome = c.getNomeVisual();

            JButton botaoCarta = new JButton("<html><center>" + nome.replace(" ", "<br>") + "</center></html>");

            botaoCarta.setPreferredSize(new Dimension(150, 220));
            botaoCarta.setFont(new Font("Arial", Font.BOLD, 14));
            botaoCarta.setFocusPainted(false);

            // Verifica se é uma carta do baralho convencional pelos símbolos ou nome Joker
            boolean convencional = nome.contains("♥") || nome.contains("♦") ||
                    nome.contains("♣") || nome.contains("♠") ||
                    nome.contains("Joker");

            if (convencional) {
                botaoCarta.setBackground(Color.WHITE); // Fundo branco exigido

                // Copas, Ouros e o Joker +4 ficam vermelhos. O resto fica preto.
                if (nome.contains("♥") || nome.contains("♦") || nome.contains("+4")) {
                    botaoCarta.setForeground(Color.RED);
                } else {
                    botaoCarta.setForeground(Color.BLACK);
                }
            } else {
                // Visual original mantido intacto para o UNO Oficial
                if (c.getCor() == Cor.VERMELHO) {
                    botaoCarta.setBackground(Color.RED);
                    botaoCarta.setForeground(Color.WHITE);
                } else if (c.getCor() == Cor.AZUL) {
                    botaoCarta.setBackground(Color.BLUE);
                    botaoCarta.setForeground(Color.WHITE);
                } else if (c.getCor() == Cor.VERDE) {
                    botaoCarta.setBackground(Color.GREEN);
                    botaoCarta.setForeground(Color.BLACK);
                } else if (c.getCor() == Cor.AMARELO) {
                    botaoCarta.setBackground(Color.YELLOW);
                    botaoCarta.setForeground(Color.BLACK);
                } else {
                    botaoCarta.setBackground(Color.DARK_GRAY);
                    botaoCarta.setForeground(Color.WHITE);
                }
            }

            botaoCarta.addActionListener(e -> {
                if (jogo.validarJogada(c)) {
                    jogo.jogarCarta(atual, c);

                    if (c.getCor() == Cor.ESPECIAL) {
                        Cor[] opcoes = {Cor.VERMELHO, Cor.AZUL, Cor.VERDE, Cor.AMARELO};
                        Cor escolhida = (Cor) JOptionPane.showInputDialog(janela, "Escolha a nova cor:", "Coringa", JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);
                        if (escolhida != null) {
                            jogo.setCorAtual(escolhida);
                        }
                    }
                    atualizarTela();
                } else {
                    JOptionPane.showMessageDialog(janela, "Jogada Inválida! Escolha outra ou compre.");
                }
            });
            painelMao.add(botaoCarta);
        }

        painelMao.revalidate();
        painelMao.repaint();
    }
}