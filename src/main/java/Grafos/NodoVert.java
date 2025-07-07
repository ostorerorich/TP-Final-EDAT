package Grafos;

public class NodoVert {
    
    // Atributos
    private Object elem;
    private NodoVert sigVertice;
    private NodoAdy primerAdy;
    
    // Constructor
    public NodoVert(Object unElem, NodoVert unSigVertice, NodoAdy unPrimerAdy){
        this.elem = unElem;
        this.sigVertice = unSigVertice;
        this.primerAdy = unPrimerAdy;
    }
    
    // Visualizadores
    public Object getElem(){
        return this.elem;
    }
    
    public NodoVert getSigVertice(){
        return this.sigVertice;
    }
    
    public NodoAdy getPrimerAdy(){
        return this.primerAdy;
    }
    
    // Modificadores
    public void setElem(Object unElem){
        this.elem = unElem;
    }
    
    public void setSigVertice(NodoVert unSigVertice){
        this.sigVertice = unSigVertice;
    }
    
    public void setPrimerAdy(NodoAdy unPrimerAdy){
        this.primerAdy = unPrimerAdy;
    }
    
}
