package EDA_LAB8_Tries.Problema2;

import java.util.*;

public class Principal {
    public static void main(String[] args) {
        Trie trie = new Trie();

        // Arreglo desordenado
        String[] palabras = {"gato", "gallina", "pato", "pez", "perro", "ganso"};

        // Insertamos en el Trie
        for (String palabra : palabras) {
            trie.insertar(palabra.toLowerCase());
        }

        // Obtenemos las palabras ordenadas
        List<String> ordenadas = trie.obtenerPalabrasOrdenadas();

        System.out.println("Palabras ordenadas:");
        for (String palabra : ordenadas) {
            System.out.println(palabra);
        }
    }
}

