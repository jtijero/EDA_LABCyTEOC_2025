package EDA_ACT2_Clases;

public class E1 { //recursividad
  void imprimir(int x){
    if(x<10 && x>0){
      System.out.println(x);
      imprimir(x-1);
    }
  }
  
  public static void main(String[] args){
    E1 recursividad = new E1();
    recursividad.imprimir(5);
  }
}
