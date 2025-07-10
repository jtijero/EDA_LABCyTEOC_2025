import java.util.ArrayList;


public class BplusTree {
    Node root;

    public BplusTree(int order) {
        this.root = new Node(order);
        this.root.isLeaf = true;
    }

    public void insert(String value, Node key) {
        Node oldNode = this.search(value);
        oldNode.insertAtLeaf(value, key);

        if (oldNode.values.size() == oldNode.order) {
            Node newNode = new Node(oldNode.order);
            newNode.isLeaf = true;
            newNode.parent = oldNode.parent;
            int mid = (int) Math.ceil(oldNode.order / 2.0) - 1;
            newNode.values = new ArrayList<>(oldNode.values.subList(mid + 1, oldNode.values.size()));
            newNode.keys = new ArrayList<>(oldNode.keys.subList(mid + 1, oldNode.keys.size()));
            newNode.nextKey = oldNode.nextKey;
            oldNode.values = new ArrayList<>(oldNode.values.subList(0, mid + 1));
            oldNode.keys = new ArrayList<>(oldNode.keys.subList(0, mid + 1));
            oldNode.nextKey = newNode;
            this.insertInParent(oldNode, newNode.values.get(0), newNode);
        }
    }

    public Node search(String value) {
        Node currentNode = this.root;
        while (!currentNode.isLeaf) {
            for (int i = 0; i < currentNode.values.size(); i++) {
                if (value.equals(currentNode.values.get(i))) {
                    currentNode = currentNode.keys.get(i + 1).get(0);
                    break;
                } else if (value.compareTo(currentNode.values.get(i)) < 0) {
                    currentNode = currentNode.keys.get(i).get(0);
                    break;
                } else if (i + 1 == currentNode.values.size()) {
                    currentNode = currentNode.keys.get(i + 1).get(0);
                    break;
                }
            }
        }
        return currentNode;
    }

    public boolean find(String value, Node key) {
        Node leaf = this.search(value);
        for (int i = 0; i < leaf.values.size(); i++) {
            if (leaf.values.get(i).equals(value)) {
                return leaf.keys.get(i).contains(key);
            }
        }
        return false;
    }

    public void insertInParent(Node n, String value, Node ndash) {
        if (this.root == n) {
            Node rootNode = new Node(n.order);
            rootNode.values.add(value);
            rootNode.keys.add(new ArrayList<>());
            rootNode.keys.add(new ArrayList<>());
            rootNode.keys.get(0).add(n);
            rootNode.keys.get(1).add(ndash);
            this.root = rootNode;
            n.parent = rootNode;
            ndash.parent = rootNode;
            return;
        }

        Node parentNode = n.parent;
        for (int i = 0; i < parentNode.keys.size(); i++) {
            if (parentNode.keys.get(i).get(0) == n) {
                parentNode.values.add(i, value);
                parentNode.keys.add(i + 1, new ArrayList<>());
                parentNode.keys.get(i + 1).add(ndash);
                if (parentNode.keys.size() > parentNode.order) {
                    Node parentdash = new Node(parentNode.order);
                    parentdash.parent = parentNode.parent;
                    int mid = (int) Math.ceil(parentNode.order / 2.0) - 1;
                    parentdash.values = new ArrayList<>(parentNode.values.subList(mid + 1, parentNode.values.size()));
                    parentdash.keys = new ArrayList<>(parentNode.keys.subList(mid + 1, parentNode.keys.size()));
                    String value_ = parentNode.values.get(mid);
                    if (mid == 0) {
                        parentNode.values = new ArrayList<>(parentNode.values.subList(0, mid + 1));
                    } else {
                        parentNode.values = new ArrayList<>(parentNode.values.subList(0, mid));
                    }
                    parentNode.keys = new ArrayList<>(parentNode.keys.subList(0, mid + 1));
                    for (int j = 0; j < parentNode.keys.size(); j++) {
                        parentNode.keys.get(j).get(0).parent = parentNode;
                    }
                    for (int j = 0; j < parentdash.keys.size(); j++) {
                        parentdash.keys.get(j).get(0).parent = parentdash;
                    }
                    this.insertInParent(parentNode, value_, parentdash);
                }
                break;
            }
        }
    }
}
