package EDA_LABC_2025.EDA_ACT2_Clases;

public class E2 { //recursividad
  void imprimir(int x){
    if(x>0){
      imprimir(x-1);
      System.out.println(x);
      
    }
  }
  
  public static void main(String[] arg ){
    E2 recursividad = new E2();
    recursividad.imprimir(5);
  }
}