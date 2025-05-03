package EDA_ACT1_poo.E4_enum;

public class EnumEjemplo {

  // Definición del Enum
  public enum Dia {
      LUNES, MARTES, MIÉRCOLES, JUEVES, VIERNES, SÁBADO, DOMINGO
  }

  public static void main(String[] args) {
      // Uso del Enum
      Dia diaFavorito = Dia.VIERNES;
      
      System.out.println("Mi día favorito es: " + diaFavorito);

      // Usar el Enum en una estructura de control
      switch (diaFavorito) {
          case LUNES:
              System.out.println("Comienza la semana.");
              break;
          case VIERNES:
              System.out.println("¡Es casi fin de semana!");
              break;
          case SÁBADO:
          case DOMINGO:
              System.out.println("Es fin de semana.");
              break;
          default:
              System.out.println("Es un día de semana.");
              break;
      }
  }
}
