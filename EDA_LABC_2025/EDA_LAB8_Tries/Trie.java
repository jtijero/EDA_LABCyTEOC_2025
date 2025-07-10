package EDA_LAB8_Tries;

import java.util.*;

public class Trie {
    private final NodoTrie raiz;

    public Trie() {
        raiz = new NodoTrie();
    }

    public void insertar(String palabra) {
        NodoTrie nodo = raiz;
        for (char c : palabra.toCharArray()) {
            int idx = c - 'a';
            if (nodo.hijos[idx] == null) {
                nodo.hijos[idx] = new NodoTrie();
            }
            nodo = nodo.hijos[idx];
        }
        nodo.esFinDePalabra = true;
    }

    public NodoTrie obtenerRaiz() {
        return raiz;
    }

    // Retorna si una palabra existe
    public boolean contiene(String palabra) {
        NodoTrie nodo = raiz;
        for (char c : palabra.toCharArray()) {
            int idx = c - 'a';
            if (nodo.hijos[idx] == null) return false;
            nodo = nodo.hijos[idx];
        }
        return nodo.esFinDePalabra;
    }
}

