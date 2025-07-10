package EDA_LAB8_Tries.Problema2;

import java.util.*;

public class Trie {
    private final NodoTrie raiz;

    public Trie() {
        raiz = new NodoTrie();
    }

    // Insertar una palabra en el Trie
    public void insertar(String palabra) {
        NodoTrie nodo = raiz;
        for (char letra : palabra.toCharArray()) {
            int indice = letra - 'a';
            if (nodo.hijos[indice] == null) {
                nodo.hijos[indice] = new NodoTrie();
            }
            nodo = nodo.hijos[indice];
        }
        nodo.esFinDePalabra = true;
    }

    // Obtener todas las palabras del Trie en orden alfabético
    public List<String> obtenerPalabrasOrdenadas() {
        List<String> resultado = new ArrayList<>();
        construirPalabras(raiz, new StringBuilder(), resultado);
        return resultado;
    }

    // Recorrido DFS para construir las palabras ordenadas
    private void construirPalabras(NodoTrie nodo, StringBuilder camino, List<String> resultado) {
        if (nodo.esFinDePalabra) {
            resultado.add(camino.toString());
        }

        for (char c = 'a'; c <= 'z'; c++) {
            int indice = c - 'a';
            if (nodo.hijos[indice] != null) {
                camino.append(c);
                construirPalabras(nodo.hijos[indice], camino, resultado);
                camino.deleteCharAt(camino.length() - 1); // retroceder
            }
        }
    }
}
