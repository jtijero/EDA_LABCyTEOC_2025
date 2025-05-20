package E5;

public class ArraySum {
  public static int sumArray(int[] arr) {
      int sum = 0;
      for (int i = 0; i < arr.length; i++) {
          sum += arr[i];
      }
      return sum;
  }
}
