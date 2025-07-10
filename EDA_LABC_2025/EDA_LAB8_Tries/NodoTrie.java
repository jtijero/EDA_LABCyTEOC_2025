package EDA_LAB8_Tries;

public class NodoTrie {
    NodoTrie[] hijos;
    boolean esFinDePalabra;

    public NodoTrie() {
        hijos = new NodoTrie[26];
        esFinDePalabra = false;
    }
}
