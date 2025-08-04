package estructuras.conjuntistas.dinamicas;

import estructuras.lineales.dinamicas.Lista;

@SuppressWarnings({ "all" })
public class ArbolAVL {

    private NodoAVL raiz;

    public ArbolAVL() {
        this.raiz = null;
    }

    public boolean insertar(Comparable elem) {
        boolean res = false;

        if (this.raiz == null) {
            this.raiz = new NodoAVL(elem);
            res = true;
        } else {
            NodoAVL resN = insertarAux(this.raiz, elem);
            if (resN != null) {
                this.raiz = resN;
                res = true;
            }
        }

        return res;
    }

    public boolean esVacio() {
        return this.raiz == null;
    }

    private NodoAVL insertarAux(NodoAVL nodo, Comparable elem) {
        boolean inserto = false;
        if (elem.compareTo(nodo.getElem()) < 0) {
            if (nodo.getIzquierdo() == null) {
                nodo.setIzquierdo(new NodoAVL(elem));
            } else {
                nodo.setIzquierdo(insertarAux(nodo.getIzquierdo(), elem));
            }
            inserto = true;
        } else if (elem.compareTo(nodo.getElem()) > 0) {
            if (nodo.getDerecho() == null) {
                nodo.setDerecho(new NodoAVL(elem));
            } else {
                nodo.setDerecho(insertarAux(nodo.getDerecho(), elem));
            }
            inserto = true;
        }

        if (inserto) {
            nodo.recalcularAltura();
            int balance = balance(nodo);
            if (Math.abs(balance) > 1) {
                nodo = balancear(nodo, balance);
            }
        }

        return inserto ? nodo : null;
    }

    public boolean eliminar(Comparable elem) {
        boolean[] eliminado = { false };
        if (this.raiz != null) {
            this.raiz = eliminarElem(this.raiz, elem, eliminado);
        }
        return eliminado[0];
    }

    private NodoAVL eliminarElem(NodoAVL nodo, Comparable elem, boolean[] eliminado) {
        NodoAVL res = nodo;

        if (nodo != null) {
            int comp = elem.compareTo(nodo.getElem());
            if (comp < 0) {
                nodo.setIzquierdo(eliminarElem(nodo.getIzquierdo(), elem, eliminado));
            } else if (comp > 0) {
                nodo.setDerecho(eliminarElem(nodo.getDerecho(), elem, eliminado));
            } else {
                eliminado[0] = true;
                if (nodo.getIzquierdo() == null) {
                    res = nodo.getDerecho();
                } else if (nodo.getDerecho() == null) {
                    res = nodo.getIzquierdo();
                } else {
                    NodoAVL max = nodo.getIzquierdo();
                    while (max.getDerecho() != null) {
                        max = max.getDerecho();
                    }
                    nodo.setElem(max.getElem());
                    nodo.setIzquierdo(eliminarElem(nodo.getIzquierdo(), max.getElem(), eliminado));
                    res = nodo;
                }
            }

            if (res != null) {
                res.recalcularAltura();
                int balance = balance(res);
                if (Math.abs(balance) > 1) {
                    res = balancear(nodo, balance);
                }
            }
        }

        return res;
    }

    private NodoAVL balancear(NodoAVL nodo, int balance) {
        if (balance > 1) {
            if (balance(nodo.getIzquierdo()) >= 0) {
                nodo = rotacionDerecha(nodo);
            } else {
                nodo = rotacionIzqDer(nodo);
            }
        } else {

            if (balance(nodo.getDerecho()) <= 0) {
                nodo = rotacionIzquierda(nodo);
            } else {
                nodo = rotacionDerIzq(nodo);
            }

        }
        nodo.recalcularAltura();
        return nodo;
    }

    private NodoAVL rotacionDerecha(NodoAVL nodo) {

        NodoAVL hi = nodo.getIzquierdo();
        NodoAVL temp = hi.getDerecho();

        hi.setDerecho(nodo);
        nodo.setIzquierdo(temp);

        nodo.recalcularAltura();
        hi.recalcularAltura();

        return hi;
    }

    private NodoAVL rotacionIzquierda(NodoAVL nodo) {

        NodoAVL hd = nodo.getDerecho();
        NodoAVL temp = hd.getIzquierdo();

        hd.setIzquierdo(nodo);
        nodo.setDerecho(temp);

        nodo.recalcularAltura();
        hd.recalcularAltura();

        return hd;

    }

    private NodoAVL rotacionIzqDer(NodoAVL nodo) {
        nodo.setIzquierdo(rotacionIzquierda(nodo.getIzquierdo()));

        NodoAVL aux = rotacionDerecha(nodo);

        return aux;
    }

    private NodoAVL rotacionDerIzq(NodoAVL nodo) {
        nodo.setDerecho(rotacionDerecha(nodo.getDerecho()));
        NodoAVL aux = rotacionIzquierda(nodo);

        return aux;
    }

