package EDA_TEOC_2025;

public class MainMediaArray {
    public static void main(String[] args) {
        int[] newArray = {10, 20, 30, 40, 50};
        int sum = ArraySum.sumArray(newArray);
        double average = (double) sum / newArray.length;
        
        System.out.println("Suma del array: " + sum);
        System.out.println("Promedio del array: " + average);
    }
}
