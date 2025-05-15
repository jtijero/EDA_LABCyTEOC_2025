package EDA_LABC_2025.EDA_ACT2_Clases;

public class E3 { //recursividad
  
  int factorial(int fact){
    if(fact>0){
      int valor = fact* factorial(fact-1);
      return valor;
    }else
      return 1;
  }

  
  public static void main(String[] arg ){
    E3 recursividad = new E3();

    int F = recursividad.factorial(5);
    System.out.println("El factorial de 5 es " + F);
  }
}