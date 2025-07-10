package EDA_PRAC2_LAB5.E3;

public class Main {
    public static void main(String[] args)
    {
        // Creando la pila
        Stack st = new Stack();

        // Agregando elmentos en la pila
        st.push(11);
        st.push(22);
        st.push(33);
        st.push(44);

        // Imprimiendo elemento de la cima de la pila
        System.out.println("El elemento en la cima es " + st.peek());

        // eliminando dos elementos de la parte superior
  		System.out.println("Removiendo dos elementos...");
        st.pop();
        st.pop();

        // Imprimir el elemento superior de la pila
        System.out.println("Elemento en la cima es " + st.peek());
    }
}
