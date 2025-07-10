package EDA_LAB8_Tries.Problema2;
 //Ordenar un arreglo de palabras o cadenas alfabéticamente usando un Trie es una forma muy eficiente y elegante de resolver el problema, especialmente si hay palabras con prefijos comunes.
public class NodoTrie {
    NodoTrie[] hijos;
    boolean esFinDePalabra;

    public NodoTrie() {
        hijos = new NodoTrie[26]; // Asumimos solo letras minúsculas a-z
        esFinDePalabra = false;
    }
}