    private int balance(NodoAVL nodo) {
        return (nodo.getIzquierdo() != null ? nodo.getIzquierdo().getAltura() : -1) -
                (nodo.getDerecho() != null ? nodo.getDerecho().getAltura() : -1);
    }

    @Override
    public String toString() {
        String res = toStringAux(this.raiz);
        return res;
    }

    private String toStringAux(NodoAVL nodo) {
        String res = "";

        if (nodo != null) {
            res += nodo.getElem() + "(alt: " + nodo.getAltura() + ")" + " HI: "
                    + (nodo.getIzquierdo() != null ? nodo.getIzquierdo().getElem() : "-") +
                    " HD: " + (nodo.getDerecho() != null ? nodo.getDerecho().getElem() : "-") + "\n";
            res += toStringAux(nodo.getIzquierdo());
            res += toStringAux(nodo.getDerecho());
        }

        return res;
    }

    public Lista listar() {
        Lista lis = new Lista();
        listarAux(this.raiz, lis);
        return lis;
    }

    private void listarAux(NodoAVL nodoArbol, Lista lis) {
        if (nodoArbol != null) {
            listarAux(nodoArbol.getIzquierdo(), lis);
            lis.insertar(nodoArbol.getElem(), lis.longitud() + 1);
            listarAux(nodoArbol.getDerecho(), lis);
        }
    }

    public boolean pertenece(Comparable elem) {
        return this.raiz != null ? perteneceAux(this.raiz, elem) : false;
    }

    private boolean perteneceAux(NodoAVL nodo, Comparable elem) {
        boolean exito = false;
        if (nodo != null) {
            if (elem.compareTo(nodo.getElem()) == 0) {
                exito = true;
            } else {
                if (elem.compareTo(nodo.getElem()) < 0) {
                    if (nodo.getIzquierdo() != null)
                        exito = perteneceAux(nodo.getIzquierdo(), elem);
                } else {
                    if (nodo.getDerecho() != null)
                        exito = perteneceAux(nodo.getDerecho(), elem);
                }
            }
        }
        return exito;
    }

    public Comparable obtener(Comparable elem) {
        return obtenerAux(this.raiz, elem);
    }

    private Comparable obtenerAux(NodoAVL nodo, Comparable elem) {
        Comparable res = null;
        if (nodo != null) {
            if (elem.compareTo(nodo.getElem()) == 0) {
                res = nodo.getElem();
            } else if (elem.compareTo(nodo.getElem()) < 0) {
                if (nodo.getIzquierdo() != null)
                    res = obtenerAux(nodo.getIzquierdo(), elem);
            } else {
                if (nodo.getDerecho() != null)
                    res = obtenerAux(nodo.getDerecho(), elem);
            }
        }
        return res;
    }

    public Comparable minimoElem() {
        Comparable res = null;
        NodoAVL nodo = this.raiz;
        if (nodo != null) {
            while (nodo.getIzquierdo() != null) {
                nodo = nodo.getIzquierdo();
            }
            res = nodo.getElem();
        }
        return res;
    }

    public Comparable maximoElem() {
        Comparable res = null;
        NodoAVL nodo = this.raiz;
        if (nodo != null) {
            while (nodo.getDerecho() != null) {
                nodo = nodo.getDerecho();
            }
            res = nodo.getElem();
        }
        return res;
    }

    public Lista listarRango(Comparable minimoElem, Comparable maximoElem) {
        Lista lis = new Lista();
        listarRangoAux(lis, this.raiz, minimoElem, maximoElem);
        return lis;
    }

    private void listarRangoAux(Lista l, NodoAVL nodo, Comparable min, Comparable max) {
        if (nodo != null) {
            if (min.compareTo(nodo.getElem()) < 0) {
                listarRangoAux(l, nodo.getIzquierdo(), min, max);
            }
            if (min.compareTo(nodo.getElem()) <= 0 && max.compareTo(nodo.getElem()) >= 0) {
                l.insertar(nodo.getElem(), l.longitud() + 1);
            }
            if (max.compareTo(nodo.getElem()) > 0) {
                listarRangoAux(l, nodo.getDerecho(), min, max);
            }
        }
    }

    public void vaciar() {
        this.raiz = null;
    }

    public ArbolAVL clone() {
        ArbolAVL nuevoArbol = new ArbolAVL();
        nuevoArbol.raiz = clonarArbol(this.raiz);
        return nuevoArbol;
    }

    private NodoAVL clonarArbol(NodoAVL nodo) {
        NodoAVL nuevoNodo = null;
        if (nodo != null) {
            nuevoNodo = new NodoAVL(nodo.getElem(), null, null);
            nuevoNodo.setIzquierdo(clonarArbol(nodo.getIzquierdo()));
            nuevoNodo.setDerecho(clonarArbol(nodo.getDerecho()));
            nuevoNodo.recalcularAltura();
        }
        return nuevoNodo;
    }

}
