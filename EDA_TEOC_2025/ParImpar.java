package EDA_TEOC_2025;

public class ParImpar {
  public static int par(int n) {
      if (n == 0) {
          return 1; // 0 es par
      } else if (n == 1) {
          return 0; // 1 no es par
      } else {
          return impar(n - 1); // Llamada recursiva a impar
      }
  }

  public static int impar(int n) {
    if (n == 0) {
        return 0; // 0 no es impar
    } else if (n == 1) {
        return 1; // 1 es impar
    } else {
        return par(n - 1); // Llamada recursiva a par
    }
}
}