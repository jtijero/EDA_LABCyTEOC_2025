package Pregunta3;

public class ArbolB {
    NodoArbolB raiz;
    int t; // grado mínimo


    public ArbolB(int t) {
        this.t = t;
        this.raiz = null;
    }


    public void recorrer() {
        if (raiz != null) raiz.recorrer();
        System.out.println();
    }


    public NodoArbolB buscar(int clave) {
        return (raiz == null) ? null : raiz.buscar(clave);
    }


    public void insertar(int clave) {
        if (raiz == null) {
            raiz = new NodoArbolB(t, true);
            raiz.claves[0] = clave;
            raiz.n = 1;
        } else {
            if (raiz.n == 2 * t - 1) {
                NodoArbolB nuevaRaiz = new NodoArbolB(t, false);
                nuevaRaiz.hijos[0] = raiz;
                dividirHijo(nuevaRaiz, 0, raiz);
                int i = (nuevaRaiz.claves[0] < clave) ? 1 : 0;
                insertarNoLleno(nuevaRaiz.hijos[i], clave);
                raiz = nuevaRaiz;
            } else {
                insertarNoLleno(raiz, clave);
            }
        }
    }


    private void insertarNoLleno(NodoArbolB nodo, int clave) {
        int i = nodo.n - 1;


        if (nodo.esHoja) {
            while (i >= 0 && nodo.claves[i] > clave) {
                nodo.claves[i + 1] = nodo.claves[i];
                i--;
            }
            nodo.claves[i + 1] = clave;
            nodo.n++;
        } else {
            while (i >= 0 && nodo.claves[i] > clave) i--;
            i++;
            if (nodo.hijos[i].n == 2 * t - 1) {
                dividirHijo(nodo, i, nodo.hijos[i]);
                if (nodo.claves[i] < clave) i++;
            }
            insertarNoLleno(nodo.hijos[i], clave);
        }
    }


    private void dividirHijo(NodoArbolB padre, int i, NodoArbolB hijoLleno) {
        NodoArbolB nuevo = new NodoArbolB(t, hijoLleno.esHoja);
        nuevo.n = t - 1;


        for (int j = 0; j < t - 1; j++)
            nuevo.claves[j] = hijoLleno.claves[j + t];


        if (!hijoLleno.esHoja) {
            for (int j = 0; j < t; j++)
                nuevo.hijos[j] = hijoLleno.hijos[j + t];
        }


        hijoLleno.n = t - 1;


        for (int j = padre.n; j >= i + 1; j--)
            padre.hijos[j + 1] = padre.hijos[j];


        padre.hijos[i + 1] = nuevo;


        for (int j = padre.n - 1; j >= i; j--)
            padre.claves[j + 1] = padre.claves[j];


        padre.claves[i] = hijoLleno.claves[t - 1];
        padre.n++;
    }


    public void eliminar(int clave) {
        if (raiz == null) {
            System.out.println("El árbol está vacío");
            return;
        }


        raiz.eliminar(clave);


        if (raiz.n == 0) {
            raiz = raiz.esHoja ? null : raiz.hijos[0];
        }
    }
}
