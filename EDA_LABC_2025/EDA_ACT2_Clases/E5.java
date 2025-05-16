package EDA_ACT2_Clases;

public class E5 {//sumar los elementos de un array con divide and conquer
  public static void main(String[] args){
    int[] array = {1,2,3,4,5,6,7,8};
    System.out.println(sumaArrayDyV(0,array.length-1,array));
  }

  public static int sumaArrayDyV(int inicio, int fin, int array[]){
    if(inicio == fin){
      return array[inicio];
    }else{

      int mitad =(inicio+fin)/2;
      int x = sumaArrayDyV(inicio, mitad, array);
      int y =sumaArrayDyV(mitad+1,fin, array);
      return x + y;
    }

    }
  }
