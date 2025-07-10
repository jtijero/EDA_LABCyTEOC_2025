package Pregunta3;
public class NodoArbolB {
    int[] claves;                // Arreglo de claves
    int t;                       // Grado mínimo
    NodoArbolB[] hijos;          // Arreglo de hijos
    int n;                       // Número actual de claves
    boolean esHoja;             // Indica si el nodo es hoja


    public NodoArbolB(int t, boolean esHoja) {
        this.t = t;
        this.esHoja = esHoja;
        this.claves = new int[2 * t - 1];
        this.hijos = new NodoArbolB[2 * t];
        this.n = 0;
    }


    // Busca una clave y retorna su índice
    public int buscarClave(int clave) {
        int i = 0;
        while (i < n && claves[i] < clave) i++;
        return i;
    }


    // Recorre el nodo e imprime claves en orden
    public void recorrer() {
        int i;
        for (i = 0; i < n; i++) {
            if (!esHoja)
                hijos[i].recorrer();
            System.out.print(" " + claves[i]);
        }
        if (!esHoja)
            hijos[i].recorrer();
    }


    // Busca una clave en el subárbol
    public NodoArbolB buscar(int clave) {
        int i = 0;
        while (i < n && clave > claves[i]) i++;


        if (i < n && claves[i] == clave)
            return this;


        if (esHoja)
            return null;


        return hijos[i].buscar(clave);
    }


    // Función principal para eliminar una clave
    public void eliminar(int clave) {
        int indice = buscarClave(clave);


        if (indice < n && claves[indice] == clave) {
            if (esHoja)
                eliminarDeHoja(indice);
            else
                eliminarDeNoHoja(indice);
        } else {
            if (esHoja) {
                System.out.println("La clave " + clave + " no existe en el árbol.");
                return;
            }


            boolean ultimaPosicion = (indice == n);


            if (hijos[indice].n < t)
                llenar(indice);


            if (ultimaPosicion && indice > n)
                hijos[indice - 1].eliminar(clave);
            else
                hijos[indice].eliminar(clave);
        }
    }


    private void eliminarDeHoja(int indice) {
        for (int i = indice + 1; i < n; i++)
            claves[i - 1] = claves[i];
        n--;
    }


    private void eliminarDeNoHoja(int indice) {
        int clave = claves[indice];


        if (hijos[indice].n >= t) {
            int predecesor = obtenerPredecesor(indice);
            claves[indice] = predecesor;
            hijos[indice].eliminar(predecesor);
        } else if (hijos[indice + 1].n >= t) {
            int sucesor = obtenerSucesor(indice);
            claves[indice] = sucesor;
            hijos[indice + 1].eliminar(sucesor);
        } else {
            fusionar(indice);
            hijos[indice].eliminar(clave);
        }
    }


    private int obtenerPredecesor(int indice) {
        NodoArbolB actual = hijos[indice];
        while (!actual.esHoja)
            actual = actual.hijos[actual.n];
        return actual.claves[actual.n - 1];
    }


    private int obtenerSucesor(int indice) {
        NodoArbolB actual = hijos[indice + 1];
        while (!actual.esHoja)
            actual = actual.hijos[0];
        return actual.claves[0];
    }


    private void llenar(int indice) {
        if (indice != 0 && hijos[indice - 1].n >= t)
            tomarDelAnterior(indice);
        else if (indice != n && hijos[indice + 1].n >= t)
            tomarDelSiguiente(indice);
        else {
            if (indice != n)
                fusionar(indice);
            else
                fusionar(indice - 1);
        }
    }


    private void tomarDelAnterior(int indice) {
        NodoArbolB hijo = hijos[indice];
        NodoArbolB hermano = hijos[indice - 1];


        for (int i = hijo.n - 1; i >= 0; i--)
            hijo.claves[i + 1] = hijo.claves[i];


        if (!hijo.esHoja) {
            for (int i = hijo.n; i >= 0; i--)
                hijo.hijos[i + 1] = hijo.hijos[i];
        }


        hijo.claves[0] = claves[indice - 1];


        if (!hijo.esHoja)
            hijo.hijos[0] = hermano.hijos[hermano.n];


        claves[indice - 1] = hermano.claves[hermano.n - 1];


        hijo.n++;
        hermano.n--;
    }


    private void tomarDelSiguiente(int indice) {
        NodoArbolB hijo = hijos[indice];
        NodoArbolB hermano = hijos[indice + 1];


        hijo.claves[hijo.n] = claves[indice];


        if (!hijo.esHoja)
            hijo.hijos[hijo.n + 1] = hermano.hijos[0];


        claves[indice] = hermano.claves[0];


        for (int i = 1; i < hermano.n; i++)
            hermano.claves[i - 1] = hermano.claves[i];


        if (!hermano.esHoja) {
            for (int i = 1; i <= hermano.n; i++)
                hermano.hijos[i - 1] = hermano.hijos[i];
        }


        hijo.n++;
        hermano.n--;
    }


    private void fusionar(int indice) {
        NodoArbolB hijo = hijos[indice];
        NodoArbolB hermano = hijos[indice + 1];


        hijo.claves[t - 1] = claves[indice];


        for (int i = 0; i < hermano.n; i++)
            hijo.claves[i + t] = hermano.claves[i];


        if (!hijo.esHoja) {
            for (int i = 0; i <= hermano.n; i++)
                hijo.hijos[i + t] = hermano.hijos[i];
        }


        for (int i = indice + 1; i < n; i++)
            claves[i - 1] = claves[i];


        for (int i = indice + 2; i <= n; i++)
            hijos[i - 1] = hijos[i];


        hijo.n += hermano.n + 1;
        n--;
    }
}
