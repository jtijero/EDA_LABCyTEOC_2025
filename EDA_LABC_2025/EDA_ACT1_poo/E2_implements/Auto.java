package E2_implements;

// Definición de la interfaz Vehiculo
interface Vehiculo {
  void iniciar();
  void detener();
}

class Auto implements Vehiculo {
  @Override
  public void iniciar() {
      System.out.println("El auto ha iniciado.");
  }

  @Override
  public void detener() {
      System.out.println("El auto se ha detenido.");
  }
}
