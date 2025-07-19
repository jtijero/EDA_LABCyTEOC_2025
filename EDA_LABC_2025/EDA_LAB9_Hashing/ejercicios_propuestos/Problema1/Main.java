package EDA_LAB9_Hashing.ejercicios_propuestos.Problema1;


// Interfaz Animal
interface Animal {
    void hacerSonido(); // Método para hacer sonido
    void moverse();      // Método para moverse
}

// Clase Perro que implementa la interfaz Animal
class Perro implements Animal {
    @Override
    public void hacerSonido() {
        System.out.println("El perro ladra: ¡Guau!");
    }

    @Override
    public void moverse() {
        System.out.println("El perro corre.");
    }
}

// Clase Gato que implementa la interfaz Animal
class Gato implements Animal {
    @Override
    public void hacerSonido() {
        System.out.println("El gato maulla: ¡Miau!");
    }

    @Override
    public void moverse() {
        System.out.println("El gato salta.");
    }
}

// Clase principal
public class Main {
    public static void main(String[] args) {
        Animal miPerro = new Perro(); // Crear un objeto Perro
        Animal miGato = new Gato();    // Crear un objeto Gato

        // Llamar a los métodos
        miPerro.hacerSonido(); // Salida: El perro ladra: ¡Guau!
        miPerro.moverse();      // Salida: El perro corre.

        miGato.hacerSonido();   // Salida: El gato maulla: ¡Miau!
        miGato.moverse();       // Salida: El gato salta.
    }
}

