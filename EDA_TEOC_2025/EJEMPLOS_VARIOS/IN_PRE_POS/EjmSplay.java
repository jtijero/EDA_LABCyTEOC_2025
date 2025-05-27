package EJEMPLOS_VARIOS.IN_PRE_POS;

public class EjmSplay {
  public static void main(String[] args) {
    ArbolSplay arbolSplay = new ArbolSplay();
    arbolSplay.insertar(10);
    arbolSplay.insertar(20);
    arbolSplay.insertar(30);
    arbolSplay.insertar(40);
    arbolSplay.insertar(50);


    System.out.println(arbolSplay.buscar(30)); // true
    arbolSplay.eliminar(30);
    System.out.println(arbolSplay.buscar(30)); // false

    System.out.println("Inorden:");
    arbolSplay.inorden(arbolSplay.getRaiz());

    System.out.println("\nPosorden:");
    arbolSplay.posorden(arbolSplay.getRaiz());

    System.out.println("\nPreorden:");
    arbolSplay.preorden(arbolSplay.getRaiz());
  }
}
