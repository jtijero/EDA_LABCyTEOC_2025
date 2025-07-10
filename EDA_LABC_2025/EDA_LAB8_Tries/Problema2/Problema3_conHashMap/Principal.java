package EDA_LAB8_Tries.Problema2.Problema3_conHashMap;

import java.util.*;

public class Principal {
    public static void main(String[] args) {
        Trie trie = new Trie();

        // Arreglo desordenado con palabras que tienen prefijos comunes
        String[] palabras = {"niño", "niña", "gato", "gallina", "pato", "perro", "pez"};

        // Insertar cada palabra en el Trie
        for (String palabra : palabras) {
            trie.insertar(palabra.toLowerCase()); // Convertimos a minúscula por consistencia
        }

        // Obtener palabras ordenadas
        List<String> ordenadas = trie.obtenerPalabrasOrdenadas();

        System.out.println("Palabras ordenadas alfabéticamente:");
        for (String palabra : ordenadas) {
            System.out.println(palabra);
        }
    }
}
