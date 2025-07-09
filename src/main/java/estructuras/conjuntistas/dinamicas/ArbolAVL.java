package estructuras.conjuntistas.dinamicas;

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

    private NodoAVL insertarAux(NodoAVL nodo, Comparable elem) {

        if (elem.compareTo(nodo.getElem()) < 0) {
            if (nodo.getIzquierdo() == null) {
                nodo.setIzquierdo(new NodoAVL(elem));
            } else {
                nodo.setIzquierdo(insertarAux(nodo.getIzquierdo(), elem));
            }
        } else if (elem.compareTo(nodo.getElem()) > 0) {
            if (nodo.getDerecho() == null) {
                nodo.setDerecho(new NodoAVL(elem));
            } else {
                nodo.setDerecho(insertarAux(nodo.getDerecho(), elem));
            }
        }

        nodo.recalcularAltura();
        int balance = balance(nodo);
        if (Math.abs(balance) > 1) {
            nodo = balancear(nodo, balance);
        }

        return nodo;
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
}
