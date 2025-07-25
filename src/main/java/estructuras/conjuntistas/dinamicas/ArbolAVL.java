package estructuras.conjuntistas.dinamicas;
import estructuras.lineales.dinamicas.Lista;

@SuppressWarnings({"all"})
public class ArbolAVL {

    //TODO: Eliminar

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

    public boolean eliminar (Comparable elem){
        // elem elemento a eliminar, se envía raíz y un nodo padre nulo
        boolean res = false;
        if (this.raiz != null) {
            res = eliminarAux(this.raiz, null, elem);
            this.raiz = eliminarRebalancear(this.raiz, elem);
        }
        return res;
    }

    private boolean eliminarAux (NodoAVL nodo, NodoAVL padre, Comparable elem){
         // nodo es el nodo actual
        // padre es el padre del nodo actual
        // elem elemento a eliminar
        boolean res = false;
        if (nodo != null) {
            if (elem.compareTo(nodo.getElem()) < 0) {
                // si X es menor al elemento actual
                res = eliminarAux(nodo.getIzquierdo(), nodo, elem);
            } else if (elem.compareTo(nodo.getElem()) > 0) {
                // si X es mayor al elemento actual
                res = eliminarAux(nodo.getDerecho(), nodo, elem);
            } else {
                // X es el elemento actual
                res = eliminarCasos(nodo, padre);
            }
        }
        return res;
    }

    private boolean eliminarCasos(NodoAVL nodo, NodoAVL padre) {
        // nodo es el nodo a eliminar y padre es el padre de este.
        boolean res = false;
        NodoAVL hi = nodo.getIzquierdo();
        NodoAVL hd = nodo.getDerecho();
        if (hi == null && hd == null) {
            // Caso 1, sin hijos
            if (padre == null) {
                // la raiz no tiene hijos
                this.raiz = null;
            } else if (padre.getIzquierdo() == nodo) {
                // borra el HI del padre
                padre.setIzquierdo(null);
            } else {
                // borra el HD del padre
                padre.setDerecho(null);
            }
            res = true;
        } else if (hi == null || hd == null) {
            // Caso 2, 1 hijo
            NodoAVL hijoExistente = (hi != null) ? hi : hd;
            if (padre == null){
                this.raiz = hijoExistente;
            } else if (padre.getIzquierdo() == nodo) {
                // Si el nodo a eliminar es el HI del padre, lo reemplazamos con su HI.
                padre.setIzquierdo(hijoExistente);
            } else {
                // si el nodo a eliminar es el HD del padre, lo reemplazamos con su HD.
                padre.setDerecho(hijoExistente);
            }
            res = true;
        } else {
            // Caso 3, 2 hijos.
            // Candidato A, máximo de la rama izquierda.
            NodoAVL candidato = nodo.getIzquierdo();
            NodoAVL padreCandidato = nodo;
            while (candidato.getDerecho() != null) {
                // se busca el nodo más a la derecha del subárbol izquierdo de nodo.
                padreCandidato = candidato;
                candidato = candidato.getDerecho();
            }
            if (padre == null) {
                // Si el nodo a eliminar es la raiz, la nueva raíz será candidato.
                this.raiz = candidato;
            } else if (padre.getIzquierdo() == nodo) {
                // Si el nodo a eliminar es el hijo izquierdo del padre, lo reemplazamos con el
                // candidato
                padre.setIzquierdo(candidato);
            } else {
                // Si el nodo a eliminar es el hijo derecho del padre, lo reemplazamos con el
                // candidato
                padre.setDerecho(candidato);
            }
            candidato.setDerecho(nodo.getDerecho());
            // Establece el HD del candidato como el hijo derecho del nodo a eliminar.
            if (padreCandidato != nodo) {
                padreCandidato.setDerecho(candidato.getIzquierdo());

                candidato.setIzquierdo(nodo.getIzquierdo());
            }
            res = true;
        }
        return res;
    }

     private NodoAVL eliminarRebalancear(NodoAVL nodo, Comparable x) {
        if (nodo != null) {
            int cmp = x.compareTo(nodo.getElem());
            if (cmp < 0) {
                nodo.setIzquierdo(eliminarRebalancear(nodo.getIzquierdo(), x));
            } else if (cmp > 0) {
                nodo.setDerecho(eliminarRebalancear(nodo.getDerecho(), x));
            }
            nodo.recalcularAltura();
            int balance = balance(nodo);
            if (Math.abs(balance) > 1) {
                nodo = balancear(nodo, balance);
            }
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

    @Override
    public String toString(){
        String res = toStringAux(this.raiz);
        return res;
    }

    private String toStringAux(NodoAVL nodo){
        String res = "";

        if (nodo != null){
            res += "Nodo: " + nodo.getElem() + " HI: "  + (nodo.getIzquierdo() != null ? nodo.getIzquierdo().getElem() : " ") +
                    " HD: " + (nodo.getDerecho() != null ? nodo.getDerecho().getElem() : " ") + "\n";
            res += toStringAux(nodo.getIzquierdo());
            res += toStringAux(nodo.getDerecho());
        }

        return res;
    }


    //TODO: Listar ciudades en orden alfabetico
    public Lista listar() {//SE RECORRE INORDEN
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

    public Comparable minimoElem(){
        Comparable res = null;
        NodoAVL nodo = this.raiz;
        if (nodo!=null){
            while (nodo.getIzquierdo()!=null){
                nodo = nodo.getIzquierdo();
            }
            res = nodo.getElem();
        }
        return res;
    }

    public Comparable maximoElem(){
        Comparable res = null;
        NodoAVL nodo = this.raiz;
        if (nodo!=null){
            while (nodo.getDerecho()!=null){
                nodo = nodo.getDerecho();
            }
            res = nodo.getElem();
        }
        return res;
    }

    public Lista listarRango(Comparable minimoElem, Comparable maximoElem){
        Lista lis = new Lista();
        listarRangoAux(lis, this.raiz, minimoElem, maximoElem);
        return lis;
    }
    private void listarRangoAux (Lista l, NodoAVL nodo, Comparable min, Comparable max){
        if (nodo!=null){
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

    public void vaciar(){
        this.raiz = null;
    }
    public ArbolAVL clone(){
        ArbolAVL nuevoArbol = new ArbolAVL();
        // Clona al arbol creando nuevos nodos con un recorrido en preorden
        nuevoArbol.raiz = clonarArbol(this.raiz);
        return nuevoArbol;
    }
    
    private NodoAVL clonarArbol(NodoAVL nodo){
        NodoAVL nuevoNodo = null;
        if (nodo != null) { // Caso base: el nodo es nulo
            // Caso recursivo: crea nuevos nodos y les agrega su hijo izquierdo y derecho mediante llamadas recursivas
            // con un recorrido en preorden
            nuevoNodo = new NodoAVL(nodo.getElem(), null, null);
            nuevoNodo.setIzquierdo(clonarArbol(nodo.getIzquierdo()));
            nuevoNodo.setDerecho(clonarArbol(nodo.getDerecho()));
        }
        return nuevoNodo;
    }



}
