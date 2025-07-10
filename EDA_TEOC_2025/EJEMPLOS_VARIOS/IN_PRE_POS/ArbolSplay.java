

class ArbolSplay {
  private NodoArbolSplay raiz;
  
  public NodoArbolSplay getRaiz() {
    return raiz; 
  }
  public void setRaiz(NodoArbolSplay raiz) {
    this.raiz = raiz;
  }

  private NodoArbolSplay rotarDerecha(NodoArbolSplay x) {
      NodoArbolSplay y = x.izquierdo;
      x.izquierdo = y.derecho;
      y.derecho = x;
      return y;
  }

  private NodoArbolSplay rotarIzquierda(NodoArbolSplay x) {
      NodoArbolSplay y = x.derecho;
      x.derecho = y.izquierdo;
      y.izquierdo = x;
      return y;
  }

  private NodoArbolSplay hacerSplay(NodoArbolSplay raiz, int clave) {
      if (raiz == null || raiz.valor == clave) return raiz;

      if (raiz.valor > clave) {
          if (raiz.izquierdo == null) return raiz;

          if (raiz.izquierdo.valor > clave) {
              raiz.izquierdo.izquierdo = hacerSplay(raiz.izquierdo.izquierdo, clave);
              raiz = rotarDerecha(raiz);
          } else if (raiz.izquierdo.valor < clave) {
              raiz.izquierdo.derecho = hacerSplay(raiz.izquierdo.derecho, clave);
              if (raiz.izquierdo.derecho != null)
                  raiz.izquierdo = rotarIzquierda(raiz.izquierdo);
          }
          return (raiz.izquierdo == null) ? raiz : rotarDerecha(raiz);
      } else {
          if (raiz.derecho == null) return raiz;

          if (raiz.derecho.valor > clave) {
              raiz.derecho.izquierdo = hacerSplay(raiz.derecho.izquierdo, clave);
              if (raiz.derecho.izquierdo != null)
                  raiz.derecho = rotarDerecha(raiz.derecho);
          } else if (raiz.derecho.valor < clave) {
              raiz.derecho.derecho = hacerSplay(raiz.derecho.derecho, clave);
              raiz = rotarIzquierda(raiz);
          }
          return (raiz.derecho == null) ? raiz : rotarIzquierda(raiz);
      }
  }

  public void insertar(int valor) {
      if (getRaiz() == null) {
          setRaiz(new NodoArbolSplay(valor));
          return;
      }
      setRaiz(hacerSplay(getRaiz(), valor));
      if (getRaiz().valor == valor) return;

      NodoArbolSplay nuevoNodo = new NodoArbolSplay(valor);
      if (getRaiz().valor > valor) {
          nuevoNodo.derecho = getRaiz();
          nuevoNodo.izquierdo = getRaiz().izquierdo;
          getRaiz().izquierdo = null;
      } else {
          nuevoNodo.izquierdo = getRaiz();
          nuevoNodo.derecho = getRaiz().derecho;
          getRaiz().derecho = null;
      }
      setRaiz(nuevoNodo);
  }

  public boolean buscar(int valor) {
      if (getRaiz() == null) return false;

      setRaiz(hacerSplay(getRaiz(), valor));
      if (getRaiz().valor == valor) return true;
      return false;
  }

  public void eliminar(int valor) {
      if (getRaiz() == null) return;

      setRaiz(hacerSplay(getRaiz(), valor));
      if (getRaiz().valor!= valor) return;

      if (getRaiz().izquierdo == null) {
          setRaiz(getRaiz().derecho);
      } else if (getRaiz().derecho == null) {
          setRaiz(getRaiz().izquierdo);
      } else {
          NodoArbolSplay temp = getRaiz().derecho;
          setRaiz(getRaiz().izquierdo);
          hacerSplay(getRaiz(), valor);
          getRaiz().derecho = temp;
      }
  }

  public void inorden(NodoArbolSplay raiz) {
    if (raiz!= null) {
      inorden(raiz.izquierdo);
      System.out.print(raiz.valor + " ");
      inorden(raiz.derecho);
    }
  }

  public void posorden(NodoArbolSplay raiz) {
    if (raiz!= null) {
      posorden(raiz.izquierdo);
      posorden(raiz.derecho);
      System.out.print(raiz.valor + " ");
    }
  }

  public void preorden(NodoArbolSplay raiz) {
    if (raiz!= null) {
      System.out.print(raiz.valor + " ");
      preorden(raiz.izquierdo);
      preorden(raiz.derecho);
    }
  }

}