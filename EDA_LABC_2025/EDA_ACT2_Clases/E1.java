package EDA_LABC_2025.EDA_ACT2_Clases;

public class E1 { //recursividad
  void imprimir(int x){
    if(x==0){
      System.out.println(x);
      imprimir(x-1);
    }
  }
  
  public static void main(String[] arg ){
    E1 recursividad = new E1();
    recursividad.imprimir(5);
  }
}
