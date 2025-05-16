package E1_extends;

abstract class Animal {
  abstract void dormir();

  // Método común (puede ser implementado o no)
  void comer() {
      System.out.println("El animal está comiendo.");
  }
}

class Perro extends Animal {
  // Implementación del método dormir
  @Override
  void dormir() {
      System.out.println("El perrito está durmiendo.");
  }

  // Método específico de Perro
  void ladrar() {
      System.out.println("¡Guau! Guau!");
  }
}