import SplayTree.NodoArbol;

public class Main {
  public static void main(String[] args) {
    Arbol arbol = new Arbol();
    arbol.insertar(44);
    arbol.insertar(12);
    arbol.insertar(29);
    arbol.insertar(23);
    arbol.insertar(42);
    arbol.insertar(61);
    arbol.insertar(7);
    arbol.insertar(17);
    System.out.println("\nINORDEN: ");
    arbol.dispararInorden();
    System.out.println("\nPOSTORDEN:");
    arbol.dispararPostorden();
    System.out.println("\nPREORDEN:");
    arbol.dispararPreorden();
    System.out.println("\n");

    System.out.println("\n>>> Buscar nodo 29: " + arbol.search(29));
    System.out.println(">>> Buscar nodo 50: " + arbol.search(50));

    NodoArbol padre29 = arbol.parent(29); //ingresar aqui padre del nodo del que se quiere buscar
    if (padre29!= null) {
      System.out.println("\n>>> Padre de 29: " + padre29.getValor());
    } else {
      System.out.println("\tNo se encontró el padre de 29");
    }

    NodoArbol padre50 = arbol.parent(50);
    if (padre50!= null) {
      System.out.println(">>> Padre de 50: " + padre50.getValor());
    } else {
      System.out.println("\tNo se encontró el padre de 50");
    } //si no se encuentra el nodo, como es el caso, pues no se imprimirá

    System.out.println("\nHijos de 29: " + arbol.son(29));// cambiar el valor para buscar otro
    System.out.println("Hijos de 50: " + arbol.son(50)+ "\n"); 

    arbol.insertar(20);
    System.out.println("\nINORDEN: ");
    arbol.dispararInorden();
  }
  
}
