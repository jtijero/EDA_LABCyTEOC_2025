package EDA_TEOC_2025.EJEMPLOS_VARIOS.AVL_Simple;

public class Node {
  public int value;
  public Node left;
  public Node right;
  public Node parent;

  public Node(int value) {
      this.value = value;
      left = right = parent = null;
  }
}
