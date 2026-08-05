/** Usamos a classe abstrata como Baralho, para que ela possa criar os outros tipos de baralhos **/
import java.util.*;

// Um baralho tem uma pilha de cartas
public abstract class Baralho {
    protected ArrayList<Carta> cartas = new ArrayList<>();

    public Baralho() {
        criarCartas();
        embaralhar();
    }

    // Cada baralho filho vai decidir como criar suas próprias cartas
    protected abstract void criarCartas();

    public void embaralhar() {
        Collections.shuffle(cartas);
        //Utilizamos essa função pré definida do Java importada da biblioteca java.util para reordenar as cartas de forma aleátoria
    }

    public Carta comprarCarta() {
        if (cartas.isEmpty()) {
            return null; // Antes de tentar te dar uma carta o baralho verifica se ainda há cartas no baralho
        }
        // Tira sempre a última carta do ArrayList (o topo do baralho), indice 0 ... size -1
        return cartas.remove(cartas.size() - 1);
    }
}