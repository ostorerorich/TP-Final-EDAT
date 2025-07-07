package grafos;
import estructuras.lineales.dinamicas.Lista;

public class Grafo {
    
    // Atributos
    private NodoVert inicio;
    
    // Constructor
    public Grafo(){
        this.inicio = null;
    }
    
    // Métodos
    public boolean insertarVertice(Object elem){
        
        boolean verticeInsertado = false;
        NodoVert nodoVertAux;
        
        if (this.inicio == null) {
            // El grafo está vacío
            this.inicio = new NodoVert(elem, null, null);
            verticeInsertado = true;
        } else {
            nodoVertAux = this.inicio;
                    
            // Busca el último vértice de la lista para setear su siguiente vértice con el nuevo, siempre y cuando
            // el elemento a insertar no esté ya en el grafo
            while (nodoVertAux != null && !nodoVertAux.getElem().equals(elem)) {
                if (nodoVertAux.getSigVertice() == null) {
                    // Vértice insertado
                    nodoVertAux.setSigVertice(new NodoVert(elem, null, null));
                    verticeInsertado = true;
                }
                
                nodoVertAux = nodoVertAux.getSigVertice();
            }
        }
        
        return verticeInsertado;
        
    }
    
    ///////////////////////////////////////
    
    public boolean insertarArco(Object origen, Object destino, Object etiqueta){
        
        boolean arcoInsertado = false;
        NodoVert nodoOrigen, nodoDestino, nodoVertAux;
        
        nodoOrigen = null;
        nodoDestino = null;
        nodoVertAux = this.inicio;
        
        while ((nodoOrigen == null || nodoDestino == null) && nodoVertAux != null) {
            // Busca el nodo origen y el nodo destino en el grafo
            if (nodoVertAux.getElem().equals(origen)) {
                nodoOrigen = nodoVertAux;
            }
            
            if (nodoVertAux.getElem().equals(destino)){
                nodoDestino = nodoVertAux;
            }
            
            nodoVertAux = nodoVertAux.getSigVertice();
        }
        
        if (nodoOrigen != null && nodoDestino != null) {
            // Si ambos nodos existen, crea un nuevo arco entre ambos
            arcoInsertado = ubicarArco(nodoOrigen, nodoDestino, etiqueta);
        }
        
        return arcoInsertado;
        
    }
    
    public boolean ubicarArco(NodoVert nodoOrigen, NodoVert nodoDestino, Object etiqueta) {
        
        boolean arcoInsertado = false;
        NodoAdy nodoAdyAux;

        if (nodoOrigen.getPrimerAdy() == null) {
            // Si el nodo origen no tiene nodos adyacentes, inserta el nodo destino como su primer adyacente
            nodoOrigen.setPrimerAdy(new NodoAdy(nodoDestino, null, etiqueta));
            arcoInsertado = true;
        } else {
            // Busca el último nodo adyacente del nodo origen para setear su siguiente adyacente al nodo destino.
            // Dependiendo del dominio, la etiqueta del arco a insertar puede o no repertirse en los arcos del nodo origen
            nodoAdyAux = nodoOrigen.getPrimerAdy();
                
            while (nodoAdyAux != null && !arcoInsertado) {
                if (nodoAdyAux.getSigAdyacente() == null) {
                    nodoAdyAux.setSigAdyacente(new NodoAdy(nodoDestino, null, etiqueta));
                    arcoInsertado = true;
                }
                
                nodoAdyAux = nodoAdyAux.getSigAdyacente();
            }
        }
            
        return arcoInsertado;
    
    }

    ///////////////////////////////////////
    
    public boolean existeVertice(Object elem){
        
        NodoVert nodoVertAux = this.inicio;
        boolean verticeEncontrado = false;
        
        // Busca el vertice que coincida con el elemento
        while (nodoVertAux != null && !verticeEncontrado) {
            if (nodoVertAux.getElem().equals(elem)) {
                verticeEncontrado = true;
            }
            
            nodoVertAux = nodoVertAux.getSigVertice();
        }
        
        return verticeEncontrado;
        
    }
    
    ///////////////////////////////////////
    
    public boolean existeArco(Object origen, Object destino){
        
        NodoVert nodoOrigen, nodoDestino, nodoVertAux;
        boolean arcoEncontrado = false;
        
        nodoOrigen = null;
        nodoDestino = null;
        nodoVertAux = this.inicio;
        
        while ((nodoOrigen == null || nodoDestino == null) && nodoVertAux != null) {
            // Busca el nodo origen y el nodo destino en el grafo
            if (nodoVertAux.getElem().equals(origen)) {
                nodoOrigen = nodoVertAux;
            }
            
            if (nodoVertAux.getElem().equals(destino)){
                nodoDestino = nodoVertAux;
            }
            
            nodoVertAux = nodoVertAux.getSigVertice();
        }
        
        if (nodoOrigen != null && nodoDestino != null) {
            // Si ambos nodos existen, verifica si existe un arco entre el nodo origen al nodo destino
            arcoEncontrado = buscarArco(nodoOrigen, destino);
            
        }
        
        return arcoEncontrado;
        
    }
    
