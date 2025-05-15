package EDA_LABC_2025.EDA_ACT2_Clases;

public class E4 { //implementar un método recursivo para ordenar los elementos de un vector
  static int[] vector = {312,614,88,22,54};

  void ordenarV(int[]vect, int cantidadDeE){
    if(cantidadDeE > 1){
      for (int Ord =0 ;Ord < cantidadDeE -1;Ord++)
        if(vect[Ord]>vect[Ord+1]){
          int aux = vect[Ord];
          vect[Ord]= vect[Ord +1];
          vect[Ord+1]=aux;
        }

        ordenarV(vect,cantidadDeE-1);
    }
  }
  
  void imprimir(){
    for (int f=0;f<vector.length;f++){
      System.out.println(vector[f]+ " ");
    System.out.println("\n");
    }
  }

  public static void main(String[] arg ){
    E4 r = new E4();
    r.imprimir ();
    r.ordenarV(vector, vector.length);
    r.imprimir();
}
}
