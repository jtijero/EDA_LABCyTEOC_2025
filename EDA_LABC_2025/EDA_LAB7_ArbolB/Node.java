import java.util.ArrayList;
import java.util.List;

public class Node {
    int order;
    List<String> values;
    List<List<Node>> keys;
    Node nextKey;
    Node parent;
    boolean isLeaf;

    public Node(int order) {
        this.order = order;
        this.values = new ArrayList<>();
        this.keys = new ArrayList<>();
        this.nextKey = null;
        this.parent = null;
        this.isLeaf = false;
    }

    public void insertAtLeaf(String value, Node key) {
        if (!this.values.isEmpty()) {
            for (int i = 0; i < this.values.size(); i++) {
                if (value.equals(this.values.get(i))) {
                    this.keys.get(i).add(key);
                    break;
                } else if (value.compareTo(this.values.get(i)) < 0) {
                    this.values.add(i, value);
                    this.keys.add(i, new ArrayList<>());
                    this.keys.get(i).add(key);
                    break;
                } else if (i + 1 == this.values.size()) {
                    this.values.add(value);
                    this.keys.add(new ArrayList<>());
                    this.keys.get(i + 1).add(key);
                    break;
                }
            }
        } else {
            this.values.add(value);
            this.keys.add(new ArrayList<>());
            this.keys.get(0).add(key);
        }
    }
}
