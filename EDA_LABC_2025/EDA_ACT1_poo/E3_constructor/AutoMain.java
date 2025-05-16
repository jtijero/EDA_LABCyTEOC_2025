package E3_constructor;

public class AutoMain {
  public static void main(String[] args) {
      // Crear un auto usando el constructor con argumentos
      Auto autoConArgumentos = new Auto("Honda Civic", 2023);
      autoConArgumentos.mostrarInfo(); // Salida: Modelo: Honda Civic, Año: 2023

      // Crear un auto usando el constructor sin argumentos
      Auto autoSinArgumentos = new Auto();
      autoSinArgumentos.mostrarInfo(); // Salida: Modelo: Desconocido, Año: 0
  }
}