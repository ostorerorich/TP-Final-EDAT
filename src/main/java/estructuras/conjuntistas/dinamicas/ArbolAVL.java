package estructuras.conjuntistas.dinamicas;

public class ArbolAVL {

    private NodoAVL raiz;

    public ArbolAVL(){
        this.raiz = null;
    }


    public boolean insertar(Comparable elem){
        boolean res = false;

        if(this.raiz == null){
            this.raiz = new NodoAVL(elem);
            res = true;
        }else{
            NodoAVL resN = insertarAux(this.raiz, elem);
            if(resN!= null){
                this.raiz = resN;
                res = true;
            }
        }

        return res;
    }

    private NodoAVL insertarAux(NodoAVL nodo, Comparable elem){

        if(elem.compareTo(nodo.getElem())<0){
            if(nodo.getIzquierdo() == null){
                nodo.setIzquierdo(new NodoAVL(elem));
            }else{
                nodo.setIzquierdo(insertarAux(nodo.getIzquierdo(), elem));
            }
        }else if (elem.compareTo(nodo.getElem()) > 0 ){
            if(nodo.getDerecho() == null){
                nodo.setDerecho(new NodoAVL(elem));
            }else{
                nodo.setDerecho(insertarAux(nodo.getDerecho(), elem));
            }
        }

        nodo.recalcularAltura();
        int balance = balance(nodo);
        if(Math.abs(balance)>1){
            nodo = balancear(nodo, balance);
        }

        return nodo;
    }

    private NodoAVL balancear(NodoAVL nodo, int balance){
        if(balance > 1){
            if(balance(nodo.getIzquierdo())>= 0){
                nodo = rotacionDerecha(nodo);
            }else{
                nodo = rotacionIzqDer(nodo);
            }
        }else {

            if(balance(nodo.getDerecho()) <= 0){
                nodo = rotacionIzquierda(nodo);
            }else {
                nodo = rotacionDerIzq(nodo);
            }

        }
        nodo.recalcularAltura();
        return nodo;
    }

    private NodoAVL rotacionDerecha(NodoAVL nodo){

        // 24

        NodoAVL hi = nodo.getIzquierdo(); // hi: 22
        NodoAVL temp = hi.getDerecho(); // hd: null

        hi.setDerecho(nodo); // 22.setDer(24)
        nodo.setIzquierdo(temp); // 24.setIz(null)

        nodo.recalcularAltura();
        hi.recalcularAltura();

        // hi: 22 hi:18 hd: 24

        //   22
        //  /  \
        // 18   24

        return hi;
    }

    private NodoAVL rotacionIzquierda(NodoAVL nodo){
        // nodo: 18

        NodoAVL hd = nodo.getDerecho(); // hd: 22
        NodoAVL temp = hd.getIzquierdo(); // temp: null

        hd.setIzquierdo(nodo); // 22.setIzq(18)
        nodo.setDerecho(temp); // 18.setDer(null)

        nodo.recalcularAltura(); //
        hd.recalcularAltura();  //

        return hd; // 22 hi: 18 hd: null
        //  22
        // /  \
        //18  null
    }

    private NodoAVL rotacionIzqDer(NodoAVL nodo){
        nodo.setIzquierdo(rotacionIzquierda(nodo.getIzquierdo()));

        NodoAVL aux = rotacionDerecha(nodo);

        return aux;
    }

    private NodoAVL rotacionDerIzq(NodoAVL nodo){
        nodo.setDerecho(rotacionDerecha(nodo.getDerecho()));
        NodoAVL aux = rotacionIzquierda(nodo);

        return aux;
    }

    private int balance(NodoAVL nodo) {
        return (nodo.getIzquierdo() != null ? nodo.getIzquierdo().getAltura() : -1) -
                (nodo.getDerecho() != null ? nodo.getDerecho().getAltura() : -1);
    }
}
