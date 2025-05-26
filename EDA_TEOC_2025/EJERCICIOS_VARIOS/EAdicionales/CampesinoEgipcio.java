package EDA_TEOC_2025;

public class CampesinoEgipcio {
    public static int mult(int p, int q) {
        if (q == 0) {
            return 0;
        } else if (q == 1) {
            return p;
        } else {
            if (q % 2 == 0) { // Verificar si q es par
                return mult(2 * p, q / 2);
            } else {
                return mult(2 * p, q / 2) + p;
            }
        }
    }
}
