package visual;

import modelo.Carta;
import modelo.Cor;
import modelo.Jogador;
import controle.Jogo;

import javax.swing.*;
import java.awt.*;

/** O visual do jogo, desenha as cartas, a tela e captura as interações do usuário.  **/
public class JanelaMesa {
    private final Jogo jogo;
    private final JFrame janela;
    private final JPanel painelMao;
    private final JLabel lblInfo;
    private final JButton btnUno;
    private final JButton btnComprar;

    public JanelaMesa(Jogo jogo) {
        this.jogo = jogo;

// Configurações base da janela do sistema operacional
        janela = new JFrame("Mesa de UNO");
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setSize(1300, 500);
        janela.setLayout(new BorderLayout());

        lblInfo = new JLabel("", SwingConstants.CENTER);
        janela.add(lblInfo, BorderLayout.NORTH);

        painelMao = new JPanel(new FlowLayout());
        janela.add(painelMao, BorderLayout.CENTER);

        JPanel painelAcoes = new JPanel(new FlowLayout());
        btnComprar = new JButton("Comprar Carta");
        btnUno = new JButton("Gritar UNO!");
        JButton btnRender = new JButton("Sair da Partida");

        btnComprar.addActionListener(ignored -> {
            Carta comprada = jogo.comprarCartaAtual();
            if (comprada != null) {
                JOptionPane.showMessageDialog(janela,
                        "Você comprou a carta:\n[" + comprada.getNomeVisual() + "]",
                        "Carta Comprada",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            atualizarTela();
        });

        btnUno.addActionListener(ignored -> {
            jogo.setGritouUno(true);
            btnUno.setEnabled(false);
            JOptionPane.showMessageDialog(janela, jogo.getJogadorAtual().getNome() + " gritou UNO!");
        });

        btnRender.addActionListener(ignored -> {
            Jogador atual = jogo.getJogadorAtual();
            JOptionPane.showMessageDialog(janela, "O jogador " + atual.getNome() + " desistiu da partida.\nPor isso, o jogo será encerrado.");
            janela.dispose();
        });

        painelAcoes.add(btnComprar);
        painelAcoes.add(btnUno);
        painelAcoes.add(btnRender);
        janela.add(painelAcoes, BorderLayout.SOUTH);

        atualizarTela();
        janela.setVisible(true);
    }

    private void atualizarTela() {
        if (jogo.verificarVitoria()) {
            JOptionPane.showMessageDialog(janela, "FIM DE JOGO! O jogador " + jogo.getJogadorAtual().getNome() + " VENCEU A PARTIDA!");
            janela.dispose();
            return;
        }

        Carta topo = jogo.getTopoDescarte();
        Jogador atual = jogo.getJogadorAtual();

        boolean isMesaConvencional = topo.getNomeVisual().contains("♥") ||
                topo.getNomeVisual().contains("♦") ||
                topo.getNomeVisual().contains("♣") ||
                topo.getNomeVisual().contains("♠") ||
                topo.getNomeVisual().contains("Joker");

        String corFormatada = jogo.getCorAtual().toString();
        if (isMesaConvencional && jogo.getCorAtual() != Cor.ESPECIAL) {
            if (jogo.getCorAtual() == Cor.VERMELHO) corFormatada = "Vermelho (Copas ♥)";
            else if (jogo.getCorAtual() == Cor.AZUL) corFormatada = "Azul (Espadas ♠)";
            else if (jogo.getCorAtual() == Cor.VERDE) corFormatada = "Verde (Paus ♣)";
            else if (jogo.getCorAtual() == Cor.AMARELO) corFormatada = "Amarelo (Ouros ♦)";
        }

        if (jogo.getDeficitCartas() > 0) {
            lblInfo.setText("Vez de: " + atual.getNome() + "  |  Topo: [" + topo.getNomeVisual() + "]  |  Cor Valida: " + corFormatada + "  |  DÉBITO: " + jogo.getDeficitCartas() + " cartas");
            btnComprar.setText("Comprar (" + jogo.getDeficitCartas() + " restantes)");
            btnComprar.setForeground(Color.RED);
        } else {
            lblInfo.setText("Vez de: " + atual.getNome() + "  |  Topo: [" + topo.getNomeVisual() + "]  |  Cor Valida: " + corFormatada);
            btnComprar.setText("Comprar Carta");
            btnComprar.setForeground(Color.BLACK);
        }

        painelMao.removeAll();

        boolean podeGritar = false;
        if (atual.getMao().size() == 2) {
            for (Carta c : atual.getMao()) {
                if (jogo.validarJogada(c)) {
                    podeGritar = true;
                    break;
                }
            }
        }
        btnUno.setEnabled(podeGritar && !jogo.isGritouUno());

        for (int i = 0; i < atual.getMao().size(); i++) {
            Carta c = atual.getMao().get(i);
            JButton btnCarta = criarBotaoCarta(c, atual, isMesaConvencional);
            painelMao.add(btnCarta);
        }

        painelMao.revalidate();
        painelMao.repaint();
    }

    private JButton criarBotaoCarta(Carta c, Jogador atual, boolean isMesaConvencional) {
        String nome = c.getNomeVisual();
        JButton btnCarta = new JButton("<html><center>" + nome.replace(" ", "<br>") + "</center></html>");
        btnCarta.setPreferredSize(new Dimension(150, 220));
        btnCarta.setFont(new Font("Arial", Font.BOLD, 14));
        btnCarta.setFocusPainted(false);

        boolean isConvencional = nome.contains("♥") || nome.contains("♦") ||
                nome.contains("♣") || nome.contains("♠") ||
                nome.contains("Joker");

        // Aplicação de paleta de cores (Baralho comum vs UNO Oficial)
        if (isConvencional) {
            btnCarta.setBackground(Color.WHITE);
            if (nome.contains("♥") || nome.contains("♦") || nome.contains("+4")) {
                btnCarta.setForeground(Color.RED);
            } else {
                btnCarta.setForeground(Color.BLACK);
            }
        } else {
            if (c.getCor() == Cor.VERMELHO) {
                btnCarta.setBackground(Color.RED);
                btnCarta.setForeground(Color.WHITE);
            } else if (c.getCor() == Cor.AZUL) {
                btnCarta.setBackground(Color.BLUE);
                btnCarta.setForeground(Color.WHITE);
            } else if (c.getCor() == Cor.VERDE) {
                btnCarta.setBackground(Color.GREEN);
                btnCarta.setForeground(Color.BLACK);
            } else if (c.getCor() == Cor.AMARELO) {
                btnCarta.setBackground(Color.YELLOW);
                btnCarta.setForeground(Color.BLACK);
            } else {
                btnCarta.setBackground(Color.DARK_GRAY);
                btnCarta.setForeground(Color.WHITE);
            }
        }

        btnCarta.addActionListener(ignored -> {
            if (jogo.validarJogada(c)) {

                boolean tinhaDuasCartas = (atual.getMao().size() == 2);
                boolean tinhaGritado = jogo.isGritouUno();

                jogo.jogarCarta(atual, c);

                if (c.getCor() == Cor.ESPECIAL) {
                    Cor escolhida = escolherCorCoringa(isMesaConvencional);
                    jogo.setCorAtual(escolhida);
                }

                if (tinhaDuasCartas && atual.getMao().size() == 1 && !tinhaGritado) {
                    JOptionPane.showMessageDialog(janela, "Você esqueceu de gritar UNO! Penalidade: +2 cartas.");
                    jogo.punirFaltaDeUno(atual);
                }

                atualizarTela();
            } else {
                JOptionPane.showMessageDialog(janela, "Jogada Inválida! Escolha outra ou compre.");
            }
        });

        return btnCarta;
    }

    private Cor escolherCorCoringa(boolean isMesaConvencional) {
        String[] opcoes = isMesaConvencional
                ? new String[]{"Vermelho (Copas ♥)", "Azul (Espadas ♠)", "Verde (Paus ♣)", "Amarelo (Ouros ♦)"}
                : new String[]{"VERMELHO", "AZUL", "VERDE", "AMARELO"};

        String escolhidaNome = null;

        while (escolhidaNome == null) {
            escolhidaNome = (String) JOptionPane.showInputDialog(janela,
                    "Escolha a nova cor ou naipe:",
                    "Coringa",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opcoes,
                    opcoes[0]);
        }

        if (escolhidaNome.contains("Vermelho") || escolhidaNome.equals("VERMELHO")) return Cor.VERMELHO;
        else if (escolhidaNome.contains("Azul") || escolhidaNome.equals("AZUL")) return Cor.AZUL;
        else if (escolhidaNome.contains("Verde") || escolhidaNome.equals("VERDE")) return Cor.VERDE;
        else return Cor.AMARELO;
    }
}