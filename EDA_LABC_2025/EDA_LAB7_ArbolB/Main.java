import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        BplusTree bplusTree = new BplusTree(3);

        // Insertamos valores
        bplusTree.insert("5", new Node(3));
        bplusTree.insert("15", new Node(3));
        bplusTree.insert("25", new Node(3));
        bplusTree.insert("35", new Node(3));
        bplusTree.insert("45", new Node(3));

        // Imprimimos el árbol
        printTree(bplusTree);

        // Búsqueda de un valor
        if (bplusTree.find("5", new Node(3))) {
            System.out.println("Encontrado");
        } else {
            System.out.println("No encontrado");
        }
    }

    public static void printTree(BplusTree tree) {
        List<Node> queue = new ArrayList<>();
        queue.add(tree.root);
        while (!queue.isEmpty()) {
            Node current = queue.remove(0);
            System.out.println("Nodo: " + current.values);
            if (!current.isLeaf) {
                for (List<Node> childrenList : current.keys) {
                    for (Node child : childrenList) {
                        queue.add(child);
                    }
                }
            }
        }
    }
}

