package estructuras.conjuntistas.dinamicas;

public class NodoAVL {
    private Comparable elem;
    private int altura;
    private NodoAVL izquierdo;
    private NodoAVL derecho;

    public NodoAVL(Comparable elem, NodoAVL izquierdo, NodoAVL derecho){
        this.elem = elem;
        this.izquierdo = izquierdo;
        this.derecho =  derecho;
        this.altura = 0;
    }

    public NodoAVL(Comparable elem){
        this.elem = elem;
        this.altura = 0;
    }

    public Comparable getElem(){
        return this.elem;
    }

    public void setElem(Comparable elem){
        this.elem = elem;
    }

    public int getAltura(){
        return this.altura;
    }

    public void recalcularAltura(){
        int alturaIzq = this.izquierdo == null ? -1 : this.getIzquierdo().getAltura();
        int alturaDer = this.derecho == null ? -1 : this.getDerecho().getAltura();
        this.altura =  Math.max(alturaDer, alturaIzq) +1;
    }

    public NodoAVL getIzquierdo(){
        return this.izquierdo;
    }

    public NodoAVL getDerecho(){
        return this.derecho;
    }

    public void setIzquierdo(NodoAVL nodo){
        this.izquierdo = nodo;
    }

    public void setDerecho(NodoAVL nodo){
        this.derecho = nodo;
    }


}
