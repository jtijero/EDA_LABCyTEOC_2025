import EDA_TEOC_2025.EJEMPLOS_VARIOS.AVL_Simple.Node;

public class Main {
    public static void main(String[] args) {
      AVL avl = new AVL();
      String word = "scorpion";
      avl.construirArbol(word);


      System.out.println("Letra\tCódigo ASCII");
      System.out.println("-----\t-----------");

      for (char c = 'a'; c <= 'z'; c++) {
        System.out.println(c + "\t" + (int) c);
      }


      System.out.println("Árbol AVL creado con la palabra '" + word + "'");

      // Prueba del método search
      int asciiValue = (int) 'h';
      Node node = avl.search(asciiValue);
      if (node!= null) {
        System.out.println("Encontrado el nodo con valor ASCII " + asciiValue + ": " + node.value);
      } else {
        System.out.println("No se encontró el nodo con valor ASCII " + asciiValue);
      }

      // Prueba del método getMin
      int min = avl.getMin();
      System.out.println("Valor mínimo del árbol AVL: " + min);

      // Prueba del método getMax
      int max = avl.getMax();
      System.out.println("Valor máximo del árbol AVL: " + max);

      // Prueba del método parent
      asciiValue = (int) 'o';
      Node parentNode = avl.parent(asciiValue);
      if (parentNode!= null) {
        System.out.println("Padre del nodo con valor ASCII " + asciiValue + ": " + parentNode.value);
      } else {
        System.out.println("No se encontró el padre del nodo con valor ASCII " + asciiValue);
      }

      // Prueba del método son
      boolean isSon = avl.son(asciiValue);
      System.out.println("El nodo con valor ASCII " + asciiValue + " es hijo: " + isSon);

      // Insertar una nueva letra
      char newChar = 'z';
      avl.insert((int) newChar);
      System.out.println("Se insertó la letra '" + newChar + "' en el árbol AVL");

      // Prueba del método search después de insertar la nueva letra
      asciiValue = (int) newChar;
      node = avl.search(asciiValue);
      if (node!= null) {
        System.out.println("Encontrado el nodo con valor ASCII " + asciiValue + ": " + node.value);
      } else {
        System.out.println("No se encontró el nodo con valor ASCII " + asciiValue);
      }
    }

  }