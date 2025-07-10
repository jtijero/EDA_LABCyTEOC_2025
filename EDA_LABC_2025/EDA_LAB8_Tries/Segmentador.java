package EDA_LAB8_Tries;

import java.util.*;

public class Segmentador {
    private final Trie diccionario;
    private final Map<String, List<String>> memo;

    public Segmentador(String[] palabras) {
        diccionario = new Trie();
        for (String palabra : palabras) {
            diccionario.insertar(palabra);
        }
        memo = new HashMap<>();
    }

    public List<String> segmentar(String cadena) {
        return backtrack(cadena);
    }

    private List<String> backtrack(String subcadena) {
        if (memo.containsKey(subcadena)) return memo.get(subcadena);

        List<String> resultado = new ArrayList<>();
        if (subcadena.isEmpty()) {
            resultado.add("");
            return resultado;
        }

        for (int i = 1; i <= subcadena.length(); i++) {
            String prefijo = subcadena.substring(0, i);
            if (diccionario.contiene(prefijo)) {
                List<String> subsegmentos = backtrack(subcadena.substring(i));
                for (String segmento : subsegmentos) {
                    if (segmento.isEmpty()) {
                        resultado.add(prefijo);
                    } else {
                        resultado.add(prefijo + " " + segmento);
                    }
                }
            }
        }

        memo.put(subcadena, resultado);
        return resultado;
    }
}
