package EDA_LAB10_Grafos.Pregunta1;

import java.util.ArrayList;
import java.util.List;

public class Nodo {
    int clave;
    Nodo izquierdo;
    Nodo derecho;
    int altura;
    List<Nodo> vecinos; // Lista de vecinos para el grafo

    public Nodo(int clave) {
        this.clave = clave;
        this.altura = 1;
        this.vecinos = new ArrayList<>();
    }
}
