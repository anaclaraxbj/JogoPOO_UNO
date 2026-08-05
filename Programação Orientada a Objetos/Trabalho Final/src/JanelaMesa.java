/** Onde acontece a interface gráfica, ela apenas repassa os cliques e atualiza a tela **/

import javax.swing.*;
import java.awt.*;

public class JanelaMesa {
    private Jogo jogo;
    private JFrame janela; // A moldura principal da janela do Windows/Mac
    private JPanel painelMao; // Onde as cartas do jogador vão ficar organizadas
    private JLabel textoInfo; // O texto de informações no topo da tela
    private JButton botaoUno;

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
        botaoUno = new JButton("Gritar UNO!");
        JButton botaoRender = new JButton("Render-se/Sair");

        // EVENTOS
        botaoComprar.addActionListener(e -> {
            jogo.comprarCartaAtual();
            atualizarTela();
        });


        botaoUno.addActionListener(e -> {
            Jogador atual = jogo.getJogadorAtual();

            // O jogador pode gritar UNO quando estiver com 2 ou 1 carta na mão
            if (atual.getMao().size() <= 2) {
                atual.setDisseUno(true);
                JOptionPane.showMessageDialog(janela, "🗣ATENÇÃO: O jogador " + atual.getNome() + " gritou UNO!");
                atualizarTela();
            }
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

    private String traduzirCorParaNaipe(Cor cor) {
        if (cor == Cor.VERMELHO) return "Vermelho (Copas ♥)";
        if (cor == Cor.AMARELO) return "Amarelo (Ouros ♦)";
        if (cor == Cor.VERDE) return "Verde (Paus ♣)";
        if (cor == Cor.AZUL) return "Azul (Espadas ♠)";
        return "Nenhuma";
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


        textoInfo.setText("Vez de: " + atual.getNome() +
                "  |  Topo: [" + topo.getNomeVisual() + "] " +
                "  |  Cor Valida: " + traduzirCorParaNaipe(jogo.getCorAtual()));

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

                    Jogador jogadorQueJogou = jogo.getJogadorAtual();

                    jogo.jogarCarta(atual, c);

                    if (jogadorQueJogou.getMao().size() == 1 && !jogadorQueJogou.isDisseUno()) {
                        JOptionPane.showMessageDialog(janela,
                                "PENALIDADE! O jogador " + jogadorQueJogou.getNome() +
                                        " ficou com 1 carta e NÃO gritou UNO!\nComprou +2 cartas de penalidade.",
                                "Pego no UNO!",
                                JOptionPane.WARNING_MESSAGE);

                        // Força a compra das 2 cartas de punição
                        for (int k = 0; k < 2; k++) {
                            jogadorQueJogou.adicionarCarta(jogo.getBaralho().comprarCarta());
                        }
                    }

                    if (c.getCor() == Cor.ESPECIAL) {
                        // Array de Strings bem claras para o usuário ler
                        String[] opcoes = {
                                "Vermelho (Copas ♥)",
                                "Azul (Espadas ♠)",
                                "Verde (Paus ♣)",
                                "Amarelo (Ouros ♦)"
                        };

                        String escolhaString = (String) JOptionPane.showInputDialog(janela, "Escolha a nova cor ou naipe:", "Coringa", JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);

                        if (escolhaString != null) {
                            // Converte a String escolhida de volta para o Enum que a lógica do jogo entende
                            if (escolhaString.contains("♥")) jogo.setCorAtual(Cor.VERMELHO);
                            else if (escolhaString.contains("♠")) jogo.setCorAtual(Cor.AZUL);
                            else if (escolhaString.contains("♣")) jogo.setCorAtual(Cor.VERDE);
                            else if (escolhaString.contains("♦")) jogo.setCorAtual(Cor.AMARELO);
                        }
                    }
                    atualizarTela();
                } else {
                    JOptionPane.showMessageDialog(janela, "Jogada Inválida! Escolha outra ou compre.");
                }
            });
            painelMao.add(botaoCarta);
        }

        if (atual.getMao().size() <= 2 && !atual.isDisseUno()) {
            botaoUno.setEnabled(true);
        } else {
            botaoUno.setEnabled(false);
        }

        painelMao.revalidate();
        painelMao.repaint();
    }
}