    public boolean buscarArco(NodoVert origen, Object destino){
        
        NodoAdy nodoAdyAux = origen.getPrimerAdy();
        boolean existeArco = false;
        
        while (nodoAdyAux != null && !existeArco) {
            // Busca entre los nodos adyacentes del nodo origen para ver si uno de ellos coincide con el nodo destino,
            // es decir, que exista un arco entre ambos nodos
            if (nodoAdyAux.getVertice().getElem().equals(destino)) {
                existeArco = true;
            }
            
            nodoAdyAux = nodoAdyAux.getSigAdyacente();
        }
        
        return existeArco;
        
    }
    
    ///////////////////////////////////////
    
    public boolean existeCamino(Object origen, Object destino){
        
        boolean caminoEncontrado = false;
        Lista lista;
        NodoVert nodoOrigen, nodoDestino, nodoVertAux;
        
        nodoOrigen = null;
        nodoDestino = null;
        nodoVertAux = this.inicio;
        
        while ((nodoOrigen == null || nodoDestino == null) && nodoVertAux != null) {
            // Busca el nodo origen y el nodo destino en el grafo
            if (nodoVertAux.getElem().equals(origen)) {
                nodoOrigen = nodoVertAux;
            }
            
            if (nodoVertAux.getElem().equals(destino)){
                nodoDestino = nodoVertAux;
            }
            
            nodoVertAux = nodoVertAux.getSigVertice();
        }
        
        if (nodoOrigen != null && nodoDestino != null) {
            // Si ambos nodos existen, verifica si existe un camino entre ambos
            lista = new Lista();
            caminoEncontrado = buscarCamino(nodoOrigen, destino, lista);
        }
        
        return caminoEncontrado;
        
    }
    
    public boolean buscarCamino(NodoVert origen, Object destino, Lista lista){
        
        boolean caminoEncontrado = false;
        NodoAdy nodoAdyAux;
        
        if (origen.getElem().equals(destino)) {
            // Caso base: se encontró un camino entre el nodo origen y el nodo destino
            caminoEncontrado = true;
        } else {
            lista.insertar(origen.getElem(), lista.longitud() + 1);
            nodoAdyAux = origen.getPrimerAdy();
            
            while (nodoAdyAux != null && !caminoEncontrado) {
                // Busca entre todos los nodos adyacentes del nodo origen y sus derivados, si existe un camino entre él
                // y el nodo destino
                if (lista.localizar(nodoAdyAux.getVertice().getElem()) < 0) {
                    // Caso recursivo: si el nuevo nodo a visitar no fue visitado antes, se hace una llamada recursiva
                    caminoEncontrado = buscarCamino(nodoAdyAux.getVertice(), destino, lista);
                }
                
                nodoAdyAux = nodoAdyAux.getSigAdyacente();
            }
        }
        
        return caminoEncontrado;
        
    }
    
    ///////////////////////////////////////
    
    public boolean esVacio(){
        // El grafo está vacío
        return this.inicio == null;
    }
    
    ///////////////////////////////////////
    
    public void vaciar(){
        // Como Java utiliza un Sistema Garbage Collector, elimina en memoria todas las posiciones a las cuales ya
        // no se tiene acceso
        this.inicio = null;
    }
    
    ///////////////////////////////////////
    
    @Override
    public String toString(){
        
        String elementos;
        NodoVert nodoVertAux = this.inicio;
        NodoAdy nodoAdyAux;
        
        elementos = "";
        
        while (nodoVertAux != null) {
            // Ingresa el elemento del nodo vertice a la cadena
            elementos += nodoVertAux.getElem().toString();
            nodoAdyAux = nodoVertAux.getPrimerAdy();
            
            if (nodoAdyAux != null) {
                // Ingresa todos los nodos adyacentes del nodo vertice actual a la cadena
                elementos += ": ";
                
                while (nodoAdyAux != null) {
                    elementos += nodoAdyAux.getVertice().getElem().toString() + " ";
                    nodoAdyAux = nodoAdyAux.getSigAdyacente();
                }
            } else {
                // El nodo vertice no tiene nodos adyacentes
                elementos += ": -";
            }
            
            // Siguiente nodo vertice
            elementos += "\n";
            nodoVertAux = nodoVertAux.getSigVertice();
        }
        
        return elementos;
        
    }
    
}
