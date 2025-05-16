package EDA_ACT2_Clases;

public class E7 {
  public static void main(String [] args){
    int[] array = {1,2,3,4,5,6,7,8};
    System.out.println(mediaArrayDyV(0,array.length -1, array));
  }

  public static float mediaArrayDyV(int inicio, int fin, int array[]){
    if(inicio == fin){
      return array[inicio];
    }else{

      int mitad =(inicio+fin)/2;
      float x = (float) mediaArrayDyV(inicio, mitad, array);
      float y =(float) mediaArrayDyV(mitad+1,fin, array);
      return (x + y)/2;
    }
  }
}

