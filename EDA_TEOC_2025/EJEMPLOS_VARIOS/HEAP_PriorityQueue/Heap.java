import java.util.ArrayList;

public class Heap<T extends Comparable<T>> {
    private ArrayList<T> elementos;

    public Heap() {
        elementos = new ArrayList<>();
    }

    public void offer(T elemento) {
        elementos.add(elemento);
        int indice = elementos.size() - 1;
        while (indice > 0) {
            int padre = (indice - 1) / 2;
            if (elementos.get(padre).compareTo(elementos.get(indice)) >= 0) {
                break;
            }
            swap(padre, indice);
            indice = padre;
        }
    }

    public T poll() {
        if (elementos.isEmpty()) {
            return null;
        }
        T raiz = elementos.get(0);
        elementos.set(0, elementos.get(elementos.size() - 1));
        elementos.remove(elementos.size() - 1);
        Amontonar(0);
        return raiz;
    }

    private void Amontonar(int indice) {
        int hijoIzquierdo = 2 * indice + 1;
        int hijoDerecho = 2 * indice + 2;
        int mayor = indice;

        if (hijoIzquierdo < elementos.size() && elementos.get(hijoIzquierdo).compareTo(elementos.get(mayor)) > 0) {
            mayor = hijoIzquierdo;
        }

        if (hijoDerecho < elementos.size() && elementos.get(hijoDerecho).compareTo(elementos.get(mayor)) > 0) {
            mayor = hijoDerecho;
        }

        if (mayor!= indice) {
            swap(mayor, indice);
            Amontonar(mayor);
        }
    }

    private void swap(int i, int j) {
        T temp = elementos.get(i);
        elementos.set(i, elementos.get(j));
        elementos.set(j, temp);
    }

    public T getRaiz() {
        return elementos.isEmpty()? null : elementos.get(0);
    }

    public ArrayList<T> getHeap() {
        return new ArrayList<>(elementos);
    }
}