package estructuras.lineales.dinamicas;

public class Cola {
    
    // Atributos
    private Nodo frente;
    private Nodo fin;
    
    // Constructor
    public Cola(){
        
        this.frente = null;
        this.fin = null;
        
    }
    
    public boolean poner(Object unElemento){
        
        Nodo nuevoNodo;
        
        nuevoNodo = new Nodo(unElemento, null);
        
        if (this.esVacia()) {
            
            this.frente = nuevoNodo;
            
        } else {
            
            this.fin.setEnlace(nuevoNodo);
            
        }
        
        this.fin = nuevoNodo;
        
        return true;
        
    }
    
    public boolean sacar(){
        
        boolean elementoQuitado;
        
        elementoQuitado = false;
        
        if (!this.esVacia()) {
            
            if (this.frente.getEnlace() == null) {
                
                this.frente = null;
                this.fin = null;
                
            } else {
                
                this.frente = this.frente.getEnlace();
                
            }
            
            elementoQuitado = true;
            
        }
        
        return elementoQuitado;
        
    }
    
    public Object obtenerFrente() {
        
        Object elementoFrente;
        
        elementoFrente = null;
        
        if (!this.esVacia()) {
            
            elementoFrente = this.frente.getElemento();
            
        }
        
        return elementoFrente;
        
    }
    
    public void vaciar(){
        
        this.frente = null;
        this.fin = null;
        
    }
    
    public boolean esVacia(){
        
        boolean colaVacia;
        
        colaVacia = false;
        
        if (this.frente == null && this.fin == null) {
            
            colaVacia = true;
            
        }
        
        return colaVacia;
        
    }
    
    public Cola clone(){
        
        Cola colaClonada;
        Nodo nodoAuxOriginal, nuevoNodo;
        
        colaClonada = new Cola();
        
        if (!this.esVacia()) {
            
            nodoAuxOriginal = this.frente;
            
            colaClonada.frente = new Nodo(nodoAuxOriginal.getElemento(), null);
            colaClonada.fin = colaClonada.frente;
                
            while (nodoAuxOriginal.getEnlace() != null) {
                
                nodoAuxOriginal = nodoAuxOriginal.getEnlace();
                nuevoNodo = new Nodo(nodoAuxOriginal.getElemento(), null);
                    
                colaClonada.fin.setEnlace(nuevoNodo);
                colaClonada.fin = nuevoNodo;
                
            }
            
        }
        
        return colaClonada;
        
    }
    
    public String toString(){
        
        String elementos;
        Nodo nodoAux;
        
        elementos = "[";
        nodoAux = this.frente;
        
        if (!this.esVacia()) {
            
            while (nodoAux != null) {
                
                elementos += nodoAux.getElemento();
                nodoAux = nodoAux.getEnlace();
                
                if (nodoAux != null) {
                    
                    elementos += ", ";
                    
                }
                
            }
            
            elementos += "]";
            
        } else {
            
            elementos = "La cola esta vacia";
            
        }
        
        return elementos;
        
    }
    
}
