package E6;

public class ArrayMult{
  public static int multiplyArray(int[] arr) {
      int product = 1;
      for (int i = 0; i < arr.length; i++) {
          product *= arr[i];
      }
      return product;
  }
}

