/** Usamos a classe abstrata como Carta, para que ela possa criar os outros tipos de cartas **/
public abstract class Carta {
    protected Cor cor;
    protected Valor valor;
    protected String nomeVisual; // Define como a carta aparece

    //Construtor da classe base "CARTA", é chamado pelas subclasses
    public Carta(Cor cor, Valor valor, String nomeVisual) {
        this.cor = cor;
        this.valor = valor;
        this.nomeVisual = nomeVisual;
    }


    // GETTERS para métodos públicos consultarem informações da carta.
    public Cor getCor() { return cor; }
    public Valor getValor() { return valor; }
    public String getNomeVisual() { return nomeVisual; }

    //Metódo abstrato, atrvés dele o Java descobre em tempo de execução qual é a subclasse real da carta e executar o efeito da carta
    public abstract void aplicarEfeito(Jogo contexto);
}