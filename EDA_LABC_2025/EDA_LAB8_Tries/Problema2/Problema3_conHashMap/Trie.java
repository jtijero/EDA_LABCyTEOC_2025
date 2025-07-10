package EDA_LAB8_Tries.Problema2.Problema3_conHashMap;

import java.util.*;

public class Trie {
    private final NodoTrie raiz;

    public Trie() {
        raiz = new NodoTrie();
    }

    // Inserta una palabra en el Trie usando HashMap
    public void insertar(String palabra) {
        NodoTrie nodo = raiz;
        for (char letra : palabra.toCharArray()) {
            nodo.hijos.putIfAbsent(letra, new NodoTrie());
            nodo = nodo.hijos.get(letra);
        }
        nodo.esFinDePalabra = true;
    }

    // Obtiene todas las palabras ordenadas alfabéticamente
    public List<String> obtenerPalabrasOrdenadas() {
        List<String> resultado = new ArrayList<>();
        construirPalabras(raiz, new StringBuilder(), resultado);
        return resultado;
    }

    // Recorrido DFS para recolectar palabras en orden
    private void construirPalabras(NodoTrie nodo, StringBuilder camino, List<String> resultado) {
        if (nodo.esFinDePalabra) {
            resultado.add(camino.toString());
        }

        // Ordena los hijos por orden alfabético antes de recorrerlos
        List<Character> letras = new ArrayList<>(nodo.hijos.keySet());
        Collections.sort(letras);

        for (char c : letras) {
            camino.append(c);
            construirPalabras(nodo.hijos.get(c), camino, resultado);
            camino.deleteCharAt(camino.length() - 1);
        }
    }
}

