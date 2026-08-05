/**Estamos usando ENUM em vez de String para garantir a segurança dos tipos. **/
public enum Cor {

    // Cores oficiais do jogo UNO (que também mapeamos para os naipes do baralho comum)
    VERMELHO,
    VERDE,
    AMARELO,
    AZUL,

    // O tipo ESPECIAL foi criado para representar as cartas pretas (Coringa e +4).
    ESPECIAL
}