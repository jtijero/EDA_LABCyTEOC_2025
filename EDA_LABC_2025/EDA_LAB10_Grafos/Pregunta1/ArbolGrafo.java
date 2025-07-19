package EDA_LABC_2025.EDA_LAB10_Grafos.Pregunta1;

import java.util.*;

public class ArbolGrafo {
    private Nodo raiz;

    public void insertar(int clave) {
        raiz = insertarRec(raiz, clave);
    }

    private Nodo insertarRec(Nodo nodo, int clave) {
        if (nodo == null) {
            return new Nodo(clave);
        }
        if (clave < nodo.clave) {
            nodo.izquierdo = insertarRec(nodo.izquierdo, clave);
        } else if (clave > nodo.clave) {
            nodo.derecho = insertarRec(nodo.derecho, clave);
        } else {
            return nodo; // Clave duplicada no se permite
        }

        nodo.altura = 1 + Math.max(obtenerAltura(nodo.izquierdo), obtenerAltura(nodo.derecho));
        return balancear(nodo);
    }

    private int obtenerAltura(Nodo nodo) {
        return nodo == null ? 0 : nodo.altura;
    }

    private Nodo balancear(Nodo nodo) {
        int balance = obtenerBalance(nodo);
        
        if (balance > 1) {
            if (obtenerBalance(nodo.izquierdo) < 0) {
                nodo.izquierdo = rotarIzquierda(nodo.izquierdo);
            }
            return rotarDerecha(nodo);
        }
        if (balance < -1) {
            if (obtenerBalance(nodo.derecho) > 0) {
                nodo.derecho = rotarDerecha(nodo.derecho);
            }
            return rotarIzquierda(nodo);
        }
        return nodo;
    }

    private int obtenerBalance(Nodo nodo) {
        return nodo == null ? 0 : obtenerAltura(nodo.izquierdo) - obtenerAltura(nodo.derecho);
    }

    private Nodo rotarDerecha(Nodo nodo) {
        Nodo nuevoRaiz = nodo.izquierdo;
        nodo.izquierdo = nuevoRaiz.derecho;
        nuevoRaiz.derecho = nodo;
        nodo.altura = Math.max(obtenerAltura(nodo.izquierdo), obtenerAltura(nodo.derecho)) + 1;
        nuevoRaiz.altura = Math.max(obtenerAltura(nuevoRaiz.izquierdo), obtenerAltura(nuevoRaiz.derecho)) + 1;
        return nuevoRaiz;
    }

    private Nodo rotarIzquierda(Nodo nodo) {
        Nodo nuevoRaiz = nodo.derecho;
        nodo.derecho = nuevoRaiz.izquierdo;
        nuevoRaiz.izquierdo = nodo;
        nodo.altura = Math.max(obtenerAltura(nodo.izquierdo), obtenerAltura(nodo.derecho)) + 1;
        nuevoRaiz.altura = Math.max(obtenerAltura(nuevoRaiz.izquierdo), obtenerAltura(nuevoRaiz.derecho)) + 1;
        return nuevoRaiz;
    }

    public boolean buscar(int clave) {
        return buscarRec(raiz, clave);
    }

    private boolean buscarRec(Nodo nodo, int clave) {
        if (nodo == null) {
            return false;
        }
        if (clave == nodo.clave) {
            return true;
        }
        return clave < nodo.clave ? buscarRec(nodo.izquierdo, clave) : buscarRec(nodo.derecho, clave);
    }

    public void agregarArista(int u, int v) {
        Nodo nodoU = buscarNodo(raiz, u);
        Nodo nodoV = buscarNodo(raiz, v);
        if (nodoU != null && nodoV != null) {
            nodoU.vecinos.add(nodoV);
            nodoV.vecinos.add(nodoU); // Grafo no dirigido
        }
    }

    private Nodo buscarNodo(Nodo nodo, int clave) {
        if (nodo == null) {
            return null;
        }
        if (clave == nodo.clave) {
            return nodo;
        }
        return clave < nodo.clave ? buscarNodo(nodo.izquierdo, clave) : buscarNodo(nodo.derecho, clave);
    }

    public int dijkstra(int inicio, int destino) {
        Nodo nodoInicio = buscarNodo(raiz, inicio);
        Nodo nodoDestino = buscarNodo(raiz, destino);
        if (nodoInicio == null || nodoDestino == null) {
            return -1; // Si alguno de los nodos no existe
        }

        Map<Nodo, Integer> distancias = new HashMap<>();
        PriorityQueue<Nodo> cola = new PriorityQueue<>(Comparator.comparingInt(distancias::get));
        Set<Nodo> visitados = new HashSet<>();

        for (Nodo nodo : obtenerNodos(raiz)) {
            distancias.put(nodo, Integer.MAX_VALUE);
        }
        distancias.put(nodoInicio, 0);
        cola.add(nodoInicio);

        while (!cola.isEmpty()) {
            Nodo actual = cola.poll();

            if (visitados.contains(actual)) {
                continue;
            }
            visitados.add(actual);

            for (Nodo vecino : actual.vecinos) {
                if (!visitados.contains(vecino)) {
                    int nuevaDistancia = distancias.get(actual) + 1; // Peso constante de 1
                    if (nuevaDistancia < distancias.get(vecino)) {
                        distancias.put(vecino, nuevaDistancia);
                        cola.add(vecino);
                    }
                }
            }
        }
        return distancias.getOrDefault(nodoDestino, -1); // Retorna -1 si no se encuentra el destino
    }

    private List<Nodo> obtenerNodos(Nodo nodo) {
        List<Nodo> nodos = new ArrayList<>();
        if (nodo != null) {
            nodos.add(nodo);
            nodos.addAll(obtenerNodos(nodo.izquierdo));
            nodos.addAll(obtenerNodos(nodo.derecho));
        }
        return nodos;
    }
}
