import SplayTree.NodoArbol;

public class Arbol {
  NodoArbol inicial;
  public Arbol(){
    this.inicial = null;
  }

  public void insertar(int valor){
    if(this.inicial == null){
      this.inicial = new NodoArbol(valor);
    }else{
      this.inicial.insertar(valor);
    }
  }

  public void dispararPreorden(){
    this.preorden(this.inicial);
  }
  public void preorden(NodoArbol nodo){
    if(nodo == null){
      return; //detiene recursividad
    }else{
      System.out.print(nodo.getValor() + ", ");
      preorden(nodo.getNodoIzq());
      preorden(nodo.getNodoDerecho());
    }
  }

  public void dispararInorden(){
    this.inorden(this.inicial);
  }
  public void inorden(NodoArbol nodo){
    if(nodo == null){
      return; //detiene recursividad
    }else{
      inorden(nodo.getNodoIzq());
      System.out.print(nodo.getValor() + ", ");
      inorden(nodo.getNodoDerecho());
    }
  }

  public void dispararPostorden(){
    this.postorden(this.inicial);
  }
  public void postorden(NodoArbol nodo){
    if(nodo == null){
      return; //detiene recursividad
    }else{
      postorden(nodo.getNodoIzq());
      postorden(nodo.getNodoDerecho());
      System.out.print(nodo.getValor() + ", ");
      
    }
  }

  public String search(int valor) {
    return search(this.inicial, valor);
  }

  private String search(NodoArbol nodo, int valor) {
    if (nodo == null) {
      return "No encontrado";
    } else if (nodo.getValor() == valor) {
      return "Encontrado";
    } else if (valor < nodo.getValor()) {
      return search(nodo.getNodoIzq(), valor);
    } else {
      return search(nodo.getNodoDerecho(), valor);
    }
  }

  public NodoArbol parent(int valor) {
    return parent(this.inicial, null, valor);
  }

  private NodoArbol parent(NodoArbol nodo, NodoArbol padre, int valor) {
    if (nodo == null) {
      return null;
    } else if (nodo.getValor() == valor) {
      return padre;
    } else if (valor < nodo.getValor()) {
      return parent(nodo.getNodoIzq(), nodo, valor);
    } else {
      return parent(nodo.getNodoDerecho(), nodo, valor);
    }
  }

  public String son(int valor) {
    NodoArbol nodo = searchNode(this.inicial, valor);
    if (nodo == null) {
      return "El nodo no existe.";
    } else {
      String hijoIzq = nodo.getNodoIzq()!= null? String.valueOf(nodo.getNodoIzq().getValor()) : "null";
      String hijoDerecho = nodo.getNodoDerecho()!= null? String.valueOf(nodo.getNodoDerecho().getValor()) : "null";
      return "Hijo izquierdo: " + hijoIzq + ", Hijo derecho: " + hijoDerecho;
    }
  }

  private NodoArbol searchNode(NodoArbol nodo, int valor) {
    if (nodo == null) {
      return null;
    } else if (nodo.getValor() == valor) {
      return nodo;
    } else if (valor < nodo.getValor()) {
      return searchNode(nodo.getNodoIzq(), valor);
    } else {
      return searchNode(nodo.getNodoDerecho(), valor);
    }
  }
}
