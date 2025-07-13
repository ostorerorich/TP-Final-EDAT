package estructuras.grafos;

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
    
    public boolean eliminarVertice(Object elem){
        
        NodoVert nodoVertAux, nodoVertEncontrado, nodoVertAnterior;
        boolean verticeEliminado = false;
        
        nodoVertAux = this.inicio;
        nodoVertEncontrado = null;
        nodoVertAnterior = null;
        
        // Busca el vertice que coincida con el elemento
        while (nodoVertAux != null && nodoVertEncontrado == null) {
            if (nodoVertAux.getElem().equals(elem)) {
                nodoVertEncontrado = nodoVertAux;
            } else {
                nodoVertAnterior = nodoVertAux;
                nodoVertAux = nodoVertAux.getSigVertice();
            }
        }
        
        if (nodoVertEncontrado != null) {
            // Si el vertice a eliminar existe en el grafo, se lo elimina a él y a todos sus arcos adyacentes
            eliminarVerticeAux(nodoVertEncontrado, nodoVertAnterior);
            eliminarArcosAdy(nodoVertEncontrado);
            verticeEliminado = true;
        }
        
        return verticeEliminado;
        
    }
    
    private void eliminarVerticeAux(NodoVert nodo, NodoVert nodoAnterior){
        
        // Elimina el vertice dependiendo de si es el primero en la lista o no
        if (this.inicio == nodo) {
            this.inicio = this.inicio.getSigVertice();
        } else {
            nodoAnterior.setSigVertice(nodo.getSigVertice());
        }
        
    }
    
    private void eliminarArcosAdy(NodoVert nodo){
        
        NodoVert nodoVertAux = this.inicio;
        
        while (nodoVertAux != null) {
            // Busca en cada vertice del grafo si tienen un arco desde ellos al nodo eliminado
            eliminarArcoAux(nodoVertAux, nodo.getElem());
            
            nodoVertAux = nodoVertAux.getSigVertice();
        }
        
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
            // Si ambos nodos existen, verifica si puede crear un nuevo arco entre ambos
            arcoInsertado = ubicarArco(nodoOrigen, nodoDestino, etiqueta);
        }
        
        return arcoInsertado;
        
    }
    
    private boolean ubicarArco(NodoVert nodoOrigen, NodoVert nodoDestino, Object etiqueta) {
        
        boolean arcoInsertado, verticeRepetido;
        NodoAdy nodoAdyAux;
        
        arcoInsertado = false;
        verticeRepetido = false;

        if (nodoOrigen.getPrimerAdy() == null) {
            // Si el nodo origen no tiene nodos adyacentes, inserta el nodo destino como su primer adyacente
            nodoOrigen.setPrimerAdy(new NodoAdy(nodoDestino, null, etiqueta));
            arcoInsertado = true;
        } else {
            nodoAdyAux = nodoOrigen.getPrimerAdy();
                
            while (nodoAdyAux != null && !arcoInsertado && !verticeRepetido) {
                // Si no existe ya un arco entre el nodo origen y el nodo destino, busca el último nodo adyacente del
                // nodo origen para setear su siguiente adyacente al nodo destino
                if (nodoAdyAux.getVertice() == nodoDestino) {
                    verticeRepetido = true;
                } else {
                    if (nodoAdyAux.getSigAdyacente() == null) {
                        nodoAdyAux.setSigAdyacente(new NodoAdy(nodoDestino, null, etiqueta));
                        arcoInsertado = true;
                    }
                }
                
                nodoAdyAux = nodoAdyAux.getSigAdyacente();
            }
        }
            
        return arcoInsertado;
    
    }
    
    ///////////////////////////////////////
    
    public boolean eliminarArco(Object origen, Object destino){
        
        NodoVert nodoOrigen, nodoDestino, nodoVertAux;
        boolean arcoEliminado = false;
        
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
            arcoEliminado = eliminarArcoAux(nodoOrigen, destino);
        }
        
        return arcoEliminado;
        
    }
    
    private boolean eliminarArcoAux(NodoVert origen, Object destino){
        
        NodoAdy nodoAdyAux, nodoAdyAnterior;
        boolean arcoEliminado = false;
        
        nodoAdyAux = origen.getPrimerAdy();
        nodoAdyAnterior = null;
        
        while (nodoAdyAux != null && !arcoEliminado) {
            // Busca el arco desde el nodo origen al nodo destino para eliminarlo
            if (nodoAdyAux.getVertice().getElem().equals(destino)) {
                // Elimina el arco dependiendo de si es el primero en la lista o no
                if (nodoAdyAnterior == null) {
                    origen.setPrimerAdy(nodoAdyAux.getSigAdyacente());
                } else {
                    nodoAdyAnterior.setSigAdyacente(nodoAdyAux.getSigAdyacente());
                }
                
                // Como cada par de vertices solo puede tener un arco entre ellos con el mismo sentido, si se
                // encontró el que tiene que eliminar, deja de revisar los demás arcos del vertice
                arcoEliminado = true;
            }
            
            nodoAdyAnterior = nodoAdyAux;
            nodoAdyAux = nodoAdyAux.getSigAdyacente();
        }
        
        return arcoEliminado;
        
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
    
    private boolean buscarArco(NodoVert origen, Object destino){
        
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
    
    private boolean buscarCamino(NodoVert origen, Object destino, Lista lista){
        
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
    
    public Lista caminoMasCorto(Object origen, Object destino){
        
        Lista caminoActual, caminoMasCorto;
        NodoVert nodoOrigen, nodoDestino, nodoVertAux;
        
        caminoMasCorto = new Lista();
        caminoActual = new Lista();
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
            // Si ambos nodos existen, verifica cual (si hay) es el camino más corto entre ambos
            caminoMasCorto = buscarCaminoMasCorto(nodoOrigen, destino, caminoActual, caminoMasCorto);
            
        }
        
        return caminoMasCorto;
        
    }
    
    private Lista buscarCaminoMasCorto(NodoVert origen, Object destino, Lista caminoActual, Lista caminoMasCorto){
        
        NodoVert nodoVertAux;
        NodoAdy nodoAdyAux;
        boolean buscar = true;
        
        // Ingresa el elemento del nodo en la lista para ir formando el camino
        caminoActual.insertar(origen.getElem(), caminoActual.longitud()+1);
        
        if (origen.getElem().equals(destino)) {
            // Caso base: se encontró un camino entre el nodo origen y el nodo destino
            if (caminoActual.longitud() < caminoMasCorto.longitud() || caminoMasCorto.longitud() <= 0) {
                // El nuevo camino es más corto que el último camino más corto encontrado o es el primero
                caminoMasCorto = caminoActual.clone();
            }
        } else {
            nodoAdyAux = origen.getPrimerAdy();
            
            while (nodoAdyAux != null && buscar) {
                // Caso recursivo: busca entre los nodos adyacentes del nodo origen y sus derivados si existe un
                // camino entre él y el nodo destino
                nodoVertAux = nodoAdyAux.getVertice();
                
                if (caminoActual.localizar(nodoVertAux.getElem()) < 0) {
                    // Si el nuevo nodo a visitar no fue visitado antes, se hace una llamada recursiva
                    caminoMasCorto = buscarCaminoMasCorto(nodoVertAux, destino, caminoActual, caminoMasCorto);
                    caminoActual.eliminar(caminoActual.longitud()); // Elimina el último elemento de la lista
                }
                
                // Verifica si sigue buscando por ese nodo
                buscar = seguirBuscandoCamino(caminoActual.longitud(), caminoMasCorto.longitud());
                
                nodoAdyAux = nodoAdyAux.getSigAdyacente();
            }
            
        }
        
        return caminoMasCorto;
        
    }
    
    private boolean seguirBuscandoCamino(int longCaminoActual, int longCaminoMasCorto){
        
        boolean buscar = true;

        // Si se encontró al menos un camino del nodo origen al nodo destino y el camino actual tiene al menos un nodo
        // menos que el camino más corto encontrado, entonces deja de buscar un camino por ese nodo ya que la lista
        // quedará igual o más larga que el camino más corto encontrado actualmente
        if (longCaminoMasCorto >= 1 && longCaminoActual+1 >= longCaminoMasCorto) {
            buscar = false;
        }
        
        return buscar;
                
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
        
        if (!this.esVacio()) {
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
        } else {
            elementos = "El grafo esta vacio";
        }
        
        return elementos;
        
    }
    
}
