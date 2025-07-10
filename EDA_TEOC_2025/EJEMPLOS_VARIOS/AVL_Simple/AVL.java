package EDA_TEOC_2025.EJEMPLOS_VARIOS.AVL_Simple;

public class AVL {
  private Node root;

  public AVL() {
      root = null;
  }

  public void construirArbol(String word) {
      for (char c : word.toCharArray()) {
          int asciiValue = (int) c;
          insert(asciiValue);
      }
  }

  public Node search(int value) {
      return searchRecursive(root, value);
  }

  private Node searchRecursive(Node node, int value) {
      if (node == null || node.value == value) {
          return node;
      }
      if (value < node.value) {
          return searchRecursive(node.left, value);
      } else {
          return searchRecursive(node.right, value);
      }
  }

  public int getMin() {
      return getMinRecursive(root).value;
  }

  private Node getMinRecursive(Node node) {
      if (node.left == null) {
          return node;
      } else {
          return getMinRecursive(node.left);
      }
  }

  public int getMax() {
      return getMaxRecursive(root).value;
  }

  private Node getMaxRecursive(Node node) {
      if (node.right == null) {
          return node;
      } else {
          return getMaxRecursive(node.right);
      }
  }

  public Node parent(int value) {
      return parentRecursive(root, value);
  }

  private Node parentRecursive(Node node, int value) {
      if (node == null) {
          return null;
      }
      if (node.left != null && node.left.value == value) {
          return node;
      }
      if (node.right != null && node.right.value == value) {
          return node;
      }
      if (value < node.value) {
          return parentRecursive(node.left, value);
      } else {
          return parentRecursive(node.right, value);
      }
  }

  public boolean son(int value) {
      Node parentNode = parent(value);
      return parentNode != null && (parentNode.left != null && parentNode.left.value == value || parentNode.right != null && parentNode.right.value == value);
  }

  public void insert(int value) {
      root = insertRecursive(root, value);
      balance(root);
  }

  private Node insertRecursive(Node node, int value) {
      if (node == null) {
          return new Node(value);
      }
      if (value < node.value) {
          node.left = insertRecursive(node.left, value);
          node.left.parent = node;
      } else {
          node.right = insertRecursive(node.right, value);
          node.right.parent = node;
      }
      return node;
  }

  private void balance(Node node) {
      if (node == null) {
          return;
      }
      int balanceFactor = getBalanceFactor(node);
      if (balanceFactor > 1) {
          if (getBalanceFactor(node.left) < 0) {
              node.left = rotateLeft(node.left);
          }
          node = rotateRight(node);
      } else if (balanceFactor < -1) {
          if (getBalanceFactor(node.right) > 0) {
              node.right = rotateRight(node.right);
          }
          node = rotateLeft(node);
      }
      balance(node.left);
      balance(node.right);
  }

  private int getBalanceFactor(Node node) {
      return getHeight(node.left) - getHeight(node.right);
  }

  private int getHeight(Node node) {
      if (node == null) {
          return 0;
      }
      return Math.max(getHeight(node.left), getHeight(node.right)) + 1;
  }

  private Node rotateLeft(Node node) {
      Node pivot = node.right;
      node.right = pivot.left;
      pivot.left = node;
      return pivot;
  }

  private Node rotateRight(Node node) {
      Node pivot = node.left;
      node.left = pivot.right;
      pivot.right = node;
      return pivot;
  }
}