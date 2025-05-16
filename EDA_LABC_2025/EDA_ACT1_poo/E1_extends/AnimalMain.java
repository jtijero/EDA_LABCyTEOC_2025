package E1_extends;

public class AnimalMain {
  public static void main(String[] args) {
    Perro Snoopy = new Perro();

    // Llamar a los métodos
    Snoopy.dormir(); // Método heredado de Animal
    Snoopy.comer();  // Método heredado de Animal
    Snoopy.ladrar(); // Método específico de Perro
  }
}
