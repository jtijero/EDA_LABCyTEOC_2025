package EDA_LAB8_Tries.Problema2.Problema3_conHashMap;

import java.util.HashMap;
import java.util.Map;

// Nodo básico del Trie, usando HashMap para los hijos
public class NodoTrie {
    Map<Character, NodoTrie> hijos;
    boolean esFinDePalabra;

    public NodoTrie() {
        hijos = new HashMap<>();
        esFinDePalabra = false;
    }
}

