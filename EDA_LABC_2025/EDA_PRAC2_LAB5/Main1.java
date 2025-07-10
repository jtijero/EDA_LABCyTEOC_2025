package EDA_LABC_2025.EDA_PRAC2_LAB5;

import java.util.ArrayList;

public class Main1 {
    public static void main(String[] args) {
        ArrayList<Integer> s = new ArrayList<>();

        // Poniendo elementos
        s.add(10);
        s.add(20);
        s.add(30);

        // Sacando e impirmiendo el elemento de la cima
        System.out.println(s.get(s.size() - 1) + " sacado de la pila");
        s.remove(s.size() - 1);

        // Observar el elemento en la cima
        System.out.println("El elemento en la cima es: " + s.get(s.size() - 1));

        // Imprimir todos los elementos en la pila
        System.out.print("Elementos presentes en la pila: ");
        while (!s.isEmpty()) {
            System.out.print(s.get(s.size() - 1) + " ");
            s.remove(s.size() - 1);
        }
    }
}
