package EDA_LABC_2025.EDA_PRAC2_LAB5.E3;

class Stack {

    // Cabeza de la lista enlazada
    Node head;

    // Constructor para inicializar la pila
    Stack() { this.head = null; }

    // Función para verificar si la pila esta vacía
    boolean isEmpty() {
      
        // Si la cabeza es null, la pila esta vacia
        return head == null;
    }

    // Función para agregar un elemento dentro de la pila
    void push(int new_data) {
      
        // Crear un nuevo nodo con los datos dados
        Node new_node = new Node(new_data);

        // Verificar si la asignación de memoria para el nuevo nodo falló
        if (new_node == null) {
            System.out.println("\nPila desbordada");
            return;
        }

        // Vincula el nuevo nodo al nodo superior actual
        new_node.next = head;

        // Actualizar la parte superior al nuevo nodo
        head = new_node;
    }

    // Función para eliminar el elemento superior de la pila
    void pop() {
      
        // Verificar si hay desbordamiento de pila
        if (isEmpty()) {
            System.out.println("\nPila desboradad por abajo");
            return;
        }
        else {
          
            // Asignar el nivel superior actual a una variable temporal
            Node temp = head;

            // Actualizar la parte superior al siguiente nodo
            head = head.next;

           // Desasignar la memoria del antiguo nodo superior
            temp = null;
        }
    }

    // Función para devolver el elemento superior de la pila
    int peek() {
      
        // Si la pila no está vacía, devuelve el elemento superior
        if (!isEmpty())
            return head.data;
        else {
            System.out.println("\nPila esta vacia");
            return Integer.MIN_VALUE;
        }
    }
}

