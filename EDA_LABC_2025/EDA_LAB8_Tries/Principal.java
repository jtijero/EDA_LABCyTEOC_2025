package EDA_LAB8_Tries;

import java.util.*;

public class Principal {
    public static void main(String[] args) {
        String[] diccionario = {"apple", "pie", "app", "le"};
        Segmentador segmentador = new Segmentador(diccionario);

        String cadena = "applepie";
        List<String> particiones = segmentador.segmentar(cadena);

        System.out.println("Segmentaciones válidas para: " + cadena);
        for (String segmento : particiones) {
            System.out.println(segmento);
        }
    }
}

