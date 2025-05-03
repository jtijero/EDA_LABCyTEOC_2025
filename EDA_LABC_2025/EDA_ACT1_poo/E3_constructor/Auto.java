package EDA_ACT1_poo.E3_constructor;

public class Auto {
  // Atributos
  private String modelo;
  private int año;

  // Constructor con argumentos
  public Auto(String modelo, int año) {
      this.modelo = modelo;
      this.año = año;
  }

  // Constructor sin argumentos
  public Auto() {
      this.modelo = "Desconocido";
      this.año = 0; // Valor por defecto
  }

  public void mostrarInfo() {
      System.out.println("Modelo: " + modelo + ", Año: " + año);
  }
}
